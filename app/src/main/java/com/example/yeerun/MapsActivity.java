package com.example.yeerun;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageButton plusButton;
    LatLng start;
    LatLng end;
    Editable rout_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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
        LatLng sydney = new LatLng(44, 22);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        plusButton = findViewById(R.id.plusButton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_showDialog(v);
            }
        });


    }

    public void btn_showDialog(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder((MapsActivity.this));
        View mView = getLayoutInflater().inflate(R.layout.routdialog, null);

        final EditText routname_textview = (EditText) mView.findViewById(R.id.routname_textview);
        Button btn_start = (Button) mView.findViewById(R.id.startlocation_button);
        Button btn_end = (Button) mView.findViewById(R.id.endlocation_button);
        Button btn_done = (Button) mView.findViewById(R.id.done_button);

        alert.setView(mView);



        final AlertDialog alertDialog = alert.create();
        alertDialog.show();

        btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertDialog.hide();

                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                             start = latLng;
                            alertDialog.show();
                        }
                    });
                }
        });
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.hide();

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        end = latLng;
                        alertDialog.show();
                    }
                });
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rout_name = routname_textview.getText();
                alertDialog.dismiss();
                System.out.println("Name: " + rout_name);
                System.out.println("Start latitude = " + start.latitude + "Start longitude = " + start.longitude);
                System.out.println("End latitude = " + end.latitude + "End longitude = " + end.longitude);
                String text = "Name: " + rout_name + "Start latitude = " + start.latitude + "Start longitude = " + start.longitude + "End latitude = " + end.latitude + "End longitude = " + end.longitude;
                showToast(v,text);
            }
        });
    }
    public void showToast(View view, String text){
        Toast.makeText(this, text,Toast.LENGTH_LONG).show();
    }
}