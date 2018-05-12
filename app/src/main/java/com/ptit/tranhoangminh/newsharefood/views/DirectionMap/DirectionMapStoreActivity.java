package com.ptit.tranhoangminh.newsharefood.views.DirectionMap;

import android.content.SharedPreferences;
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
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.presenters.polylinePresenters.DirectionMapPresenterLogic;

public class DirectionMapStoreActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    MapFragment mapFragment;
    double latitue;
    double longtitute;
    SharedPreferences sharedPreferences;
    Location current_location;
    String link;
    //https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&key=AIzaSyCPMjB01i28rWRigg3l_a_pHLs27WnKGz4
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direction_map_layout);

        //location-store
        latitue = getIntent().getDoubleExtra("latitute", 0);
        longtitute = getIntent().getDoubleExtra("longtitute", 0);

        //location-current
        sharedPreferences = getSharedPreferences("toado", MODE_PRIVATE);
        current_location = new Location("");
        current_location.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude", "0")));
        current_location.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude", "0")));

        link="https://maps.googleapis.com/maps/api/directions/json?origin=" + current_location.getLatitude() + "," + current_location.getLongitude() + "&destination=" +latitue+"," + longtitute + "&language=vi&key=AIzaSyCPMjB01i28rWRigg3l_a_pHLs27WnKGz4";

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(current_location.getLatitude(),current_location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        googleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        googleMap.moveCamera(cameraUpdate);

        DirectionMapPresenterLogic directionMapPresenterLogic=new DirectionMapPresenterLogic();
        directionMapPresenterLogic.getRoadToStore(googleMap,link);
    }
}
