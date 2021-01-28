package com.app.gestiuneexamene;

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
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.app.gestiuneexamene.database.AppDatabase;
import com.app.gestiuneexamene.models.Examen;
import com.app.gestiuneexamene.utils.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Examen>examene = new ArrayList<>();
    private ArrayAdapter<Examen> examenArrayAdapter;
    private ListView exameneLV;
    private Intent intent;
    private AppDatabase appDatabase;
    private ProgressBar spinnerProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getInstance(this);
        spinnerProgress = findViewById(R.id.spinnerProgress);
        spinnerProgress.setVisibility(View.GONE);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<Examen> examenes = new ArrayList<>();
        examenes = (ArrayList<Examen>) appDatabase.examenDao().getAll();
        for (Examen examen : examenes) {
            editor.putString("E" + examen.getId(),examen.toString());
        }
        editor.apply();
        editor.commit();

        exameneLV = findViewById(R.id.exameneLV);
        examenArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,examene);
        exameneLV.setAdapter(examenArrayAdapter);

        exameneLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MainActivity.this,AddExamen.class);
                intent.putExtra("examen",examene.get(position));
                intent.putExtra("pos",position);
                startActivityForResult(intent,3);
            }
        });

        exameneLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete examen");
                builder.setMessage("Are you sure you want to delete this exam?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        examene.remove(position);
                        examenArrayAdapter.notifyDataSetChanged();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater  = new MenuInflater(MainActivity.this);
        menuInflater.inflate(R.menu.menu,menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addExamen) {
            intent = new Intent(MainActivity.this,AddExamen.class);
            intent.putParcelableArrayListExtra("examene",examene);
            startActivityForResult(intent,2);
        }
        if(item.getItemId() == R.id.syncRetea) {
            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    spinnerProgress.setVisibility(View.VISIBLE);
                }

                @Override
                protected void onPostExecute(String s) {
                    try {
                        if(s!= null) {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("examene");
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                int id = jsonObject1.getInt("id");
                                String den = jsonObject1.getString("denumireMaterie");
                                String sala = jsonObject1.getString("sala");
                                int nrParticip = jsonObject1.getInt("numarStudenti");
                                String numeProf = jsonObject1.getString("supraveghetor");
                                Examen examen = new Examen(id,den,nrParticip,sala,numeProf);
                                examene.add(examen);
                            }
                        }
                        examenArrayAdapter.notifyDataSetChanged();
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        spinnerProgress.setVisibility(View.GONE);
                    }
                    super.onPostExecute(s);

                }
            };
            asyncTask.execute("http://pdm.ase.ro/examen/examen.json.txt");
        }
        if(item.getItemId() == R.id.grafic) {
            intent = new Intent(MainActivity.this,GraficPieChart.class);
            intent.putParcelableArrayListExtra("examene",examene);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.backup) {
            for (Examen examen : examene) {
                appDatabase.examenDao().insertExamen(examen);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 2) {
                ArrayList<Examen> examen = new ArrayList<>();
                examen = data.getParcelableArrayListExtra("examene");
                examene.clear();
                examene.addAll(examen);
                examenArrayAdapter.notifyDataSetChanged();
            }
            if(requestCode == 3) {
                Examen examen = data.getParcelableExtra("examen");
                int pos = data.getIntExtra("pos",1);
                examene.get(pos).setDenumireMaterie(examen.getDenumireMaterie());
                examene.get(pos).setSupraveghetor(examen.getSupraveghetor());
                examene.get(pos).setNrStudenti(examen.getNrStudenti());
                examene.get(pos).setSala(examen.getSala());
                examenArrayAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}