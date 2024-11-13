package com.cscodetech.moverslorry.adepter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cscodetech.moverslorry.R;
import com.cscodetech.moverslorry.model.LoadHistoryDataItem;
import com.cscodetech.moverslorry.retrofit.APIClient;
import com.cscodetech.moverslorry.utils.SessionManager;
import com.cscodetech.moverslorry.utils.Utility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyLoadAdapter extends RecyclerView.Adapter<MyLoadAdapter.MyViewHolder> {


    private Context mContext;
    private List<LoadHistoryDataItem> loadHistoryDataItems;
    private RecyclerTouchListener listener;
    SessionManager sessionManager;

    public interface RecyclerTouchListener {

        public void onClickLoadPost(LoadHistoryDataItem item, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgt)
        public ImageView imgt;
        @BindView(R.id.txt_title)
        public TextView txtTitle;
        @BindView(R.id.txt_price)
        public TextView txtPrice;
        @BindView(R.id.txt_tonne)
        public TextView txtTonne;
        @BindView(R.id.txt_pick)
        public TextView txtPick;
        @BindView(R.id.txt_drop)
        public TextView txtDrop;
        @BindView(R.id.txt_date)
        public TextView txtDate;
        @BindView(R.id.txt_staus)
        public TextView txtStaus;
        @BindView(R.id.txt_distnt)
        public TextView txtDistnt;
        @BindView(R.id.lvl_clicl)
        public LinearLayout lvlClicl;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public MyLoadAdapter(Context mContext, List<LoadHistoryDataItem> typeList, final RecyclerTouchListener listener) {
        this.mContext = mContext;
        this.loadHistoryDataItems = typeList;
        this.listener = listener;
        sessionManager = new SessionManager(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.load_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        LoadHistoryDataItem item = loadHistoryDataItems.get(position);
        Glide.with(mContext).load(APIClient.BASE_URL + "/" + item.getVehicleImg()).thumbnail(Glide.with(mContext).load(R.drawable.tprofile)).into(holder.imgt);
        holder.txtTitle.setText("" + item.getVehicleTitle());
        holder.txtPrice.setText(sessionManager.getStringData(SessionManager.currency) + item.getAmount());
        holder.txtTonne.setText(" /" + item.getAmtType());
        holder.txtPick.setText("" + item.getPickupState());
        holder.txtDrop.setText("" + item.getDropState());
        holder.txtDate.setText("" + Utility.getInstance().parseDateToddMMyy(item.getPostDate()));
        holder.txtStaus.setText("" + item.getLoadStatus());
        holder.txtDistnt.setText("" + item.getLoadDistance());

        holder.lvlClicl.setOnClickListener(view -> listener.onClickLoadPost(item, position));

    }

    @Override
    public int getItemCount() {
        return loadHistoryDataItems.size();
    }

    public List<LoadHistoryDataItem> getItemList() {
        return loadHistoryDataItems;
    }
}