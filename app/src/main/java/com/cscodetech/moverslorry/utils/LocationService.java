package com.ruru.routelorry.utils;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;



public class LocationService extends Service {

    private static final long INTERVAL = 10000; // 10 seconds
    private static final float SMALLEST_DISPLACEMENT = 5f; // 5 meters
    private FusedLocationProviderClient locationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();
            startForeground(1, notification);
        }

        locationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(INTERVAL);
        locationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    Location location = locationResult.getLastLocation();
                    Log.e("Location Mayur","--"+location.getLatitude());
                    Log.e("Location","--"+location.getLongitude());
                    // Handle location updates here
                }
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestLocationUpdates();
        return START_STICKY;
    }

    private void requestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationClient.removeLocationUpdates(locationCallback);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

