package com.smartdroidesign.bootcamplocator.services;

import com.smartdroidesign.bootcamplocator.model.LocationProject;

import java.util.ArrayList;

/**
 * Created by mstara on 29/01/2018.
 */

// Creating the actual data for the locations

public class DataService {
    private static final DataService Instance = new DataService();

    public static DataService getInstance() {
        return Instance;
    }

    private DataService() {

    }

    public ArrayList<LocationProject> getBootcampLocationWithin10MilesOfZip(int zipcode) {

        ArrayList<LocationProject> list = new ArrayList<>();
        list.add(new LocationProject(53.290f, 6.353f, "Tallaght", "24 New Bancroft Avenue", "slo"));
        list.add(new LocationProject(53.321f, 6.280f, "Harold's Cross", "11 Mountain View Avenue", "slo"));
        list.add(new LocationProject(53.265f,  6.229f, "Sandyford", "2 Lambs Cross Dublin 18 Ireland", "slo"));
        return list;

    }
}
