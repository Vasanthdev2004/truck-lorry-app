package com.ruru.routelorry.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ruru.routelorry.R;
import com.ruru.routelorry.model.LoadDetailMain;
import com.ruru.routelorry.model.RestResponse;
import com.ruru.routelorry.retrofit.APIClient;
import com.ruru.routelorry.retrofit.GetResult;
import com.ruru.routelorry.utils.CustPrograssbar;
import com.ruru.routelorry.utils.SessionManager;
import com.ruru.routelorry.utils.Utility;
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

public class BookLorryDetailsActivity extends BaseActivity implements GetResult.MyListener {

    @BindView(R.id.img_back)
    public ImageView imgBack;
    @BindView(R.id.img_call)
    public ImageView imgCall;

    @BindView(R.id.imgt)
    public ImageView imgt;
    @BindView(R.id.txt_title)
    public TextView txtTitle;
    @BindView(R.id.txt_price)
    public TextView txtPrice;
    @BindView(R.id.txt_pricel)
    public TextView txtPricel;
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
    @BindView(R.id.imgs)
    public ShapeableImageView imgs;
    @BindView(R.id.txt_name)
    public TextView txtName;
    @BindView(R.id.txt_rates)
    public TextView txtRates;
    @BindView(R.id.txt_status)
    public TextView txtStatus;
    @BindView(R.id.txt_pay)
    public TextView txtPay;
    @BindView(R.id.txt_reject)
    public TextView txtReject;
    @BindView(R.id.txt_accept)
    public TextView txtAccept;
    @BindView(R.id.txt_offer)
    public TextView txtOffer;
    @BindView(R.id.lvl_lorryownerresponse)
    public LinearLayout lvlLorryownerresponse;
    @BindView(R.id.lvl_nobids)
    public LinearLayout lvlNobids;
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
    @BindView(R.id.btn_pickupdrop)
    public TextView btnPickupdrop;
    @BindView(R.id.lvl_tpayment_row)
    public LinearLayout lvlTpaymentRow;
    @BindView(R.id.lvl_paymentinfo)
    public LinearLayout lvlPaymentinfo;
    @BindView(R.id.txt_actionbar)
    public TextView txtActionbar;
    @BindView(R.id.btn_rating)
    public TextView btnRating;

    @BindView(R.id.txt_tonneorfixl)
    public TextView txtTonneorfixl;
    @BindView(R.id.txt_totall)
    public TextView txtTotall;
    @BindView(R.id.txt_total)
    public TextView txtTotal;

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

    String ownerid = "owner_id";

    String status_ = "status";
    String tonne = "Tonne";
    String loadid = "load_id";

    CustPrograssbar custPrograssbar;
    String applicationjson = "application/json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_lorry_details);
        ButterKnife.bind(this);

        custPrograssbar = new CustPrograssbar();
        getBookLoadDetails();
    }

    private void getBookLoadDetails() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ownerid, sessionmanager.getUserDetails().getId());
            jsonObject.put(loadid, getIntent().getStringExtra("lid"));

            RequestBody bodyRequest = RequestBody.create(MediaType.parse(applicationjson), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().bookDetails(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
            custPrograssbar.prograssCreate(BookLorryDetailsActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getDecision(JSONObject jsonObject) {


        RequestBody bodyRequest = RequestBody.create(MediaType.parse(applicationjson), jsonObject.toString());
        Call<JsonObject> call = APIClient.getInterface().offerDecision(bodyRequest);
        GetResult getResult = new GetResult(this);
        getResult.setMyListener(this);
        getResult.callForLogin(call, "2");
        custPrograssbar.prograssCreate(BookLorryDetailsActivity.this);

    }

    private void getLorryDecision(String status) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ownerid, sessionmanager.getUserDetails().getId());
            jsonObject.put(loadid, getIntent().getStringExtra("lid"));
            jsonObject.put(status_, status);
            jsonObject.put("load_type", "FIND_LORRY");


            RequestBody bodyRequest = RequestBody.create(MediaType.parse(applicationjson), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().lorrydecision(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "2");
            custPrograssbar.prograssCreate(BookLorryDetailsActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getRateUpdate(String rate, String ratetext) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", sessionmanager.getUserDetails().getId());
            jsonObject.put(loadid, getIntent().getStringExtra("lid"));
            jsonObject.put("total_lrate", rate);
            jsonObject.put("rate_ltext", ratetext);
            RequestBody bodyRequest = RequestBody.create(MediaType.parse(applicationjson), jsonObject.toString());
            Call<JsonObject> call = APIClient.getInterface().rateUpdate(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "2");
            custPrograssbar.prograssCreate(BookLorryDetailsActivity.this);
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
        txtClick.setText(getResources().getString(R.string.rate_to) + " " + myLoadData.getLoadDetails().getLoaderName());

        Glide.with(this).load(APIClient.BASE_URL + "/" + myLoadData.getLoadDetails().getLoaderImg()).thumbnail(Glide.with(this).load(R.drawable.tprofile)).into(img_p);
        txt_name.setText("" + myLoadData.getLoadDetails().getLoaderName());
        txt_lorry.setText("" + myLoadData.getLoadDetails().getLoaderMobile());
        txtClick.setOnClickListener(v -> {
            mBottomSheetDialog.cancel();
            String ratin = String.valueOf(rating.getRating());
            getRateUpdate(ratin, edRevire.getText().toString());


        });
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.show();
    }

    LoadDetailMain myLoadData;

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closePrograssBar();

            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                myLoadData = gson.fromJson(result.toString(), LoadDetailMain.class);
                txtActionbar.setText(getResources().getString(R.string.load) + " #" + myLoadData.getLoadDetails().getId());
                Glide.with(this).load("https://chart.googleapis.com/chart?cht=qr&chl={%22path%22:%22verifyticket.php%22,%22ticket_id%22:%221%22,%22uid%22:%221%22}&chs=160x160&chld=L|0").thumbnail(Glide.with(this).load(R.drawable.tprofile)).into(imgt);
                Glide.with(this).load(APIClient.BASE_URL + "/" + myLoadData.getLoadDetails().getVehicleImg()).thumbnail(Glide.with(this).load(R.drawable.tprofile)).into(imgt);
                txtTitle.setText(myLoadData.getLoadDetails().getVehicleTitle());


                if (myLoadData.getLoadDetails().getAmtType().equalsIgnoreCase(tonne)) {
                    txtPrice.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getAmount());
                    txtTonneorfix.setText("/ " + myLoadData.getLoadDetails().getAmtType());
                    txtTotal.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getTotalAmt());
                    if (myLoadData.getLoadDetails().getFlowId().equalsIgnoreCase("1") || myLoadData.getLoadDetails().getFlowId().equalsIgnoreCase("4")) {
                        txtPricel.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getAmount());
                        txtTonneorfixl.setText(" /" + myLoadData.getLoadDetails().getAmtType());
                        txtTotall.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getTotalAmt());
                    } else if (myLoadData.getLoadDetails().getFlowId().equalsIgnoreCase("3") || myLoadData.getLoadDetails().getFlowId().equalsIgnoreCase("6")) {
                        txtPricel.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getOfferPrice());
                        txtTonneorfixl.setText(" /" + myLoadData.getLoadDetails().getAmtType());
                        txtTotall.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getOfferTotal());
                    }


                } else {
                    txtTotal.setVisibility(View.GONE);
                    txtPrice.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getAmount());
                    txtTonneorfix.setText("/ " + myLoadData.getLoadDetails().getAmtType());

                    if (myLoadData.getLoadDetails().getFlowId().equalsIgnoreCase("1") || myLoadData.getLoadDetails().getFlowId().equalsIgnoreCase("4")) {
                        txtPricel.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getTotalAmt());
                        txtTonneorfixl.setText(" /" + myLoadData.getLoadDetails().getAmtType());
                        txtTotall.setVisibility(View.GONE);
                    } else if (myLoadData.getLoadDetails().getFlowId().equalsIgnoreCase("3") || myLoadData.getLoadDetails().getFlowId().equalsIgnoreCase("6")) {
                        txtPricel.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getOfferTotal());
                        txtTonneorfixl.setText(" /" + myLoadData.getLoadDetails().getAmtType());
                        txtTotall.setVisibility(View.GONE);
                    }


                }

                txtPick.setText(myLoadData.getLoadDetails().getPickupState());
                txtPickaddress.setText(myLoadData.getLoadDetails().getPickupPoint());
                txtDrop.setText(myLoadData.getLoadDetails().getDropState());
                txtDropaddress.setText(myLoadData.getLoadDetails().getDropPoint());
                txtDate.setText("" + Utility.getInstance().parseDateToddMMyy(myLoadData.getLoadDetails().getPostDate()));
                txtTonne.setText("" + myLoadData.getLoadDetails().getWeight());
                txtMaterial.setText("" + myLoadData.getLoadDetails().getMaterialName());

                Glide.with(this).load(APIClient.BASE_URL + "/" + myLoadData.getLoadDetails().getLoaderImg()).thumbnail(Glide.with(this).load(R.drawable.tprofile)).into(imgs);
                txtName.setText(myLoadData.getLoadDetails().getLoaderName());

                txtRates.setText("" + myLoadData.getLoadDetails().getLoaderRate());


                switch (myLoadData.getLoadDetails().getFlowId()) {
                    case "0":
                        lvlPaymentinfo.setVisibility(View.GONE);
                        txtStatus.setVisibility(View.GONE);
                        lvlLorryownerresponse.setVisibility(View.VISIBLE);
                        txtPay.setVisibility(View.GONE);
                        break;
                    case "1":


                        lvlPaymentinfo.setVisibility(View.GONE);
                        txtStatus.setVisibility(View.GONE);
                        txtPay.setVisibility(View.GONE);
                        txtPmethod.setText("" + myLoadData.getLoadDetails().getpMethodName());
                        txtTrasaction.setText("" + myLoadData.getLoadDetails().getOrderTransactionId());
                        txtSubtotal.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getTotalAmt());
                        txtWallet.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getWalAmt());
                        txtTpayment.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getPayAmt());
                        btnPickupdrop.setVisibility(View.GONE);


                        break;
                    case "4":


                        lvlPaymentinfo.setVisibility(View.VISIBLE);
                        txtStatus.setVisibility(View.GONE);
                        txtPay.setVisibility(View.GONE);
                        txtPmethod.setText("" + myLoadData.getLoadDetails().getpMethodName());
                        txtTrasaction.setText("" + myLoadData.getLoadDetails().getOrderTransactionId());
                        txtSubtotal.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getTotalAmt());
                        txtWallet.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getWalAmt());
                        txtTpayment.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getPayAmt());
                        btnPickupdrop.setVisibility(View.VISIBLE);
                        btnPickupdrop.setText(getResources().getString(R.string.pickup));
                        imgCall.setVisibility(View.GONE);
                        lvlContact.setVisibility(View.VISIBLE);
                        txtPickName.setText("" + myLoadData.getLoadDetails().getPickName());
                        txtPickMob.setText("" + myLoadData.getLoadDetails().getPickMobile());
                        txtDropName.setText("" + myLoadData.getLoadDetails().getDropName());
                        txtDropMob.setText("" + myLoadData.getLoadDetails().getDropMobile());

                        break;
                    case "2":
                    case "5":
                        lvlPaymentinfo.setVisibility(View.GONE);
                        txtStatus.setVisibility(View.VISIBLE);
                        txtStatus.setText("" + myLoadData.getLoadDetails().getCommentReject());
                        txtPay.setVisibility(View.GONE);
                        break;
                    case "6":
                        lvlLorryownerresponse.setVisibility(View.VISIBLE);
                        lvlPaymentinfo.setVisibility(View.GONE);
                        txtStatus.setVisibility(View.GONE);


                        txtPay.setVisibility(View.GONE);
                        break;

                    case "3":
                        lvlPaymentinfo.setVisibility(View.GONE);
                        txtStatus.setVisibility(View.VISIBLE);
                        txtStatus.setText("Waiting for Offer Response");


                        break;
                    case "7":
                        lvlContact.setVisibility(View.VISIBLE);
                        txtPickName.setText("" + myLoadData.getLoadDetails().getPickName());
                        txtPickMob.setText("" + myLoadData.getLoadDetails().getPickMobile());
                        txtDropName.setText("" + myLoadData.getLoadDetails().getDropName());
                        txtDropMob.setText("" + myLoadData.getLoadDetails().getDropMobile());
                        lvlPaymentinfo.setVisibility(View.VISIBLE);
                        txtStatus.setVisibility(View.GONE);
                        txtPay.setVisibility(View.GONE);
                        txtPmethod.setText("" + myLoadData.getLoadDetails().getpMethodName());
                        txtTrasaction.setText("" + myLoadData.getLoadDetails().getOrderTransactionId());
                        txtSubtotal.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getTotalAmt());
                        txtWallet.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getWalAmt());
                        txtTpayment.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getPayAmt());
                        btnPickupdrop.setVisibility(View.VISIBLE);
                        btnPickupdrop.setText(getResources().getString(R.string.drop));
                        break;
                    case "8":

                        lvlPaymentinfo.setVisibility(View.VISIBLE);
                        txtStatus.setVisibility(View.GONE);
                        txtPay.setVisibility(View.GONE);
                        txtPmethod.setText("" + myLoadData.getLoadDetails().getpMethodName());
                        txtTrasaction.setText("" + myLoadData.getLoadDetails().getOrderTransactionId());
                        txtSubtotal.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getTotalAmt());
                        txtWallet.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getWalAmt());
                        txtTpayment.setText(sessionmanager.getStringData(SessionManager.currency) + myLoadData.getLoadDetails().getPayAmt());
                        if (myLoadData.getLoadDetails().getIsRate().equalsIgnoreCase("0")) {
                            btnRating.setVisibility(View.VISIBLE);
                            btnRating.setText(getResources().getString(R.string.rate_to) + " " + myLoadData.getLoadDetails().getLoaderName());
                        }
                        break;
                    default:
                        break;
                }
            } else if (callNo.equalsIgnoreCase("2")) {
                Gson gson = new Gson();

                RestResponse restResponse = gson.fromJson(result.toString(), RestResponse.class);
                Toast.makeText(BookLorryDetailsActivity.this, restResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                if (restResponse.getResult().equalsIgnoreCase("true")) {
                    finish();
                }

            }
        } catch (Exception e) {
            Log.e("sjdsjd", "" + e.getMessage());
        }
    }


    public void bidderOffers(Context context) {
        Activity activity = (Activity) context;
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);
        View rootView = activity.getLayoutInflater().inflate(R.layout.lorry_book_offer_layout, null);
        mBottomSheetDialog.setContentView(rootView);
        TextView txtClick = rootView.findViewById(R.id.btn_sendoffer);
        EditText edAmount = rootView.findViewById(R.id.ed_amount);
        EditText edDescription = rootView.findViewById(R.id.ed_description);
        Switch swichtonne = rootView.findViewById(R.id.swichtonne);
        final String[] atype = {""};
        final double[] tamout = new double[1];
        swichtonne.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!TextUtils.isEmpty(edAmount.getText().toString())) {
                if (b) {
                    swichtonne.setText("Per Tonnes");
                    atype[0] = tonne;
                    tamout[0] = Double.parseDouble(edAmount.getText().toString()) * Double.parseDouble(myLoadData.getLoadDetails().getWeight());

                } else {
                    swichtonne.setText("Fix");
                    atype[0] = "Fixed";
                    tamout[0] = Double.parseDouble(edAmount.getText().toString());

                }
            }

        });

        txtClick.setOnClickListener(v -> {
            if (!swichtonne.isChecked()) {
                swichtonne.setText("Fix");
                atype[0] = "Fixed";
                tamout[0] = Double.parseDouble(edAmount.getText().toString());

            }
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(ownerid, sessionmanager.getUserDetails().getId());
                jsonObject.put(loadid, getIntent().getStringExtra("lid"));
                jsonObject.put(status_, "3");
                jsonObject.put("offer_description", edDescription.getText().toString());
                jsonObject.put("offer_price", edAmount.getText().toString());
                jsonObject.put("offer_type", atype[0]);
                jsonObject.put("offer_total", tamout[0]);

                getDecision(jsonObject);

                mBottomSheetDialog.cancel();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
        mBottomSheetDialog.show();
    }

    @OnClick({R.id.img_back, R.id.txt_reject, R.id.txt_accept, R.id.txt_offer, R.id.btn_pickupdrop, R.id.img_call, R.id.btn_rating, R.id.imgs, R.id.img_call_pick, R.id.img_call_drop})

    public void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;

            case R.id.img_call:

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + myLoadData.getLoadDetails().getLoaderMobile()));
                startActivity(intent);

                break;
            case R.id.img_call_pick:

                Intent intent2 = new Intent(Intent.ACTION_CALL);
                intent2.setData(Uri.parse("tel:" + myLoadData.getLoadDetails().getPickMobile()));
                startActivity(intent2);
                break;
            case R.id.img_call_drop:

                Intent intent1 = new Intent(Intent.ACTION_CALL);
                intent1.setData(Uri.parse("tel:" + myLoadData.getLoadDetails().getDropMobile()));
                startActivity(intent1);
                break;
            case R.id.btn_pickupdrop:
                if (btnPickupdrop.getText().toString().equalsIgnoreCase("drop")) {
                    getLorryDecision("2");
                } else {
                    getLorryDecision("1");
                }
                break;
            case R.id.txt_reject:
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(ownerid, sessionmanager.getUserDetails().getId());
                    jsonObject.put(loadid, getIntent().getStringExtra("lid"));
                    jsonObject.put(status_, "2");
                    jsonObject.put("comment_reject", "");
                    getDecision(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;
            case R.id.txt_accept:
                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put(ownerid, sessionmanager.getUserDetails().getId());
                    jsonObject1.put(loadid, getIntent().getStringExtra("lid"));
                    jsonObject1.put(status_, "1");
                    jsonObject1.put("comment_reject", "");
                    getDecision(jsonObject1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.txt_offer:
                bidderOffers(this);
                break;
            case R.id.btn_rating:
                sendReview(BookLorryDetailsActivity.this);
                break;
            case R.id.imgs:
                startActivity(new Intent(this, BiderInfoActivity.class)
                        .putExtra("uid", myLoadData.getLoadDetails().getUid()));
                break;
            default:
                break;
        }
    }


}