package com.example.student.newsapp.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.student.newsapp.ItemThirdFragment;
import com.example.student.newsapp.categorylist;
import com.example.student.newsapp.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.PersonViewHolder> {

    private ArrayList<categorylist> al_news;

    ItemThirdFragment fragment;


    public CategoryAdapter(ArrayList<categorylist> al_news, ItemThirdFragment fragment) {
        this.al_news = al_news;
        this.fragment=fragment;
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView personPhoto;
        RelativeLayout rl_main;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
            rl_main = (RelativeLayout) itemView.findViewById(R.id.rl_main);
        }
    }



    @Override
    public CategoryAdapter.PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder,final int i) {

        Glide.with(fragment).load(al_news.get(i).getImage_id())
                .skipMemoryCache(false)
                .into(personViewHolder.personPhoto);

        personViewHolder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.fn_click(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (al_news == null) ? 0 : al_news.size();
    }



}
