package com.smartdroidesign.bootcamplocator.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.smartdroidesign.bootcamplocator.holders.LocationsViewHolder;
import com.smartdroidesign.bootcamplocator.model.LocationProject;

import java.util.ArrayList;

/**
 * Created by mstara on 30/01/2018.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationsViewHolder> {

    private ArrayList<LocationProject> locations;

    public LocationsAdapter(ArrayList<LocationProject> locations) {
        this.locations = locations;
    }

    @Override
    public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(LocationsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
