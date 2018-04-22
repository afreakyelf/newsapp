package com.example.student.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.student.newsapp.Categorywisechannel;
import com.example.student.newsapp.DetailsActivity;
import com.example.student.newsapp.Model_list;
import com.example.student.newsapp.R;
import com.example.student.newsapp.serializablelistofcategorychannel;

import java.util.ArrayList;

import static com.example.student.newsapp.Common.ConstVariable.INTENT_CHANNELNAME;
import static com.example.student.newsapp.Common.ConstVariable.INTENT_CHANNELSOURCE;
import static com.example.student.newsapp.Adapter.RVAdapter.channelsource2;
import static com.example.student.newsapp.Adapter.RVAdapter.channelname2;
public class categorychannel extends RecyclerView.Adapter<categorychannel.PersonViewHolder> {

    private ArrayList<serializablelistofcategorychannel> al_news;
    private ArrayList<Model_list> al;
    public static int countyc;
    public static String channelname3;
    public static String channelsource3;

    Categorywisechannel fragment;
    Context context;

    public categorychannel(ArrayList<serializablelistofcategorychannel> al_news, Categorywisechannel fragment, Context context) {
        this.al_news = al_news;
        this.context = context;
        this.fragment=fragment;
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        ImageView personPhoto;
        RelativeLayout rl_main;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
            rl_main = (RelativeLayout) itemView.findViewById(R.id.rl_main);
        }
    }



    @Override
    public categorychannel.PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
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

        countyc = al_news.get(i).setCount(countt);
        Log.d("count "+i, String.valueOf(countyc));


            channelname2 = al_news.get(i).getStr_name();
            channelsource2 = al_news.get(i).getStr_channel();
        }





    @Override
    public int getItemCount() {
        return (al_news == null) ? 0 : al_news.size();
    }



}
