package com.example.student.newsapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.student.newsapp.Adapter.RVAdapter;
import com.example.student.newsapp.Adapter.categorychannel;
import com.example.student.newsapp.Common.ConstVariable;
import com.example.student.newsapp.Firebase.show;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.student.newsapp.Common.Colors.colors;
import static com.example.student.newsapp.DetailsActivity.mCircleDrawable;
import static com.example.student.newsapp.DetailsActivity.progressBar;


public class Categorywisechannel extends android.support.v4.app.Fragment implements ConstVariable{
    private ArrayList<serializablelistofcategorychannel> al_model;
    private ArrayList<categorylist> list;
    RVAdapter adapter;
    private String position;

    private Dialog dialog;
    private TextView alert , alertmessage;
    private Button button , loginbutton;
    ItemThirdFragment itemThirdFragment;
    FirebaseAuth mAuth;



    public static Categorywisechannel newInstance(){
        Categorywisechannel fragment= new Categorywisechannel();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main3,container,false);



        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertaialog);
        alertmessage = (TextView)dialog.findViewById(R.id.alertmessage);
        alert = (TextView)dialog.findViewById(R.id.message);
        button = (Button) dialog.findViewById(R.id.cancel_action);
        loginbutton = (Button) dialog.findViewById(R.id.login_now);


        progressBar = (ProgressBar)rootView.findViewById(R.id.progress);
        mCircleDrawable = new Circle();
        mCircleDrawable.setBounds(0, 0, 100, 100);
        mCircleDrawable.setColor(colors[7]);
        progressBar.setIndeterminateDrawable(mCircleDrawable);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.GONE);

        RecyclerView rv=(RecyclerView)rootView.findViewById(R.id.rv);

        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager llm = new GridLayoutManager(getActivity(),2);
        rv.setLayoutManager(llm);


        Bundle bundle =getArguments();
        if(bundle != null) {
            al_model = (ArrayList<serializablelistofcategorychannel>) bundle.getSerializable("A");
            position = bundle.getString("Title");
        }

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(position);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        Categorywisechannel itemoneFragment = this;
        categorychannel categorychannel =  new categorychannel ((ArrayList<serializablelistofcategorychannel>) al_model,itemoneFragment,getActivity());
        rv.setAdapter(categorychannel);
        return rootView;
    }



    @Override
    public void callback(HashMap<String, Object> tmpMap, String dataType, int mode) {

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bookmark,menu);

        super.onCreateOptionsMenu(menu,inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            Fragment fragment = new ItemThirdFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else {
            FirebaseAuth  mAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            if(firebaseUser!=null){
                startActivity(new Intent(getActivity(),show.class));}
            else {

                alert.setText("Message");
                alertmessage.setText("Please Login First!");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                loginbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment  fragment = new ItemFourFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content, fragment);

                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        ((MainActivity)getActivity()).fn_testing();
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        }
        return super.onOptionsItemSelected(item);
}



}

