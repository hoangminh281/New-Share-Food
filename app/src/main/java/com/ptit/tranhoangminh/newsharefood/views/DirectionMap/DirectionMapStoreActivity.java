package com.ptit.tranhoangminh.newsharefood.views.DirectionMap;
//test github

import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.presenters.polylinePresenters.DirectionMapPresenterLogic;

import java.util.List;


public class DirectionMapStoreActivity extends AppCompatActivity implements OnMapReadyCallback, DirectionImp {
    GoogleMap googleMap;
    MapFragment mapFragment;
    double latitue;
    double longtitute;
    SharedPreferences sharedPreferences;
    Location current_location;
    String link;
    DirectionMapPresenterLogic directionMapPresenterLogic;

    //https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&key=AIzaSyCPMjB01i28rWRigg3l_a_pHLs27WnKGz4
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direction_map_layout);

        directionMapPresenterLogic = new DirectionMapPresenterLogic(DirectionMapStoreActivity.this);
        //location-store
        latitue = getIntent().getDoubleExtra("latitute", 0);
        longtitute = getIntent().getDoubleExtra("longtitute", 0);


        //location-current
        sharedPreferences = getSharedPreferences("toado", MODE_PRIVATE);
        current_location = new Location("");
        current_location.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude", "0")));
        current_location.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude", "0")));

        link = "https://maps.googleapis.com/maps/api/directions/json?origin=" + current_location.getLatitude() + "," + current_location.getLongitude() + "&destination=" + latitue + "," + longtitute + "&language=vi&key=AIzaSyBIZGjL6zFg5ukQr0ymPhz7ZLpje-cCMkw";
        // link="https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&key=AIzaSyCPMjB01i28rWRigg3l_a_pHLs27WnKGz4";
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.clear();
        //marker current_location
        LatLng latLng = new LatLng(current_location.getLatitude(), current_location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        googleMap.addMarker(markerOptions);

        //marker store_location
        LatLng latLng_store = new LatLng(latitue, longtitute);
        MarkerOptions markerOptions_store = new MarkerOptions();
        markerOptions_store.position(latLng_store);

        googleMap.addMarker(markerOptions_store);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);


        directionMapPresenterLogic.getRoadToStore(googleMap, link);

    }

    @Override
    public void GetListToaDo(List<LatLng> latLngList) {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.width(15);
        polylineOptions.color(Color.RED);
        for (LatLng value : latLngList) {
            polylineOptions.add(value);
        }
        Polyline polyline = googleMap.addPolyline(polylineOptions);
    }
}
