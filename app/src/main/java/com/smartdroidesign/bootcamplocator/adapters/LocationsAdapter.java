package com.smartdroidesign.bootcamplocator.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartdroidesign.bootcamplocator.R;
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
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_location, parent, false);
        return new LocationsViewHolder(card);
    }

    @Override
    public void onBindViewHolder(LocationsViewHolder holder, int position) {
        final LocationProject location = locations.get(position);
        holder.updateUI(location);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
