package com.cscodetech.moverslorry.ui;


import static com.cscodetech.moverslorry.utils.SessionManager.intro;
import static com.cscodetech.moverslorry.utils.SessionManager.login;

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
import com.cscodetech.moverslorry.model.Login;
import com.cscodetech.moverslorry.model.RestResponse;
import com.cscodetech.moverslorry.retrofit.APIClient;
import com.cscodetech.moverslorry.retrofit.GetResult;
import com.cscodetech.moverslorry.utils.CustPrograssbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.onesignal.OneSignal;

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

public class LoginActivity extends BaseActivity implements GetResult.MyListener {


    @BindView(R.id.lvl_password)
    public LinearLayout lvlPassword;
    @BindView(R.id.txt_forgot)
    public TextView txtForgot;
    @BindView(R.id.btn_login)
    public TextView btnLogin;
    @BindView(R.id.lvl_signup)
    public LinearLayout lvlSignup;

    @BindView(R.id.spinnerautocompet)
    public AutoCompleteTextView spinnerautocompet;
    @BindView(R.id.menu)
    public TextInputLayout menu;
    @BindView(R.id.ed_username)
    public TextInputEditText edUsername;
    @BindView(R.id.edit_password)
    public TextInputEditText editPassword;
    CustPrograssbar custPrograssbar;
    String applicationjson="application/json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ButterKnife.bind(this);
        custPrograssbar = new CustPrograssbar();
        getCode();

    }

    private void getCode() {
        JSONObject jsonObject = new JSONObject();
        RequestBody bodyRequest = RequestBody.create(jsonObject.toString(),MediaType.parse(applicationjson));
        Call<JsonObject> call = APIClient.getInterface().getCodelist(bodyRequest);
        GetResult getResult = new GetResult(this);
        getResult.setMyListener(this);
        getResult.callForLogin(call, "1");

    }

    private void checkMobile() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", edUsername.getText().toString());
            jsonObject.put("ccode", spinnerautocompet.getText().toString());

            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(),MediaType.parse(applicationjson));
            Call<JsonObject> call = APIClient.getInterface().mobileCheck(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "2");
            custPrograssbar.prograssCreate(LoginActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void login() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", edUsername.getText().toString());
            jsonObject.put("ccode", spinnerautocompet.getText().toString());
            jsonObject.put("password", editPassword.getText().toString());

            RequestBody bodyRequest = RequestBody.create(jsonObject.toString(),MediaType.parse(applicationjson));
            Call<JsonObject> call = APIClient.getInterface().userLogin(bodyRequest);
            GetResult getResult = new GetResult(this);
            getResult.setMyListener(this);
            getResult.callForLogin(call, "3");
            custPrograssbar.prograssCreate(LoginActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.txt_forgot, R.id.btn_login, R.id.lvl_signup})
    public void onBindClick(View view) {
        switch (view.getId()) {

            case R.id.txt_forgot:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));

                break;
            case R.id.btn_login:

                if (lvlPassword.getVisibility() == View.VISIBLE) {
                    if (!TextUtils.isEmpty(edUsername.getText().toString()) && !TextUtils.isEmpty(editPassword.getText().toString())) {
                        login();
                    }

                } else {
                    if (!TextUtils.isEmpty(edUsername.getText().toString())) {
                        checkMobile();
                    }
                }
                break;
            case R.id.lvl_signup:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
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
            } else if (callNo.equalsIgnoreCase("2")) {
                Gson gson = new Gson();
                RestResponse response = gson.fromJson(result.toString(), RestResponse.class);
                if (response.getResult().equals("true")) {
                    Toast.makeText(this, "Number is not register", Toast.LENGTH_SHORT).show();

                } else {

                    lvlPassword.setVisibility(View.VISIBLE);

                }
            } else if (callNo.equalsIgnoreCase("3")) {
                Gson gson = new Gson();
                Login loginUser = gson.fromJson(result.toString(), Login.class);
                Toast.makeText(this, loginUser.getResponseMsg(), Toast.LENGTH_LONG).show();
                if (loginUser.getResult().equalsIgnoreCase("true")) {

                    sessionmanager.setUserDetails(loginUser.getUserLogin());

                    sessionmanager.setBooleanData(login, true);
                    sessionmanager.setBooleanData(intro, true);

                    OneSignal.sendTag("owner_id", loginUser.getUserLogin().getId());

                    startActivity(new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
            }
        } catch (Exception e) {
            Log.e("Error","--> "+e.getMessage());

        }
    }
}