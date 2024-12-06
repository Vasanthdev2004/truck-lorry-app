package com.ruru.routelorry.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ruru.routelorry.R;
import com.ruru.routelorry.adepter.MyLoadAdapter;
import com.ruru.routelorry.model.LoadHistoryDataItem;
import com.ruru.routelorry.model.MyLoad;
import com.ruru.routelorry.retrofit.APIClient;
import com.ruru.routelorry.retrofit.GetResult;
import com.ruru.routelorry.ui.MyLoadDetailsActivity;
import com.ruru.routelorry.utils.CustPrograssbar;
import com.ruru.routelorry.utils.SessionManager;
import com.ruru.routelorry.utils.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CurrentLoadFragment extends Fragment implements MyLoadAdapter.RecyclerTouchListener, GetResult.MyListener {

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.lvl_nodata)
    public LinearLayout lvlNodata;

    public static CurrentLoadFragment newInstance() {
        return new CurrentLoadFragment();
    }

    SessionManager sessionManager;
    CustPrograssbar custPrograssbar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recent_load, container, false);
        ButterKnife.bind(this, view);
        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getLoad();
        return view;
    }


    private void getLoad() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("owner_id", sessionManager.getUserDetails().getId());
            jsonObject.put("status", "Current");

            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(),MediaType.parse("application/json"));
            Call<JsonObject> call = APIClient.getInterface().loadHistory(bodyRequest);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    custPrograssbar.closePrograssBar();

                    Gson gson = new Gson();
                    MyLoad myLoad = gson.fromJson(response.body().toString(), MyLoad.class);
                    if (myLoad.getLoadHistoryData().size() != 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        lvlNodata.setVisibility(View.GONE);
                        MyLoadAdapter myLoadAdapter = new MyLoadAdapter(getActivity(), myLoad.getLoadHistoryData(), CurrentLoadFragment.this);
                        recyclerView.setAdapter(myLoadAdapter);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        lvlNodata.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    custPrograssbar.closePrograssBar();
                    call.cancel();
                    t.printStackTrace();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    MyLoadAdapter categoryAdapter;

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("2")) {
                Gson gson = new Gson();
                MyLoad myLoad = gson.fromJson(result.toString(), MyLoad.class);
                if (myLoad.getLoadHistoryData().size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    lvlNodata.setVisibility(View.GONE);
                    categoryAdapter = new MyLoadAdapter(getActivity(), myLoad.getLoadHistoryData(), this);
                    recyclerView.setAdapter(categoryAdapter);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    lvlNodata.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            Log.e("Error","--> "+e.getMessage());

        }

    }

    int tempPosition = -1;

    @Override
    public void onClickLoadPost(LoadHistoryDataItem item, int position) {
        tempPosition = position;

        startActivity(new Intent(getActivity(), MyLoadDetailsActivity.class).putExtra("lid", item.getId()));

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (Utility.getInstance().removepost) {
                categoryAdapter.getItemList().remove(tempPosition);
                this.categoryAdapter.notifyDataSetChanged();
                Utility.getInstance().removepost = false;
            }

        } catch (Exception e) {
            Log.e("Error","--> "+e.getMessage());

        }

    }
}