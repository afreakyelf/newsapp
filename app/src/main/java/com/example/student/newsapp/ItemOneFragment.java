package com.example.student.newsapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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


public class ItemOneFragment extends android.support.v4.app.Fragment implements ConstVariable{
    private ArrayList<Model_list> al_model;
    RVAdapter adapter;
    Toolbar toolbar;
    private Dialog dialog;
    private TextView alert , alertmessage;
    private Button button , loginbutton;
    FirebaseAuth mAuth;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onStart() {
        Log.d("one","Start");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("one","Resume");
        super.onResume();
    }

    @Override
    public void onDestroy() {
        Log.d("one","Destroy");
        super.onDestroy();
    }

    @Override
    public void onStop() {
        Log.d("one","Stop");
        super.onStop();
    }

    public static ItemOneFragment newInstance(){
        ItemOneFragment fragment= new ItemOneFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main3,container,false);
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
        RecyclerView rv=(RecyclerView)rootView.findViewById(R.id.rv);
        initializeData();
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager llm = new GridLayoutManager(getActivity(),2);
        rv.setLayoutManager(llm);

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

        ItemOneFragment itemoneFragment = this;
        RVAdapter adapter= new RVAdapter(al_model ,itemoneFragment , getActivity());
        rv.setAdapter(adapter);
        return rootView;
    }


    private void initializeData()
    {
        al_model = new ArrayList<>();
        al_model.add(new Model_list(CHANNELABC, ABCNEWS, R.drawable.gabc));
        al_model.add(new Model_list(CHANNELALJAZEERAENGLISH, ALJAZEERAENGLISH, R.drawable.gaijazeera));
        al_model.add(new Model_list(CHANNELASSOCIATEDPRESS,ASSOCIATEDPRESS, R.drawable.gassociativepress));
        al_model.add(new Model_list(CHANNELBBCNEWS, BBCNEWS, R.drawable.gbbc));
        al_model.add(new Model_list(CHANNELCNN, CNN, R.drawable.gcnn));
        al_model.add(new Model_list(CHANNELINDEPENDENT,INDEPENDENT, R.drawable.gindependent));
        al_model.add(new Model_list(CHANNELMETRO, METRO, R.drawable.gmetro));
        al_model.add(new Model_list(CHANNELMIRROR, MIRROR, R.drawable.gmirror));
        al_model.add(new Model_list(CHANNELNEWSWEEK, NEWSWEEK, R.drawable.gnewsweek));
        al_model.add(new Model_list(CHANNELNEWYORKMAGAZINE, NEWYORKMAGAZINE, R.drawable.gnewyork));
        al_model.add(new Model_list(CHANNELREDDITRALL, REDDITRALL, R.drawable.greddit));
        al_model.add(new Model_list(CHANNELREUTERS, REUTERS, R.drawable.greuters));
        al_model.add(new Model_list(CHANNELTHEGUARDIANAU, THEGUARDIANAU, R.drawable.gtheguradian));
        al_model.add(new Model_list(CHANNELTHEGUARDIANUK, THEGUARDIANUK, R.drawable.gtheguradian));
        al_model.add(new Model_list(CHANNELTHEHINDU,THEHINDU, R.drawable.gthehindu));
        al_model.add(new Model_list(CHANNELTHEHUFFINGTONPOST, THEHUFFINGTONPOST, R.drawable.gthehuffingtonpost));
        al_model.add(new Model_list(CHANNELTHENEWYORKTIMES, THENEWYORKTIMES, R.drawable.gthenewyorktimes));
        al_model.add(new Model_list(CHANNELTHETELEGRAPH, THETELEGRAPH, R.drawable.gthetelegraph));
        al_model.add(new Model_list(CHANNELTIME, TIME, R.drawable.gtimes));
        al_model.add(new Model_list(CHANNELTHEWASHINGTONPOST, THEWASHINGTONPOST, R.drawable.gthewashingtonpost));
        al_model.add(new Model_list(CHANNELTHETIMESOFINDIA, THETIMESOFINDIA, R.drawable.gtoi));
        al_model.add(new Model_list(CHANNELUSATODAY, USATODAY, R.drawable.gusatoday));

    }
/*
    public void fn_click(int position){
        Fragment fragment = new channelwisenewsdetailsActivity();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundleobj = new Bundle();
        bundleobj.putCharSequence(INTENT_CHANNELNAME,al_model.get(position).getStr_name());
        bundleobj.putCharSequence(INTENT_CHANNELSOURCE,al_model.get(position).getStr_channel());
        fragment.setArguments(bundleobj);
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }*/


    @Override
    public void callback(HashMap<String, Object> tmpMap, String dataType, int mode) {


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.bookmark,menu);

        super.onCreateOptionsMenu(menu,inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null){
            startActivity(new Intent(getActivity(),show.class));}
        else {

            alert.setText("Alert");
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
                    Fragment fragment = new ItemFourFragment();
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
        return super.onOptionsItemSelected(item);
    }

}
