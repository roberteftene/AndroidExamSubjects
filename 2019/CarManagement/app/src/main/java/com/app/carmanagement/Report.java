package com.app.carmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Report extends AppCompatActivity {

    private ArrayList<Car> cars = new ArrayList<>();
    private Intent intent;
    private Map<String,Integer> source;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        intent = getIntent();
        cars = intent.getParcelableArrayListExtra("cars");
        linearLayout = findViewById(R.id.barLayout);
        source = getSource(cars);
        linearLayout.addView(new BarChartView(getApplicationContext(),source));

    }

    private Map<String, Integer> getSource(List<Car> carList){
        if(carList == null || carList.isEmpty()){
            return new HashMap<>();
        } else {
            Map<String, Integer> results = new HashMap<>();

            for(Car car: carList){
                if(results.containsKey(String.valueOf(car.isPayed()))){
                    results.put(String.valueOf(car.isPayed()), results.get(String.valueOf(car.isPayed())) + 1);

                } else {
                    results.put(String.valueOf(car.isPayed()), 1);
                }
            }
            return results;
        }
    }
}