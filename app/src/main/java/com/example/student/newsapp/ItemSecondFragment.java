package com.example.student.newsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static com.example.student.newsapp.Common.ConstVariable.INTENT_CHANNELNAME;
import static com.example.student.newsapp.Common.ConstVariable.INTENT_CHANNELSOURCE;

public class ItemSecondFragment extends android.support.v4.app.Fragment{
    private ArrayList<Model_list> al_news;
    private Context context;

    public static ItemSecondFragment newInstance(){
        ItemSecondFragment fragment= new ItemSecondFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main3,container,false);

        Fragment fragment = new headlinenews();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundleobj = new Bundle();
        bundleobj.putCharSequence(INTENT_CHANNELNAME,"Google News");
        bundleobj.putCharSequence(INTENT_CHANNELSOURCE,"google-news");
        fragment.setArguments(bundleobj);
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        return rootView;
    }

    @Override
    public void onStart() {
        Log.d("Second","Start");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("second","Resume");
        super.onResume();
    }

    @Override
    public void onDestroy() {
        Log.d("Second","Destroy");
        super.onDestroy();
    }

    @Override
    public void onStop() {
        Log.d("Second","Stop");
        super.onStop();
    }

}

