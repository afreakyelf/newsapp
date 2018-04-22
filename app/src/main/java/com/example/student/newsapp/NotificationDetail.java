package com.example.student.newsapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student.newsapp.Adapter.Adapter_detail;
import com.example.student.newsapp.Common.ConstVariable;
import com.example.student.newsapp.Common.MyListener;
import com.example.student.newsapp.Firebase.show;
import com.firebase.client.Firebase;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.student.newsapp.Adapter.RVAdapter.channelname;
import static com.example.student.newsapp.Common.Colors.colors;
import static com.example.student.newsapp.DetailsActivity.boolean_login;


public class NotificationDetail extends AppCompatActivity implements ConstVariable,MyListener{

    ArrayList<HashMap<String, Object>> al_details;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    Adapter_detail obj_adapter;
    public static boolean boolean_home;
    public static String str_channelname,str_channelsource,count;

    MyListener ml;

    Dialog new_dialog;
    FirebaseAuth mAuth;

    private Dialog dialog;
    private TextView alert , alertmessage;
    private Button button , loginbutton;
    public static ProgressBar progressBar;
    public static Circle mCircleDrawable;
    public static boolean boolean_category;

   /* @Override
    protected void onStart() {
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.show();
                super.onStart();
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailactivity);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        Firebase.setAndroidContext(this);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);



        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertaialog);
        alertmessage = (TextView)dialog.findViewById(R.id.alertmessage);
        alert = (TextView)dialog.findViewById(R.id.message);
        button = (Button) dialog.findViewById(R.id.cancel_action);
        loginbutton = (Button) dialog.findViewById(R.id.login_now);



        progressBar = (ProgressBar)findViewById(R.id.progress);
        mCircleDrawable = new Circle();
        mCircleDrawable.setBounds(0, 0, 100, 100);
        mCircleDrawable.setColor(colors[7]);
        progressBar.setIndeterminateDrawable(mCircleDrawable);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.GONE);



        listener();

        str_channelname = getIntent().getStringExtra(INTENT_CHANNELNAME);
        str_channelsource=getIntent().getStringExtra(INTENT_CHANNELSOURCE);
        //      count = getIntent().getStringExtra("count");


        HashMap<String, Object> HM = new HashMap<>();
        HM.put(SOURCE, str_channelsource);
        if (str_channelname.matches(CHANNELDERTAGESSPIEGEL)){
            HM.put(SORTBY,LATEST);
        }else {
            HM.put(SORTBY,TOP);
        }

        HM.put(APIKEY, APIKEYID);

        getSupportActionBar().setTitle(str_channelname);
        fn_response(this,HM,0);


    }




    private void listener(){
        setOnEventListener(this);


    }


    public void setOnEventListener(MyListener ml) {
        this.ml = ml;
    }


    public void fn_response(final Context context, HashMap<String, Object> HM, final int mode){


        progressBar.setVisibility(View.VISIBLE);


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getresponse("articles",HM,"");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //Dismiss Dialog
                progressBar.setVisibility(View.GONE);

                List<HashMap<String, String>> dataList = new ArrayList<>();

                if(response.isSuccessful()) {
                    try {
                        HashMap<String, Object> dataMap = null;
                        String str_testing = response.body().string();
                        dataMap = new HashMap<String, Object>();


                        JSONObject jsonObject = new JSONObject(str_testing);

                        JSONArray data= jsonObject.getJSONArray("articles");

                        for(int i=0;i<data.length();i++){

                            JSONObject d = data.getJSONObject(i);

                            String description = d.getString("description");
                            String title = d.getString("title");
                            String Image = d.getString("urlToImage");
                            String url = d.getString("url");
                            //tmp hashmap for single contact
                            HashMap<String,String> samachar = new HashMap<>();
                            //adding each child node
                            samachar.put("description", description);
                            samachar.put("title",title);
                            samachar.put("urlToImage",Image);
                            samachar.put("url",url);
                            dataList.add(samachar);


                        }
                        Log.e("json"+"162", "jsonObject1: " + dataList);
                        dataMap.put(RESULT, dataList);

                        switch (mode)
                        {
                            case HOME:
                                ml.callback(dataMap, "live", mode);
                                Log.e("Utils123", "response : " + str_testing.toString());
                                break;
                        }
                        Log.e("MainActivity",str_testing);
                    }catch (Exception e){
                        e.printStackTrace();
                    }



                } else {
                    Toast.makeText(context,"Something wrong", LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Call",t.toString());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context,"No Internet Connection! Please check your Network.",LENGTH_SHORT).show();
            }
        });
    }






    @Override
    public void callback(HashMap<String, Object> tmpMap, String dataType, int mode) {

        if(mode == HOME){
            ArrayList<HashMap<String, Object>> al_details = (ArrayList<HashMap<String, Object>>) tmpMap.get(RESULT);
            if (al_details.size()!=0){obj_adapter = new Adapter_detail(al_details,this);
                recyclerView.setAdapter(obj_adapter);
            }}
    }


    //    public void readmoreclick() {
//        Intent i = new Intent(this,WebviewActivity.class);
//        i.putExtra(INTENT_CHANNELURL,al_details.get(URL_WEB).toString());
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(i);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bookmark,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {

            if (channelname == CHANNELBLOOMBERG || channelname == CHANNELFORTUNE ||
                    channelname == CHANNELCNBC || channelname == CHANNELFINANCIALTIMES ||
                    channelname == CHANNELTHEECONOMIST || channelname == CHANNELBUSINESSINSIDER ||
                    channelname == CHANNELTHEWALLSTREETJOURNAL || channelname == CHANNELBUZZFEED
                    || channelname == CHANNELENTERTAINMENTWEEKLY || channelname == CHANNELMASHABLE
                    || channelname == CHANNELTHELADBIBLE || channelname == CHANNELIGN
                    || channelname == CHANNELPOLYGON || channelname == CHANNELMTVNEWS
                    || channelname == CHANNELBREITBARTNEWS || channelname == CHANNELNATIONALGEOGRAPHIC
                    || channelname == CHANNELNEWSCIENTIST || channelname == CHANNELBBCSPORT
                    || channelname == CHANNELESPN || channelname == CHANNELESPNCRICINFO
                    || channelname == CHANNELFOOTBALLITALIA || channelname == CHANNELFOURFOURTWO
                    || channelname == CHANNELFOXSPORTS || channelname == CHANNELNFLNEWS
                    || channelname == CHANNELTHESPORTBIBLE || channelname == CHANNELTALKSPORT
                    || channelname == CHANNELARSTECHNICA || channelname == CHANNELENGADGET
                    || channelname == CHANNELHACKERNEWS || channelname == CHANNELRECODE
                    || channelname == CHANNELTECHCRUNCH || channelname == CHANNELTECHRADAR
                    || channelname == CHANNELTHENEXTWEB || channelname == CHANNELTHEVERGE

                    ) {
                startActivity(new Intent(NotificationDetail.this, MainActivity.class));
                boolean_category = true;
            }else
            {
                startActivity(new Intent(NotificationDetail.this, MainActivity.class));
            }

        }



        else {
            FirebaseAuth  mAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            if(firebaseUser!=null){
                startActivity(new Intent(this,show.class));}
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
                       startActivity(new Intent(NotificationDetail.this,MainActivity.class));
                        boolean_login = true;
                    }
                });
                dialog.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (channelname == CHANNELBLOOMBERG || channelname == CHANNELFORTUNE ||
                channelname == CHANNELCNBC || channelname == CHANNELFINANCIALTIMES ||
                channelname == CHANNELTHEECONOMIST || channelname == CHANNELBUSINESSINSIDER ||
                channelname == CHANNELTHEWALLSTREETJOURNAL || channelname == CHANNELBUZZFEED
                || channelname == CHANNELENTERTAINMENTWEEKLY || channelname == CHANNELMASHABLE
                || channelname == CHANNELTHELADBIBLE || channelname == CHANNELIGN
                || channelname == CHANNELPOLYGON || channelname == CHANNELMTVNEWS
                || channelname == CHANNELBREITBARTNEWS || channelname == CHANNELNATIONALGEOGRAPHIC
                || channelname == CHANNELNEWSCIENTIST || channelname == CHANNELBBCSPORT
                || channelname == CHANNELESPN || channelname == CHANNELESPNCRICINFO
                || channelname == CHANNELFOOTBALLITALIA || channelname == CHANNELFOURFOURTWO
                || channelname == CHANNELFOXSPORTS || channelname == CHANNELNFLNEWS
                || channelname == CHANNELTHESPORTBIBLE || channelname == CHANNELTALKSPORT
                || channelname == CHANNELARSTECHNICA || channelname == CHANNELENGADGET
                || channelname == CHANNELHACKERNEWS || channelname == CHANNELRECODE
                || channelname == CHANNELTECHCRUNCH || channelname == CHANNELTECHRADAR
                || channelname == CHANNELTHENEXTWEB || channelname == CHANNELTHEVERGE
                ) {

            startActivity(new Intent(NotificationDetail.this, MainActivity.class));
            boolean_category = true;

        }else
        {
            startActivity(new Intent(NotificationDetail.this, MainActivity.class));
        }

    }
}
