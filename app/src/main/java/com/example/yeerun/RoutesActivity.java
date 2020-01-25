package com.example.yeerun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;

public class RoutesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        ArrayList<Rout> routes = DatabaseService.getDatabaseService().loadRoutes();
        ArrayList<String> id= new ArrayList<>();
        ArrayList<String> names= new ArrayList<>();
        for(int i = 0; i<routes.size(); i++) {
            id.add("#" + (i + 1));
            names.add(routes.get(i).getName()+" (" + routes.get(i).getLength() + "km)");
        }

//        ListView listViewNum= (ListView) findViewById(R.id.listViewID);
//        final ArrayAdapter<String> arrayAdapterNum = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, id);
//        listViewNum.setAdapter(arrayAdapterNum);

        ListView listView= (ListView) findViewById(R.id.listViewName);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(arrayAdapter);

//        ListView header1 = (ListView) findViewById(R.id.headerID);
        ListView header2 = (ListView) findViewById(R.id.headerName);
//        ArrayList<String> header1Text= new ArrayList<>();
//        header1Text.add("ID");
//        final ArrayAdapter<String> headerAdapter1 = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, header1Text);
//        header1.setAdapter(headerAdapter1);
        ArrayList<String> header2Text= new ArrayList<>();
        header2Text.add("Name");
        final ArrayAdapter<String> headerAdapter2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, header2Text);
        header2.setAdapter(headerAdapter2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Rout r = null;

                ArrayList<Rout> routes = DatabaseService.getDatabaseService().loadRoutes();
                for(int i = 0; i<routes.size(); i++){
                    if(i==position){
                        r = routes.get(i);
                    }
                }
                System.out.println("Item clicked: " + position + " " + r.getName() + r.getStartX() + " " + r.getEndX());
                openActivityShowMap(r);
            }
        });



        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setBackgroundColor(Color.TRANSPARENT);
        button1.setTextColor(Color.WHITE);

        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setBackgroundColor(Color.parseColor("#ccff00"));
        button2.setTextColor(Color.WHITE);

        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                button1.setBackgroundColor(Color.parseColor("#ccff00"));
                button2.setBackgroundColor(Color.TRANSPARENT);

                return false;
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityStats();
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

        Button addNew = (Button)findViewById(R.id.addNew);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMap();
            }
        });

    }

    private void openActivityShowMap(Rout r) {
        System.out.println("Name: " + r.getName());
        System.out.println("Start latitude = " + r.getStartX() + "Start longitude = " + r.getStartY());
        System.out.println("End latitude = " + r.getEndX() + "End longitude = " + r.getEndY());
        String text = r.getName() + "///" + r.getStartX() + "///" + r.getStartY() + "///" + r.getEndX() + "///" + r.getEndY();
        Intent intent = new Intent(this,ShowRouteActivity.class);
        intent.putExtra("ROUTE", text);
        startActivity(intent);
    }

    private void openActivityMap() {
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

    private void openActivityStats(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
