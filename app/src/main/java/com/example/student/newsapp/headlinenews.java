package com.example.student.newsapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student.newsapp.Adapter.HeadlineAdapter;
import com.example.student.newsapp.Common.ConstVariable;
import com.example.student.newsapp.Common.MyListener;
import com.example.student.newsapp.Firebase.show;
import com.firebase.client.Firebase;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

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
import static com.example.student.newsapp.Common.Colors.colors;


public class headlinenews extends Fragment implements ConstVariable,MyListener, SwipeRefreshLayout.OnRefreshListener {

    ArrayList<HashMap<String, Object>> al_details;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    HeadlineAdapter obj_adapter;
    BottomNavigationView bottomNavigationView;
    ImageView nointernet;
    private SwipeRefreshLayout swipeRefreshLayout;

    FirebaseAuth mAuth;


    RelativeLayout relativelayout;
    Dialog new_dialog;
    DatabaseReference mDatabaseReference;
    StorageReference mStorageReference;
    ProgressDialog mProgressDialog;
    private Firebase mRoofRef;
    public Uri mImgUri=null;

    Circle mCircleDrawable;
    public static ProgressBar progressBar;


    String str_channelname,str_channelsource;

    MyListener ml;
    HashMap<String, Object> HM;
    private AdView adView;
    private Dialog dialog;
    private TextView alert , alertmessage;
    private Button button , loginbutton;
    headlinenews headlinenews;



    public static headlinenews newInstance(){
        headlinenews fragment= new headlinenews();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.headlinelayout, container, false);
            recyclerView = (RecyclerView) rootview.findViewById(R.id.rv);
            recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(recyclerViewLayoutManager);

        relativelayout = (RelativeLayout) rootview.findViewById(R.id.relativelayout);


        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                fn_response(getActivity(),HM,0);
            }
        });





        nointernet = (ImageView) rootview.findViewById(R.id.nointernet);

        Firebase.setAndroidContext(getActivity());
        setHasOptionsMenu(true);
        headlinenews headlinenews = this;

        progressBar = (ProgressBar)rootview.findViewById(R.id.progress);
        mCircleDrawable = new Circle();
        mCircleDrawable.setBounds(0, 0, 100, 100);
        mCircleDrawable.setColor(colors[7]);
        progressBar.setIndeterminateDrawable(mCircleDrawable);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.GONE);



        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertaialog);
        alertmessage = (TextView)dialog.findViewById(R.id.alertmessage);
        alert = (TextView)dialog.findViewById(R.id.message);
        button = (Button) dialog.findViewById(R.id.cancel_action);
        loginbutton = (Button) dialog.findViewById(R.id.login_now);


        Toolbar toolbar = (Toolbar) rootview.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Headlines");
        toolbar.setAlpha(1);

        listener();

        Bundle bundle =getArguments();
        if(bundle != null) {
            str_channelname = bundle.getString(INTENT_CHANNELNAME);
            str_channelsource = bundle.getString(INTENT_CHANNELSOURCE);
        }

     HM = new HashMap<>();
        HM.put(SOURCE, str_channelsource);
        if (str_channelname.matches(CHANNELDERTAGESSPIEGEL)){
            HM.put(SORTBY,LATEST);
        }else {
            HM.put(SORTBY,TOP);
        }
        HM.put(APIKEY, APIKEYID);
        fn_response(getActivity(),HM,0);


     /*   mProgressDialog = new ProgressDialog(getActivity());
        mDatabaseReference= FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseauth = FirebaseAuth.getInstance();
        final String userid = firebaseauth.getCurrentUser().getUid();
        mRoofRef = new Firebase("https://firebae-b5687.firebaseio.com/").child("User_Details").child(userid).push();
        mStorageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://firebae-b5687.appspot.com");
*/


        return rootview;
    }
/*

    @Override
    public void onRefresh() {
        fn_response(getActivity(),HM,0);

    }
*/



    private void listener(){
        setOnEventListener(this);


    }






    public void setOnEventListener(MyListener ml) {
        this.ml = ml;
    }


    public void fn_response(final Context context, HashMap<String, Object> HM, final int mode){


        progressBar.setVisibility(View.VISIBLE);
        nointernet.setVisibility(View.GONE);


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getresponse("articles",HM,"");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressBar.setVisibility(View.GONE);
                List<HashMap<String, String>> dataList = new ArrayList<>();
                if(response.isSuccessful()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    try {
                        HashMap<String, Object> dataMap = null;
                        String str_testing = response.body().string();
                        dataMap = new HashMap<String, Object>();
                        JSONObject jsonObject = new JSONObject(str_testing);
                        JSONArray data= jsonObject.getJSONArray("articles");
                        for(int i=0;i<data.length();i++){
                            JSONObject d = data.getJSONObject(i);
                          //  String description = d.getString("description");
                            String title = d.getString("title");
                            String Image = d.getString("urlToImage");
                            String url = d.getString("url");
                            String des = d.getString("description");
                            String time = d.getString("publishedAt");
                            //tmp hashmap for single contact
                            HashMap<String,String> samachar = new HashMap<>();

                            samachar.put("title",title);
                            samachar.put("urlToImage",Image);
                            samachar.put("url",url);
                            samachar.put("description",des);
                            samachar.put("publishedAt",time);

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
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(context,"Something wrong", LENGTH_SHORT).show();
                }
                nointernet.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Call",t.toString());
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setVisibility(View.GONE);
                nointernet.setImageResource(R.drawable.nointernet);
                nointernet.setVisibility(View.VISIBLE);
            }
        });
    }






    @Override
    public void callback(HashMap<String, Object> tmpMap, String dataType, int mode) {

        if(mode == HOME){
            ArrayList<HashMap<String, Object>> al_details = (ArrayList<HashMap<String, Object>>) tmpMap.get(RESULT);
            if (al_details.size()!=0){

                obj_adapter = new HeadlineAdapter(al_details,headlinenews,getContext());
                recyclerView.setAdapter(obj_adapter);
            }}
    }


    /*public void bookmark(int position) {

      String atv =  al_details.get(position).get("title").toString();
        mImgUri = Uri.parse(al_details.get(position).get("urlToImage").toString());
      imgview.setImageURI(mImgUri);
        StorageReference filePath = mStorageReference.child("User_Images").child(mImgUri.getLastPathSegment());

        Firebase childRef_name = mRoofRef.child("Image_Title");
        childRef_name.setValue(atv);


        filePath.putFile(mImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Uri downloadUri = taskSnapshot.getDownloadUrl();  //Ignore This error

                mRoofRef.child("Image_URL").setValue(downloadUri.toString());

                Glide.with(getActivity())
                        .load(downloadUri)
                        .crossFade()
                        .placeholder(R.drawable.loading)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(imgview);
                Toast.makeText(getActivity(), "Updated.", Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });
    }*/


//    public void readmoreclick() {
//        Intent i = new Intent(getActivity(),WebviewActivity.class);
//        i.putExtra(INTENT_CHANNELURL,al_details.get(URL_WEB).toString());
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(i);
//    }





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
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onRefresh() {
        fn_response(getActivity(),HM,0);
        swipeRefreshLayout.setRefreshing(true);
        nointernet.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

    }


}
