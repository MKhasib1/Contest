package com.example.hackathon;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WaterTracking extends AppCompatActivity {


    private TextView glassesDrinked, glassesNeeded, timerRemain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_tracking);


        glassesDrinked = findViewById(R.id.glassesWaterDrinked);
        glassesNeeded = findViewById(R.id.glassesWaterNeeded);
        timerRemain = findViewById(R.id.timerTextView);
    }

    public void LeadershipActivity(View view) {
//        Intent leadership = new Intent(WaterTracking.this, .class);
//        startActivity(leadership);
    }

    public void drink200MLButton(View view) {
    }
}
