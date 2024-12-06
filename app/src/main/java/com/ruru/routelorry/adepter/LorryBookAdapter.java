package com.ruru.routelorry.adepter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ruru.routelorry.R;
import com.ruru.routelorry.model.LorrydataItem;
import com.ruru.routelorry.retrofit.APIClient;
import com.ruru.routelorry.ui.BiderInfoActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LorryBookAdapter extends RecyclerView.Adapter<LorryBookAdapter.MyViewHolder> {



    private Context mContext;
    private List<LorrydataItem> lorrydataItemList;
    private RecyclerTouchListener listener;


    public interface RecyclerTouchListener {
        public void onClickLorryInfo(LorrydataItem item, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_trak)
        public ImageView imgTrak;
        @BindView(R.id.txt_trackname)
        public TextView txtTrackname;
        @BindView(R.id.txt_location)
        public TextView txtLocation;
        @BindView(R.id.txt_location2)
        public TextView txtLocation2;
        @BindView(R.id.txt_vnumber)
        public TextView txtVnumber;
        @BindView(R.id.txt_weight)
        public TextView txtWeight;
        @BindView(R.id.txt_routs)
        public TextView txtRouts;
        @BindView(R.id.txt_rc_verified)
        public TextView txtRcVerified;
        @BindView(R.id.imgs)
        public ShapeableImageView imgs;
        @BindView(R.id.txt_name)
        public TextView txtName;
        @BindView(R.id.txt_rates)
        public TextView txtRates;
        @BindView(R.id.txt_booknow)
        public TextView txtBooknow;
        @BindView(R.id.lvl_click)
        public LinearLayout lvlClick;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public LorryBookAdapter(Context mContext, List<LorrydataItem> typeList, final RecyclerTouchListener listener) {
        this.mContext = mContext;
        this.lorrydataItemList = typeList;
        this.listener = listener;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lorry_book_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        LorrydataItem item = lorrydataItemList.get(position);
        holder.txtTrackname.setText(item.getLorryTitle());
        holder.txtLocation.setText(item.getPickupPoint());
        holder.txtLocation2.setText(item.getDropPoint());
        holder.txtVnumber.setText(item.getLorryNo());
        holder.txtRates.setText(item.getReview());
        holder.txtWeight.setText("" + item.getWeight() + " " + mContext.getResources().getString(R.string.tonnes));
        holder.txtRouts.setText( item.getRoutesCount()+" "+ mContext.getResources().getString(R.string.routes));
        holder.txtRcVerified.setText(R.string.rc_verified);
        if (item.getRcVerify().equalsIgnoreCase("1")) {
            holder.txtRcVerified.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.ic_document_done), null, null, null);
        }

        holder.txtName.setText(item.getLorryOwnerTitle());
        Glide.with(mContext).load(APIClient.BASE_URL + "/" + item.getLorryOwnerImg()).thumbnail(Glide.with(mContext).load(R.drawable.tprofile)).into(holder.imgs);
        Glide.with(mContext).load(APIClient.BASE_URL + "/" + item.getLorryImg()).thumbnail(Glide.with(mContext).load(R.drawable.tprofile)).into(holder.imgTrak);

        String lastWord = item.getCurrLocation().substring(item.getCurrLocation().lastIndexOf(",") + 3);
        Log.e("Location ", "--" + lastWord);


        holder.txtBooknow.setOnClickListener(view -> listener.onClickLorryInfo(item, position));

        holder.imgs.setOnClickListener(view -> mContext.startActivity(new Intent(mContext, BiderInfoActivity.class)
                .putExtra("uid", item.getLorryOwnerId())));
    }

    @Override
    public int getItemCount() {
        return lorrydataItemList.size();
    }
}