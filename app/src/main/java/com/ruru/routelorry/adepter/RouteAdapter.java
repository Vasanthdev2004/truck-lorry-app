package com.ruru.routelorry.adepter;

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
import com.ruru.routelorry.R;
import com.ruru.routelorry.model.StateDataItem;
import com.ruru.routelorry.retrofit.APIClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.MyViewHolder> {


    private Context mContext;
    int tonne;
    private List<StateDataItem> typeList;

    private RecyclerTouchListener listener;


    public interface RecyclerTouchListener {
        public void onClickRouteInfo(StateDataItem item, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        public ImageView img;
        @BindView(R.id.txt_name)
        public TextView txtName;

        @BindView(R.id.lvl_click)
        public LinearLayout lvlClick;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public RouteAdapter(Context mContext, List<StateDataItem> typeList, final RecyclerTouchListener listener) {
        this.mContext = mContext;
        this.typeList = typeList;
        this.listener = listener;



    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.route_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        StateDataItem item = typeList.get(position);
        Glide.with(mContext).load(APIClient.BASE_URL + "/" + item.getImg()).thumbnail(Glide.with(mContext).load(R.drawable.tprofile)).into(holder.img);
        holder.txtName.setText(item.getTitle());





        if (item.isSelect()) {
            holder.lvlClick.setBackground(mContext.getDrawable(R.drawable.round_boxfill));
        } else {
            holder.lvlClick.setBackground(mContext.getDrawable(R.drawable.round_box));
        }
        holder.lvlClick.setOnClickListener(view -> {
            if (item.isSelect()) {
                item.setSelect(false);
                typeList.set(position,item);
            } else {
                item.setSelect(true);
                typeList.set(position,item);
                listener.onClickRouteInfo(item, position);
            }

            notifyDataSetChanged();

        });
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

}