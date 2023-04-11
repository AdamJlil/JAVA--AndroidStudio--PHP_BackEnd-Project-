package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.Marker;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapView;


public class MainActivity_Gmap extends AppCompatActivity {
    MapView mapView;
    LinearLayout layout;
    Button btnReturn;

    Context c =this;
    public static int mapIndex;
    public static LatLng userloc;
    public static String userphone;
    public static String userusername;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gmap);

        btnReturn = findViewById(R.id.btnReturn);
        layout = (LinearLayout) findViewById(R.id.layout);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, MainActivity_Logedin.class);
                startActivity(i);
            }
        });

        String[] long_lat = StaticLog.localisation.split("!!");

        LatLng pos = new LatLng(Double.parseDouble(long_lat[0]) , Double.parseDouble(long_lat[1]));

        if(mapIndex == 1){
            showMapBox(pos, StaticLog.user, StaticLog.phone, 5, 10000);
        }
        else if (mapIndex == 2 ){
            showMapBox(userloc, userusername, userphone, 10, 10000);
        }



    }




    //FUNCTIONS =================================================================================

    private void showMapBox(LatLng pos, String user, String phone, int zoom_lvl, int duration){
        // Set your Mapbox access token
        Mapbox.getInstance(getApplicationContext(),
                "sk.eyJ1IjoidGFpdjc3NyIsImEiOiJjbGJreW1mNWIwMmNiM3ZxeHY4bzJvNWh2In0.C0lwJlaEeEvok2QeSQyoHw");

        // Create a MapView and set the dark style

        mapView = new MapView(c);



        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.DARK, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        // Map is set up and the style has loaded. Now you can start using the map.
                    }
                });
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(pos))
                        .title(user)
                        .snippet(phone);

                com.mapbox.mapboxsdk.annotations.Marker marker
                        = mapboxMap.addMarker(markerOptions);



                // Animate the zoom to the marker's position
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(marker.getPosition())
                        .zoom(zoom_lvl)
                        .build();

                // Create the CameraUpdate object
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

                // Set the duration of the animation
                mapboxMap.animateCamera(cameraUpdate, duration);
            }
        });


        layout.addView(mapView);

    }


}

