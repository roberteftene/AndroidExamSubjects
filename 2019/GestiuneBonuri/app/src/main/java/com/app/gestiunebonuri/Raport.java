package com.app.gestiunebonuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Raport extends AppCompatActivity {

    private ArrayList<Bon> bons = new ArrayList<>();
    private Intent intent;
    private Map<String,Integer> source;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raport);
        linearLayout = findViewById(R.id.barLayout);

        intent = getIntent();
        bons = intent.getParcelableArrayListExtra("bonuri");
        source = getSource(bons);
        linearLayout.addView(new BarChartView(getApplicationContext(),source));
    }


    private Map<String, Integer> getSource(List<Bon> bonList){
        if(bonList == null || bonList.isEmpty()){
            return new HashMap<>();
        } else {
            Map<String, Integer> results = new HashMap<>();

            for(Bon bon: bonList){
                if(results.containsKey(bon.getServiciu())){
                    results.put(bon.getServiciu(), results.get(bon.getServiciu()) + 1);

                } else {
                    results.put(bon.getServiciu(), 1);
                }
            }
            return results;
        }
    }
}