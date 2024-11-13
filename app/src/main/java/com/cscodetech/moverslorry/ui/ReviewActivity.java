package com.cscodetech.moverslorry.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cscodetech.moverslorry.R;
import com.cscodetech.moverslorry.adepter.ReviewAdapter;
import com.cscodetech.moverslorry.model.BidInfo;
import com.cscodetech.moverslorry.model.UserLogin;
import com.cscodetech.moverslorry.retrofit.APIClient;
import com.cscodetech.moverslorry.retrofit.GetResult;
import com.cscodetech.moverslorry.utils.CustPrograssbar;
import com.cscodetech.moverslorry.utils.Utility;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ReviewActivity extends BaseActivity implements GetResult.MyListener {


    @BindView(R.id.imgs)
    public ShapeableImageView imgs;
    @BindView(R.id.txt_name)
    public TextView txtName;
    @BindView(R.id.txt_rates)
    public TextView txtRates;
    @BindView(R.id.txt_date)
    public TextView txtDate;
    @BindView(R.id.txt_lorry)
    public TextView txtLorry;
    @BindView(R.id.txt_review)
    public TextView txtReview;

    @BindView(R.id.recyclerView_review)
    public RecyclerView recyclerViewReview;
    @BindView(R.id.img_back)
    public ImageView imgBack;
    @BindView(R.id.txt_totalrview)
    public TextView txtTotalrview;
    @BindView(R.id.rl)
    public LinearLayout rl;
    CustPrograssbar custPrograssbar;

    UserLogin userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_info);
        ButterKnife.bind(this);

        custPrograssbar = new CustPrograssbar();
        userLogin = sessionmanager.getUserDetails();


        recyclerViewReview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewReview.setItemAnimator(new DefaultItemAnimator());

        getBidInfo();
    }

    private void getBidInfo() {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("owner_id", userLogin.getId());
            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(),MediaType.parse("application/json"));
            Call<JsonObject> call = APIClient.getInterface().transProfile(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
            custPrograssbar.prograssCreate(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.img_back})
    public void onBindClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        }
    }


    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                BidInfo bidInfo = gson.fromJson(result.toString(), BidInfo.class);
                if (bidInfo.getResult().equalsIgnoreCase("true")) {
                    Glide.with(this).load(APIClient.BASE_URL + "/" + bidInfo.getLorrizprofile().getProPic()).thumbnail(Glide.with(this).load(R.drawable.tprofile)).centerCrop().into(imgs);

                    txtName.setText("" + bidInfo.getLorrizprofile().getName());
                    txtRates.setText("" + bidInfo.getLorrizprofile().getReview());
                    txtDate.setText("" + Utility.getInstance().parseDateToddMMyy(bidInfo.getLorrizprofile().getRdate()));
                    txtLorry.setText("" + bidInfo.getLorrizprofile().getTotalLorry());
                    txtReview.setText("" + bidInfo.getLorrizprofile().getTotalReview());
                    txtTotalrview.setText(bidInfo.getLorrizprofile().getReview() + " ● " + bidInfo.getLorrizprofile().getTotalReview() + " " + getResources().getString(R.string.review));


                    ReviewAdapter adapter1 = new ReviewAdapter(this, bidInfo.getLorrizprofile().getTotalReviewUserWise());
                    recyclerViewReview.setAdapter(adapter1);

                }
            }
        } catch (Exception e) {
            Log.e("Error", "--> " + e.getMessage());
        }
    }
}