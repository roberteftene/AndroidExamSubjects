package com.app.reteteculinare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.reteteculinare.database.AppDatabase;
import com.app.reteteculinare.models.Reteta;
import com.app.reteteculinare.utils.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Intent i;
    ArrayList<Reteta> retetas = new ArrayList<>();
    private ListView reteteLV;
    ArrayAdapter<Reteta> arrayAdapter;
    private Spinner categFilter;
    ArrayAdapter<CharSequence> adapter;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String currentDateAndTime = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(System.currentTimeMillis());
        editor.putString("current",currentDateAndTime);
        editor.apply();
        editor.commit();
        appDatabase = AppDatabase.getInstance(this);
        retetas  = (ArrayList<Reteta>) appDatabase.retetaDao().getAll();
        reteteLV = findViewById(R.id.reteteLV);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,retetas);
        reteteLV.setAdapter(arrayAdapter);

        categFilter = findViewById(R.id.categFilter);
        adapter = ArrayAdapter.createFromResource(this,R.array.categorie,R.layout.support_simple_spinner_dropdown_item);
        categFilter.setAdapter(adapter);
        categFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (categFilter.getSelectedItem().toString()) {
                    case "ciorbe":
                        getRetetaByCateg(0);
                        break;
                    case "gustari":
                        getRetetaByCateg(1);
                        break;
                    case "dulciuri":
                        getRetetaByCateg(2);
                        break;
                    case "felPrincipal":
                        getRetetaByCateg(3);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        reteteLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i = new Intent(MainActivity.this,AddReteta.class);
                i.putExtra("reteta",retetas.get(position));
                i.putExtra("pos",position);
                startActivityForResult(i,3);
            }
        });

        reteteLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                appDatabase = AppDatabase.getInstance(MainActivity.this);
                appDatabase.retetaDao().insertReteta(retetas.get(position));
                return true;
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addReteta) {
            i = new Intent(MainActivity.this,AddReteta.class);
            i.putParcelableArrayListExtra("retete",retetas);
            startActivityForResult(i,2);
        }
        if(item.getItemId() == R.id.sync) {
            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if(s!=null) {
                        try {
                            JSONArray jsonArray = new JSONArray(s);
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String den = jsonObject.getString("denumire");
                                String des = jsonObject.getString("descriere");
                                String categ = jsonObject.getString("categorie");
                                int nrCalorii = jsonObject.getInt("calorii");
                                String data = jsonObject.getString("dataAdaugare");
                                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(data);
                                Reteta reteta = new Reteta(den,des,date,nrCalorii,categ);
                                retetas.add(reteta);
                            }
                            arrayAdapter.notifyDataSetChanged();
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            asyncTask.execute("https://api.mocki.io/v1/3ab8369c");
        }
        if(item.getItemId() == R.id.info) {
            String currentDate;
            SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
            currentDate =  sharedPreferences.getString("current","Muie");
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Informatie");
            builder.setMessage("Data ultimei utilizari este: " + currentDate);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2) {
                ArrayList<Reteta> temp = new ArrayList<>();
                temp = data.getParcelableArrayListExtra("retete");
                retetas.clear();
                retetas.addAll(temp);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(this, retetas.toString(), Toast.LENGTH_LONG).show();
            }
            if (requestCode == 3) {
                if (data.getParcelableExtra("reteta") != null) {
                    Reteta reteta = data.getParcelableExtra("reteta");
                    int pos = data.getIntExtra("pos", 1);
                    retetas.get(pos).setDenumire(reteta.getDenumire());
                    retetas.get(pos).setDescriere(reteta.getDescriere());
                    retetas.get(pos).setCalorii(reteta.getCalorii());
                    retetas.get(pos).setCategorie(reteta.getCategorie());
                    retetas.get(pos).setDataAdaugare(reteta.getDataAdaugare());
                    appDatabase.retetaDao().updateReteta(retetas.get(pos));
                    arrayAdapter.notifyDataSetChanged();
                } else {
                    int pos = data.getIntExtra("pos", 1);
                    appDatabase.retetaDao().deleteReteta(retetas.get(pos));
                    retetas.remove(pos);
                    ArrayList<Reteta> arrayList = new ArrayList<>();
                    arrayList.addAll(retetas);
                    retetas.clear();
                    retetas.addAll(arrayList);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    protected void getRetetaByCateg(int categ) {
        ArrayList<Reteta> retete = new ArrayList<>();
            for (Reteta reteta : retetas) {
                if(reteta.getCategorie().equals(adapter.getItem(categ))) {
                    retete.add(reteta);
                }
            }
            arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, retete);
        reteteLV.setAdapter(arrayAdapter);
    }
}