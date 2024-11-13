package com.cscodetech.moverslorry.ui;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.moverslorry.R;
import com.cscodetech.moverslorry.adepter.VehicleAdapter;
import com.cscodetech.moverslorry.model.BidLorryDataItem;
import com.cscodetech.moverslorry.model.TempAddLorry;
import com.cscodetech.moverslorry.model.UserLogin;
import com.cscodetech.moverslorry.model.Vehicle;
import com.cscodetech.moverslorry.model.VehicleDataItem;
import com.cscodetech.moverslorry.retrofit.APIClient;
import com.cscodetech.moverslorry.retrofit.GetResult;
import com.cscodetech.moverslorry.utils.CustPrograssbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class AddLorryActivity extends BaseActivity implements GetResult.MyListener, VehicleAdapter.RecyclerTouchListener {

    @BindView(R.id.img_back)
    public ImageView imgBack;
    @BindView(R.id.txt_actionbar)
    public TextView txtActionbar;
    @BindView(R.id.ed_lorrylname)
    public EditText edLorrylname;
    @BindView(R.id.ed_typeweight)
    public EditText edTypeweight;
    @BindView(R.id.recyclerview_vehicle)
    public RecyclerView recyclerviewVehicle;
    @BindView(R.id.btn_next)
    public TextView btnNext;
    CustPrograssbar custPrograssbar;

    UserLogin userLogin;
    public TempAddLorry tempAddLorry;
    BidLorryDataItem lorryDataItem;


    private static AddLorryActivity instance ;

    public static AddLorryActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lorry);
        ButterKnife.bind(this);
        instance=this;
        tempAddLorry = new TempAddLorry();
        lorryDataItem = getIntent().getParcelableExtra("my_class");
        if (lorryDataItem != null) {

            tempAddLorry.setDescription(lorryDataItem.getDescription());
            tempAddLorry.setRoute(lorryDataItem.getTotalRoutes());
            tempAddLorry.setLorryNo(lorryDataItem.getLorryNo());
            tempAddLorry.setVehicleId(lorryDataItem.getLorryTitle());
            tempAddLorry.setWeight(lorryDataItem.getWeight());

            tempAddLorry.setRecordId(lorryDataItem.getId());

            edTypeweight.setText("" + lorryDataItem.getWeight());
            edLorrylname.setText("" + lorryDataItem.getLorryNo());
            txtActionbar.setText(lorryDataItem.getLorryTitle());

        } else {
            tempAddLorry.setRecordId("-1");
        }
        ActivityCompat.requestPermissions(this,
                new String[]{

                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                }, 1010);
        custPrograssbar = new CustPrograssbar();

        userLogin = sessionmanager.getUserDetails();
        tempAddLorry.setOwnerId(userLogin.getId());
        recyclerviewVehicle.setLayoutManager(new GridLayoutManager(this, 2, VERTICAL, false));
        recyclerviewVehicle.setItemAnimator(new DefaultItemAnimator());
        edTypeweight.setOnFocusChangeListener((view, b) -> {
            Log.e("BBBBBBBB", "-->" + b);
            if (!b) {
                tempAddLorry.setVehicleId(null);
                recyclerviewVehicle.setAdapter(new VehicleAdapter(AddLorryActivity.this, vehicle.getVehicleData(), tempAddLorry.getVehicleId(), AddLorryActivity.this));

            }
        });

        getCurrentAddress();
        getVehicle();

    }


    @OnClick({R.id.img_back, R.id.btn_next})
    public void onBindClick(View view) {
        int id = view.getId();
        if (id == R.id.img_back) {
            finish();
        } else if (id == R.id.btn_next) {
            if (TextUtils.isEmpty(edLorrylname.getText().toString())) {
                edLorrylname.setError("");
                return;
            }
            if (TextUtils.isEmpty(edTypeweight.getText().toString())) {
                edTypeweight.setError("");
                return;
            }
            if (tempAddLorry.getVehicleId() == null) {
                Toast.makeText(this, "Select Vehicle Type", Toast.LENGTH_SHORT).show();
                return;
            }
            if (weight < Integer.parseInt(edTypeweight.getText().toString())) {
                recyclerviewVehicle.setAdapter(new VehicleAdapter(AddLorryActivity.this, vehicle.getVehicleData(), tempAddLorry.getVehicleId(), AddLorryActivity.this));

                return;
            }
            tempAddLorry.setLorryNo(edLorrylname.getText().toString());
            tempAddLorry.setWeight(edTypeweight.getText().toString());

            startActivity(new Intent(AddLorryActivity.this, AddLorryTwoActivity.class).putExtra("mydata", tempAddLorry));
        }
    }

    private void getVehicle() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", userLogin.getId());
            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));
            Call<JsonObject> call = APIClient.getInterface().getVehocle(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
            custPrograssbar.prograssCreate(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    Vehicle vehicle;

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                vehicle = gson.fromJson(result.toString(), Vehicle.class);
                if (vehicle.getVehicleData().size() != 0) {
                    btnNext.setVisibility(View.VISIBLE);
                    recyclerviewVehicle.setAdapter(new VehicleAdapter(this, vehicle.getVehicleData(), tempAddLorry.getVehicleId(), this));

                } else {
                    btnNext.setVisibility(View.GONE);


                }

            }

        } catch (Exception e) {
            Log.e("Error", "--> " + e.getMessage());

        }
    }

    int weight = 0;

    @Override
    public void onClickVehicleInfo(VehicleDataItem item, int position) {
        weight = Integer.parseInt(item.getMaxWeight());
        tempAddLorry.setVehicleId(item.getId());
    }

    public void getCurrentAddress() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String currentLocation;
        if (locationManager != null) {
            try {
                if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            } catch (Exception ex) {
                Log.i("msg", "fail to request location update, ignore", ex);
            }
            Location location = getLastKnownLocation(locationManager);
            if (location != null) {
                currentLocation = getAddressFromLocation(location);
                if (currentLocation != null) {
                    tempAddLorry.setCurrLocation(currentLocation);
                }
            } else {
                Toast.makeText(AddLorryActivity.this, "ERROR_FETCHING_LOCATION", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Location getLastKnownLocation(LocationManager locationManager) {
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return null;
            }
            return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else {
            return null;
        }
    }

    private String getAddressFromLocation(Location location) {
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {

                String locality = addresses.get(0).getLocality();
                String subLocality = addresses.get(0).getSubLocality();
                if (subLocality != null) {
                    return locality + "," + subLocality;
                } else {
                    return locality;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}