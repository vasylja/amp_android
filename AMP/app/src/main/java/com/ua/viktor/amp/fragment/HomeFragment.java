package com.ua.viktor.amp.fragment;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.ua.viktor.amp.R;
import com.ua.viktor.amp.activity.DecoderActivity;
import com.ua.viktor.amp.activity.TestActivity;


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


}
