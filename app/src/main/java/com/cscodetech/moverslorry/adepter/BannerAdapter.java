package com.cscodetech.moverslorry.adepter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import com.cscodetech.moverslorry.R;
import com.cscodetech.moverslorry.model.BannerItem;
import com.cscodetech.moverslorry.retrofit.APIClient;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MyViewHolder> {
    private Context mContext;
    private List<BannerItem> itemList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;


        public MyViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.img);
        }
    }

    public BannerAdapter(Context mContext, List<BannerItem> categoryList) {
        this.mContext = mContext;
        this.itemList = categoryList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banner_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        String item = itemList.get(position).getImg();
        Glide.with(mContext).load(APIClient.BASE_URL + "/" + item).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }
}