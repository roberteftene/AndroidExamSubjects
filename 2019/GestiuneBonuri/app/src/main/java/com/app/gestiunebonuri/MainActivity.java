package com.app.gestiunebonuri;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button addBonBtn, despreBtn, syncReteaBtn, raportBtn, listaBonuriBtn,saveInDB;
    private ArrayList<Bon> bons = new ArrayList<>();
    private ArrayAdapter<Bon>arrayAdapter;
    private Intent intent;
    private AppDatabse appDatabse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabse = AppDatabse.getInstance(this);
        addBonBtn = findViewById(R.id.addBonBtn);
        despreBtn = findViewById(R.id.despreBtn);
        raportBtn = findViewById(R.id.raportBtn);
        syncReteaBtn = findViewById(R.id.syncReteaBtn);
        listaBonuriBtn = findViewById(R.id.listaBonBtn);

        despreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Eftene Robert", Toast.LENGTH_SHORT).show();
            }
        });

        addBonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,AddBon.class);
                intent.putParcelableArrayListExtra("bonuri",bons);
                startActivityForResult(intent,2);
            }
        });

        listaBonuriBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,ListaBonuri.class);
                intent.putParcelableArrayListExtra("bonuri",bons);
                startActivityForResult(intent,3);
            }
        });

        saveInDB = findViewById(R.id.saveInDB);
        saveInDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Bon bon : bons) {
                    appDatabse.bonDao().insertBon(bon);
                }
            }
        });

        syncReteaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask asyncTask = new AsyncTask(){
                    @Override
                    protected void onPostExecute(String s) {
                        try {
                            if(s!=null) {

                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("bonuri");
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    String serviciu = obj.getString("serviciu");
                                    int nr = Integer.parseInt(obj.getString("numar"));
                                    String ghiseu = obj.getString("ghiseu");
                                    String date =obj.getString("data");

                                    Bon bon = new Bon(nr,serviciu,date,ghiseu);
                                    bons.add(bon);
                                }
                            }
                        }catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        super.onPostExecute(s);
                    }
                };
                asyncTask.execute("https://api.mocki.io/v1/ec5d1d20");
            }
        });

        raportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,Raport.class);
                intent.putParcelableArrayListExtra("bonuri",bons);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 2) {
                bons.clear();
                bons = data.getParcelableArrayListExtra("bonuri");
            }
            if(requestCode == 3) {
                bons.clear();
                bons = data.getParcelableArrayListExtra("bonuri");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}