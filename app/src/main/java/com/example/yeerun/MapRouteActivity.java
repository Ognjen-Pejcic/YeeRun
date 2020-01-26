package com.example.yeerun;

import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import Modules.DirectionFinderListener;


public class MapRouteActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    LatLng start;
    LatLng end;
    LatLng myPosition;
    String name;
    ProgressDialog progressDialog;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_route);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        String infoText = getIntent().getStringExtra("START");
        showToast(infoText);
        String[] info = infoText.split("///");
        name = info[0];
        start = new LatLng(Double.parseDouble(info[1]), Double.parseDouble(info[2]));
        end = new LatLng(Double.parseDouble(info[3]), Double.parseDouble(info[4]));
        System.out.println(start.latitude + " " + start.longitude);

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

        // Add a marker in Sydney and move the camera
//        LatLng yourLocation = new LatLng(myPosition.latitude, myPosition.longitude);
        mMap.addMarker(new MarkerOptions().position(start).title("START"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(start));
        mMap.addMarker(new MarkerOptions().title("FINISH").position(end));

        DatabaseService.getDatabaseService().addRoute(name,0.0, "00:00", start.latitude, start.longitude, end.latitude, end.longitude);

        mMap.setMyLocationEnabled(true);
        mMap.addPolyline(new PolylineOptions().add(start,end).width(10).color(Color.parseColor("#ccff00")));
    }

    public void showToast(String text){
        Toast.makeText(this, text,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDirectionFinderStart() {
         progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Rout> rout) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Rout route : rout) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 16));
//            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
//            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

//            originMarkers.add(mMap.addMarker(new MarkerOptions()
                   // .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                  //  .title(route.startAddress)
//                    .position(start)));
//            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
               //     .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                 //   .title(route.endAddress)
//                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
    }
}
}
