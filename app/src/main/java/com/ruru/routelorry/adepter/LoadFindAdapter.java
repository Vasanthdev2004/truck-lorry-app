package com.ruru.routelorry.adepter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ruru.routelorry.R;
import com.ruru.routelorry.model.LoaddataItem;
import com.ruru.routelorry.retrofit.APIClient;
import com.ruru.routelorry.ui.BiderInfoActivity;
import com.ruru.routelorry.utils.SessionManager;
import com.ruru.routelorry.utils.Utility;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadFindAdapter extends RecyclerView.Adapter<LoadFindAdapter.MyViewHolder> {


    private Context mContext;
    private List<LoaddataItem> loadHistoryDataItems;
    private RecyclerTouchListener listener;
    SessionManager sessionManager;

    public interface RecyclerTouchListener {

        public void onClickFindLoad(LoaddataItem item, int position);

        public void onClickBidDeletLoad(LoaddataItem item, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgt)
        public ImageView imgt;
        @BindView(R.id.txt_title)
        public TextView txtTitle;
        @BindView(R.id.txt_price)
        public TextView txtPrice;
        @BindView(R.id.txt_tonnefix)
        public TextView txtTonnefix;
        @BindView(R.id.txt_pick)
        public TextView txtPick;
        @BindView(R.id.txt_drop)
        public TextView txtDrop;
        @BindView(R.id.txt_date)
        public TextView txtDate;
        @BindView(R.id.txt_tonne)
        public TextView txtTonne;
        @BindView(R.id.txt_material)
        public TextView txtMaterial;
        @BindView(R.id.imgs)
        public ShapeableImageView imgs;
        @BindView(R.id.txt_name)
        public TextView txtName;
        @BindView(R.id.txt_rates)
        public TextView txtRates;
        @BindView(R.id.txt_booknow)
        public TextView txtBooknow;
        @BindView(R.id.txt_priceb)
        public TextView txtPriceb;
        @BindView(R.id.txt_tonnefixb)
        public TextView txtTonnefixb;
        @BindView(R.id.txt_dist)
        public TextView txtDist;
        @BindView(R.id.lvl_clicl)
        public LinearLayout lvlClicl;
        @BindView(R.id.img_delete)
        public ImageView imgDelete;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public LoadFindAdapter(Context mContext, List<LoaddataItem> typeList, final RecyclerTouchListener listener) {
        this.mContext = mContext;
        this.loadHistoryDataItems = typeList;
        this.listener = listener;
        sessionManager = new SessionManager(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.findload_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        LoaddataItem item = loadHistoryDataItems.get(position);
        Glide.with(mContext).load(APIClient.BASE_URL + "/" + item.getVehicleImg()).thumbnail(Glide.with(mContext).load(R.drawable.tprofile)).into(holder.imgt);
        Glide.with(mContext).load(APIClient.BASE_URL + "/" + item.getOwnerImg()).thumbnail(Glide.with(mContext).load(R.drawable.tprofile)).into(holder.imgs);
        holder.txtTitle.setText("" + item.getVehicleTitle());
        holder.txtPrice.setText(sessionManager.getStringData(SessionManager.currency) + item.getAmount());
        holder.txtTonne.setText("" + item.getWeight());
        holder.txtTonnefix.setText(" /" + item.getAmtType());
        holder.txtPick.setText("" + item.getPickupState());
        holder.txtDrop.setText("" + item.getDropState());
        holder.txtDate.setText("" + Utility.getInstance().parseDateToddMMyy(item.getPostDate()));
        holder.txtName.setText("" + item.getOwnerName());
        holder.txtRates.setText("" + item.getOwnerRating());
        holder.txtMaterial.setText("" + item.getMaterialName());
        holder.txtDist.setText("" + item.getLoadDistance());

        if (item.getIsBid() == 0) {
            holder.txtBooknow.setVisibility(View.VISIBLE);
            holder.imgDelete.setVisibility(View.GONE);
        } else if (item.getIsBid() == 1) {
            holder.txtBooknow.setVisibility(View.GONE);
            holder.imgDelete.setVisibility(View.VISIBLE);
            holder.txtTonnefixb.setText(" /" + item.getBidAmountType());
            holder.txtPriceb.setText(sessionManager.getStringData(SessionManager.currency) + item.getBidAmount());

            holder.txtTonnefixb.setVisibility(View.VISIBLE);
            holder.txtPriceb.setVisibility(View.VISIBLE);

        } else if (item.getIsBid() == 2) {
            holder.txtBooknow.setVisibility(View.GONE);
            holder.imgDelete.setVisibility(View.GONE);
            holder.txtTonnefixb.setText(" /" + item.getBidAmountType());
            holder.txtPriceb.setText(sessionManager.getStringData(SessionManager.currency) + item.getBidAmount());
        } else if (item.getIsBid() == 3) {
            holder.txtBooknow.setVisibility(View.GONE);
            holder.imgDelete.setVisibility(View.VISIBLE);
            holder.txtTonnefixb.setVisibility(View.VISIBLE);
            holder.txtTonnefixb.setText("Rejected");

        }

        holder.txtBooknow.setOnClickListener(view -> {
            if (item.getIsBid() == 0)
                listener.onClickFindLoad(item, position);
        });

        holder.imgDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("Do you want to remove bid ?");
            builder.setTitle("Remove !");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                dialog.cancel();
                listener.onClickBidDeletLoad(item, position);

            });
            builder.setNegativeButton("No", (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        holder.txtPick.setOnClickListener(view -> displayAddressWindow(holder.txtPick, item.getPickupPoint()));

        holder.txtDrop.setOnClickListener(view -> displayAddressWindow(holder.txtDrop, item.getDropPoint()));

        holder.imgs.setOnClickListener(view -> mContext.startActivity(new Intent(mContext, BiderInfoActivity.class)
                .putExtra("uid", item.getUid())));


    }

    @Override
    public int getItemCount() {
        return loadHistoryDataItems.size();
    }


    private void displayAddressWindow(View anchorView, String address) {
        Activity activity = (Activity) mContext;
        PopupWindow popup = new PopupWindow(mContext);
        View layout = activity.getLayoutInflater().inflate(R.layout.popup_content, null);
        TextView textView = layout.findViewById(R.id.txt_address);
        textView.setText("" + address);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setWidth(android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        popup.setBackgroundDrawable(new ColorDrawable());
        popup.showAsDropDown(anchorView);
    }

    public void bidSenditem(int pos) {
        LoaddataItem item = loadHistoryDataItems.get(pos);
        item.setIsBid(1);

        loadHistoryDataItems.set(pos, item);
        notifyDataSetChanged();
    }

    public void bidRemoveitem(int pos) {
        Log.e("jfjfj", "ljalkfjasklfj" + pos);
        LoaddataItem item = loadHistoryDataItems.get(pos);
        item.setIsBid(0);
        loadHistoryDataItems.set(pos, item);
        notifyDataSetChanged();
    }
}