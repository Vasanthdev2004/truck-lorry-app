package com.cscodetech.moverslorry.ui;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.moverslorry.R;
import com.cscodetech.moverslorry.adepter.RouteAdapter;
import com.cscodetech.moverslorry.model.State;
import com.cscodetech.moverslorry.model.StateDataItem;
import com.cscodetech.moverslorry.model.TempAddLorry;
import com.cscodetech.moverslorry.model.UserLogin;
import com.cscodetech.moverslorry.retrofit.APIClient;
import com.cscodetech.moverslorry.retrofit.GetResult;
import com.cscodetech.moverslorry.utils.CustPrograssbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class AddLorryTwoActivity extends BaseActivity implements GetResult.MyListener, RouteAdapter.RecyclerTouchListener {


    @BindView(R.id.img_back)
    public ImageView imgBack;
    @BindView(R.id.txt_actionbar)
    public TextView txtActionbar;
    @BindView(R.id.recyclerview_route)
    public RecyclerView recyclerviewRoute;
    @BindView(R.id.btn_next)
    public TextView btnNext;
    CustPrograssbar custPrograssbar;
    UserLogin userLogin;
    TempAddLorry tempAddLorry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lorry_two);
        ButterKnife.bind(this);
        custPrograssbar = new CustPrograssbar();

        userLogin = sessionmanager.getUserDetails();
        tempAddLorry = (TempAddLorry) getIntent().getSerializableExtra("mydata");
        recyclerviewRoute.setLayoutManager(new GridLayoutManager(this, 2, VERTICAL, false));
        recyclerviewRoute.setItemAnimator(new DefaultItemAnimator());


        getRoutes();

    }


    @OnClick({R.id.img_back, R.id.btn_next, R.id.txt_select})
    public void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_next:
                handleNextButtonClick();
                break;
            case R.id.txt_select:
                handleSelectAllButtonClick();
                break;
            default:
                break;
        }
    }

    private void handleNextButtonClick() {
        String routs = getSelectedRoutes();
        if (!routs.isEmpty()) {
            tempAddLorry.setRoutes(routs);
            startActivity(new Intent(AddLorryTwoActivity.this, AddLorryThreeActivity.class).putExtra("mydata", tempAddLorry));
        } else {
            Toast.makeText(this, "Please select at least one route or multiple.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getSelectedRoutes() {
        StringBuilder routesBuilder = new StringBuilder();
        for (StateDataItem item : state.getStateData()) {
            if (item.isSelect()) {
                if (routesBuilder.length() > 0) {
                    routesBuilder.append(",");
                }
                routesBuilder.append(item.getId());
            }
        }
        return routesBuilder.toString();
    }

    private void handleSelectAllButtonClick() {
        for (StateDataItem item : state.getStateData()) {
            item.setSelect(true);
        }
        recyclerviewRoute.setAdapter(new RouteAdapter(this, state.getStateData(), this));
    }

    private void getRoutes() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("owner_id", userLogin.getId());
            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(),MediaType.parse("application/json"));
            Call<JsonObject> call = APIClient.getInterface().getStatelist(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
            custPrograssbar.prograssCreate(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    State state;

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                state = gson.fromJson(result.toString(), State.class);
                List<StateDataItem> itemList = new ArrayList<>();

                if (tempAddLorry.getRoute() != null) {
                    for (int i = 0; i < state.getStateData().size(); i++) {
                        StateDataItem item = state.getStateData().get(i);
                        if (Arrays.asList(tempAddLorry.getRoute()).contains(item.getTitle())) {
                            item.setSelect(true);
                            itemList.add(item);
                        } else {
                            itemList.add(item);
                        }
                    }
                } else {
                    itemList = state.getStateData();
                }

                recyclerviewRoute.setAdapter(new RouteAdapter(AddLorryTwoActivity.this, itemList, AddLorryTwoActivity.this));


            }

        } catch (Exception e) {
            Log.e("Errror", "---> ");
        }
    }


    @Override
    public void onClickRouteInfo(StateDataItem item, int position) {
        Log.e("item", item.getTitle());

    }
}