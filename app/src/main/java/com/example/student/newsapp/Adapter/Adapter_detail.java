package com.example.student.newsapp.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
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
import com.example.student.newsapp.DetailsActivity;
import com.example.student.newsapp.R;
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

import static com.example.student.newsapp.DetailsActivity.boolean_login;



public class Adapter_detail extends RecyclerView.Adapter<Adapter_detail.ViewHolder> implements ConstVariable{

    ArrayList<HashMap<String, Object>> al_details;
    Context mContext;



    DatabaseReference mDatabaseReference;
    StorageReference mStorageReference;
    ProgressDialog mProgressDialog;
    private Firebase mRoofRef;
    public Uri mImgUri= null;
    FirebaseAuth mAuth;
    private ProgressDialog dialog;
    public  DetailsActivity detailsActivity;
    private String time ,atv,des;
    private boolean setnews = false;
    public String userid , timea;

    public Adapter_detail(ArrayList<HashMap<String,Object>> al_details, Context context) {

        this.al_details = al_details;
        this.mContext=context;


    }


    @Override
    public void callback(HashMap<String, Object> tmpMap, String dataType, int mode) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_image , share, bookmark1;
        TextView tv_title, tv_des , tv_url;
        RelativeLayout rl_detail;
        Button button;



        public ViewHolder(View v) {

            super(v);

            iv_image = (ImageView) v.findViewById(R.id.iv_image);
            tv_title = (TextView) v.findViewById(R.id.tv_title);
            tv_des = (TextView) v.findViewById(R.id.tv_des);
            button = (Button) v.findViewById(R.id.button);
            bookmark1 = (ImageView) v.findViewById(R.id.bookmark1);
            rl_detail = (RelativeLayout) v.findViewById(R.id.rldetail);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //fragment.readmoreclick();

                   

                    Intent i = new Intent(mContext,WebviewActivity.class);
                    i.putExtra(INTENT_CHANNELURL,al_details.get(getLayoutPosition()).get(URL_WEB).toString());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);

                }
            });

            share = (ImageView) v.findViewById(R.id.share);
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
                            bookmark1.setImageResource(R.drawable.bookmarkedstar);
                        }else {

                          bookmark1.setImageResource(R.drawable.bookmarkstar);
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
    public Adapter_detail.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detailactivity, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {

        try {
            Glide.with(Vholder.itemView.getContext()).load(al_details.get(position).get(URLTOIMAGE))
                    .skipMemoryCache(false)
                    .thumbnail(Glide.with(mContext).load(R.drawable.loading_circle))
                    .crossFade()
                    .listener(new RequestListener<Object, GlideDrawable>() {

                        @Override
                        public boolean onException(Exception e, Object model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Log.d("adapter detail glide","issue on position"+al_details.get(position));
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, Object model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                         //   Toast.makeText(mContext, "Success  position"+al_details.get(position), Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                    .into(Vholder.iv_image);
        } catch (Exception e) {
            e.printStackTrace();

        }


        Vholder.tv_title.setText(al_details.get(position).get(TITLE).toString());
        Vholder.tv_des.setText(al_details.get(position).get(DESCRIPTION).toString());

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertaialog);

        final TextView alertmessage = (TextView)dialog.findViewById(R.id.alertmessage);
        final TextView alert = (TextView)dialog.findViewById(R.id.message);
        final Button button = (Button) dialog.findViewById(R.id.cancel_action);
        final Button loginbutton = (Button) dialog.findViewById(R.id.login_now);

        timea= al_details.get(position).get("publishedAt").toString();
        timea = timea.replace(".", ":");
        Log.d("time",timea);
        Vholder.fn_savedstate(timea);



        Vholder.bookmark1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setnews = true;

                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();

                if (user != null) {

                    atv = al_details.get(position).get("title").toString();
                    des = al_details.get(position).get("description").toString();
                    time = al_details.get(position).get("publishedAt").toString();
                    time = time.replace(".", ":");

                    mImgUri = Uri.parse(al_details.get(position).get("urlToImage").toString());


                    mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                    FirebaseAuth firebaseauth = FirebaseAuth.getInstance();
                    userid = firebaseauth.getCurrentUser().getUid();


                    mRoofRef = new Firebase("https://newsly-e074e.firebaseio.com/").child("User_Details").child(userid).child(time + userid);
                    mStorageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://newsly-e074e.appspot.com");

                    mDatabaseReference.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (setnews) {
                                if (dataSnapshot.child("User_Details").child(userid).hasChild(time + userid)) {
                                    mDatabaseReference.child("User_Details").child(userid).child(time + userid).removeValue();
                                    Snackbar.make(Vholder.rl_detail,"Bookmark Removed",Snackbar.LENGTH_SHORT).setActionTextColor(Color.YELLOW).show();
                                    Vholder.bookmark1.setImageResource(R.drawable.bookmarkstar);
                                    setnews = false;
                                }   else {
                                    Snackbar.make(Vholder.rl_detail,"Bookmarked",Snackbar.LENGTH_SHORT).setActionTextColor(Color.YELLOW).show();
                                    final String id = mRoofRef.getKey();
                                    Log.e("Bookmark ID", id);
                                    final Firebase childRef_name = mRoofRef.child("Image_Title");
                                    childRef_name.setValue(atv);
                                    Firebase childdes = mRoofRef.child("Image_des");
                                    childdes.setValue(des);
                                    mRoofRef.child("Image_URL").setValue(mImgUri.toString());
                                    Snackbar.make(Vholder.rl_detail,"Bookmarked",Snackbar.LENGTH_SHORT).setActionTextColor(Color.YELLOW).show();
                                    Vholder.bookmark1.setImageResource(R.drawable.bookmarkedstar);

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


                    detailsActivity = new DetailsActivity();

                    loginbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((Activity)mContext).finish();
                            boolean_login = true;






                        }
                    });
                    dialog.show();
                }





            }
        });


    }

    @Override
    public int getItemCount() {

        return al_details.size();
    }


}



/*
package com.newsapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsapp.Activity.WebviewActivity;
import com.newsapp.Common.ConstVariable;
import com.newsapp.Model.Model_list;
import com.newsapp.R;

import java.util.ArrayList;
import java.util.HashMap;




public class Adapter_detail extends RecyclerView.Adapter<Adapter_detail.ViewHolder> implements ConstVariable {

    ArrayList<HashMap<String, Object>> al_details;
    Activity context;
    Typeface typeface_timesbi_8, typeface_timesbd_8;

    public Adapter_detail(Activity context, ArrayList<HashMap<String, Object>> al_details) {

        this.al_details = al_details;
        this.context = context;
        typeface_timesbi_8 = Typeface.createFromAsset(context.getAssets(), "timesbi_8.ttf");
        typeface_timesbd_8 = Typeface.createFromAsset(context.getAssets(), "timesi_8.ttf");

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_image;
        TextView tv_title, tv_des;
        RelativeLayout rl_click;

        public ViewHolder(View v) {

            super(v);

            iv_image = (ImageView) v.findViewById(R.id.iv_image);
            tv_title = (TextView) v.findViewById(R.id.tv_title);
            tv_des = (TextView) v.findViewById(R.id.tv_des);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent_web = new Intent(context, WebviewActivity.class);
                    intent_web.putExtra(INTENT_CHANNELURL, al_details.get(getLayoutPosition()).get(URL_WEB).toString());
                    intent_web.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent_web);
                    context.overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                }
            });


        }
    }

    @Override
    public Adapter_detail.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detailactivity, parent, false);

        ViewHolder viewHolder1 = new ViewHolder(view);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {

        try {
            Glide.with(context).load(al_details.get(position).get(URLTOIMAGE))
                    .skipMemoryCache(false)
                    .into(Vholder.iv_image);
        } catch (Exception e) {
            e.printStackTrace();

        }
        Vholder.tv_title.setTypeface(typeface_timesbi_8);
        Vholder.tv_des.setTypeface(typeface_timesbd_8);

        Vholder.tv_title.setText(al_details.get(position).get(TITLE).toString());
        Vholder.tv_des.setText(al_details.get(position).get(DESCRIPTION).toString());


    }

    @Override
    public int getItemCount() {

        return al_details.size();
    }


}

*/

