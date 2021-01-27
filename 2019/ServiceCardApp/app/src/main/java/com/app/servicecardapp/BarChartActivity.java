package com.app.servicecardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.app.servicecardapp.models.ServiceCard;
import com.app.servicecardapp.utils.BarChartView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarChartActivity extends AppCompatActivity {

    private ArrayList<ServiceCard> serviceCards;
    private Intent intent;
    private Map<String, Double> source;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        intent = getIntent();
        serviceCards = intent.getParcelableArrayListExtra("cards");
        source = getSource(serviceCards);
        linearLayout = findViewById(R.id.layoutBar);
        linearLayout.addView(new BarChartView(getApplicationContext(),source));

    }

    private Map<String, Double> getSource(List<ServiceCard> serviceCards){
        if(serviceCards == null || serviceCards.isEmpty()){
            return new HashMap<>();
        } else {
            Map<String, Double> results = new HashMap<>();

            for(ServiceCard card: serviceCards){
                if(results.containsKey(card.getServiceDepartment())){
                    results.put(card.getServiceDepartment(), results.get(card.getServiceDepartment()) + card.getServiceCost());

                } else {
                    results.put(card.getServiceDepartment(), card.getServiceCost());
                }
            }
            return results;
        }
    }
}

