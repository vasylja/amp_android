package com.ua.viktor.amp.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.ua.viktor.amp.R;
import com.ua.viktor.amp.activity.DecoderActivity;
import com.ua.viktor.amp.activity.TestActivity;
import com.ua.viktor.amp.dao.Choice;
import com.ua.viktor.amp.dao.ChoiceDao;
import com.ua.viktor.amp.dao.DaoMaster;
import com.ua.viktor.amp.dao.DaoSession;
import com.ua.viktor.amp.dao.Question;
import com.ua.viktor.amp.dao.QuestionDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getName();
    private ImageButton mQrButton;
    private Button mEnterButton;
    private boolean isEnterValid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mQrButton = (ImageButton) view.findViewById(R.id.imageButton);
        mEnterButton = (Button) view.findViewById(R.id.button);


        final EditText editText = (EditText) view.findViewById(R.id.code);
        editText.setCursorVisible(false);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateEnter(s.toString());
                updateEnterButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEnterButton.setEnabled(false); // default state should be disabled

        if (!isNetworkOn(getActivity())) {
            Toast.makeText(getActivity(),
                    "No network connection", Toast.LENGTH_SHORT).show();
        } else {

            mQrButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(getActivity(), DecoderActivity.class);
                    startActivity(intent);
                }
            });

            mEnterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = editText.getText().toString();
                    getTestData(id);
                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Request...");
                    progressDialog.show();
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // On complete call either onLoginSuccess or onLoginFailed
                                    // onLoginSuccess();
                                    // onLoginFailed();
                                    progressDialog.dismiss();
                                }
                            }, 1000);
                }
            });

        }

        return view;
    }


    public void onTestDataFailed() {
        Toast.makeText(getActivity(), "Request failed", Toast.LENGTH_SHORT).show();
    }

    public void getTestData(final String id) {
        String movieUrl = "http://192.168.57.1:3000/questions/" + id;
        if (isNetworkOn(getActivity())) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(movieUrl)
                    .addHeader("Accept", "application/json")
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                             @Override
                             public void onFailure(Request request, IOException e) {
                                 Log.v("ERRor", "lolka");
                             }

                             @Override
                             public void onResponse(Response response) throws IOException {
                                 try {
                                     if (response.isSuccessful()) {
                                         final String json = response.body().string();
                                         Log.v("JSON", json);
                                         if (!json.isEmpty()) {
                                             getQuestionData(json, id);
                                         }
                                         getActivity().runOnUiThread(new Runnable() {
                                             @Override
                                             public void run() {
                                                 //    movieUI();
                                                 if (!json.isEmpty()) {
                                                     Intent intent = new Intent(getActivity(), TestActivity.class);
                                                     intent.putExtra(TAG, id);
                                                     startActivity(intent);
                                                 } else {
                                                     onTestDataFailed();
                                                 }
                                             }
                                         });
                                     }
                                 } catch (IOException e) {
                                     Log.e(TAG, "EROR", e);
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }


                             }
                         }

            );
        } else {
            Toast.makeText(getActivity(), "Network isn't available", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isNetworkOn(Context context) {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void updateEnterButtonState() {
        if (isEnterValid) {
            mEnterButton.setEnabled(true);
        } else {
            mEnterButton.setEnabled(false);
        }
    }

    private void validateEnter(String text) {
        isEnterValid = !text.isEmpty();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getQuestionData(String jsonData, String id) throws JSONException {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "lease-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        ChoiceDao leaseDao = daoSession.getChoiceDao();

        List<Choice> mChoices = leaseDao._queryQuestion_Question(Long.parseLong(id));

        if (mChoices.isEmpty()) {
            JSONObject jsonObject = new JSONObject(jsonData);

            Question question = new Question();
            question.setText(jsonObject.getString("text"));
            question.setID(jsonObject.getInt("id"));
            question.setId(Long.parseLong(id));

            JSONArray data = jsonObject.getJSONArray("choices");


            ArrayList<Choice> notes = new ArrayList<Choice>();

            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonChoices = data.getJSONObject(i);
                Choice choice = new Choice();

                choice.setItem(jsonChoices.getString("text"));
                choice.setID(jsonChoices.getInt("id"));
                choice.setQuestionId(Long.parseLong(id));
                notes.add(choice);

            }

            ChoiceDao choiceDao = daoSession.getChoiceDao();
            choiceDao.insertInTx(notes);
            QuestionDao questionDao = daoSession.getQuestionDao();
            questionDao.insertOrReplace(question);
        }
    }

}
