package com.app.gestiuneobiecteinventar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Graphic extends AppCompatActivity {

    LinearLayout linearLayout;
    TextView uzuraMica, uzuraMedie, uzuraMare;
    ArrayList<ObiectInventar> obiectInventars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);
        Intent i = new Intent();
        i = getIntent();
        obiectInventars = i.getParcelableArrayListExtra("obiecte");

        uzuraMica = findViewById(R.id.uzuraMicaTxt);
        uzuraMedie = findViewById(R.id.uzuraMedieTxt);
        uzuraMare = findViewById(R.id.uzuraMareTxt);

        double valoareUzuraMica = 0;
        double valoareUzuraMedie = 0;
        double valoareUzuraMare = 0;
        for (int i1 = 0; i1 < obiectInventars.size(); i1++) {
            if(obiectInventars.get(i1).getUzura().equals("mica")) {
                valoareUzuraMica += obiectInventars.get(i1).getValoare();
            }
            if(obiectInventars.get(i1).getUzura().equals("medie")) {
                valoareUzuraMedie += obiectInventars.get(i1).getValoare();
            }
            if(obiectInventars.get(i1).getUzura().equals("mare")) {
                valoareUzuraMare += obiectInventars.get(i1).getValoare();
            }
        }

        uzuraMare.setText("Valoare uzura mare: " + valoareUzuraMare);
        uzuraMedie.setText("Valoare uzura medie: " + valoareUzuraMedie);
        uzuraMica.setText("Valoare uzura mica: " + valoareUzuraMica);

        double values[] = {valoareUzuraMare,valoareUzuraMedie,valoareUzuraMica};
        linearLayout = findViewById(R.id.graphLayout);
        linearLayout.addView(new PieChartView(this,calculatePieChartData(values)));
        PieChartView p = new PieChartView(this,values);

    }

    private double[] calculatePieChartData(double[] values) {
        double valuesSum = 0;
        double[] pieValuesPercentage = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            valuesSum += values[i];
        }
        for (int i = 0; i < values.length; i++) {
            pieValuesPercentage[i] = 360 * (values[i] / valuesSum);
        }
        return pieValuesPercentage;
    }
}