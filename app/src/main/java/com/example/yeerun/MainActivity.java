package com.example.yeerun;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int STORAGE_PERMISSION_CODE=1;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this,"Permission already granted",Toast.LENGTH_LONG).show();
        }else{
            requestStoragePermission();
        }

        //DatabaseService.getDatabaseService().makeFileWithRoutes();
        LinkedList<Rout> routes = DatabaseService.getDatabaseService().loadRoutes();
        ArrayList<String> id= new ArrayList<>();
        ArrayList<String> names= new ArrayList<>();
        ArrayList<String> lengths= new ArrayList<>();
        ArrayList<String> times= new ArrayList<>();

        for(int i = 0; i<routes.size(); i++){
            id.add("#" + (i+1));
            names.add(routes.get(i).getName());
            lengths.add(routes.get(i).getLength()+"");
            times.add(routes.get(i).getTime());
        }
//
//        ListView listViewNum= (ListView) findViewById(R.id.listViewNum);
//        final ArrayAdapter<String> arrayAdapterNum = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, id);
//        listViewNum.setAdapter(arrayAdapterNum);
//
//        ListView listView= (ListView) findViewById(R.id.listView);
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, names);
//        listView.setAdapter(arrayAdapter);
//
//        ListView listView2= (ListView) findViewById(R.id.listView2);
//        final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, lengths);
//        listView2.setAdapter(arrayAdapter2);
//
//        ListView listView3= (ListView) findViewById(R.id.listView3);
//        final ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, times);
//        listView3.setAdapter(arrayAdapter3);
//
//        ListView header1 = (ListView) findViewById(R.id.header1);
//        ListView header2 = (ListView) findViewById(R.id.header2);
//        ListView header3 = (ListView) findViewById(R.id.header3);
//        ListView header4 = (ListView) findViewById(R.id.header4);
//        ArrayList<String> header1Text= new ArrayList<>();
//        header1Text.add("ID");
//        final ArrayAdapter<String> headerAdapter1 = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, header1Text);
//        header1.setAdapter(headerAdapter1);
//        ArrayList<String> header2Text= new ArrayList<>();
//        header2Text.add("Name");
//        final ArrayAdapter<String> headerAdapter2 = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, header2Text);
//        header2.setAdapter(headerAdapter2);
//        ArrayList<String> header3Text= new ArrayList<>();
//        header3Text.add("Length");
//        final ArrayAdapter<String> headerAdapter3 = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, header3Text);
//        header3.setAdapter(headerAdapter3);
//        ArrayList<String> header4Text= new ArrayList<>();
//        header4Text.add("Time");
//        final ArrayAdapter<String> headerAdapter4 = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, header4Text);
//        header4.setAdapter(headerAdapter4);

        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setBackgroundColor(Color.parseColor("#ccff00"));
        button1.setTextColor(Color.WHITE);

        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setBackgroundColor(Color.TRANSPARENT);
        button2.setTextColor(Color.WHITE);

        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                button1.setBackgroundColor(Color.parseColor("#ccff00"));
                button2.setBackgroundColor(Color.TRANSPARENT);
                return false;
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityRouts();
            }
        });
        button2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                button2.setBackgroundColor(Color.parseColor("#ccff00"));
                button1.setBackgroundColor(Color.TRANSPARENT);
                return false;
            }
        });

        Spinner spinner = (Spinner) this.findViewById(R.id.spinner);
        List<String> spinnerList = new ArrayList<String>();
        spinnerList.add("Name A-Z");
        spinnerList.add("Name Z-A");
        spinnerList.add("Length longest");
        spinnerList.add("Length shortest");
        spinnerList.add("Time longest");
        spinnerList.add("Time shortest");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>  (this,R.layout.spinner_item, spinnerList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(dataAdapter);







    }

    private void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for creating database")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==STORAGE_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission GRANTED", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openActivityRouts(){
        Intent intent = new Intent(this,RoutesActivity.class);
        startActivity(intent);
    }
}
