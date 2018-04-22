package com.example.student.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.student.newsapp.Common.ConstVariable;
import com.example.student.newsapp.DetailsActivity;
import com.example.student.newsapp.ItemOneFragment;
import com.example.student.newsapp.Model_list;
import com.example.student.newsapp.R;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> implements ConstVariable {

    public static ArrayList<Model_list> al_news;
    public static int county;




    public static String channelname;
    public static String channelsource;
    public static String channelname2;
    public static String channelsource2;

    private ItemOneFragment fragment;
    private Context context;
    SharedPreferences sharedPreferences;
    private int count = 0;


    public RVAdapter(ArrayList<Model_list> al_news, ItemOneFragment fragment, Context context) {
        this.al_news = al_news;
        this.fragment=fragment;
        this.context = context;
    }

    @Override
    public void callback(HashMap<String, Object> tmpMap, String dataType, int mode) {

    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        ImageView personPhoto;
        RelativeLayout rl_main;

        PersonViewHolder(View itemView) {
            super(itemView);
            //cv = (CardView) itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
            rl_main = (RelativeLayout) itemView.findViewById(R.id.rl_main);
        }
    }



    @Override
    public RVAdapter.PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main2, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder,final int i) {



        personViewHolder.personName.setText(al_news.get(i).getStr_name());
        Glide.with(fragment).load(al_news.get(i).getImage_id())
                .skipMemoryCache(false)
                .into(personViewHolder.personPhoto);




        personViewHolder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         /*       context.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);*/

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(INTENT_CHANNELSOURCE,al_news.get(i).getStr_channel());
                intent.putExtra(INTENT_CHANNELNAME,al_news.get(i).getStr_name());

                thiswasclicked(i);


                context.startActivity(intent);

            }
        });
    }

    private void thiswasclicked(int i) {
       int countt = al_news.get(i).getCount();
        countt++;
        county = al_news.get(i).setCount(countt);
        Log.d("count "+i, String.valueOf(county));

        channelname2 = al_news.get(i).getStr_name();
        channelsource2 = al_news.get(i).getStr_channel();

        SharedPreferences sharedPreferences = context.getSharedPreferences("pref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("count",county);
        editor.commit();
        int counts = sharedPreferences.getInt("count",county);
        Log.d("Shared Pref count " , String.valueOf(counts));

       /* if(Count<4){
            channelname = "Google News";
            channelsource ="google-news";

        }
        else
        {

            Toast.makeText(context,al_news.get(i).getStr_name(),Toast.LENGTH_SHORT).show();
            channelname = al_news.get(i).getStr_name();
            channelsource = al_news.get(i).getStr_channel();
        }*/


    }

    @Override
    public int getItemCount() {
        return (al_news == null) ? 0 : al_news.size();
    }



}

/*

package com.example.chivu.imagep.Adapter;

        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.Typeface;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.example.chivu.imagep.Common.ConstVariable;
        import com.example.chivu.imagep.DetailsActivity;
        import com.example.chivu.imagep.Model_list;
        import com.example.chivu.imagep.R;

        import java.util.ArrayList;
        import java.util.HashMap;


public class Adapter_list extends RecyclerView.Adapter<Adapter_list.ViewHolder> implements ConstVariable{

    ArrayList<Model_list> al_news;
    Activity context;
    Typeface timesbi_8;

    public Adapter_list(Activity context, ArrayList<Model_list> al_news) {

        this.al_news = al_news;
        this.context = context;


    }


    @Override
    public void callback(HashMap<String, Object> tmpMap, String dataType, int mode) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_image;
        RelativeLayout rl_main;
        TextView tv_channelname;

        public ViewHolder(View v) {

            super(v);

            iv_image = (ImageView) v.findViewById(R.id.iv_logo);

            rl_main = (RelativeLayout)v.findViewById(R.id.rl_main);
            tv_channelname= (TextView)v.findViewById(R.id.tv_channelname);



        }
    }

    @Override
    public Adapter_list.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_newlist, parent, false);

        ViewHolder viewHolder1 = new ViewHolder(view);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position) {

        Glide.with(context).load(al_news.get(position).getImage_id())
                .skipMemoryCache(false)
                .into(Vholder.iv_image);

//        Vholder.tv_channelname.setTypeface(timesbi_8);
        Vholder.tv_channelname.setText(al_news.get(position).getStr_name());


        Vholder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,DetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(INTENT_CHANNELSOURCE,al_news.get(position).getStr_channel());
                intent.putExtra(INTENT_CHANNELNAME,al_news.get(position).getStr_name());
                context.startActivity(intent);




            }
        });

    }

    @Override
    public int getItemCount() {

        return al_news.size();
    }


}


*/
