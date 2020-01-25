package com.example.yeerun;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapRouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng start;
    LatLng end;
    LatLng myPosition;
    String name;

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
        DatabaseService.getDatabaseService().addRoute(name,5.0, "15:00", start.latitude, start.longitude, end.latitude, end.longitude);
    }

    public void showToast(String text){
        Toast.makeText(this, text,Toast.LENGTH_LONG).show();
    }
}
