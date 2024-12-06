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
import com.ruru.routelorry.adepter.LorryBookAdapter;
import com.ruru.routelorry.model.BookLorry;
import com.ruru.routelorry.model.LorrydataItem;
import com.ruru.routelorry.retrofit.APIClient;
import com.ruru.routelorry.ui.BookLorryDetailsActivity;
import com.ruru.routelorry.utils.CustPrograssbar;
import com.ruru.routelorry.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CompletBookLoadFragment extends Fragment implements LorryBookAdapter.RecyclerTouchListener {
    @BindView(R.id.lvl_nodata)
    public LinearLayout lvlNodata;
    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
    SessionManager sessionManager;
    CustPrograssbar custPrograssbar;
    public static CompletBookLoadFragment newInstance() {
        return new CompletBookLoadFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complet_post, container, false);
        ButterKnife.bind(this, view);
        sessionManager=new SessionManager(getActivity());
        custPrograssbar=new CustPrograssbar();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LorryBookAdapter categoryAdapter = new LorryBookAdapter(getActivity(), new ArrayList<>(), this);
        recyclerView.setAdapter(categoryAdapter);
        getLoad();
        return view;
    }
    private void getLoad() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("owner_id", sessionManager.getUserDetails().getId());
            jsonObject.put("status", "complete");

            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(),MediaType.parse("application/json"));
            Call<JsonObject> call = APIClient.getInterface().bookHistory(bodyRequest);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    custPrograssbar.closePrograssBar();

                    Gson gson = new Gson();
                    BookLorry bookLorry = gson.fromJson(response.body().toString(), BookLorry.class);
                    if (bookLorry.getResult().equalsIgnoreCase("true")) {
                        if (bookLorry.getBookHistory().size() != 0) {
                            LorryBookAdapter categoryAdapter = new LorryBookAdapter(getActivity(), bookLorry.getBookHistory(), CompletBookLoadFragment.this);
                            recyclerView.setAdapter(categoryAdapter);
                            lvlNodata.setVisibility(View.GONE);
                        } else {
                            lvlNodata.setVisibility(View.VISIBLE);
                        }
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
    public void onClickLorryInfo(LorrydataItem item, int position) {

        startActivity(new Intent(getActivity(), BookLorryDetailsActivity.class).putExtra("lid",item.getLorryId()));

    }
}