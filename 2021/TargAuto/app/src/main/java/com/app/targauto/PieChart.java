package com.app.targauto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class PieChart extends AppCompatActivity {

    private Intent intent;
    private ArrayList<Car> cars = new ArrayList<>();
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        intent = getIntent();
        cars = intent.getParcelableArrayListExtra("objs");
        linearLayout = findViewById(R.id.pieLayout);


    }

}