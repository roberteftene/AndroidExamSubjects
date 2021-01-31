package com.app.targauto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarChart extends AppCompatActivity {

    private LinearLayout linearLayout;
    private ArrayList<Car> cars = new ArrayList<>();
    private Intent intent;
    private Map<String,Integer> source;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        intent = getIntent();
        cars = intent.getParcelableArrayListExtra("objs");

        linearLayout = findViewById(R.id.barLayout);
        source = getSource(cars);
        linearLayout.addView(new BarChartView(this,source));
    }

    private Map<String, Integer> getSource(List<Car> carList){
        if(carList == null || carList.isEmpty()){
            return new HashMap<>();
        } else {
            Map<String, Integer> results = new HashMap<>();

            for(Car car: carList){
                if(results.containsKey(car.getCategorieMasina())){
                    results.put(car.getCategorieMasina(), results.get(car.getCategorieMasina()) + 1);

                } else {
                    results.put(car.getCategorieMasina(), 1);
                }
            }
            return results;
        }
    }
}