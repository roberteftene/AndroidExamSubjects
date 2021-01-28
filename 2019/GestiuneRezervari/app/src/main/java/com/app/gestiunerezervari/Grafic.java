package com.app.gestiunerezervari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafic extends AppCompatActivity {

    private LinearLayout linearLayout;
    private ArrayList<Rezervare> rezervares = new ArrayList<>();
    private Intent intent;
    private Map<String,Integer> source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preluare_rezervari);
        intent = getIntent();
        rezervares = intent.getParcelableArrayListExtra("rezervari");
        source = getSource(rezervares);
        linearLayout = findViewById(R.id.layoutBar);
        linearLayout.addView(new BarChartView(getApplicationContext(),source));
    }

    private Map<String, Integer> getSource(List<Rezervare> rezervareList){
        if(rezervareList == null || rezervareList.isEmpty()){
            return new HashMap<>();
        } else {
            Map<String, Integer> results = new HashMap<>();

            for(Rezervare rezervare: rezervareList){
                if(results.containsKey(rezervare.getTipCamera())){
                    results.put(rezervare.getTipCamera(), results.get(rezervare.getTipCamera()) + 1);

                } else {
                    results.put(rezervare.getTipCamera(), 1);
                }
            }
            return results;
        }
    }
}