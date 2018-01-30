package com.smartdroidesign.bootcamplocator.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.smartdroidesign.bootcamplocator.R;
import com.smartdroidesign.bootcamplocator.fragments.MainScreenFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener {

    final int PERMISSION_LOCATION = 111;
    private GoogleApiClient mGoogleApiClient;

    // 8 - Storing a reference of the MainScreenFragment
    private MainScreenFragment mainScreenFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // 1 - Building object for GoogleApiClient

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        // 0 - Loading MainScreenFragment
        mainScreenFragment = (MainScreenFragment)getSupportFragmentManager().findFragmentById(R.id.container_main_fragment);
        if(mainScreenFragment == null) {
            mainScreenFragment = MainScreenFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_main_fragment, mainScreenFragment)
                    .commit();
        }

    }

    // 2 - Overriding onStart and connecting GoogleApiClient

    @Override
    protected void onStart() {
        super.onStart();
    }


    // 3 -  GoogleApiClient callback:
    // Checking for permission - checkSelfPermission();
    // If no permissions exist, asking for it - onRequestPermissionsResult();
    // If permission given, turn on LocationServices - startLocationServices(); via requestLocationUpdates();

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);
            Log.v("DONKEY", "Requesting permissions");
        } else {
            Log.v("DONKEY", "Starting LocationServices from onConnected");
            startLocationServices();
        }

    }


    // 4 -  Make a pop up appear on the screen asking for permissions
    // If permission granted startLocationServices();
    // If permission not granted "else" (in this case we let the user know)

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startLocationServices();
                    Log.v("DONKEY", "Permissions granted");
                } else {
                    // show a pop up dialog stating permission has been denied
                    Log.v("DONKEY", "Permissions not granted");

                }
            }
        }
    }


    // 5 -  Sending request to start a Location Service and retrieve the location
    // Creates a request that asks for location every once in a while - LocationRequest.PRIORITY_LOW_POWER
    // If permissions are not handled properly, throw an error/exception

    public void startLocationServices(){
        Log.v("DONKEY", "Starting location services called");

        try {
            LocationRequest req = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, req, this);
            // About the deprecation of FusedLocationApi: https://stackoverflow.com/questions/46481789/android-locationservices-fusedlocationapi-deprecated
            Log.v("DONKEY", "Requesting location's updates");
        } catch (SecurityException exception) {
            Log.v("DONKEY", exception.toString());
        }

    }


    // 6 - Getting the user location
    @Override
    public void onLocationChanged(Location location) {
        Log.v("DONKEY", "Long: " + location.getLongitude() + "- Lat: " + location.getLatitude());
        // - 9 Updating the fragment visually whenever the user position changes
        // turning location into latitude and longitude
        mainScreenFragment.setUserMarker(new LatLng(location.getLatitude(), location.getLongitude()));
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    // Overriding onStop
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
