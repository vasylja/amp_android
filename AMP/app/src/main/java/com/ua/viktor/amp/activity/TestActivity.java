package com.ua.viktor.amp.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ua.viktor.amp.R;
import com.ua.viktor.amp.adapter.TestItemAdapter;
import com.ua.viktor.amp.dao.Choice;
import com.ua.viktor.amp.dao.ChoiceDao;
import com.ua.viktor.amp.dao.DaoMaster;
import com.ua.viktor.amp.dao.DaoSession;
import com.ua.viktor.amp.dao.Question;
import com.ua.viktor.amp.dao.QuestionDao;
import com.ua.viktor.amp.fragment.HomeFragment;

import java.util.List;

public class TestActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView questionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        questionText=(TextView)findViewById(R.id.textView3);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbart);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent intent=getIntent();




        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "lease-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        ChoiceDao leaseDao = daoSession.getChoiceDao();
        String position=intent.getStringExtra(HomeFragment.TAG);
        List<Choice> choiceList =leaseDao._queryQuestion_Question(Long.parseLong(position));

        QuestionDao questionDao=daoSession.getQuestionDao();
        Question question=questionDao.loadByRowId(Long.parseLong(position));


        questionText.setText(question.getText());


        TestItemAdapter testItemAdapter = new TestItemAdapter(choiceList);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getApplication(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(testItemAdapter);
        testItemAdapter.SetOnClickListener(new TestItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //view.setBackgroundColor(getResources().getColor(R.color.cardview_shadow_start_color));

                // view.setClickable(false);

            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // savedInstanceState.putString(STATE_USER, mUser);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
