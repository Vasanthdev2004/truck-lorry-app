package com.cscodetech.moverslorry.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.moverslorry.R;
import com.cscodetech.moverslorry.adepter.AutoCompleteAdapter;
import com.cscodetech.moverslorry.adepter.LoadFindAdapter;
import com.cscodetech.moverslorry.adepter.VehicleTypeAdapter;
import com.cscodetech.moverslorry.model.BidLorry;
import com.cscodetech.moverslorry.model.BidLorryDataItem;
import com.cscodetech.moverslorry.model.CheckState;
import com.cscodetech.moverslorry.model.FindLoad;
import com.cscodetech.moverslorry.model.LoaddataItem;
import com.cscodetech.moverslorry.model.RestResponse;
import com.cscodetech.moverslorry.model.UserLogin;
import com.cscodetech.moverslorry.retrofit.APIClient;
import com.cscodetech.moverslorry.retrofit.GetResult;
import com.cscodetech.moverslorry.utils.CustPrograssbar;
import com.cscodetech.moverslorry.utils.SessionManager;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
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
import retrofit2.Callback;
import retrofit2.Response;

public class FindLoadActivity extends AppCompatActivity implements VehicleTypeAdapter.RecyclerTouchListener, LoadFindAdapter.RecyclerTouchListener, GetResult.MyListener {

    @BindView(R.id.img_back)
    public ImageView imgBack;
    @BindView(R.id.pickup)
    public AutoCompleteTextView pickup;
    @BindView(R.id.drop)
    public AutoCompleteTextView drop;
    @BindView(R.id.btn_next)
    public TextView btnNext;
    @BindView(R.id.txt_availeble)
    public TextView txtAvaileble;
    @BindView(R.id.recyclerView_vehicletype)
    public RecyclerView recyclerViewVehicletype;
    @BindView(R.id.recyclerView_lorry)
    public RecyclerView recyclerViewLorry;
    @BindView(R.id.lvl_emty)
    public LinearLayout lvlEmty;

    CustPrograssbar custPrograssbar;
    SessionManager sessionManager;
    UserLogin userLogin;

    AutoCompleteAdapter adapter;
    AutoCompleteAdapter adapterDrop;
    private PlacesClient placesClient;
    String pickStats;
    String dropStats;
    String ownerid = "owner_id";
    String load_id = "load_id";
    String applicationjson = "application/json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_load);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        custPrograssbar = new CustPrograssbar();
        userLogin = sessionManager.getUserDetails();

        ActivityCompat.requestPermissions(this,
                new String[]{

                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                }, 1010);


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.api_key));
        }
        placesClient = Places.createClient(this);

        pickup.setThreshold(1);
        pickup.setOnItemClickListener(autocompleteClickListener);
        adapter = new AutoCompleteAdapter(this, placesClient);
        pickup.setAdapter(adapter);

        drop.setThreshold(1);
        drop.setOnItemClickListener(autocompleteClickListenerDrop);
        adapterDrop = new AutoCompleteAdapter(this, placesClient);
        drop.setAdapter(adapterDrop);

        recyclerViewVehicletype.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewVehicletype.setItemAnimator(new DefaultItemAnimator());

        recyclerViewLorry.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewLorry.setItemAnimator(new DefaultItemAnimator());
        findLoad("0", "0");

    }

    private void findLoad(String pickid, String dropid) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ownerid, userLogin.getId());
            jsonObject.put("pick_state_id", pickid);
            jsonObject.put("drop_state_id", dropid);

            RequestBody bodyRequest = RequestBody.create(MediaType.parse(applicationjson), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().findLoad(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
            custPrograssbar.prograssCreate(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getIsService(String pstate, String dstate) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pick_state_name", pstate);
            jsonObject.put("drop_state_name", dstate);

            RequestBody bodyRequest = RequestBody.create(MediaType.parse(applicationjson), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().getstateid(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "2");
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

            RequestBody bodyRequest = RequestBody.create(MediaType.parse(applicationjson), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().bidload(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "3");
            custPrograssbar.prograssCreate(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getLorry(LoaddataItem item) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ownerid, userLogin.getId());
            jsonObject.put(load_id, item.getId());

            RequestBody bodyRequest = RequestBody.create(MediaType.parse(applicationjson), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().getlorryList(bodyRequest);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                    Gson gson = new Gson();
                    BidLorry bidLorry = gson.fromJson(response.body(), BidLorry.class);
                    if (bidLorry.getResult().equalsIgnoreCase("true") && bidLorry.getBidLorryData().size() != 0) {
                        sendBid(FindLoadActivity.this, item, bidLorry.getBidLorryData());

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e("err", "-->");
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

            RequestBody bodyRequest = RequestBody.create(MediaType.parse(applicationjson), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().deleteBid(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "4");
            custPrograssbar.prograssCreate(this);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    FindLoad findLoad;
    int isBid = -1;
    LoadFindAdapter loadFindAdapter;

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                findLoad = gson.fromJson(result.toString(), FindLoad.class);

                if (findLoad.getFindLoadData().size() != 0) {
                    lvlEmty.setVisibility(View.VISIBLE);

                    VehicleTypeAdapter vehicleTypeAdapter = new VehicleTypeAdapter(this, findLoad.getFindLoadData(), this);
                    recyclerViewVehicletype.setAdapter(vehicleTypeAdapter);


                    loadFindAdapter = new LoadFindAdapter(this, findLoad.getFindLoadData().get(0).getLoaddata(), this);
                    recyclerViewLorry.setAdapter(loadFindAdapter);

                    txtAvaileble.setText(findLoad.getFindLoadData().get(0).getLoaddata().size() + " " + getString(R.string.available));
                } else {
                    lvlEmty.setVisibility(View.GONE);
                }


            } else if (callNo.equalsIgnoreCase("2")) {
                Gson gson = new Gson();
                CheckState checkState = gson.fromJson(result.toString(), CheckState.class);
                if (checkState.getResult().equalsIgnoreCase("true")) {
                    findLoad(checkState.getCurrStateId(), checkState.getDropStateId());
                }
            } else if (callNo.equalsIgnoreCase("3")) {
                Gson gson = new Gson();
                RestResponse response = gson.fromJson(result.toString(), RestResponse.class);
                if (response.getResult().equals("true")) {
                    loadFindAdapter.bidSenditem(isBid);
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
            Log.e("Error", "--" + e.getMessage());
        }

    }


    @Override
    public void onClickVehicleTypeInfo(String item, int position) {

        txtAvaileble.setText(findLoad.getFindLoadData().get(position).getLoaddata().size() + " " + getResources().getString(R.string.available));
        loadFindAdapter = new LoadFindAdapter(this, findLoad.getFindLoadData().get(position).getLoaddata(), this);
        recyclerViewLorry.setAdapter(loadFindAdapter);

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


    @OnClick({R.id.img_back, R.id.btn_next})
    public void onBindClick(View view) {
        int id = view.getId();
        if (id == R.id.img_back) {
            finish();
        } else if (id == R.id.btn_next) {
            getIsService(pickStats, dropStats);
        }
    }

    public void sendBid(Context context, LoaddataItem item, List<BidLorryDataItem> lorryLst) {
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, arealist);

        EditText edAmount = rootView.findViewById(R.id.ed_amount);
        TextInputEditText editDescption = rootView.findViewById(R.id.edit_descption);
        AutoCompleteTextView spinnerautocompet = rootView.findViewById(R.id.spinnerautocompet);

        Switch swichtonne = rootView.findViewById(R.id.swichtonne);
        CheckBox checkbox = rootView.findViewById(R.id.checkbox);
        TextView btnUpdate = rootView.findViewById(R.id.btn_update);
        spinnerautocompet.setText(arealist.get(0));
        spinnerautocompet.setAdapter(arrayAdapter);

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


    private final AdapterView.OnItemClickListener autocompleteClickListener = (adapterView, view, i, l) -> {
        try {
            final AutocompletePrediction item = adapter.getItem(i);
            String placeID = null;
            if (item != null) {
                placeID = item.getPlaceId();
            }

            List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS
                    , Place.Field.LAT_LNG, Place.Field.ADDRESS_COMPONENTS);

            FetchPlaceRequest request = null;
            if (placeID != null) {
                request = FetchPlaceRequest.builder(placeID, placeFields)
                        .build();
            }

            if (request != null) {
                placesClient.fetchPlace(request).addOnSuccessListener(task -> {

                    for (int j = 0; j < task.getPlace().getAddressComponents().asList().size(); j++) {
                        if (task.getPlace().getAddressComponents().asList().get(j).getTypes().get(0).equalsIgnoreCase("administrative_area_level_1")) {

                            pickStats = task.getPlace().getAddressComponents().asList().get(j).getName();
                            break;
                        }
                    }

                }).addOnFailureListener(e -> e.printStackTrace());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };


    private final AdapterView.OnItemClickListener autocompleteClickListenerDrop = (adapterView, view, i, l) -> {
        try {
            final AutocompletePrediction item = adapterDrop.getItem(i);
            String placeID = null;
            if (item != null) {
                placeID = item.getPlaceId();
            }
            List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS
                    , Place.Field.LAT_LNG, Place.Field.ADDRESS_COMPONENTS);

            FetchPlaceRequest request = null;
            if (placeID != null) {
                request = FetchPlaceRequest.builder(placeID, placeFields)
                        .build();
            }

            if (request != null) {
                placesClient.fetchPlace(request).addOnSuccessListener(task -> {

                    for (int j = 0; j < task.getPlace().getAddressComponents().asList().size(); j++) {
                        if (task.getPlace().getAddressComponents().asList().get(j).getTypes().get(0).equalsIgnoreCase("administrative_area_level_1")) {
                            dropStats = task.getPlace().getAddressComponents().asList().get(j).getName();
                            break;
                        }
                    }

                }).addOnFailureListener(e -> e.printStackTrace());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

}