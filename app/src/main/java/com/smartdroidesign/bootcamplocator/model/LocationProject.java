package com.smartdroidesign.bootcamplocator.model;

/**
 * Created by mstara on 29/01/2018.
 */

// Model class to define the data for each of the markers and the list item

public class LocationProject {

    private float longitude;
    private float latitude;
    private String locationTitle;
    private String locationAddress;
    private String locationImgUrl;

    public LocationProject(float latitude, float longitude, String locationTitle, String locationAddress, String locationImgUrl) {
        this.longitude = latitude;
        this.latitude = longitude;
        this.locationTitle = locationTitle;
        this.locationAddress = locationAddress;
        this.locationImgUrl = locationImgUrl;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float lotitude) {
        this.latitude = lotitude;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public void setLocationTitle(String locationTitle) {
        this.locationTitle = locationTitle;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationImgUrl() {
        return locationImgUrl;
    }

    public void setLocationImgUrl(String locationImgUrl) {
        this.locationImgUrl = locationImgUrl;
    }
}
