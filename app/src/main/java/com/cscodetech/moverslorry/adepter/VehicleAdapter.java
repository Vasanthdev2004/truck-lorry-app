package com.ruru.routelorry.adepter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ruru.routelorry.R;
import com.ruru.routelorry.model.VehicleDataItem;
import com.ruru.routelorry.retrofit.APIClient;
import com.ruru.routelorry.ui.AddLorryActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder> {


    private Context mContext;
    int tonne;
    String isSelect;
    private List<VehicleDataItem> typeList;
    private RecyclerTouchListener listener;
    private int lastCheckedPos = -1;


    public interface RecyclerTouchListener {
        public void onClickVehicleInfo(VehicleDataItem item, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        public ImageView img;
        @BindView(R.id.txt_name)
        public TextView txtName;
        @BindView(R.id.txt_type)
        public TextView txtType;
        @BindView(R.id.lvl_click)
        public LinearLayout lvlClick;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public VehicleAdapter(Context mContext, List<VehicleDataItem> typeList, String isSelect, final RecyclerTouchListener listener) {
        this.mContext = mContext;
        this.typeList = typeList;
        this.listener = listener;
        this.isSelect = isSelect;
       lastCheckedPos = -1;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        VehicleDataItem item = typeList.get(position);
        Glide.with(mContext).load(APIClient.BASE_URL + "/" + item.getImg()).thumbnail(Glide.with(mContext).load(R.drawable.tprofile)).into(holder.img);
        holder.txtName.setText(item.getTitle());
        holder.txtType.setText(item.getMinWeight() + " - " + item.getMaxWeight() + " Tonnes");

        if (lastCheckedPos == position) {
            holder.lvlClick.setBackground(mContext.getDrawable(R.drawable.round_boxfill));
        } else {
            holder.lvlClick.setBackground(mContext.getDrawable(R.drawable.round_box));
        }

        if (isSelect!=null && isSelect.equalsIgnoreCase(item.getTitle())) {
            isSelect = "0";
            if (tonne < Integer.parseInt(item.getMaxWeight())) {
                lastCheckedPos = position;
                listener.onClickVehicleInfo(item, position);
                //delay button
                holder.lvlClick.postDelayed(() -> {
                    notifyDataSetChanged();

                }, 800);

            }

        }
        holder.lvlClick.setOnClickListener(view -> {
            Log.e("Liclclc", "djk");
            Log.e("Liclclc", ""+tonne);
            if(!TextUtils.isEmpty(AddLorryActivity.getInstance().edTypeweight.getText())){

                if (Integer.parseInt(AddLorryActivity.getInstance().edTypeweight.getText().toString()) < Integer.parseInt(item.getMaxWeight())) {
                    lastCheckedPos = position;
                    listener.onClickVehicleInfo(item, position);
                    notifyDataSetChanged();
                }else {
                    AddLorryActivity.getInstance().tempAddLorry.setVehicleId(null);
                    Toast.makeText(mContext,"The vehicle types do not match to the number of tonnes.",Toast.LENGTH_SHORT).show();
                }
            }else {
                AddLorryActivity.getInstance().edTypeweight.setError("");
            }


        });
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }
}