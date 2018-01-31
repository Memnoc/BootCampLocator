package com.smartdroidesign.bootcamplocator.fragments;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainScreenFragment extends Fragment  implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions userMarker;
    private LocationsListFragment mListFragment;


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

        mListFragment = (LocationsListFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.conatainer_locations_list);

        if(mListFragment == null){
            mListFragment = LocationsListFragment.newInstance();
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.conatainer_locations_list, mListFragment)
                    .commit();

        }

        // Setting the text field into the search bar
        final EditText zipText = (EditText)view.findViewById(R.id.zip_code_text);
        zipText.setOnKeyListener(new View.OnKeyListener() {

            // Grabbing the text from the search bar when the user presses enter
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {

                    // Here is where you would check the validity of the zip code in a real-world app
                    // Also parsing the string into an integer, and pass it over to the  updateMapForZip() functions
                    String text = zipText.getText().toString();
                    int zip = Integer.parseInt(text);

                    // Here is where you hide the soft keyboard
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(zipText.getWindowToken(),0);


                    showList();
                    updateMapForZip(zip);

                    return true;
                }

                return false;
            }
        });

        hideList();
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

        // Determining user location with Geocoder using the locale of your device when you don't have a user typing the zip code
        // this will grab the user's location long and lat provided by Geocoder, convert it to a string, pass it over to updateMapForZip(zip);
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            // Grabbing the first address from the array list Address
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            // Grabbing the zip code from that list
            int zip = 0;
            try {
                zip = Integer.parseInt(addresses.get(0).getPostalCode());
            } catch (NumberFormatException e) {
                System.out.println("assigned value");
            }

            updateMapForZip(zip);
        } catch (IOException exception) {

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

    private void showList(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .show(mListFragment)
                .commit();


    }

    private void hideList(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .hide(mListFragment)
                .commit();


    }

}
