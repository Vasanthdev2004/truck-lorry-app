package com.ruru.routelorry.ui;


import static com.ruru.routelorry.utils.SessionManager.currency;
import static com.ruru.routelorry.utils.SessionManager.wallet;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ruru.routelorry.R;
import com.ruru.routelorry.model.EarningData;
import com.ruru.routelorry.model.Payout;
import com.ruru.routelorry.model.PayoutlistItem;
import com.ruru.routelorry.model.RestResponse;
import com.ruru.routelorry.model.UserLogin;
import com.ruru.routelorry.retrofit.APIClient;
import com.ruru.routelorry.retrofit.GetResult;
import com.ruru.routelorry.utils.CustPrograssbar;
import com.ruru.routelorry.utils.SessionManager;
import com.ruru.routelorry.utils.Utility;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;
import retrofit2.Call;

public class WalletActivity extends BaseActivity implements GetResult.MyListener {


    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.txt_erlimit)
    TextView txtErlimit;

    @BindView(R.id.recy_transaction)
    RecyclerView recyTransaction;
    @BindView(R.id.lvl_notfound)
    LinearLayout lvlNotfound;


    CustPrograssbar custPrograssbar;
    UserLogin user;
    private String owneri = "owner_id";
    private String applicationjson = "application/json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet);
        ButterKnife.bind(this);
        custPrograssbar = new CustPrograssbar();

        user = sessionmanager.getUserDetails();
        txtTotal.setText("" + sessionmanager.getStringData(currency) + sessionmanager.getFloatData(wallet));

        recyTransaction.setLayoutManager(new GridLayoutManager(this, 1));
        recyTransaction.setItemAnimator(new DefaultItemAnimator());

        getEarning();

    }

    private void getHistry() {
        custPrograssbar.prograssCreate(WalletActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(owneri, user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(jsonObject.toString(), MediaType.parse(applicationjson));
        Call<JsonObject> call = APIClient.getInterface().payoutList(bodyRequest);
        GetResult getResult = new GetResult(this);
        getResult.setMyListener(this);
        getResult.callForLogin(call, "1");

    }

    private void getEarning() {
        custPrograssbar.prograssCreate(WalletActivity.this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(owneri, user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(jsonObject.toString(), MediaType.parse(applicationjson));
        Call<JsonObject> call = APIClient.getInterface().getearning(bodyRequest);
        GetResult getResult = new GetResult(this);
        getResult.setMyListener(this);
        getResult.callForLogin(call, "3");

    }

    private void sendWithdraw(String amt, String type, String bankname, String accnumber, String acc_name, String ifsccode, String upiid, String paypalid) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(owneri, user.getId());
            jsonObject.put("amt", amt);
            jsonObject.put("r_type", type);
            jsonObject.put("bank_name", bankname);
            jsonObject.put("acc_number", accnumber);
            jsonObject.put("acc_name", acc_name);
            jsonObject.put("ifsc_code", ifsccode);
            jsonObject.put("upi_id", upiid);
            jsonObject.put("paypal_id", paypalid);
            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(), MediaType.parse(applicationjson));
            Call<JsonObject> call = APIClient.getInterface().requestWithdraw(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "2");
            custPrograssbar.prograssCreate(WalletActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.img_back, R.id.txt_addmunny})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_addmunny:
                bottonWithdraw();
                break;

            default:
                break;
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                Payout walletHistry = gson.fromJson(result.toString(), Payout.class);

                if (walletHistry.getResult().equalsIgnoreCase("true")) {
                    if (walletHistry.getPayoutlist().isEmpty()) {
                        recyTransaction.setVisibility(View.GONE);
                        lvlNotfound.setVisibility(View.VISIBLE);
                    } else {
                        HistryAdp histryAdp = new HistryAdp(walletHistry.getPayoutlist());
                        recyTransaction.setAdapter(histryAdp);
                    }

                } else {
                    recyTransaction.setVisibility(View.GONE);
                    lvlNotfound.setVisibility(View.VISIBLE);
                }
            } else if (callNo.equalsIgnoreCase("2")) {

                Gson gson = new Gson();
                RestResponse restResponse = gson.fromJson(result.toString(), RestResponse.class);
                Toast.makeText(this, restResponse.getResponseMsg(), Toast.LENGTH_LONG).show();
                if (restResponse.getResult().equalsIgnoreCase("true")) {
                    finish();
                }
            } else if (callNo.equalsIgnoreCase("3")) {

                Gson gson = new Gson();
                EarningData withdraw = gson.fromJson(result.toString(), EarningData.class);
                if (withdraw.getResult().equalsIgnoreCase("true")) {
                    txtErlimit.setText(getResources().getString(R.string.withdraw_limit) + " " + sessionmanager.getStringData(SessionManager.currency) + withdraw.getEarning().getWithdrawLimit());
                    txtTotal.setText(sessionmanager.getStringData(SessionManager.currency) + withdraw.getEarning().getEarninga());
                    getHistry();
                }
            }
        } catch (Exception e) {
            e.toString();

        }

    }


    private void bottonWithdraw() {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_withdraw, null);
        mBottomSheetDialog.setContentView(sheetView);

        RadioGroup radio = sheetView.findViewById(R.id.radio);
        TextView btnSubmit = sheetView.findViewById(R.id.btn_submit);
        TextView txtReject = sheetView.findViewById(R.id.txt_reject);
        EditText edAmount = sheetView.findViewById(R.id.ed_amount);
        EditText edbankname = sheetView.findViewById(R.id.ed_bankname);
        EditText edaccountno = sheetView.findViewById(R.id.ed_accountno);
        EditText edaccountname = sheetView.findViewById(R.id.ed_accountname);
        EditText edaccountifsc = sheetView.findViewById(R.id.ed_accountifsc);
        EditText edaccountupi = sheetView.findViewById(R.id.ed_accountupi);
        EditText edaccountpaypal = sheetView.findViewById(R.id.ed_accountpaypal);

        radio.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton radioButton = sheetView.findViewById(i);
            Log.e("IT", "-->" + i);
            switch (radioButton.getText().toString()) {
                case "upi":
                    setVisibility(edbankname, edaccountno, edaccountname, edaccountifsc, edaccountupi, edaccountpaypal, false, false, false, false, true,false);
                    break;
                case "bank transfer":
                    setVisibility(edbankname, edaccountno, edaccountname, edaccountifsc, edaccountupi, edaccountpaypal, true, true, true, true, false,false);
                    break;
                case "paypal":
                    setVisibility(edbankname, edaccountno, edaccountname, edaccountifsc, edaccountupi, edaccountpaypal, false, false, false, false, false,true);
                    break;
                default:
                    break;
            }
        });

        txtReject.setOnClickListener(view -> mBottomSheetDialog.cancel());

        btnSubmit.setOnClickListener(view -> {
            RadioButton radioButton = sheetView.findViewById(radio.getCheckedRadioButtonId());
            String paymentType = radioButton.getText().toString();

            switch (paymentType) {
                case "upi":
                    if (TextUtils.isEmpty(edaccountupi.getText().toString())) {
                        edaccountupi.setError("");
                    } else {
                        mBottomSheetDialog.cancel();
                        sendWithdraw(edAmount.getText().toString(), paymentType, "", "", "", "", edaccountupi.getText().toString(), "");
                    }
                    break;
                case "bank transfer":
                    if (TextUtils.isEmpty(edbankname.getText().toString())) {
                        edbankname.setError("");
                    } else if (TextUtils.isEmpty(edaccountname.getText().toString())) {
                        edaccountname.setError("");
                    } else if (TextUtils.isEmpty(edaccountno.getText().toString())) {
                        edaccountno.setError("");
                    } else if (TextUtils.isEmpty(edaccountifsc.getText().toString())) {
                        edaccountifsc.setError("");
                    } else {
                        mBottomSheetDialog.cancel();
                        sendWithdraw(edAmount.getText().toString(), paymentType, edbankname.getText().toString(), edaccountno.getText().toString(), edaccountname.getText().toString(), edaccountifsc.getText().toString(), "", "");
                    }
                    break;
                case "paypal":
                    if (TextUtils.isEmpty(edaccountpaypal.getText().toString())) {
                        edaccountpaypal.setError("");
                    } else {
                        mBottomSheetDialog.cancel();
                        sendWithdraw(edAmount.getText().toString(), paymentType, "", "", "", "", "", edaccountpaypal.getText().toString());
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + paymentType);
            }
        });

        mBottomSheetDialog.show();
    }

    private void setVisibility(View view1, View view2, View view3, View view4, View view5, View view6, boolean v1, boolean v2, boolean v3, boolean v4, boolean v5, boolean v6) {
        view1.setVisibility(v1 ? View.VISIBLE : View.GONE);
        view2.setVisibility(v2 ? View.VISIBLE : View.GONE);
        view3.setVisibility(v3 ? View.VISIBLE : View.GONE);
        view4.setVisibility(v4 ? View.VISIBLE : View.GONE);
        view5.setVisibility(v5 ? View.VISIBLE : View.GONE);
        view6.setVisibility(v6 ? View.VISIBLE : View.GONE);


    }


    public class HistryAdp extends RecyclerView.Adapter<HistryAdp.MyViewHolder> {
        private List<PayoutlistItem> list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.img_proof)
            public ImageViewZoom imgProof;
            @BindView(R.id.txt_status)
            public TextView txtStatus;
            @BindView(R.id.txt_requst)
            public TextView txtRequst;
            @BindView(R.id.txt_amt)
            public TextView txtAmt;
            @BindView(R.id.txt_payby)
            public TextView txtPayby;
            @BindView(R.id.txt_r_date)
            public TextView txtRDate;


            public MyViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);

            }
        }

        public HistryAdp(List<PayoutlistItem> list) {
            this.list = list;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_histry, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            PayoutlistItem payout = list.get(position);

            Glide.with(WalletActivity.this).load(APIClient.BASE_URL + "/" + payout.getProof()).into(holder.imgProof);

            holder.txtStatus.setText("" + payout.getStatus());
            holder.txtRequst.setText("" + payout.getPayoutId());
            holder.txtAmt.setText(sessionmanager.getStringData(SessionManager.currency) + payout.getAmt());
            holder.txtPayby.setText("" + payout.getRType());
            holder.txtRDate.setText("" + payout.getRDate());


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Utility.getInstance().walletUp) {
            Utility.getInstance().walletUp = false;
            getHistry();
        }

    }
}