package com.ruru.routelorry.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.ruru.routelorry.ui.MyLoadDetailsActivity;
import com.ruru.routelorry.utils.CustPrograssbar;
import com.ruru.routelorry.utils.SessionManager;
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


public class CompletLoadFragment extends Fragment implements MyLoadAdapter.RecyclerTouchListener {

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.lvl_nodata)
    public LinearLayout lvlNodata;
    SessionManager sessionManager;
    CustPrograssbar custPrograssbar;

    public static CompletLoadFragment newInstance() {
        return new CompletLoadFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complet_post, container, false);
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
            jsonObject.put("status", "Complete");

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
                        MyLoadAdapter adapter = new MyLoadAdapter(getActivity(), myLoad.getLoadHistoryData(), CompletLoadFragment.this);
                        recyclerView.setAdapter(adapter);
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

    @Override
    public void onClickLoadPost(LoadHistoryDataItem item, int position) {

        startActivity(new Intent(getActivity(), MyLoadDetailsActivity.class).putExtra("lid", item.getId()));

    }
}