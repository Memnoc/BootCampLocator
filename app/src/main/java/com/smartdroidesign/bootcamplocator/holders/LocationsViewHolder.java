package com.smartdroidesign.bootcamplocator.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.smartdroidesign.bootcamplocator.R;
import com.smartdroidesign.bootcamplocator.model.LocationProject;

/**
 * Created by mstara on 30/01/2018.
 */

public class LocationsViewHolder extends RecyclerView.ViewHolder {

    private ImageView locationImage;
    private TextView locationTitle;
    private TextView locationAddress;

    public LocationsViewHolder(View itemView) {
        super(itemView);

        locationTitle = (TextView)itemView.findViewById(R.id.location_title);
        locationTitle = (TextView)itemView.findViewById(R.id.location_address);
        locationImage = (ImageView)itemView.findViewById(R.id.location_image);
    }

    public void updateUI(LocationProject location){

    }
}
