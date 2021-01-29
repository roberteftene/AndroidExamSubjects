package com.app.agentieimobiliara;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Imobil> imobils = new ArrayList<>();
    private GridView imobileGV;
    private ArrayAdapter<Imobil> imobilArrayAdapter;
    private Intent intent;
    private Button addImobilBtn,saveImobileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPostExecute(String s) {
                try {
                    if(s!=null) {
                        JSONObject jsonObject = new JSONObject(s);
                        JSONArray jsonArray = jsonObject.getJSONArray("imobile");
                        for(int i =0; i < jsonArray.length();i++){
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String adresa = obj.getString("adresa");
                            int numar = obj.getInt("numar");
                            boolean isDisponibil = obj.getBoolean("disponibil");
                            String ora = obj.getString("ora");
                            String categ = obj.getString("categorie");

                            Imobil imobil = new Imobil(adresa,numar,categ,isDisponibil,ora);
                            imobils.add(imobil);
                        }
                    }
                    imobilArrayAdapter.notifyDataSetChanged();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
                super.onPostExecute(s);
            }
        };
        asyncTask.execute("https://api.mocki.io/v1/3497e5d2");

        saveImobileBtn = findViewById(R.id.saveImobileBtn);
        saveImobileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                for (Imobil imobil : imobils) {
                    editor.putString("I"+imobil.getNumar(),imobil.toString());
                }
                editor.apply();
                editor.commit();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        Map<String,?> source = sharedPreferences.getAll();
        for(Map.Entry<String,?> entry : source.entrySet()) {
            Log.v("Obiecte",entry.getValue().toString());
        }
        imobileGV = findViewById(R.id.imobileGV);
        imobilArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,imobils);
        addImobilBtn = findViewById(R.id.addImobilBtn);
        imobileGV.setAdapter(imobilArrayAdapter);
        addImobilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,AddImobil.class);
                intent.putParcelableArrayListExtra("imobile",imobils);
                startActivityForResult(intent,2);
            }
        });

        imobileGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MainActivity.this,AddImobil.class);
                intent.putExtra("imobil",imobils.get(position));
                intent.putExtra("pos",position);
                startActivityForResult(intent,3);
            }
        });

        imobileGV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imobils.remove(position);
                        imobilArrayAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 2) {
                ArrayList<Imobil> imobilsnew = new ArrayList<>();
                imobilsnew = data.getParcelableArrayListExtra("imobile");
                imobils.clear();
                imobils.addAll(imobilsnew);
                imobilArrayAdapter.notifyDataSetChanged();
            }
            if(requestCode==3) {
                Imobil imobil = data.getParcelableExtra("imobil");
                int posi = data.getIntExtra("pos",1);
                imobils.get(posi).setEsteDisponibil(imobil.isEsteDisponibil());
                imobils.get(posi).setOraAdaugare(imobil.getOraAdaugare());
                imobils.get(posi).setNumar(imobil.getNumar());
                imobils.get(posi).setCategorie(imobil.getCategorie());
                imobils.get(posi).setAdresa(imobil.getAdresa());
                imobilArrayAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}