package com.ruru.routelorry.adepter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ruru.routelorry.R;
import com.ruru.routelorry.model.FindLoadDataItem;
import com.ruru.routelorry.utils.SessionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VehicleTypeAdapter extends RecyclerView.Adapter<VehicleTypeAdapter.MyViewHolder> {


    private Context mContext;
    private List<FindLoadDataItem> typeList;
    private RecyclerTouchListener listener;
    private int lastCheckedPos = 0;
    SessionManager sessionManager;

    public interface RecyclerTouchListener {
        public void onClickVehicleTypeInfo(String item, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_type)
        public TextView txtType;
        @BindView(R.id.txt_tonne)
        public TextView txtTonne;
        @BindView(R.id.lvl_click)
        public LinearLayout lvlClick;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public VehicleTypeAdapter(Context mContext, List<FindLoadDataItem> typeList, final RecyclerTouchListener listener) {
        this.mContext = mContext;
        this.typeList = typeList;
        this.listener = listener;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_type_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (lastCheckedPos == position) {
            holder.lvlClick.setBackground(mContext.getDrawable(R.drawable.rounded_boxfill));
        } else {
            holder.lvlClick.setBackground(mContext.getDrawable(R.drawable.rounded_box));
        }

        holder.txtType.setText("" + typeList.get(position).getTitle());

        holder.txtTonne.setText("" + typeList.get(position).getMinWeight() + " - " + typeList.get(position).getMaxWeight());

        holder.lvlClick.setOnClickListener(view -> {
            lastCheckedPos = position;
            listener.onClickVehicleTypeInfo("", position);
            notifyDataSetChanged();

        });
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }
}