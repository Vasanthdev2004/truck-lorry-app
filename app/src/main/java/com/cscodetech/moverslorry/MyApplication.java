package com.cscodetech.moverslorry;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.onesignal.OneSignal;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

            FirebaseApp.initializeApp(this);
            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

            // OneSignal Initialization
            OneSignal.initWithContext(this);
            OneSignal.setAppId("*****************************************");
    }


}