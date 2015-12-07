package com.ua.viktor.amp.fragment;

import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ua.viktor.amp.R;
import com.ua.viktor.amp.activity.DetailActivity;
import com.ua.viktor.amp.dao.Choice;
import com.ua.viktor.amp.dao.ChoiceDao;
import com.ua.viktor.amp.dao.DaoMaster;
import com.ua.viktor.amp.dao.DaoSession;
import com.ua.viktor.amp.dao.Question;
import com.ua.viktor.amp.dao.QuestionDao;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.communication.IOnItemFocusChangedListener;
import org.eazegraph.lib.models.PieModel;

import java.util.List;

/**
 * Created by viktor on 19.10.15.
 */
public class HistoryDetailFragment extends ChartFragment {

    private PieChart mPieChart;
    private TextView questionText;
    private List<Choice> mChoices;


    public HistoryDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        mPieChart = (PieChart) view.findViewById(R.id.piechart);

        Bundle bundle=getArguments();
        int position=0;
        if(bundle!=null) {
            position = bundle.getInt(DetailActivity.POSI);

        }
        questionText= (TextView) view.findViewById(R.id.textQuestion);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getActivity(), "lease-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();



        ChoiceDao leaseDao = daoSession.getChoiceDao();
        mChoices = leaseDao._queryQuestion_Question((long)position);

        QuestionDao questionDao=daoSession.getQuestionDao();
        Question question=questionDao.loadByRowId((long)position);

       // Log.v("Size", "" + mChoices.size());
       // Log.v("position",""+position);

       questionText.setText(question.getText());

        loadData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPieChart.startAnimation();
    }

    @Override
    public void restartAnimation() {
        mPieChart.startAnimation();
    }

    @Override
    public void onReset() {

    }

    private void loadData() {
      for (int i=0;i<mChoices.size();i++) {

          ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
          // generate random color
          int color = generator.getRandomColor();
          mPieChart.addPieSlice(new PieModel(mChoices.get(i).getItem(), i+1, color));
      }
        mPieChart.setOnItemFocusChangedListener(new IOnItemFocusChangedListener() {
            @Override
            public void onItemFocusChanged(int _Position) {
              // Log.v("PieChart", "Position: " + _Position);
            }
        });
    }


}
