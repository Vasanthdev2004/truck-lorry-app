package com.cscodetech.moverslorry.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cscodetech.moverslorry.R;
import com.cscodetech.moverslorry.model.Contry;
import com.cscodetech.moverslorry.model.RestResponse;
import com.cscodetech.moverslorry.model.UserLogin;
import com.cscodetech.moverslorry.retrofit.APIClient;
import com.cscodetech.moverslorry.retrofit.GetResult;
import com.cscodetech.moverslorry.utils.SessionManager;
import com.cscodetech.moverslorry.utils.Utility;
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

public class ForgotPasswordActivity extends BaseActivity implements GetResult.MyListener {

    @BindView(R.id.l1)
    public LinearLayout l1;
    @BindView(R.id.spinnerautocompet)
    public AutoCompleteTextView spinnerautocompet;
    @BindView(R.id.ed_username)
    public TextInputEditText edUsername;
    @BindView(R.id.btn_continus)
    public TextView btnContinus;
    @BindView(R.id.lvl_login)
    public LinearLayout lvlLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        getCode();
    }


    private void checkMobile() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", edUsername.getText().toString());
            jsonObject.put("ccode", spinnerautocompet.getText().toString());

            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(),MediaType.parse("application/json"));
            Call<JsonObject> call = APIClient.getInterface().mobileCheck(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "2");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getCode() {
        JSONObject jsonObject = new JSONObject();
        RequestBody bodyRequest = RequestBody.create(jsonObject.toString(),MediaType.parse("application/json"));
        Call<JsonObject> call = APIClient.getInterface().getCodelist(bodyRequest);
        GetResult getResult = new GetResult(this);
        getResult.setMyListener(this);
        getResult.callForLogin(call, "1");

    }

    @OnClick({R.id.btn_continus, R.id.lvl_login})

    public void onBindClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_continus) {
            if(TextUtils.isEmpty(edUsername.getText().toString())){
                edUsername.setText("");
            }else {
                checkMobile();


            }
        } else if (id == R.id.lvl_login) {
            finish();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                Contry contry = gson.fromJson(result.toString(), Contry.class);
                List<String> arealist = new ArrayList<>();
                for (int i = 0; i < contry.getCountryCode().size(); i++) {
                    if (contry.getCountryCode().get(i).getStatus().equalsIgnoreCase("1")) {
                        arealist.add(contry.getCountryCode().get(i).getCcode());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_dropdown_item_1line, arealist);
                spinnerautocompet.setText(arealist.get(0));
                spinnerautocompet.setAdapter(adapter);
            }else if (callNo.equalsIgnoreCase("2")) {
                Gson gson = new Gson();
                RestResponse response = gson.fromJson(result.toString(), RestResponse.class);
                if (response.getResult().equals("true")) {
                    Toast.makeText(this, "Number is not register", Toast.LENGTH_SHORT).show();

                } else {
                    Utility.getInstance().isvarification=0;
                    SessionManager sessionManager=new SessionManager(this);
                    UserLogin userLogin=new UserLogin();
                    userLogin.setMobile(edUsername.getText().toString());
                    userLogin.setCcode(spinnerautocompet.getText().toString());
                    sessionManager.setUserDetails(userLogin);
                    startActivity(new Intent(this, VerificationActivity.class));

                }
            }
        } catch (Exception e) {
            Log.e("Error","-"+e.getMessage());

        }
    }
}