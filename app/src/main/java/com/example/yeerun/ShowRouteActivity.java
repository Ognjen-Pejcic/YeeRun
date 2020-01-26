package com.example.yeerun;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Text;


public class ShowRouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng start;
    LatLng end;
    LatLng myPosition;
    String name;
    TextView textView;
    LocationManager locationManager;
    LocationListener locationListener;
    String time;
    TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_route);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        String infoText = getIntent().getStringExtra("ROUTE");
        //showToast(infoText);
        String[] info = infoText.split("///");
        name = info[0];
        start = new LatLng(Double.parseDouble(info[1]), Double.parseDouble(info[2]));
        end = new LatLng(Double.parseDouble(info[3]), Double.parseDouble(info[4]));
        System.out.println(start.latitude + " " + start.longitude);
        textView = (TextView)findViewById(R.id.textView);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //getLocation();


        mMap.setMyLocationEnabled(true);
        LatLng location = new LatLng(44, 22);
        mMap.addMarker(new MarkerOptions().position(start).title("START"));
        mMap.addMarker(new MarkerOptions().title("FINISH").position(end));
        textView.setText(name);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        //textView2 = (TextView)findViewById(R.id.textView2);
        //Button locationBtn = (Button)findViewById(R.id.locationBtn);
        Button startBtn = (Button)findViewById(R.id.startBtn);
        final StopWatch s = new StopWatch();
//        locationBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //getLocation();
//                s.stopThread();
//            }
//        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMap.addPolyline(new PolylineOptions().add(start,end).width(10).color(Color.parseColor("#ccff00")));
//                s.startThread();
//
//                while (true)
//                {
//                    int[] curTime = s.getTime();
//                    //System.out.println(curTime[0] + " : " + curTime[1] + " : " + curTime[2] + " : " + curTime[3]);
//                    time ="Time: " + curTime[1] + ":" + curTime[2];
//                    changeTime(time);
//                }
            }
        });



    }

    private void changeTime(String time) {
        System.out.println(time);
        textView2.setText(time);
    }

    private void getLocation() {
        Intent intent = getIntent();
        if (intent.getIntExtra("Place Number",0) == 0 ){

            // Zoom into users location
            locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    centreMapOnLocation(location,"Your Location");
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centreMapOnLocation(lastKnownLocation,"Your Location");
                mMap.addMarker(new MarkerOptions().position(start).title("START"));
                mMap.addMarker(new MarkerOptions().title("FINISH").position(end));

            } else {

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
    }

    public void centreMapOnLocation(Location location, String title){
        if(location!=null) {
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(userLocation).title(title));
            mMap.addMarker(new MarkerOptions().position(start).title("START"));
            mMap.addMarker(new MarkerOptions().title("FINISH").position(end));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12));
        }

    }
}
