package com.ua.viktor.amp.fragment;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ua.viktor.amp.R;
import com.ua.viktor.amp.activity.DecoderActivity;
import com.ua.viktor.amp.activity.TestActivity;
import com.ua.viktor.amp.model.Choice;
import com.ua.viktor.amp.model.ChoiceDao;
import com.ua.viktor.amp.model.DaoMaster;
import com.ua.viktor.amp.model.DaoSession;
import com.ua.viktor.amp.model.Question;
import com.ua.viktor.amp.model.QuestionDao;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private ImageButton mQrButton;
    private Button mEnterButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        mQrButton= (ImageButton) view.findViewById(R.id.imageButton);
        mEnterButton= (Button) view.findViewById(R.id.button);


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "lease-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        ChoiceDao leaseDao = daoSession.getChoiceDao();
        List<Choice> leaseList = leaseDao.loadAll();
       // Log.v("Size",leaseList.size()+"");
       // Log.v("GreenDao",leaseList.get(28).getQuestionId()+"");
        TextInputLayout textInputLayout=(TextInputLayout)view.findViewById(R.id.button2);
      //  textInputLayout.se
        EditText editText =(EditText)view.findViewById(R.id.code);
      //  editText.setCursorVisible(false);

        mQrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),DecoderActivity.class);
                       startActivity(intent);
            }
        });
       mEnterButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getActivity(),TestActivity.class);
        startActivity(intent);
        }
      });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "lease-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
     // insertSampleData(daoSession);
        daoSession.clear();

    }
    public void insertSampleData(DaoSession daoSession) {
        Question question = new Question();
        question.setText("Question");
      question.setId((long) 0);

        ArrayList<Choice> notes = new ArrayList<Choice>();

        for (int i = 0; i < 5; i++) {
            Choice choice=new Choice();
            choice.setItem("b" + i);
            choice.setQuestionId((long) 0);
            notes.add(choice);
        }
        ChoiceDao choiceDao=daoSession.getChoiceDao();
        choiceDao.insertInTx(notes);
        QuestionDao questionDao = daoSession.getQuestionDao();
        questionDao.insertOrReplace(question);



    }
}
