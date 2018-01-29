package com.smartdroidesign.bootcamplocator.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smartdroidesign.bootcamplocator.R;
import com.smartdroidesign.bootcamplocator.model.LocationProject;
import com.smartdroidesign.bootcamplocator.services.DataService;

import java.util.ArrayList;

public class MainScreenFragment extends Fragment  implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions userMarker;


    public MainScreenFragment() {
        // Required empty public constructor
    }


    public static MainScreenFragment newInstance() {
        MainScreenFragment fragment = new MainScreenFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


    // 7 - Visualize the user location on the map given latitude and longitude objects
    // Storing the marker for the user
    // removing the previous marker when/if position has changed
    // store marker as private MarkerOptions userMarker;

    public void setUserMarker(LatLng latLng){
        if(userMarker == null){
            userMarker = new MarkerOptions().position(latLng).title("Current location");
            mMap.addMarker(userMarker);
            Log.v("DONKEY", "Lat: " + latLng.latitude + "Long: " + latLng.longitude);
        }

        updateMapForZip(92284);
        // 9 - Actually moving the camera where the marker is on the screen
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

    }

    private void updateMapForZip(int zipcode){

        // Fake data
        ArrayList<LocationProject> locations = DataService.getInstance().getBootcampLocationWithin10MilesOfZip(zipcode);

        for (int x = 0; x < locations.size(); x++) {
            LocationProject loc = locations.get(x);
            MarkerOptions marker = new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude()));
            marker.title(loc.getLocationTitle());
            marker.snippet(loc.getLocationAddress());
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
            mMap.addMarker(marker);

        }
    }

}
