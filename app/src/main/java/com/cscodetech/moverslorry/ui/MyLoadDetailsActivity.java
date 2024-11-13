package com.cscodetech.moverslorry.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cscodetech.moverslorry.R;
import com.cscodetech.moverslorry.model.LoadDetailMain;
import com.cscodetech.moverslorry.model.LoadDetails;
import com.cscodetech.moverslorry.model.RestResponse;
import com.cscodetech.moverslorry.model.UserLogin;
import com.cscodetech.moverslorry.retrofit.APIClient;
import com.cscodetech.moverslorry.retrofit.GetResult;
import com.cscodetech.moverslorry.utils.CustPrograssbar;
import com.cscodetech.moverslorry.utils.SessionManager;
import com.cscodetech.moverslorry.utils.Utility;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

public class MyLoadDetailsActivity extends AppCompatActivity implements GetResult.MyListener {

    @BindView(R.id.img_back)
    public ImageView imgBack;

    @BindView(R.id.imgt)
    public ImageView imgt;
    @BindView(R.id.txt_title)
    public TextView txtTitle;
    @BindView(R.id.txt_price)
    public TextView txtPrice;
    @BindView(R.id.txt_tonneorfix)
    public TextView txtTonneorfix;
    @BindView(R.id.txt_pick)
    public TextView txtPick;
    @BindView(R.id.txt_pickaddress)
    public TextView txtPickaddress;
    @BindView(R.id.txt_drop)
    public TextView txtDrop;
    @BindView(R.id.txt_dropaddress)
    public TextView txtDropaddress;
    @BindView(R.id.txt_date)
    public TextView txtDate;
    @BindView(R.id.txt_tonne)
    public TextView txtTonne;
    @BindView(R.id.txt_material)
    public TextView txtMaterial;
    @BindView(R.id.btn_pickup)
    public TextView btnPickup;
    @BindView(R.id.btn_rating)
    public TextView btnRating;
    @BindView(R.id.btn_drop)
    public TextView btnDrop;
    @BindView(R.id.imgs)
    public ShapeableImageView imgs;
    @BindView(R.id.txt_name)
    public TextView txtName;
    @BindView(R.id.txt_pricel)
    public TextView txtPricel;
    @BindView(R.id.txt_tonnefixb)
    public TextView txtTonnefixb;
    @BindView(R.id.txt_actionbar)
    public TextView txtActionbar;

    @BindView(R.id.lvl_paymentinfo)
    public LinearLayout lvlPaymentinfo;

    @BindView(R.id.txt_pmethod)
    public TextView txtPmethod;
    @BindView(R.id.pmethod_row)
    public LinearLayout pmethodRow;
    @BindView(R.id.txt_trasaction)
    public TextView txtTrasaction;
    @BindView(R.id.lvl_transaction_row)
    public LinearLayout lvlTransactionRow;
    @BindView(R.id.txt_subtotal)
    public TextView txtSubtotal;
    @BindView(R.id.lvl_sub_total_row)
    public LinearLayout lvlSubTotalRow;
    @BindView(R.id.txt_tax)
    public TextView txtTax;
    @BindView(R.id.lvl_tax_row)
    public LinearLayout lvlTaxRow;
    @BindView(R.id.txt_wallet)
    public TextView txtWallet;
    @BindView(R.id.lvl_wallet_row)
    public LinearLayout lvlWalletRow;
    @BindView(R.id.txt_tpayment)
    public TextView txtTpayment;

    @BindView(R.id.lvl_contact)
    public LinearLayout lvlContact;

    @BindView(R.id.txt_pick_name)
    public TextView txtPickName;

    @BindView(R.id.txt_pick_mob)
    public TextView txtPickMob;

    @BindView(R.id.txt_drop_name)
    public TextView txtDropName;

    @BindView(R.id.txt_drop_mob)
    public TextView txtDropMob;


    @BindView(R.id.txt_rates)
    public TextView txtRates;
    SessionManager sessionManager;
    UserLogin userLogin;
    CustPrograssbar custPrograssbar;
    String loadid = "load_id";
    String applicationjson = "application/json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_load_details);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        userLogin = sessionManager.getUserDetails();
        custPrograssbar = new CustPrograssbar();
        getLoad();


    }

    @OnClick({R.id.img_back, R.id.btn_pickup, R.id.btn_drop, R.id.btn_rating, R.id.imgs, R.id.img_call_pick, R.id.img_call_drop})
    public void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_pickup:
                getLorryDecision("1");
                break;
            case R.id.img_call_pick:
                Log.e("Number", "-->" + loadDetails.getLoadDetails().getLoaderMobile());
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + loadDetails.getLoadDetails().getPickMobile()));
                startActivity(intent);
                break;
            case R.id.img_call_drop:

                Intent intent1 = new Intent(Intent.ACTION_CALL);
                intent1.setData(Uri.parse("tel:" + loadDetails.getLoadDetails().getDropMobile()));
                startActivity(intent1);
                break;
            case R.id.btn_drop:
                getLorryDecision("2");
                break;
            case R.id.btn_rating:
                sendReview(this);
                break;
            case R.id.imgs:
                startActivity(new Intent(this, BiderInfoActivity.class)
                        .putExtra("uid", loadDetails.getLoadDetails().getUid()));
                break;
            default:
                break;


        }
    }

    private void getLoad() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("owner_id", userLogin.getId());
            jsonObject.put(loadid, getIntent().getStringExtra("lid"));

            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(), MediaType.parse(applicationjson));
            Call<JsonObject> call = APIClient.getInterface().loadDetails(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
            custPrograssbar.prograssCreate(this);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getLorryDecision(String status) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("owner_id", sessionManager.getUserDetails().getId());
            jsonObject.put(loadid, getIntent().getStringExtra("lid"));
            jsonObject.put("status", status);
            jsonObject.put("load_type", "POST_LOAD");


            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(), MediaType.parse(applicationjson));
            Call<JsonObject> call = APIClient.getInterface().lorrydecision(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "2");
            custPrograssbar.prograssCreate(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getRateUpdate(String rate, String ratetext) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", sessionManager.getUserDetails().getId());
            jsonObject.put(loadid, getIntent().getStringExtra("lid"));
            jsonObject.put("total_lrate", rate);
            jsonObject.put("rate_ltext", ratetext);
            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(), MediaType.parse(applicationjson));
            Call<JsonObject> call = APIClient.getInterface().rateUpdate(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "2");
            custPrograssbar.prograssCreate(MyLoadDetailsActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendReview(Context context) {
        Activity activity = (Activity) context;
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);
        View rootView = activity.getLayoutInflater().inflate(R.layout.sendrview_layout, null);
        mBottomSheetDialog.setContentView(rootView);
        TextView txtClick = rootView.findViewById(R.id.btn_load);
        ImageView img_p = rootView.findViewById(R.id.img_p);
        TextView txt_name = rootView.findViewById(R.id.txt_name);
        TextView txt_lorry = rootView.findViewById(R.id.txt_lorry);
        EditText edRevire = rootView.findViewById(R.id.ed_revire);
        RatingBar rating = rootView.findViewById(R.id.rating);
        txtClick.setText(getResources().getString(R.string.rate_to) + " " + loadDetails.getLoadDetails().getLoaderName());
        Glide.with(this).load(APIClient.BASE_URL + "/" + loadDetails.getLoadDetails().getLoaderImg()).thumbnail(Glide.with(MyLoadDetailsActivity.this).load(R.drawable.tprofile)).into(img_p);
        txt_name.setText("" + loadDetails.getLoadDetails().getLoaderName());
        txt_lorry.setText("" + loadDetails.getLoadDetails().getLoaderMobile());
        txtClick.setOnClickListener(v -> {
            mBottomSheetDialog.cancel();
            String ratin = String.valueOf(rating.getRating());
            getRateUpdate(ratin, edRevire.getText().toString());


        });
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.show();
    }

    LoadDetailMain loadDetails;

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                handleLoadDetails(result.toString());
            } else if (callNo.equalsIgnoreCase("2")) {
                handleRestResponse(result.toString());
            }
        } catch (Exception e) {
            Log.e("Error", "--> " + e.getMessage());
        }

    }

    private void handleLoadDetails(String result) {
        Gson gson = new Gson();
        loadDetails = gson.fromJson(result, LoadDetailMain.class);
        if (loadDetails.getResult().equalsIgnoreCase("true")) {
            setLoadDetails(loadDetails.getLoadDetails());
        }
    }

    private void setLoadDetails(LoadDetails loadDetails) {

        Glide.with(this).load(APIClient.BASE_URL + "/" + loadDetails.getVehicleImg()).thumbnail(Glide.with(MyLoadDetailsActivity.this).load(R.drawable.tprofile)).into(imgt);
        txtActionbar.setText(getResources().getString(R.string.load) + " #" + loadDetails.getId());
        txtTitle.setText("" + loadDetails.getVehicleTitle());
        txtPrice.setText(sessionManager.getStringData(SessionManager.currency) + loadDetails.getAmount());
        txtTonneorfix.setText(" /" + loadDetails.getAmtType());
        txtPick.setText("" + loadDetails.getPickupState());
        txtPickaddress.setText("" + loadDetails.getPickupPoint());
        txtDrop.setText("" + loadDetails.getDropState());
        txtDropaddress.setText("" + loadDetails.getDropPoint());
        txtDate.setText("" + Utility.getInstance().parseDateToddMMyy(loadDetails.getPostDate()));
        txtTonne.setText("" + loadDetails.getWeight());
        txtMaterial.setText("" + loadDetails.getMaterialName());

        Glide.with(this).load(APIClient.BASE_URL + "/" + loadDetails.getLoaderImg()).thumbnail(Glide.with(MyLoadDetailsActivity.this).load(R.drawable.tprofile)).into(imgs);
        txtName.setText("" + loadDetails.getLoaderName());
        txtRates.setText("" + loadDetails.getLoaderRate());
        txtPricel.setText(sessionManager.getStringData(SessionManager.currency) + loadDetails.getAmount());
        txtTonnefixb.setText("/ " + loadDetails.getAmtType());

        txtPmethod.setText("" + loadDetails.getpMethodName());
        txtTrasaction.setText("" + loadDetails.getOrderTransactionId());
        txtSubtotal.setText(sessionManager.getStringData(SessionManager.currency) + loadDetails.getTotalAmt());
        txtWallet.setText(sessionManager.getStringData(SessionManager.currency) + loadDetails.getWalAmt());
        txtTpayment.setText(sessionManager.getStringData(SessionManager.currency) + loadDetails.getPayAmt());

        if (loadDetails.getWalAmt().equalsIgnoreCase("0")) {
            lvlWalletRow.setVisibility(View.GONE);
        } else {
            lvlWalletRow.setVisibility(View.VISIBLE);
            txtWallet.setText(getResources().getString(R.string.load) + " #" + loadDetails.getWalAmt());
        }

        handleFlowId(loadDetails.getFlowId(), loadDetails.getIsRate(), loadDetails.getLoaderName());
    }

    private void handleFlowId(String flowId, String isRate, String loaderName) {
        if (flowId.equalsIgnoreCase("0")) {
            Log.e("Error", "--> ");
        } else if (flowId.equalsIgnoreCase("1")) {
            btnPickup.setVisibility(View.VISIBLE);
            lvlContact.setVisibility(View.VISIBLE);
            btnDrop.setVisibility(View.GONE);
            txtPickName.setText("" + loadDetails.getLoadDetails().getPickName());
            txtPickMob.setText("" + loadDetails.getLoadDetails().getPickMobile());
            txtDropName.setText("" + loadDetails.getLoadDetails().getDropName());
            txtDropMob.setText("" + loadDetails.getLoadDetails().getDropMobile());

        } else if (flowId.equalsIgnoreCase("2")) {
            btnDrop.setVisibility(View.VISIBLE);
            btnPickup.setVisibility(View.GONE);
            lvlContact.setVisibility(View.VISIBLE);
            txtPickName.setText("" + loadDetails.getLoadDetails().getPickName());
            txtPickMob.setText("" + loadDetails.getLoadDetails().getPickMobile());
            txtDropName.setText("" + loadDetails.getLoadDetails().getDropName());
            txtDropMob.setText("" + loadDetails.getLoadDetails().getDropMobile());
        } else if (flowId.equalsIgnoreCase("3") && isRate.equalsIgnoreCase("0")) {
            btnDrop.setVisibility(View.GONE);
            btnPickup.setVisibility(View.GONE);
            btnRating.setVisibility(View.VISIBLE);
            btnRating.setText(getResources().getString(R.string.rate_to) + " " + loaderName);
        } else if (flowId.equalsIgnoreCase("3")) {
            btnDrop.setVisibility(View.GONE);
            btnPickup.setVisibility(View.GONE);
            btnRating.setVisibility(View.GONE);
        }
    }

    private void handleRestResponse(String result) {
        Gson gson = new Gson();
        RestResponse restResponse = gson.fromJson(result, RestResponse.class);
        Toast.makeText(MyLoadDetailsActivity.this, restResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
        if (restResponse.getResult().equalsIgnoreCase("true")) {
            finish();
        }
    }
}