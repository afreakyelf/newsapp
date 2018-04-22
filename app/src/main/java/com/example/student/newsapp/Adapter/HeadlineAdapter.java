package com.example.student.newsapp.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.student.newsapp.Common.ConstVariable;
import com.example.student.newsapp.Common.WebviewActivity;
import com.example.student.newsapp.ItemFourFragment;
import com.example.student.newsapp.MainActivity;
import com.example.student.newsapp.R;
import com.example.student.newsapp.headlinenews;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;


public class HeadlineAdapter extends RecyclerView.Adapter<HeadlineAdapter.ViewHolder>  implements ConstVariable{

    ArrayList<HashMap<String, Object>> al_details;
    headlinenews fragment;
    Context mContext;

    public static boolean boolean_id;
    boolean setnews = false;

    ImageView iv_image;
    TextView tv_title;


    Snackbar snackbar;

    DatabaseReference mDatabaseReference;
    StorageReference mStorageReference;
    ProgressDialog mProgressDialog;
    private Firebase mRoofRef;
    public Uri mImgUri= null;
    FirebaseAuth mAuth;

    public String  atv, des;
    public static String time;
    FirebaseAuth firebaseauth;
    String userid , timea;




    public HeadlineAdapter(ArrayList<HashMap<String,Object>> al_details, headlinenews fragment, Context context) {

        this.al_details = al_details;
        this.fragment=fragment;
        this.mContext=context;
    }


    @Override
    public HeadlineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.headlines, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);

        return viewHolder1;
    }


    @Override
    public void callback(HashMap<String, Object> tmpMap, String dataType, int mode) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_image ;
        public ImageView share ,bookmark;
        TextView tv_title;
        RelativeLayout rl_click;
        Button button;



        public ViewHolder(View v) {

            super(v);

            iv_image = (ImageView) v.findViewById(R.id.imageview);
            tv_title = (TextView) v.findViewById(R.id.tv1);
            rl_click = (RelativeLayout) v.findViewById(R.id.rlheadline);
            share = (ImageView) v.findViewById(R.id.share);
            bookmark = (ImageView) v.findViewById(R.id.bookmark);

            // readmore = (TextView) v.findViewById(R.id.readmore);




            tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext,WebviewActivity.class);
                    i.putExtra(INTENT_CHANNELURL,al_details.get(getLayoutPosition()).get(URL_WEB).toString());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                }
            });


            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareintent = new Intent(Intent.ACTION_SEND);
                    shareintent.setType("text/plain");
                    shareintent.putExtra(Intent.EXTRA_TEXT,al_details.get(getLayoutPosition()).get(URL_WEB).toString());
                    mContext.startActivity(Intent.createChooser(shareintent,"Share Via"));
                }
            });

        }


        public void fn_savedstate(final String timea) {
            mAuth = FirebaseAuth.getInstance();
            final FirebaseUser user = mAuth.getCurrentUser();

            if(user!=null) {
                userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("User_Details").child(userid).hasChild(timea+userid)){
                            bookmark.setImageResource(R.drawable.bookmarkedstar);
                        }else {
                          bookmark.setImageResource(R.drawable.bookmarkstar);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }else {


            }



        }
    }


    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {



        try {
            Glide.with(mContext).load(al_details.get(position).get(URLTOIMAGE))
                    .thumbnail( Glide.with(mContext).load(R.drawable.loading_icon))
                    .skipMemoryCache(false)
                    .dontAnimate()
                    .listener(new RequestListener<Object, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, Object model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Log.d("headline glide issue ","issue on position"+al_details.get(position).get("title"));
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, Object model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            Log.d("headline glide success","Success on position"+al_details.get(position).get("title"));
                            Vholder.iv_image.setImageDrawable(resource);
                            return false;
                        }
                            })
                    .into(Vholder.iv_image);

            Log.d("headline link" + position, (String) al_details.get(position).get(URLTOIMAGE));

        } catch (Exception e) {
            e.printStackTrace();

        }

        Vholder.tv_title.setText(al_details.get(position).get(TITLE).toString());


        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertaialog);

        final TextView alertmessage = (TextView)dialog.findViewById(R.id.alertmessage);
        final TextView alert = (TextView)dialog.findViewById(R.id.message);
        final Button button = (Button) dialog.findViewById(R.id.cancel_action);
        final Button loginbutton = (Button) dialog.findViewById(R.id.login_now);





    //    boolean_id = false;
        timea= al_details.get(position).get("publishedAt").toString();
        timea = timea.replace(".", ":");
        Log.d("time",timea);
        Vholder.fn_savedstate(timea);




       Vholder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setnews = true;

                mAuth = FirebaseAuth.getInstance();
                final FirebaseUser user = mAuth.getCurrentUser();



                    if (user != null) {

                        atv = al_details.get(position).get("title").toString();
                        des = al_details.get(position).get("description").toString();
                        time = al_details.get(position).get("publishedAt").toString();
                        time = time.replace(".", ":");

                        mImgUri = Uri.parse(al_details.get(position).get("urlToImage").toString());




                        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                        FirebaseAuth firebaseauth = FirebaseAuth.getInstance();
                        final String userid = firebaseauth.getCurrentUser().getUid();


                        mRoofRef = new Firebase("https://newsly-e074e.firebaseio.com/").child("User_Details").child(userid).child(time + userid);
                        mStorageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://newsly-e074e.appspot.com");

                        mDatabaseReference.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (setnews) {
                                    if (dataSnapshot.child("User_Details").child(userid).hasChild(time + userid)) {
                                        mDatabaseReference.child("User_Details").child(userid).child(time + userid).removeValue();
                                        Vholder.bookmark.setImageResource(R.drawable.bookmarkstar);
                                        Snackbar.make(Vholder.rl_click,"Bookmark removed",Snackbar.LENGTH_SHORT).setActionTextColor(Color.YELLOW).show();
                                        setnews = false;
                                    }
                                    else {
                                        Vholder.bookmark.setImageResource(R.drawable.bookmarkedstar);
                                        final String id = mRoofRef.getKey();
                                        Log.e("Bookmark ID", id);
                                        final Firebase childRef_name = mRoofRef.child("Image_Title");
                                        childRef_name.setValue(atv);
                                        Firebase childdes = mRoofRef.child("Image_des");
                                        childdes.setValue(des);
                                        mRoofRef.child("Image_URL").setValue(mImgUri.toString());
                                        Snackbar.make(Vholder.rl_click,"Bookmarked",Snackbar.LENGTH_SHORT).show();


                                    }
                                }




                            }

                            //end of datachange

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });





                    }



                else {
                    alert.setText("Alert");
                    alertmessage.setText("Please Login First .");
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
                            FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content, fragment);

                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            ((MainActivity)mContext).fn_testing();
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }

            }


       });




    }

    private void abc(final String time) {

    }


    @Override
    public int getItemCount() {

        return al_details.size();
    }



}



