package com.app.curs;

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
import android.widget.Toast;

import com.app.curs.database.AppDatabase;
import com.app.curs.models.Curs;
import com.app.curs.utils.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Curs> cursuri = new ArrayList<>();
    private Intent intent;
    private ListView cursuriLv;
    private ArrayAdapter<Curs> cursArrayAdapter;
    private AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getInstance(this);

        cursuriLv = findViewById(R.id.cursuriLv);
        cursuri.add(new Curs("DAM",130,"A123","Doinea"));
        cursArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,cursuri);
        cursuriLv.setAdapter(cursArrayAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<Curs>cursFromDb = new ArrayList<>();
        cursFromDb = (ArrayList<Curs>) appDatabase.cursDao().getAll();
        for (Curs curs : cursFromDb) {
            editor.putString("Curs" + curs.getIdCurs(),curs.toString());
        }
        editor.commit();
        editor.apply();

        cursuriLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MainActivity.this,AddCurs.class);
                intent.putExtra("curs",cursuri.get(position));
                intent.putExtra("pos",position);
                startActivityForResult(intent,3);

            }
        });

        cursuriLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete curs");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cursuri.remove(position);
                        cursArrayAdapter.notifyDataSetChanged();
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addCurs) {

            intent = new Intent(MainActivity.this,AddCurs.class);
            intent.putParcelableArrayListExtra("cursuri",cursuri);
            startActivityForResult(intent,2);

        }
        if(item.getItemId() == R.id.syncRetea) {

            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected void onPostExecute(String s) {
                    if(s!=null) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("cursuri");
                            for(int i = 0; i <jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                int idCurs = Integer.parseInt(obj.getString("idCurs"));
                                String den = obj.getString("denumire");
                                int nrPart = Integer.parseInt(obj.getString("numarParticipanti"));
                                String sala = obj.getString("sala");
                                String prof = obj.getString("profesorTitular");
                                Curs curs = new Curs(idCurs,den,nrPart,sala,prof);
                                cursuri.add(curs);
                            }
                            cursArrayAdapter.notifyDataSetChanged();
                        }catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    super.onPostExecute(s);
                }
            };
            asyncTask.execute("http://pdm.ase.ro/examen/cursuri.json.txt");

        }
        if(item.getItemId() == R.id.grafic) {
            intent = new Intent(MainActivity.this,Grafic.class);
            intent.putParcelableArrayListExtra("cursuri",cursuri);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.database) {
            for (Curs curs : cursuri) {
                appDatabase.cursDao().insertCurs(curs);
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 2) {
                ArrayList<Curs> temp = new ArrayList<>();
                temp = data.getParcelableArrayListExtra("cursuri");
                cursuri.clear();
                cursuri.addAll(temp);
                cursArrayAdapter.notifyDataSetChanged();
                Toast.makeText(this, cursuri.toString(), Toast.LENGTH_SHORT).show();
            }
            if(requestCode == 3) {
                Curs curs = data.getParcelableExtra("curs");
                int pos = data.getIntExtra("pos",1);
                cursuri.get(pos).setDenumire(curs.getDenumire());
                cursuri.get(pos).setNrParticipanti(curs.getNrParticipanti());
                cursuri.get(pos).setSala(curs.getSala());
                cursuri.get(pos).setProfesorTitular(curs.getProfesorTitular());
                cursArrayAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}