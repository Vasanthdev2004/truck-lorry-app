package com.ruru.routelorry.adepter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ruru.routelorry.R;
import com.ruru.routelorry.model.TotalReviewUserWiseItem;
import com.ruru.routelorry.retrofit.APIClient;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {



    private Context mContext;
    private List<TotalReviewUserWiseItem> typeList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgs)
        public ShapeableImageView imgs;
        @BindView(R.id.txt_name)
        public TextView txtName;
        @BindView(R.id.txt_offerprice)
        public TextView txtOfferprice;
        @BindView(R.id.txt_rates)
        public TextView txtRates;
        @BindView(R.id.txt_textreview)
        public TextView txtTextreview;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ReviewAdapter(Context mContext, List<TotalReviewUserWiseItem> typeList) {
        this.mContext = mContext;
        this.typeList = typeList;



    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(mContext).load(APIClient.BASE_URL + "/" + typeList.get(position).getUserImg()).thumbnail(Glide.with(mContext).load(R.drawable.tprofile)).into(holder.imgs);
        holder.txtName.setText(""+typeList.get(position).getCustomername());
        holder.txtRates.setText(""+typeList.get(position).getRateNumber());
        holder.txtTextreview.setText(""+typeList.get(position).getRateText());



    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }
}