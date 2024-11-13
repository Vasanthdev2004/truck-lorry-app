package com.cscodetech.moverslorry.adepter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cscodetech.moverslorry.R;
import com.cscodetech.moverslorry.model.BidLorryDataItem;
import com.cscodetech.moverslorry.retrofit.APIClient;
import com.cscodetech.moverslorry.ui.AddLorryActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeLorryAdapter extends RecyclerView.Adapter<HomeLorryAdapter.MyViewHolder> {


    private Context mContext;
    private List<BidLorryDataItem> typeList;



    public interface RecyclerTouchListener {
        public void onClickHomeLorryInfo(String item, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgt)
        public ImageView imgt;
        @BindView(R.id.txt_title)
        public TextView txtTitle;
        @BindView(R.id.txt_routs)
        public TextView txtRouts;
        @BindView(R.id.txt_rcverification)
        public TextView txtRcverification;
        @BindView(R.id.txt_lorrynumber)
        public TextView txtLorrynumber;
        @BindView(R.id.lvl_clicl)
        public LinearLayout lvlClicl;
        @BindView(R.id.img_edit)
        public ImageView imgEdit;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public HomeLorryAdapter(Context mContext, List<BidLorryDataItem> typeList) {
        this.mContext = mContext;
        this.typeList = typeList;



    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homelorry_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(mContext).load(APIClient.BASE_URL + "/" + typeList.get(position).getLorryImg()).thumbnail(Glide.with(mContext).load(R.drawable.tprofile)).into(holder.imgt);
        holder.txtTitle.setText("" + typeList.get(position).getLorryTitle());
        holder.txtRouts.setText(typeList.get(position).getRoutes()+"+ "+mContext.getResources().getString(R.string.routs));

        if (typeList.get(position).getRcVerify().equalsIgnoreCase("1")) {
            holder.txtRcverification.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.ic_document_done), null, null, null);
            holder.txtRcverification.setText(mContext.getResources().getString(R.string.rc_verified));
        }else if(typeList.get(position).getRcVerify().equalsIgnoreCase("2")) {
            holder.txtRcverification.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.ic_unverified), null, null, null);
            holder.txtRcverification.setText("Document Reupload");
        }else {
            holder.txtRcverification.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.ic_document_process), null, null, null);
            holder.txtRcverification.setText(mContext.getResources().getString(R.string.rc_verifiednot));

        }
        holder.txtLorrynumber.setText("" + typeList.get(position).getLorryNo());

        holder.txtRouts.setOnClickListener(view -> displayAddressWindow(holder.txtRouts, typeList.get(position).getTotalRoutes()));
        holder.imgEdit.setOnClickListener(view -> mContext.startActivity(new Intent(mContext, AddLorryActivity.class).putExtra("my_class",typeList.get(position))));
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    private void displayAddressWindow(View anchorView, String address[]) {
        Activity activity = (Activity) mContext;
        PopupWindow popup = new PopupWindow(mContext);
        View layout = activity.getLayoutInflater().inflate(R.layout.popup_content1, null);

        ListView myListview = (ListView) layout.findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.popup_content1_chail, address);
        myListview.setAdapter(adapter);
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
}