package com.ruru.routelorry.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ruru.routelorry.R;
import com.ruru.routelorry.model.Faq;
import com.ruru.routelorry.model.FaqDataItem;
import com.ruru.routelorry.model.UserLogin;
import com.ruru.routelorry.retrofit.APIClient;
import com.ruru.routelorry.retrofit.GetResult;
import com.ruru.routelorry.utils.CustPrograssbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class FaqActivity extends BaseActivity implements GetResult.MyListener {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView imgBack;


    @BindView(R.id.recycler_product)
    RecyclerView recyclerProduct;

    CustPrograssbar custPrograssbar;
    UserLogin user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);

        user= sessionmanager.getUserDetails();
        custPrograssbar=new CustPrograssbar();

        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        mLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerProduct.setLayoutManager(mLayoutManager2);
        recyclerProduct.setItemAnimator(new DefaultItemAnimator());
        getFaq();


    }
    private void getFaq() {
        custPrograssbar.prograssCreate(this);
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("uid", user.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(jsonObject.toString(),MediaType.parse("application/json"));
        Call<JsonObject> call = APIClient.getInterface().getFaq(bodyRequest);
        GetResult getResult = new GetResult(this);
        getResult.setMyListener(this);
        getResult.callForLogin(call, "1");

    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if(callNo.equalsIgnoreCase("1")){
                Gson gson=new Gson();
                Faq faq=gson.fromJson(result.toString(),Faq.class);
                if(faq.getResult().equalsIgnoreCase("true")){
                    recyclerProduct.setAdapter(new MyFaqAdepter(faq.getFaqData()));
                }

            }
        }catch (Exception e){
e.toString();
        }
    }

    public class MyFaqAdepter extends RecyclerView.Adapter<MyFaqAdepter.ViewHolder> {
        private List<FaqDataItem> orderData;

        public MyFaqAdepter(List<FaqDataItem> orderData) {
            this.orderData = orderData;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.faq_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,
                                     int position) {
            Log.e("position", "" + position);
            FaqDataItem order = orderData.get(position);
            holder.txtTital.setText("" + order.getQuestion());
            holder.txtAns.setText("" + order.getAnswer());

            holder.lvlClick.setOnClickListener(v -> {
                if (holder.lvlItem.getVisibility() == View.VISIBLE) {

                    holder.imgRight.setBackgroundResource(R.drawable.ic_faq_right);
                    TranslateAnimation animate = new TranslateAnimation(
                            0,
                            0,
                            0,
                            holder.lvlItem.getHeight());
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    holder.lvlItem.startAnimation(animate);
                    holder.lvlItem.setVisibility(View.GONE);
                } else {
                    holder.lvlItem.setVisibility(View.VISIBLE);
                    TranslateAnimation animate = new TranslateAnimation(
                            0,
                            0,
                            holder.lvlItem.getHeight(),
                            0);
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    holder.lvlItem.startAnimation(animate);

                    holder.imgRight.setBackgroundResource(R.drawable.ic_faq_down);

                }
            });
        }

        @Override
        public int getItemCount() {
            return orderData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.txt_tital)
            TextView txtTital;
            @BindView(R.id.txt_ans)
            TextView txtAns;

            @BindView(R.id.lvl_item)
            LinearLayout lvlItem;
            @BindView(R.id.lvl_click)
            LinearLayout lvlClick;
            @BindView(R.id.img_right)
            ImageView imgRight;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }
}