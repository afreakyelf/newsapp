/*
package com.example.student.newsapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.student.newsapp.Adapter.Adapter_detail;
import com.example.student.newsapp.Common.ConstVariable;
import com.example.student.newsapp.Common.MyListener;
import com.example.student.newsapp.Firebase.show;
import com.firebase.client.Firebase;
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


public class channelwisenewsdetailsActivity extends Fragment implements ConstVariable,MyListener{

    ArrayList<HashMap<String, Object>> al_details;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    Adapter_detail obj_adapter;

    String str_channelname,str_channelsource;

    MyListener ml;

    Dialog new_dialog;
    FirebaseAuth mAuth;

    private Dialog dialog;
    private TextView alert , alertmessage;
    private Button button;;


    public static channelwisenewsdetailsActivity newInstance(){
        channelwisenewsdetailsActivity fragment= new channelwisenewsdetailsActivity();
        return fragment;
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_main3, container, false);


        Firebase.setAndroidContext(getActivity());

        recyclerView = (RecyclerView) rootview.findViewById(R.id.rv);
        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);



        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertaialog);
        alertmessage = (TextView)dialog.findViewById(R.id.alertmessage);
        alert = (TextView)dialog.findViewById(R.id.message);
        button = (Button) dialog.findViewById(R.id.alertbutton);





        listener();



        Bundle bundle =getArguments();
        if(bundle != null) {
            str_channelname = bundle.getString(INTENT_CHANNELNAME);
            str_channelsource = bundle.getString(INTENT_CHANNELSOURCE);
        }

        HashMap<String, Object> HM = new HashMap<>();
        HM.put(SOURCE, str_channelsource);
        if (str_channelname.matches(CHANNELDERTAGESSPIEGEL)){
            HM.put(SORTBY,LATEST);
        }else {
            HM.put(SORTBY,TOP);
        }

        HM.put(APIKEY, APIKEYID);



        fn_response(getActivity(),HM,0);

        Toolbar toolbar = (Toolbar) rootview.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(str_channelname);
        return rootview;
    }



    private void listener(){
       setOnEventListener(this);


    }


    public void setOnEventListener(MyListener ml) {
        this.ml = ml;
    }


    public void fn_response(final Context context, HashMap<String, Object> HM, final int mode){


        final ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please Wait");
        dialog.show();



        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getresponse("articles",HM,"");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //Dismiss Dialog
               dialog.dismiss();

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



                   // else{
//                        dataMap.put(RESULT, null);
//                        Log.e("Utils 165", "response " + result);
                    //}

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
               dialog.dismiss();
                Toast.makeText(context,"No Internet Connection! Please check your Network.",LENGTH_SHORT).show();
            }
        });
    }



    
    
    
    @Override
    public void callback(HashMap<String, Object> tmpMap, String dataType, int mode) {

        if(mode == HOME){
            ArrayList<HashMap<String, Object>> al_details = (ArrayList<HashMap<String, Object>>) tmpMap.get(RESULT);
            if (al_details.size()!=0){
                channelwisenewsdetailsActivity channelwisenewsdetailsActivity = this;
                obj_adapter = new Adapter_detail(al_details,channelwisenewsdetailsActivity,getContext());
                recyclerView.setAdapter(obj_adapter);
            }}
    }


//    public void readmoreclick() {
//        Intent i = new Intent(getActivity(),WebviewActivity.class);
//        i.putExtra(INTENT_CHANNELURL,al_details.get(URL_WEB).toString());
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(i);
//    }
public void fn_dialog()
{
    new_dialog=new Dialog(getActivity());
    Button button;

    new_dialog.setContentView(R.layout.alertaialog);
    button = (Button) new_dialog.findViewById(R.id.alertbutton);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new_dialog.dismiss();
        }
    });
    new_dialog.show();

}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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

            alert.setText("Message");
            alertmessage.setText("Please Login First!");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }


}
*/
