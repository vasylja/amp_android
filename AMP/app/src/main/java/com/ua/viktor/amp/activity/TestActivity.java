package com.ua.viktor.amp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ua.viktor.amp.R;
import com.ua.viktor.amp.adapter.TestItemAdapter;

public class TestActivity extends AppCompatActivity {
  private   RecyclerView mRecyclerView;
  private   RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbart);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
   final String []mas={"dfdf","dfdfsd"};
        TestItemAdapter testItemAdapter=new TestItemAdapter(mas);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
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
