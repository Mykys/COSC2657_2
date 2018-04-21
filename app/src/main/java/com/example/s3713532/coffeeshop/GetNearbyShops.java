package com.example.s3713532.coffeeshop;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s3713532 on 4/21/18.
 */

public class GetNearbyShops extends AsyncTask<Void, Void, Void> {

    private GoogleMap mMap;
    private double lat, lon, radius;
    private String json;
    private int state;
    private int price;

    private CameraZoomView screen;

    private List<Shop> shopsWithinZoomLevel;

    public GetNearbyShops (GoogleMap mMap, double lat, double lon, double radius, int state, int price) {
        this.mMap = mMap;
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
        this.state = state;
        this.screen = new CameraZoomView(mMap);
        this.price = price;
    }

    public List<Shop> getShopsWithinZoomLevel() {
        return shopsWithinZoomLevel;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        json = HttpHandler.get("http://bestlab.us:8080/places");

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        // Retrieves all shops in database
        Gson gson = new Gson();
        Shop[] shops = gson.fromJson(json, Shop[].class);

        List<Shop> nearbyShopList = screen.findNearbyShops(shops, lat, lon, radius);

        if (state == 1) {
            shopsWithinZoomLevel = screen.findShopsWithinZoomLevel(nearbyShopList);
            screen.ShowNearbyShops(shopsWithinZoomLevel);
        }

        if (state == 2) {
            shopsWithinZoomLevel = screen.findShopsWithinZoomLevel(nearbyShopList);
            List<Shop> filteredShops = screen.findShopWithinPriceRange(shopsWithinZoomLevel, price);
            screen.ShowNearbyShops(filteredShops);
        }

    }

}
