package com.example.dellpc.khabri.ui.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dellpc.khabri.R;
import com.example.dellpc.khabri.activity.Dashboard;
import com.example.dellpc.khabri.data.DataManager;
import com.example.dellpc.khabri.data.SharedPrefsHelper;
import com.example.dellpc.khabri.utils.MvpApp;
import com.example.dellpc.khabri.utils.Utility;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Random;

public class SplashActivity extends Activity  implements SplashMvpView{
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    final String[] textlist = {"24 Hour News Source", "It’s a New Story Here.", "News From Your Neighborhood", "See It First Here."};
    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    GoogleApiClient mGoogleApiClient;
    private String TAG = SplashActivity.class.getSimpleName();
    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;
    private SharedPrefsHelper mPref;

    SplashPresenter mSplashPresenter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();

        mSplashPresenter = new SplashPresenter(dataManager);
        mSplashPresenter.onAttach(this);

        mSplashPresenter.iniliatizeAddtionalClients();
        mSplashPresenter.checkInternet();
    }



    @Override
    public boolean isOnline() {
        return Utility.isOnline(SplashActivity.this);
    }

    @Override
    public void initializeClients() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mGoogleApiClient = new GoogleApiClient.Builder(SplashActivity.this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        mPref=new SharedPrefsHelper(SplashActivity.this);

        TextView message = findViewById(R.id.message);
        Random rand = new Random();

        int  n = rand.nextInt(textlist.length);
        message.setText(textlist[n]);
    }


    @Override
    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           mSplashPresenter.requestPermission();
            return;
        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLastLocation = task.getResult();

                                mPref.setLatitude(String.valueOf(mLastLocation.getLatitude()));
                                mPref.setLongitude(String.valueOf(mLastLocation.getLongitude()));

                                if (Utility.isOnline(SplashActivity.this)) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity( new Intent(SplashActivity.this,Dashboard.class));
                                        }
                                    },1500);
                                    ;

                                } else {
                                    mSplashPresenter.noInternet();
                                }

                            } else {
                               mSplashPresenter.enableLocation();
                            }


                        }
                    });
        }

    }

    /**
     * Return the current state of the permissions needed.
     */
    @Override
    public boolean CheckLocationPermission() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(SplashActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }


    @Override
    public void dialognointernet()
    {

        AlertDialog dialog = new AlertDialog.Builder(SplashActivity.this)
                .setTitle("Connection Failed")
                .setMessage("Please check your internet connection.")

                .setNegativeButton("Try Again", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mSplashPresenter.checkInternet();
                    }
                })
                .setNeutralButton("Show Saved data",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(SplashActivity.this,"No saved stories available. Please enable internet connection",Toast.LENGTH_LONG).show();



                    }
                }).setCancelable(false)
                .create();
        dialog.show();
    }

    @Override
    public void showSettingDialog()
    {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//Setting priotity of Location request to high
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);//5 sec Time interval for location update
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient to show dialog always when GPS is off

        //noinspection deprecation
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                // final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        //updateGPSStatus("GPS is Enabled in your device");

                       mSplashPresenter.getUserLocation();


                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(SplashActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            mPref.setLatitude("28.5355");
                            mPref.setLongitude("77.3910");

                        startActivity( new Intent(SplashActivity.this,Dashboard.class));
                        // continuewithoutinternet();


                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }


}

