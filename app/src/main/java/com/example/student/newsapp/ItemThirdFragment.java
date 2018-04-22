package com.example.student.newsapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

import com.example.student.newsapp.Adapter.CategoryAdapter;
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


public class ItemThirdFragment extends Fragment implements ConstVariable {

    TextView tv1;
    Toolbar toolbar;
    public static ArrayList<categorylist> categorylists;
   public static ArrayList<serializablelistofcategorychannel> al_model;
    private Dialog dialog;
    private TextView alert , alertmessage;
    private Button button , loginbutton;
    FirebaseAuth mAuth;

    public static ItemThirdFragment newInstance(){
        ItemThirdFragment fragment = new ItemThirdFragment();
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }






    private void initializeData()
    {
        categorylists = new ArrayList<>();
        categorylists.add(new categorylist(R.drawable.bussinessicon,"Business"));
        categorylists.add(new categorylist(R.drawable.entertainmenticon,"Entertainment"));
        categorylists.add(new categorylist(R.drawable.gaming,"Gaming"));
        categorylists.add(new categorylist(R.drawable.music,"Music"));
        categorylists.add(new categorylist(R.drawable.politics,"Politics"));
        categorylists.add(new categorylist(R.drawable.sciencenature,"Science and Nature"));
        categorylists.add(new categorylist(R.drawable.sportsicon,"Sports"));
        categorylists.add(new categorylist(R.drawable.technologyicon,"Technology"));



    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main3,container,false);
        setHasOptionsMenu(true);

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

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Categories");

        RecyclerView rv=(RecyclerView)rootView.findViewById(R.id.rv);
        initializeData();
       // rv.setHasFixedSize(true);
        RecyclerView.LayoutManager llm = new StaggeredGridLayoutManager(2,1);
        rv.setLayoutManager(llm);

        ItemThirdFragment itemThirdFragmentFragment = this;
        CategoryAdapter adapter= new CategoryAdapter(categorylists ,itemThirdFragmentFragment);
        rv.setAdapter(adapter);
        return rootView;
    }


    public  void fn_click(int position){

        if(position == 0){
            businessdata();
        }else if(position == 1){
            entertainmaint();
        }else if(position == 2){
           gaming();
        }else if(position == 3){
           music();
        }else if(position == 4){
           politics();
        }else if(position == 5){
           science();
        }else if(position == 6){
           sports();
        }else if(position == 7){
           technology();
        }
        Fragment  fragment = new Categorywisechannel();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundleobj = new Bundle();
        bundleobj.putSerializable("A",al_model);
        bundleobj.putString("Title",categorylists.get(position).getCategory());
        fragment.setArguments(bundleobj);
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public static void technology() {
        al_model = new ArrayList<>();
        al_model.add(new serializablelistofcategorychannel(CHANNELARSTECHNICA,ARSTECHNICA, R.drawable.tarstechnica));
        al_model.add(new serializablelistofcategorychannel(CHANNELENGADGET,ENGADGET, R.drawable.tengadget));
        al_model.add(new serializablelistofcategorychannel(CHANNELHACKERNEWS,HACKERNEWS, R.drawable.thackernews));
        al_model.add(new serializablelistofcategorychannel(CHANNELRECODE,RECODE, R.drawable.trecode));
        al_model.add(new serializablelistofcategorychannel(CHANNELTECHCRUNCH,TECHCRUNCH, R.drawable.ttechcrunch));
        al_model.add(new serializablelistofcategorychannel(CHANNELTECHRADAR,TECHRADAR, R.drawable.ttechradar));
        al_model.add(new serializablelistofcategorychannel(CHANNELTHENEXTWEB,THENEXTWEB, R.drawable.tthenextweb));
        al_model.add(new serializablelistofcategorychannel(CHANNELTHEVERGE,THEVERGE, R.drawable.ttheverge));



    }

    public static void sports() {
        al_model = new ArrayList<>();
        al_model.add(new serializablelistofcategorychannel(CHANNELBBCSPORT,BBCSPORT, R.drawable.sbbcsport));
        al_model.add(new serializablelistofcategorychannel(CHANNELESPN,ESPN, R.drawable.sespn));
        al_model.add(new serializablelistofcategorychannel(CHANNELESPNCRICINFO,ESPNCRICINFO, R.drawable.sespncricinfo));
        al_model.add(new serializablelistofcategorychannel(CHANNELFOOTBALLITALIA,FOOTBALLITALIA, R.drawable.sfootballitalia));
        al_model.add(new serializablelistofcategorychannel(CHANNELFOURFOURTWO, FOURFOURTWO, R.drawable.sfourfourtwo));
        al_model.add(new serializablelistofcategorychannel(CHANNELFOXSPORTS,FOXSPORTS, R.drawable.sfoxsportslogo));
        al_model.add(new serializablelistofcategorychannel(CHANNELNFLNEWS,NFLNEWS, R.drawable.snflnews));
        al_model.add(new serializablelistofcategorychannel(CHANNELTHESPORTBIBLE,THESPORTBIBLE, R.drawable.ssportsbible));
        al_model.add(new serializablelistofcategorychannel(CHANNELTALKSPORT,TALKSPORT, R.drawable.stalksport));


    }

    public static void science() {
        al_model = new ArrayList<>();
        al_model.add(new serializablelistofcategorychannel(CHANNELNATIONALGEOGRAPHIC, NATIONALGEOGRAPHIC, R.drawable.snnationalgeographic));
        al_model.add(new serializablelistofcategorychannel(CHANNELNEWSCIENTIST, NEWSCIENTIST, R.drawable.snnewscientist));
    }

    public static void politics() {
        al_model = new ArrayList<>();
        al_model.add(new serializablelistofcategorychannel(CHANNELBREITBARTNEWS,BREITBARTNEWS,R.drawable.politicsbreitbart));
    }

    public static void music() {
        al_model = new ArrayList<>();
        al_model.add(new serializablelistofcategorychannel(CHANNELMTVNEWS, MTVNEWS,R.drawable.mtvnews));
    }

    public static void gaming() {
        al_model = new ArrayList<>();
        al_model.add(new serializablelistofcategorychannel(CHANNELIGN, IGN, R.drawable.gamingign));
        al_model.add(new serializablelistofcategorychannel(CHANNELPOLYGON, POLYGON, R.drawable.gamingpolygon));
    }

    public static void entertainmaint() {
        al_model = new ArrayList<>();
        al_model.add(new serializablelistofcategorychannel(CHANNELBUZZFEED, BUZZFEED, R.drawable.ebuzzfeed));
        al_model.add(new serializablelistofcategorychannel(CHANNELENTERTAINMENTWEEKLY, ENTERTAINMENTWEEKLY, R.drawable.eentertainmentweekly));
        al_model.add(new serializablelistofcategorychannel(CHANNELMASHABLE, MASHABLE, R.drawable.emashable));
        al_model.add(new serializablelistofcategorychannel(CHANNELTHELADBIBLE, THELADBIBLE, R.drawable.etheladbible));
        al_model.add(new serializablelistofcategorychannel(CHANNELDAILYMAIL,DAILYMAIL,R.drawable.edailyonlinde));

    }



    public static void businessdata()

    {
        al_model = new ArrayList<>();
        al_model.add(new serializablelistofcategorychannel(CHANNELBLOOMBERG,BLOOMBERG, R.drawable.businessbloomberg));
        al_model.add(new serializablelistofcategorychannel(CHANNELCNBC,CNBC, R.drawable.businesscnbc));
        al_model.add(new serializablelistofcategorychannel(CHANNELFINANCIALTIMES,FINANCIALTIMES, R.drawable.businessfinancialtimes));
        al_model.add(new serializablelistofcategorychannel(CHANNELFORTUNE,FORTUNE, R.drawable.businessfortune));
        al_model.add(new serializablelistofcategorychannel(CHANNELTHEECONOMIST,THEECONOMIST, R.drawable.businesstheeconomist));
        al_model.add(new serializablelistofcategorychannel(CHANNELTHEWALLSTREETJOURNAL, THEWALLSTREETJOURNAL, R.drawable.businesswallstreetjournal));
        al_model.add(new serializablelistofcategorychannel(CHANNELBUSINESSINSIDER, BUSINESSINSIDER, R.drawable.bussinessinsider));




    }


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

    @Override
    public void onStart() {
        Log.d("Third","Start");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("Third","Resume");
        super.onResume();
    }

    @Override
    public void onDestroy() {
        Log.d("Third","Destroy");
        super.onDestroy();
    }

    @Override
    public void onStop() {
        Log.d("Third","Stop");
        super.onStop();
    }


}
