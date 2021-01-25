package com.app.gestiuneobiecteinventar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button addObiectBtn, syncBtn, despreBtn, graficBtn, listaInventarBtn;
    private TextView introdTxt;
    ArrayList<ObiectInventar>arrayListObiecte = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addObiectBtn = findViewById(R.id.addObiectBtn);
        syncBtn = findViewById(R.id.syncBtn);
        despreBtn  =findViewById(R.id.despreBtn);
        graficBtn = findViewById(R.id.graficBtn);
        listaInventarBtn = findViewById(R.id.listaObiecteBtn);
        introdTxt = findViewById(R.id.introductionTxt);

        String[] dateNTime = new String[2];
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        dateNTime[0] = sharedPreferences.getString("time","muie");
        dateNTime[1] = sharedPreferences.getString("date","sugi");

        despreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introdTxt.setText("Eftene Robert. Ultima utilizare a aplicatiei este: " + dateNTime[1]+ " " + dateNTime[0]);
            }
        });

        addObiectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AdaugareObiect.class);
                intent.putParcelableArrayListExtra("obiecte",arrayListObiecte);
                startActivityForResult(intent,2);
            }
        });

        listaInventarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListaObiecteInventar.class);
                intent.putParcelableArrayListExtra("obiecte",arrayListObiecte);
                startActivityForResult(intent,3);
            }
        });

        graficBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Graphic.class);
                intent.putParcelableArrayListExtra("obiecte",arrayListObiecte);
                startActivity(intent);
            }
        });

        syncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        if(s != null) {
                            try {

                            }catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                };
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=  sharedPreferences.edit();

        editor.putString("time", new SimpleDateFormat("hh:mm").format(System.currentTimeMillis()));
        editor.putString("date", new SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis()));
        editor.apply();
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (requestCode == 2) {

                arrayListObiecte = data.getParcelableArrayListExtra("obiecte");
                Toast.makeText(this, arrayListObiecte.toString(), Toast.LENGTH_SHORT).show();
            } else
            if (requestCode == 3) {
                arrayListObiecte = data.getParcelableArrayListExtra("obiecteNoi");

            }
        }
    }
}