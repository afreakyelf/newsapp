package com.example.student.newsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_test extends Fragment{
    String INTENTCHANNELNAME = "channel_name";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.activity_fragment_test, container, false);
        TextView tv =(TextView)rootview.findViewById(R.id.tv);


        Bundle bundle =getArguments();

        if(null!=bundle) {
            String email= bundle.getString(INTENTCHANNELNAME);

            tv.setText(email);
        }


////retrieving data using bundle
//        Bundle bundle=getArguments();
//        memail.setText(String.valueOf(bundle.getString("email")));

        return rootview;
    }
}
