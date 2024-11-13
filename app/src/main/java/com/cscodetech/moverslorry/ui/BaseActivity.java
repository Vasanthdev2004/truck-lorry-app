package com.cscodetech.moverslorry.ui;


import static com.cscodetech.moverslorry.utils.SessionManager.language;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.cscodetech.moverslorry.R;
import com.cscodetech.moverslorry.utils.SessionManager;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {

    public SessionManager sessionmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionmanager =new SessionManager(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if(sessionmanager.getStringData(language)==null){
            setApplicationlanguage("en");
        }else {
            setApplicationlanguage(sessionmanager.getStringData(language));

        }


    }
    public void setApplicationlanguage(String language) {
        try {
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                conf.setLocale(new Locale(language)); // API 17+ only.
            } else {
                conf.locale = new Locale(language);
            }
            res.updateConfiguration(conf, dm);
        }catch (Exception e){
            Log.e("Error for RTL ","-->"+e.getMessage());
        }

    }
}