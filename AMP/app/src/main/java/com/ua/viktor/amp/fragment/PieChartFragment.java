package com.ua.viktor.amp.fragment;

import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ua.viktor.amp.R;
import com.ua.viktor.amp.activity.DetailActivity;
import com.ua.viktor.amp.model.Choice;
import com.ua.viktor.amp.model.ChoiceDao;
import com.ua.viktor.amp.model.DaoMaster;
import com.ua.viktor.amp.model.DaoSession;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.communication.IOnItemFocusChangedListener;
import org.eazegraph.lib.models.PieModel;

import java.util.List;

/**
 * Created by viktor on 19.10.15.
 */
public class PieChartFragment extends ChartFragment {

    private PieChart mPieChart;
    private TextView questionText;
    private List<Choice> mChoices;
    public PieChartFragment() {
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

        Log.v("Size",""+mChoices.size());
        Log.v("position",""+position);
        Log.v("GreenDao",mChoices.get(0).getQuestion().getText()+"");
       questionText.setText(mChoices.get(0).getQuestion().getText());

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
          mPieChart.addPieSlice(new PieModel(mChoices.get(i).getItem(), i, color));
      }
        mPieChart.setOnItemFocusChangedListener(new IOnItemFocusChangedListener() {
            @Override
            public void onItemFocusChanged(int _Position) {
              // Log.v("PieChart", "Position: " + _Position);
            }
        });
    }


}
