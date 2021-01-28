package com.app.gestiunerezervari;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button addRezervare, saveRezervare, syncRezervare, despreBtn, graficBtn;
    private ArrayList<Rezervare> rezervares = new ArrayList<>();
    private Intent intent;
    private ArrayAdapter<Rezervare> rezervareArrayAdapter;
    private ListView rezervariLV;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addRezervare = findViewById(R.id.addRezervareBtn);
        saveRezervare = findViewById(R.id.saveBdBtn);
        syncRezervare = findViewById(R.id.preluareRezervariBtn);
        despreBtn = findViewById(R.id.despreBtn);
        graficBtn = findViewById(R.id.graficBtn);
        rezervariLV = findViewById(R.id.rezervariLV);
        rezervareArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,rezervares);
        rezervariLV.setAdapter(rezervareArrayAdapter);
        appDatabase = AppDatabase.getInstance(this);
        List<Rezervare> rezervareList = new ArrayList<>();
        rezervareList = appDatabase.rezervareDao().getAll();
        Log.v("rezervareFromDB",rezervareList.toString());
        rezervariLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MainActivity.this,AddRezervare.class);
                intent.putExtra("rezervare",rezervares.get(position));
                intent.putExtra("pos",position);
                startActivityForResult(intent,3);
            }
        });

        rezervariLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Are you sure you want to delete?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rezervares.remove(position);
                        rezervareArrayAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setCancelable(true);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });

        addRezervare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,AddRezervare.class);
                intent.putParcelableArrayListExtra("rezervari",rezervares);
                startActivityForResult(intent,2);
            }
        });

        saveRezervare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Rezervare rezervare : rezervares) {
                    appDatabase.rezervareDao().insertRezervare(rezervare);
                }
            }
        });

        graficBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,Grafic.class);
                intent.putParcelableArrayListExtra("rezervari",rezervares);
                startActivity(intent);
            }
        });


        syncRezervare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected void onPostExecute(String s) {
                        try {
                            if(s!=null) {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONObject jsonObject1 = jsonObject.getJSONObject("rezervari");
                                JSONArray jsonArray = jsonObject1.getJSONArray("rezervare");
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);

                                    int id = obj.getInt("idRezervare");
                                    String numeClient =  obj.getString("numeClient");
                                    String tip = obj.getString("tipCamera");
                                    int durata = Integer.parseInt(obj.getString("durataSejur"));
                                    double suma = Double.parseDouble(obj.getString("sumaPlata"));
                                    String data = obj.getString("dataCazare");
                                    String[] dataAndTime =  data.split(" ");

                                    Rezervare rezervare = new Rezervare(id,numeClient,tip,durata,suma,dataAndTime[0]);
                                    rezervares.add(rezervare);
                                }
                            }
                            rezervareArrayAdapter.notifyDataSetChanged();
                        }catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        super.onPostExecute(s);
                    }
                };
                asyncTask.execute("http://pdm.ase.ro/examen/rezervari.json.txt");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(resultCode == RESULT_OK) {
           if(requestCode == 2) {
                ArrayList<Rezervare> temp = new ArrayList<>();
                temp = data.getParcelableArrayListExtra("rezervari");
                rezervares.clear();
                rezervares.addAll(temp);
               rezervareArrayAdapter.notifyDataSetChanged();
           }
           if(requestCode == 3) {
               Rezervare rezervare = data.getParcelableExtra("rezervare");
               int pos = data.getIntExtra("pos",1);
               rezervares.get(pos).setNumeClient(rezervare.getNumeClient());
               rezervares.get(pos).setSumaPlata(rezervare.getSumaPlata());
               rezervares.get(pos).setDurataSejur(rezervare.getDurataSejur());
               rezervares.get(pos).setDataCazare(rezervare.getDataCazare());
               rezervares.get(pos).setTipCamera(rezervare.getTipCamera());
               rezervareArrayAdapter.notifyDataSetChanged();
           }
       }
        super.onActivityResult(requestCode, resultCode, data);
    }
}