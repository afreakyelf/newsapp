package com.example.student.newsapp;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.student.newsapp.Common.ConstVariable;
import com.firebase.client.Firebase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import static com.example.student.newsapp.Adapter.RVAdapter.channelname;
import static com.example.student.newsapp.Adapter.RVAdapter.channelsource;
import static com.example.student.newsapp.AlarmReceiver.boolean_headline;
import static com.example.student.newsapp.DetailsActivity.boolean_login;
import static com.example.student.newsapp.ItemThirdFragment.al_model;
import static com.example.student.newsapp.ItemThirdFragment.businessdata;
import static com.example.student.newsapp.ItemThirdFragment.categorylists;
import static com.example.student.newsapp.ItemThirdFragment.entertainmaint;
import static com.example.student.newsapp.ItemThirdFragment.gaming;
import static com.example.student.newsapp.ItemThirdFragment.music;
import static com.example.student.newsapp.ItemThirdFragment.politics;
import static com.example.student.newsapp.ItemThirdFragment.science;
import static com.example.student.newsapp.ItemThirdFragment.sports;
import static com.example.student.newsapp.ItemThirdFragment.technology;
import static com.example.student.newsapp.NotificationDetail.boolean_category;

public class MainActivity extends AppCompatActivity implements ConstVariable{
    headlinenews context;
    private TextView mTextMessage , alertmessage, alert;
    private ImageView bookmarkbutton;
    private AdView adView;
    public static InterstitialAd interstitialAd;
    private FirebaseAuth mAuth;
    private Button button;
    private Dialog dialog;
    public static BottomNavigationView bottomNavigationView;
    int pos = 0;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,2);
        Intent intent=new Intent("android.action.DISPLAY_NOTIFICATION");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, intent , PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast );



        MobileAds.initialize(this,"ca-app-pub-3940256099942544/6300978111");



        final AdView adView = (AdView) findViewById(R.id.adView);
        final AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);




        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.performClick();

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                      Fragment selectedFragment = null;

                        switch (item.getItemId()) {
                            case R.id.navigation_home:

                              //  toolbar.setTitle("Home");
                                selectedFragment = ItemOneFragment.newInstance();
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.content, selectedFragment);
                                 transaction.addToBackStack(null);

                                transaction.commit();
                                return true;

                            case R.id.navigation_headlines:
                              //  toolbar.setTitle("Headlines");
                                selectedFragment = ItemSecondFragment.newInstance();
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.content, selectedFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                return true;
                            case R.id.navigation_categories:
                              //  toolbar.setTitle("Categories");

                                selectedFragment = ItemThirdFragment.newInstance();
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.content, selectedFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                return true;

                            case R.id.navigation_notes:
                              //  toolbar.setTitle("Notes");
                               

                                selectedFragment = ItemFourFragment.newInstance();
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.content, selectedFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                return true;

                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });



    /*   if(channelname == CHANNELBLOOMBERG ||channelname == CHANNELFORTUNE ||channelname == CHANNELCNBC || channelname == CHANNELFINANCIALTIMES || channelname == CHANNELTHEECONOMIST || channelname == CHANNELBUSINESSINSIDER || channelname == CHANNELTHEWALLSTREETJOURNAL){

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, ItemFourFragment.newInstance());
            transaction.commit();
            bottomNavigationView.setSelectedItemId(R.id.navigation_notes);

        }
            else{
*/
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, ItemOneFragment.newInstance());
                 bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                transaction.commit();


            //}


          if(Objects.equals(channelsource, "google-news")){
              abc();
          }


    }


    private void abc() {
        if (boolean_headline){
            boolean_headline=false;
            Fragment  fragment = new ItemSecondFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment);

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            bottomNavigationView.setSelectedItemId(R.id.navigation_headlines);

        }





    }


    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    public void onBackPressed() {
        if(bottomNavigationView.getSelectedItemId() != R.id.navigation_home) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }else {


            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.alertdialogforshowbookmark);

            final TextView alertmessage = (TextView)dialog.findViewById(R.id.alertmessage);
            final TextView alert = (TextView)dialog.findViewById(R.id.message);
            final Button no = (Button) dialog.findViewById(R.id.cancel_action);
            final Button yes = (Button) dialog.findViewById(R.id.login_now);

            alert.setText("Alert");
            alertmessage.setText("You want to exit ?");
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });



            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });

            dialog.show();



          /*  AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("You want to exit!!!!");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    });
            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();*/

        }
    }

    public  void fn_testing(){
        bottomNavigationView.setSelectedItemId(R.id.navigation_notes);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (boolean_login){
            boolean_login=false;
            Fragment  fragment = new ItemFourFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment);

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            bottomNavigationView.setSelectedItemId(R.id.navigation_notes);

        }



        if (boolean_category){
            boolean_category=false;


            if(channelname == CHANNELBLOOMBERG || channelname == CHANNELFORTUNE ||
                    channelname == CHANNELCNBC || channelname == CHANNELFINANCIALTIMES ||
                    channelname == CHANNELTHEECONOMIST || channelname == CHANNELBUSINESSINSIDER ||
                    channelname == CHANNELTHEWALLSTREETJOURNAL){
                businessdata();
                pos = 0;
            }else if(channelname == CHANNELBUZZFEED || channelname == CHANNELENTERTAINMENTWEEKLY || channelname == CHANNELMASHABLE
                    || channelname == CHANNELTHELADBIBLE){
                entertainmaint();
                pos = 1;
            }else if(channelname == CHANNELIGN
                    || channelname == CHANNELPOLYGON ){
                gaming();
                pos = 2;
            }else if(channelname == CHANNELMTVNEWS){
                music();
                pos = 3;
            }else if(channelname == CHANNELBREITBARTNEWS){
                politics();
                pos = 4;
            }else if( channelname == CHANNELNATIONALGEOGRAPHIC
                    || channelname == CHANNELNEWSCIENTIST){
                science();
                pos = 5;
            }else if(channelname == CHANNELBBCSPORT
                    || channelname == CHANNELESPN || channelname == CHANNELESPNCRICINFO
                    || channelname == CHANNELFOOTBALLITALIA || channelname == CHANNELFOURFOURTWO
                    || channelname == CHANNELFOXSPORTS || channelname == CHANNELNFLNEWS
                    || channelname == CHANNELTHESPORTBIBLE || channelname == CHANNELTALKSPORT){
                sports();
                pos = 6;
            }else if(channelname == CHANNELARSTECHNICA || channelname == CHANNELENGADGET
                    || channelname == CHANNELHACKERNEWS || channelname == CHANNELRECODE
                    || channelname == CHANNELTECHCRUNCH || channelname == CHANNELTECHRADAR
                    || channelname == CHANNELTHENEXTWEB || channelname == CHANNELTHEVERGE){
                technology();
                pos =7;
            }

     bottomNavigationView.setSelectedItemId(R.id.navigation_categories);

            Fragment  fragment = new Categorywisechannel();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Bundle bundleobj = new Bundle();
            bundleobj.putSerializable("A",al_model);
            bundleobj.putString("Title",categorylists.get(pos).getCategory());
            fragment.setArguments(bundleobj);
            fragmentTransaction.replace(R.id.content, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }


    }

    @Override
    public void callback(HashMap<String, Object> tmpMap, String dataType, int mode) {

    }


}

