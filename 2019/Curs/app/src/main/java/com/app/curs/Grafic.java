package com.app.curs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.app.curs.models.Curs;
import com.app.curs.utils.BarChartView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafic extends AppCompatActivity {

    private ArrayList<Curs> cursuri;
    private Intent intent;
    private Map<String, Integer> source;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafic);
        intent = getIntent();
        cursuri = intent.getParcelableArrayListExtra("cursuri");
        source = getSource(cursuri);
        linearLayout = findViewById(R.id.layoutBar);
        linearLayout.addView(new BarChartView(getApplicationContext(),source));
    }

    private Map<String, Integer> getSource(List<Curs> cursuri){
        if(cursuri == null || cursuri.isEmpty()){
            return new HashMap<>();
        } else {
            Map<String, Integer> results = new HashMap<>();

            for(Curs curs: cursuri){
                if(results.containsKey(curs.getDenumire())){
                    results.put(curs.getDenumire(), results.get(curs.getDenumire()) + curs.getNrParticipanti());

                } else {
                    results.put(curs.getDenumire(), curs.getNrParticipanti());
                }
            }
            return results;
        }
    }
}