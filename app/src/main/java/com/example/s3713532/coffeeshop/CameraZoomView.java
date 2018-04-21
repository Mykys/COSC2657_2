package com.example.s3713532.coffeeshop;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by s3713532 on 4/21/18.
 */

public class CameraZoomView {

    private GoogleMap mMap;
    private LatLngBounds currBounds;
    private double prevX, prevY;

    public CameraZoomView (GoogleMap mMap) {
        this.mMap = mMap;
        //this.prevBounds = null;
        this.currBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
        this.prevX = 0;
        this.prevY = 0;
    }

    public LatLngBounds getCurrBounds() {
        return currBounds;
    }

    public void update() {
        prevX = currBounds.southwest.latitude;
        prevY = currBounds.northeast.longitude;
        currBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
    }

    // Find nearby places from chosen location
    public List<Shop> findNearbyShops(Shop[] shops, double currLat, double currLon, double radius) {

        List<Shop> nearbyShopList = new ArrayList<>();

        for (Shop shop : shops) {
            double dist = distance(shop.getLat(), shop.getLon(), currLat, currLon) * 1.609344; // kilometers
            if (dist <= radius) {
                nearbyShopList.add(shop);
            }
        }

        return nearbyShopList;
    }

    // Show nearby shops
    public void ShowNearbyShops(List<Shop> nearbyShopsList) {
        mMap.clear();

        for (Shop shop : nearbyShopsList) {
            LatLng latLng = new LatLng(shop.getLat(), shop.getLon());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(shop.getName())
                    //.snippet(snippet)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.kitty));
            mMap.addMarker(markerOptions);
        }
    }

    // Find shops within the current camera zoom level
    public List<Shop> findShopsWithinZoomLevel (List<Shop> nearbyShopList) {

        double x1 = currBounds.southwest.latitude;
        double x2 = currBounds.northeast.latitude;
        double y1 = currBounds.northeast.longitude;
        double y2 = currBounds.southwest.longitude;

        List<Shop> shopsWithinZoomLevel = new ArrayList<>();

        for (Shop shop : nearbyShopList) {
            double shopLat = shop.getLat();
            double shopLon = shop.getLon();
            if (shopLat >= x1 && shopLat <= x2 && shopLon <= y1 && shopLon >= y2) {
                shopsWithinZoomLevel.add(shop);
            }
        }
        return shopsWithinZoomLevel;
    }

    // Check if the camera view has changed
    public boolean isCameraViewChange () {
        double currX = currBounds.southwest.latitude;
        double currY = currBounds.northeast.longitude;

        if (currX != prevX || currY != prevY) {
            prevX = currX;
            prevY = currY;
            return true;
        }
        return false;
    }

    // Shops within price range
    public List<Shop> findShopWithinPriceRange (List<Shop> nearbyShopList, int priceRange) {

        List<Shop> shopsWithinPriceRange = new ArrayList<>();

        for (Shop shop : nearbyShopList) {
            if (shop.getPrice() <= priceRange) {
                shopsWithinPriceRange.add(shop);
            }
        }
        return shopsWithinPriceRange;
    }

    // Calculate the distance between two locations
    private double getDistance (LatLng locA, LatLng locB) {
        return distance(locA.latitude, locA.longitude, locB.latitude, locB.longitude);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
