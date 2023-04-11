package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MainActivity_Logedin extends AppCompatActivity {
    TextView longitude, latitude, city;
    Context c = this;
    Button btnShowMap;
    public static Button btnShare;
    //listView========================================================
    public static ListView lstView;
    public static ArrayList<ObjetUser> lst = new ArrayList<ObjetUser>();
    //position localisation============================================
    LatLng pos;


    public static myArrayAdapter myArrayAdapter;


    LocationManager mLocationManagerGPS;
    LocationListener mLocationListenerGPS;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_disconnect) {
            StaticLog.localisation = "";
            StaticLog.auto = "";
            StaticLog.id = "";
            StaticLog.user = "";
            StaticLog.phone = "";
            StaticLog.name = "";
            Intent intent = new Intent(c, MainActivity.class);
            c.startActivity(intent);
            return true;
        }

        if (id == R.id.action_profile) {
            Intent intent = new Intent(c, MainActivity_profile.class);
            c.startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logedin);

        Context c = this;
        longitude = findViewById(R.id.txtLongitude);
        latitude = findViewById(R.id.txtLatitude);
        city = findViewById(R.id.txtCity);
        btnShowMap = findViewById(R.id.btnShowMap);
        lstView = (ListView) findViewById(R.id.lstView);
        btnShare = findViewById(R.id.btnAuto);





        // TRY CATCH ============================================================================

        try{

            if(StaticLog.aBoolean){
                StaticLog.aBoolean = false;
                dbWorker db = new dbWorker(this);
                db.execute("userSharedLocation");
            }






            //LOCATION ===========================================================================
            mLocationManagerGPS = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            mLocationListenerGPS = new LocationListener() {
                public void onLocationChanged(Location location) {

                    latitude.setText(Double.toString(location.getLatitude()));
                    longitude.setText(Double.toString(location.getLongitude()));

                    double lat = Double.parseDouble(latitude.getText().toString());
                    double lon = Double.parseDouble(longitude.getText().toString());


                    try {
                        //init
                        Geocoder geocoder = new Geocoder(c, Locale.getDefault());
                        //get
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        Address address = addresses.get(0);

                        //set
                        city.setText(address.getLocality().toUpperCase() + ", " +
                                address.getCountryName().toUpperCase());


                        StaticLog.localisation = lat + "!!" + lon;



                    } catch (Exception e) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(c);
                        builder.setTitle("Warning")
                                .setMessage("Oops! Couldn't get your location.");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }


                }


                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                    showAlert(404);
                }

            };






            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestLocationPermission();
                } else {
                    mLocationManagerGPS.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, mLocationListenerGPS);
                }
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions

                return;
            }
            mLocationManagerGPS.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,  // Use the GPS provider
                    10,  // Minimum time interval between updates (in milliseconds)
                    0,  // Minimum distance between updates (in meters)
                    mLocationListenerGPS  // The location listener to use
            );






        }catch (Exception e ){
            Log.i("toto", e.getMessage());
        }








        //Delete and map zoom_up ===================================================================
        lstView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle("Delete");
                builder.setMessage("Do you want to delete this user from the list");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lst.remove(position);
                        MainActivity_Logedin.myArrayAdapter.notifyDataSetChanged();


                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create();
                builder.show();

                return false;
            }
        });

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dbWorker dbWorker = new dbWorker(c);
                dbWorker.execute("selectLocUser", lst.get(position).getUsername());
            }
        });





        //EVENT BTN CLICK +======================================================================
        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity_Gmap.mapIndex = 1;
                lst.clear();
                Intent intent = new Intent(c, MainActivity_Gmap.class);
                c.startActivity(intent);
            }
        });




        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StaticLog.auto.equals("false")) {


                    try {
                        dbWorker dbw = new dbWorker(c);

                        if(!StaticLog.localisation.contains("!!")){
                            StaticLog.localisation = "not updated";
                        }
                        dbw.execute("updateLocation", StaticLog.localisation, StaticLog.id);

                    }catch (Exception e){
                        Log.i("tag2", e.getMessage());
                    }


                }
                else if(StaticLog.auto.equals("true")){
                    dbWorker dbw = new dbWorker(c);
                    dbw.execute("updateAutoFalse", StaticLog.id);
//                    dbw.execute("userSharedLocation");
                }
            }
        });


    }






    //REQ PERMISSION

    private void requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to know your location ?").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ActivityCompat.requestPermissions(MainActivity_Logedin.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }).show();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Allow us to get your location for further option and better services.").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName())));
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();
        }
    }



    private void showAlert(int messageId) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(messageId).setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void changeBtnAuto(){
        if(StaticLog.auto.equals("true")){
            btnShare.setText("Stop sharing your location");
            btnShare.setBackgroundColor(Color.RED);
        }
        else{
            btnShare.setText("share your location");
            btnShare.setBackgroundColor(Color.argb(255, 0, 255, 0));
        }
    }

}