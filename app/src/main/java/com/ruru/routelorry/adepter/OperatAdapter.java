package com.ruru.routelorry.adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ruru.routelorry.R;
import com.ruru.routelorry.model.StatelistItem;
import com.ruru.routelorry.retrofit.APIClient;
import com.ruru.routelorry.utils.SessionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperatAdapter extends RecyclerView.Adapter<OperatAdapter.MyViewHolder> {


    private Context mContext;
    private List<StatelistItem> typeList;

    SessionManager sessionManager;

    public interface RecyclerTouchListener {


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        public ImageView img;
        @BindView(R.id.txt_stats)
        public TextView txtStats;
        @BindView(R.id.txt_load)
        public TextView txtLoad;
        @BindView(R.id.txt_lorry)
        public TextView txtLorry;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public OperatAdapter(Context mContext, List<StatelistItem> typeList) {
        this.mContext = mContext;
        this.typeList = typeList;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.operat_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        StatelistItem item=typeList.get(position);
        Glide.with(mContext).load(APIClient.BASE_URL + "/" + item.getImg()).thumbnail(Glide.with(mContext).load(R.drawable.tprofile)).into(holder.img);
        holder.txtStats.setText(""+item.getTitle());
        holder.txtLoad.setText(""+item.getTotalLoad()+" "+mContext.getResources().getString(R.string.load));
        holder.txtLorry.setText(""+item.getTotalLorry() +" "+mContext.getResources().getString(R.string.lorry));


    }

    @Override
    public int getItemCount() {

        return typeList.size();
    }
}