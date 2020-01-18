package com.example.yeerun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class RoutesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setBackgroundColor(Color.MAGENTA);
        button1.setTextColor(Color.WHITE);

        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setBackgroundColor(Color.BLACK);
        button2.setTextColor(Color.WHITE);

        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                button1.setBackgroundColor(Color.MAGENTA);
                button2.setBackgroundColor(Color.BLACK);

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
                button2.setBackgroundColor(Color.MAGENTA);
                button1.setBackgroundColor(Color.BLACK);
                return false;
            }
        });
    }

    private void openActivityStats(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
