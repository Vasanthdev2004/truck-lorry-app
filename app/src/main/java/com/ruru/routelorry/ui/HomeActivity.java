package com.ruru.routelorry.ui;

import android.Manifest;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ruru.routelorry.R;
import com.ruru.routelorry.fragment.HomeFragment;
import com.ruru.routelorry.fragment.ProfileFragment;
import com.ruru.routelorry.utils.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation)
    public BottomNavigationView bottomNavigation;
    @BindView(R.id.container)
    public FrameLayout container;

    private static final HomeActivity instance = new HomeActivity();
    public static HomeActivity getInstance() {
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                }, 1010);
        ButterKnife.bind(this);

        openFragment(new HomeFragment(), "Homepage");
        if (!Utility.hasGPSDevice(this)) {
            Utility.enableLoc(this);
        }


//        Intent intent = new Intent(this, LocationService.class);
//        if (!isRunning(this, intent)) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                startForegroundService(intent);
//            } else {
//                startService(intent);
//            }
//        }


        bottomNavigation.setOnItemSelectedListener(item -> {
            // Handle navigation item selection

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    openFragment(new HomeFragment(), "Homepage");
                    return true;
                case R.id.navigation_sms:

                    item.setCheckable(false);
                    startActivity(new Intent(HomeActivity.this, MyLoadActivity.class));

                    return true;
                case R.id.navigation_notifications:
                    item.setCheckable(false);
                    startActivity(new Intent(HomeActivity.this, MyBookLoadActivity.class));
                    return true;
                case R.id.navigation_notifications2:

                    openFragment(new ProfileFragment(), "");
                    return true;
                default:
                    return false;
            }

        });

    }


    public void openFragment(Fragment fragment, String s) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(s);
        transaction.commit();

    }

    public void profileMenuClick() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        fragment.onActivityResult(requestCode, resultCode, data);
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof HomeFragment && fragment.isVisible()) {
            Log.e("dada", "active");
        } else {
            finish();
            Log.e("dada", "Inactive");

        }
    }

    private boolean isRunning(Context context, Intent intent) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (services == null || services.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo info : services) {
            if (info.service.equals(intent.getComponent())) {
                return true;
            }
        }
        return false;
    }
}