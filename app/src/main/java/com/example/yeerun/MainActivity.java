package com.example.yeerun;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseService.getDatabaseService().makeFileWithRoutes();
        LinkedList<Rout> routes = DatabaseService.getDatabaseService().loadRoutes();
        ArrayList<String> names= new ArrayList<>();
        ArrayList<String> lengths= new ArrayList<>();
        ArrayList<String> times= new ArrayList<>();
        for(int i = 0; i<routes.size(); i++){
            names.add(routes.get(i).getName());
            lengths.add(routes.get(i).getLength()+"");
            times.add(routes.get(i).getTime());

        }

        ListView listView= (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(arrayAdapter);

        ListView listView2= (ListView) findViewById(R.id.listView2);
        final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, lengths);
        listView2.setAdapter(arrayAdapter2);

        ListView listView3= (ListView) findViewById(R.id.listView3);
        final ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, times);
        listView3.setAdapter(arrayAdapter3);

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
    }

    private void openActivityRouts(){
        Intent intent = new Intent(this,RoutesActivity.class);
        startActivity(intent);
    }
}
