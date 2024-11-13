package com.cscodetech.moverslorry.fragment;


import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.cscodetech.moverslorry.R;
import com.cscodetech.moverslorry.adepter.BannerAdapter;
import com.cscodetech.moverslorry.adepter.HomeLorryAdapter;
import com.cscodetech.moverslorry.adepter.OperatAdapter;
import com.cscodetech.moverslorry.model.Home;
import com.cscodetech.moverslorry.model.HomeData;
import com.cscodetech.moverslorry.model.UserLogin;
import com.cscodetech.moverslorry.retrofit.APIClient;
import com.cscodetech.moverslorry.retrofit.GetResult;
import com.cscodetech.moverslorry.ui.AddLorryActivity;
import com.cscodetech.moverslorry.ui.FindLoadActivity;
import com.cscodetech.moverslorry.ui.HomeActivity;
import com.cscodetech.moverslorry.ui.IdentityVerifyActivity;
import com.cscodetech.moverslorry.ui.NearLoadActivity;
import com.cscodetech.moverslorry.ui.NotificationActivity;
import com.cscodetech.moverslorry.utils.CustPrograssbar;
import com.cscodetech.moverslorry.utils.CustomRecyclerView;
import com.cscodetech.moverslorry.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;


public class HomeFragment extends Fragment implements GetResult.MyListener, HomeLorryAdapter.RecyclerTouchListener {


    @BindView(R.id.txt_user)
    public TextView txtUser;
    @BindView(R.id.img_profile)
    public ImageView imgProfile;
    @BindView(R.id.lvltop)
    public LinearLayout lvltop;
    @BindView(R.id.lvl_findload)
    public LinearLayout lvlFindload;
    @BindView(R.id.lvl_nearload)
    public LinearLayout lvlNearload;
    @BindView(R.id.lvl_attachlorry)
    public LinearLayout lvlAttachlorry;
    @BindView(R.id.recycler_banner)
    public CustomRecyclerView recyclerviewBanner;

    @BindView(R.id.recyclerview_operate)
    public RecyclerView recyclerviewOperate;
    @BindView(R.id.recyclerview_mylorry)
    public RecyclerView recyclerviewMylorry;

    @BindView(R.id.txt_isverryfy)
    public TextView txtIsverryfy;
    @BindView(R.id.txt_mylorry)
    public TextView txtMylorry;
    @BindView(R.id.pullToRefresh)
    public SwipeRefreshLayout pullToRefresh;

    SessionManager sessionManager;
    UserLogin userLogin;
    CustPrograssbar custPrograssbar;
    DotsIndicator extensiblePageIndicator;
    HomeData homeData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(getActivity());
        sessionManager.getUserDetails();
        userLogin = sessionManager.getUserDetails();
        txtUser.setText("" + userLogin.getName());
        Glide.with(getActivity()).load(APIClient.BASE_URL + "/" + userLogin.getProPic()).thumbnail(Glide.with(getActivity()).load(R.drawable.tprofile)).into(imgProfile);

        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        mLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewBanner.setLayoutManager(mLayoutManager1);
        recyclerviewBanner.setItemAnimator(new DefaultItemAnimator());




        recyclerviewOperate.setLayoutManager(new GridLayoutManager(getActivity(), 3, VERTICAL, false));
        recyclerviewOperate.setItemAnimator(new DefaultItemAnimator());

        recyclerviewMylorry.setLayoutManager(new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false));
        recyclerviewMylorry.setItemAnimator(new DefaultItemAnimator());


        pullToRefresh.setOnRefreshListener(() -> {
            getHome(); // your code
            pullToRefresh.setRefreshing(false);
        });

        getHome();
        return view;
    }

    private void getHome() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("owner_id", userLogin.getId());
            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));
            Call<JsonObject> call = APIClient.getInterface().getHomePage(bodyRequest);
            GetResult getResult = new GetResult(getActivity());
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
            custPrograssbar.prograssCreate(getActivity());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void callback(JsonObject result, String callNo) {

        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                Home home = gson.fromJson(result.toString(), Home.class);
                homeData = home.getHomeData();
                switch (homeData.getIsVerify()) {
                    case "0":
                        txtIsverryfy.setCompoundDrawablesWithIntrinsicBounds(null, null, getActivity().getDrawable(R.drawable.ic_document_pending), null);
                        break;
                    case "1":
                        txtIsverryfy.setCompoundDrawablesWithIntrinsicBounds(null, null, getActivity().getDrawable(R.drawable.ic_document_process), null);
                        break;
                    case "2":
                        txtIsverryfy.setCompoundDrawablesWithIntrinsicBounds(null, null, getActivity().getDrawable(R.drawable.ic_document_done), null);
                        break;
                    case "3":
                        txtIsverryfy.setCompoundDrawablesWithIntrinsicBounds(null, null, getActivity().getDrawable(R.drawable.ic_unverified), null);

                        break;
                    case "4":
                        txtIsverryfy.setCompoundDrawablesWithIntrinsicBounds(null, null, getActivity().getDrawable(R.drawable.ic_unverified), null);

                        break;
                    case "5":
                        txtIsverryfy.setCompoundDrawablesWithIntrinsicBounds(null, null, getActivity().getDrawable(R.drawable.ic_unverified), null);

                        break;
                    default:
                        break;
                }
                txtIsverryfy.setText("" + homeData.getTopMsg());

                sessionManager.setStringData(SessionManager.currency, home.getHomeData().getCurrency());
                BannerAdapter bannerAdp = new BannerAdapter(getActivity(), home.getHomeData().getBanner());
                recyclerviewBanner.setAdapter(bannerAdp);
                recyclerviewBanner.startAutoRotation();
                if (home.getHomeData().getBidLorryData().size() != 0) {
                    txtMylorry.setVisibility(View.VISIBLE);
                }
                recyclerviewMylorry.setAdapter(new HomeLorryAdapter(getActivity(), home.getHomeData().getBidLorryData()));

                recyclerviewOperate.setAdapter(new OperatAdapter(getActivity(), home.getHomeData().getStatelist()));
            }

        } catch (Exception e) {
            Log.e("Error", "--> " + e.getMessage());

        }
    }


    @Override
    public void onClickHomeLorryInfo(String item, int position) {
        Log.e("item", item);
    }

    @OnClick({R.id.lvl_findload, R.id.lvl_nearload, R.id.lvl_attachlorry, R.id.img_noti, R.id.img_profile})
    public void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.lvl_findload:
                if (homeData.getIsVerify().equalsIgnoreCase("2")) {
                    startActivity(new Intent(getActivity(), FindLoadActivity.class));
                } else if (!homeData.getIsVerify().equalsIgnoreCase("1")) {
                    startActivity(new Intent(getActivity(), IdentityVerifyActivity.class).putExtra("isverry", homeData.getIsVerify()));
                } else {
                    Toast.makeText(getActivity(), homeData.getTopMsg(), Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.lvl_nearload:
                if (homeData.getIsVerify().equalsIgnoreCase("2")) {
                    startActivity(new Intent(getActivity(), NearLoadActivity.class));
                } else if (!homeData.getIsVerify().equalsIgnoreCase("1")) {
                    startActivity(new Intent(getActivity(), IdentityVerifyActivity.class).putExtra("isverry", homeData.getIsVerify()));
                } else {
                    Toast.makeText(getActivity(), homeData.getTopMsg(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.lvl_attachlorry:
                if (homeData.getIsVerify().equalsIgnoreCase("2")) {
                    startActivity(new Intent(getActivity(), AddLorryActivity.class));
                } else if (!homeData.getIsVerify().equalsIgnoreCase("1")) {
                    startActivity(new Intent(getActivity(), IdentityVerifyActivity.class).putExtra("isverry", homeData.getIsVerify()));
                } else {
                    Toast.makeText(getActivity(), homeData.getTopMsg(), Toast.LENGTH_LONG).show();
                }


                break;
            case R.id.img_noti:

                startActivity(new Intent(getActivity(), NotificationActivity.class));

                break;
            case R.id.img_profile:

                if (HomeActivity.getInstance() != null) {
                    HomeActivity.getInstance().profileMenuClick();
                }

                break;
            default:
                break;
        }
    }
}