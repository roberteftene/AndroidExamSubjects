 package com.app.gestiunejucatori;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
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

import com.app.gestiunejucatori.database.AppDatabase;
import com.app.gestiunejucatori.models.Jucator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

 public class MainActivity extends AppCompatActivity {
     private Intent intent;
     private ArrayList<Jucator> jucators = new ArrayList<>();
     private ListView jucatoriLV;
    private ArrayAdapter<Jucator> jucatorArrayAdapter;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getInstance(this);
        jucatoriLV = findViewById(R.id.jucatorLV);
        jucatorArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,jucators);
        jucatoriLV.setAdapter(jucatorArrayAdapter);

        List<Jucator>jucatorsList = new ArrayList<>();
        jucatorsList = appDatabase.jucatorDao().getAll();
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Jucator jucator : jucatorsList) {
            editor.putString("JUCATOR" + jucator.getId(),jucator.toString());
        }
        editor.commit();
        editor.apply();

        jucatoriLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MainActivity.this,AddJucator.class);
                intent.putExtra("jucator",jucators.get(position));
                intent.putExtra("pos",position);
                startActivityForResult(intent,3);
            }
        });

        jucatoriLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Are you sure you want to delete this player?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jucators.remove(position);
                        jucatorArrayAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });
    }


     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         MenuInflater menuInflater = new MenuInflater(MainActivity.this);
         menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addJucator) {
            intent = new Intent(MainActivity.this,AddJucator.class);
            intent.putParcelableArrayListExtra("jucatori",jucators);
            startActivityForResult(intent,2);
        }
        if(item.getItemId() == R.id.syncRetea) {
            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected void onPostExecute(String s) {
                    try {

                        if(s!=null) {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("echipa");
                            JSONArray jsonArray = jsonObject1.getJSONArray("jucatori");
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String nume = obj.getString("nume");
                                int nr = obj.getInt("numar");
                                String data = obj.getString("dataNasterii");
                                String[] dataParsed = data.split("/");
                                StringBuilder dataNew = new StringBuilder();
                                dataNew.append(dataParsed[0]);
                                dataNew.append("-");
                                dataNew.append(dataParsed[1]);
                                dataNew.append("-");
                                dataNew.append(dataParsed[2]);
                                String poz = obj.getString("pozitie");
                                Jucator jucator = new Jucator(nr,nume,dataNew.toString(),poz);
                                jucators.add(jucator);
                            }
                        }
                    jucatorArrayAdapter.notifyDataSetChanged();
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    super.onPostExecute(s);
                }
            };
            asyncTask.execute("http://pdm.ase.ro/examen/jucatori.json.txt");
        }
        if(item.getItemId() == R.id.grafic) {
            intent = new Intent(MainActivity.this,Grafic.class);
            intent.putParcelableArrayListExtra("jucatori",jucators);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.backup) {
            for (Jucator jucator : jucators) {
                appDatabase.jucatorDao().insertJucator(jucator);
            }
        }

         return super.onOptionsItemSelected(item);

    }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 2) {
                ArrayList<Jucator> temp = new ArrayList<>();
                temp = data.getParcelableArrayListExtra("jucatori");
                jucators.clear();
                jucators.addAll(temp);
                jucatorArrayAdapter.notifyDataSetChanged();
            }
            if(requestCode == 3) {
                Jucator jucator = data.getParcelableExtra("jucator");
                int pos = data.getIntExtra("pos",1);
                jucators.get(pos).setDataNasterii(jucator.getDataNasterii());
                jucators.get(pos).setPozitie(jucator.getPozitie());
                jucators.get(pos).setNume(jucator.getNume());
                jucators.get(pos).setNumar(jucator.getNumar());
                jucatorArrayAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
     }
 }