package com.app.gestiunejucatori;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.app.gestiunejucatori.models.Jucator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafic extends AppCompatActivity {

    private ArrayList<Jucator> jucators;
    private Intent intent;
    private Map<String,Integer> source;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafic);
        linearLayout = findViewById(R.id.barLayout);
        intent = getIntent();
        jucators = intent.getParcelableArrayListExtra("jucatori");
        source = getSource(jucators);
        linearLayout.addView(new BarChartView(getApplicationContext(),source));

    }

    private Map<String, Integer> getSource(List<Jucator> jucatorsNew){
        if(jucatorsNew == null || jucatorsNew.isEmpty()){
            return new HashMap<>();
        } else {
            Map<String, Integer> results = new HashMap<>();

            for(Jucator jucator: jucatorsNew){
                if(results.containsKey(jucator.getPozitie())){
                    results.put(jucator.getPozitie(), results.get(jucator.getPozitie()) + 1);

                } else {
                    results.put(jucator.getPozitie(), 1);
                }
            }
            return results;
        }
    }
}