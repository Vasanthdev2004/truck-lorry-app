package com.ruru.routelorry.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ruru.routelorry.R;
import com.ruru.routelorry.adepter.LoadFindAdapter;
import com.ruru.routelorry.model.BidLorry;
import com.ruru.routelorry.model.BidLorryDataItem;
import com.ruru.routelorry.model.FindLoadDataItem;
import com.ruru.routelorry.model.LoaddataItem;
import com.ruru.routelorry.model.RestResponse;
import com.ruru.routelorry.model.UserLogin;
import com.ruru.routelorry.retrofit.APIClient;
import com.ruru.routelorry.retrofit.GetResult;
import com.ruru.routelorry.utils.CustPrograssbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearLoadActivity extends BaseActivity implements LoadFindAdapter.RecyclerTouchListener, GetResult.MyListener {

    @BindView(R.id.img_back)
    public ImageView imgBack;

    @BindView(R.id.txt_availeble)
    public TextView txtAvaileble;
    @BindView(R.id.pullToRefresh)
    public SwipeRefreshLayout pullToRefresh;
    @BindView(R.id.recyclerView_lorry)
    public RecyclerView recyclerViewLorry;
    CustPrograssbar custPrograssbar;

    UserLogin userLogin;
    LoadFindAdapter loadFindAdapter;
    int isBid = -1;
    String ownerid = "owner_id";
    String load_id = "load_id";
    String applicationjson = "application/json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_load);
        ButterKnife.bind(this);
        custPrograssbar = new CustPrograssbar();

        userLogin = sessionmanager.getUserDetails();


        recyclerViewLorry.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewLorry.setItemAnimator(new DefaultItemAnimator());


        ActivityCompat.requestPermissions(this,
                new String[]{

                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                }, 1010);

        pullToRefresh.setOnRefreshListener(() -> {
            getCurrentAddress();
            // your code
            pullToRefresh.setRefreshing(false);
        });
        getCurrentAddress();

    }

    public void getCurrentAddress() {
        // Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = null;

        if (locationManager != null) {
            try {
                if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
            } catch (Exception ex) {
                Log.i("msg", "fail to request location update, ignore", ex);
            }

            location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try {
                getNear(location.getLatitude(), location.getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(NearLoadActivity.this, "ERROR_FETCHING_LOCATION", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("elele", "jeje");
        }
    }


    private void getNear(double lats, double longs) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ownerid, userLogin.getId());
            jsonObject.put("lats", lats);
            jsonObject.put("longs", longs);
            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(), MediaType.parse(applicationjson));
            Call<JsonObject> call = APIClient.getInterface().nearLoad(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
            custPrograssbar.prograssCreate(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClickFindLoad(LoaddataItem item, int position) {
        isBid = position;
        getLorry(item);
    }

    @Override
    public void onClickBidDeletLoad(LoaddataItem item, int position) {
        isBid = position;
        rempveBid(item);
    }

    private void getLorry(LoaddataItem item) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ownerid, userLogin.getId());
            jsonObject.put(load_id, item.getId());

            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(), MediaType.parse(applicationjson));
            Call<JsonObject> call = APIClient.getInterface().getlorryList(bodyRequest);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                    Gson gson = new Gson();
                    BidLorry bidLorry = gson.fromJson(response.body(), BidLorry.class);
                    if (bidLorry.getResult().equalsIgnoreCase("true") && bidLorry.getBidLorryData().size() != 0) {

                        sendBidBottomesheet(NearLoadActivity.this, item, bidLorry.getBidLorryData());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e("Error", "00");
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void rempveBid(LoaddataItem item) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ownerid, userLogin.getId());
            jsonObject.put(load_id, item.getId());

            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(), MediaType.parse(applicationjson));
            Call<JsonObject> call = APIClient.getInterface().deleteBid(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "4");
            custPrograssbar.prograssCreate(this);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendBid(String loadid, String lorryid, String amount, String amttype, String totalamt, String isimmediate, String totalload, String des) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ownerid, userLogin.getId());
            jsonObject.put(load_id, loadid);
            jsonObject.put("lorry_id", lorryid);
            jsonObject.put("amount", amount);
            jsonObject.put("amt_type", amttype);
            jsonObject.put("total_amt", totalamt);
            jsonObject.put("is_immediate", isimmediate);
            jsonObject.put("total_load", totalload);
            jsonObject.put("total_load", totalload);
            jsonObject.put("description", des);

            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(), MediaType.parse(applicationjson));
            Call<JsonObject> call = APIClient.getInterface().bidload(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "3");
            custPrograssbar.prograssCreate(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendBidBottomesheet(Context context, LoaddataItem item, List<BidLorryDataItem> lorryLst) {
        final String[] amtType = new String[1];
        final double[] amtTotal = new double[1];
        Activity activity = (Activity) context;
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);
        View rootView = activity.getLayoutInflater().inflate(R.layout.bid_layout, null);
        mBottomSheetDialog.setContentView(rootView);


        List<String> arealist = new ArrayList<>();
        for (int i = 0; i < lorryLst.size(); i++) {
            arealist.add(lorryLst.get(i).getLorryTitle() + " (" + lorryLst.get(i).getLorryNo() + ")");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, arealist);

        EditText edAmount = rootView.findViewById(R.id.ed_amount);
        TextInputEditText editDescption = rootView.findViewById(R.id.edit_descption);
        AutoCompleteTextView spinnerautocompet = rootView.findViewById(R.id.spinnerautocompet);

        Switch swichtonne = rootView.findViewById(R.id.swichtonne);
        CheckBox checkbox = rootView.findViewById(R.id.checkbox);
        TextView btnUpdate = rootView.findViewById(R.id.btn_update);
        spinnerautocompet.setText(arealist.get(0));
        spinnerautocompet.setAdapter(adapter);

        swichtonne.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!TextUtils.isEmpty(edAmount.getText().toString())) {
                if (b) {
                    swichtonne.setText("Per Tonnes");
                    amtType[0] = "Tonne";
                    amtTotal[0] = Double.parseDouble(edAmount.getText().toString()) * Double.parseDouble(item.getWeight());
                } else {
                    swichtonne.setText("Fix");
                    amtType[0] = "Fixed";
                    amtTotal[0] = Double.parseDouble(edAmount.getText().toString());


                }
            }

        });


        btnUpdate.setOnClickListener(v -> {
            mBottomSheetDialog.cancel();
            if (!swichtonne.isChecked()) {
                swichtonne.setText("Fix");
                amtType[0] = "Fixed";
                amtTotal[0] = Double.parseDouble(edAmount.getText().toString());
            }
            String lorrid = lorryLst.get(arealist.indexOf(spinnerautocompet.getText().toString())).getId();
            sendBid(item.getId(), lorrid, edAmount.getText().toString(), amtType[0], String.valueOf(amtTotal[0]), checkbox.isChecked() ? "1" : "0", item.getWeight(), editDescption.getText().toString());

        });
        mBottomSheetDialog.show();
    }


    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                FindLoadDataItem findLoadDataItem = gson.fromJson(result.toString(), FindLoadDataItem.class);
                txtAvaileble.setText(findLoadDataItem.getLoaddata().size() + "+ " + getResources().getString(R.string.available));
                loadFindAdapter = new LoadFindAdapter(this, findLoadDataItem.getLoaddata(), this);
                recyclerViewLorry.setAdapter(loadFindAdapter);

            } else if (callNo.equalsIgnoreCase("3")) {
                Gson gson = new Gson();
                RestResponse response = gson.fromJson(result.toString(), RestResponse.class);
                if (response.getResult().equals("true")) {
                    loadFindAdapter.bidSenditem(isBid);
                    getCurrentAddress();

                }
                Toast.makeText(this, response.getResponseMsg(), Toast.LENGTH_SHORT).show();

            } else if (callNo.equalsIgnoreCase("4")) {
                Gson gson = new Gson();
                Log.e("bidposition", "" + isBid);
                RestResponse response = gson.fromJson(result.toString(), RestResponse.class);
                if (response.getResult().equals("true")) {
                    loadFindAdapter.bidRemoveitem(isBid);
                }


            }
        } catch (Exception e) {
            Log.e("Error", "--> " + e.getMessage());

        }

    }

    @OnClick({R.id.img_back})
    public void onBindClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        }
    }
}