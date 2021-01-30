package com.app.deplasariangajati;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Deplasare>deplasares = new ArrayList<>();
    private Intent intent;
    private ArrayAdapter<Deplasare> arrayAdapter;
    private ListView listView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.valueForTxt);

        listView = findViewById(R.id.deplasariLV);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,deplasares);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MainActivity.this,ImageActivity.class);
                intent.putExtra("imgUrl",deplasares.get(position).getNumeAngajat());
                startActivity(intent);
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
        if(item.getItemId() == R.id.download) {
            AsyncTask asyncTask = new AsyncTask()   {

                @Override
                protected void onPostExecute(String s) {
                    try {
                        if(s!=null) {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("deplasari");
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                String numePoza = obj.getString("nume");
                                int durata = Integer.parseInt(obj.getString("durata"));
                                String oraPlecare = obj.getString("ora");
                                String motiv = obj.getString("motiv");
                                String mobil = obj.getString("mobil");

                                Deplasare deplasare = new Deplasare(numePoza,oraPlecare,motiv,durata,mobil);
                                deplasares.add(deplasare);
                            }

                        }
                        arrayAdapter.notifyDataSetChanged();
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    super.onPostExecute(s);
                }
            };

            asyncTask.execute("https://api.mocki.io/v1/810ff9bf");
        }
        if(item.getItemId() == R.id.saveMenu) {
            File file = new File(MainActivity.this.getFilesDir(),"text");
            if(!file.exists()) {
                file.mkdir();
            }
            try {
                File deplasariReport = new File(file,"selected_items.txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(deplasariReport));
                for (Deplasare deplasare : deplasares) {
                    if(deplasare.getDurataDeplasare() > Integer.parseInt(editText.getText().toString())) {
                        writer.write(deplasare.toString());
                        writer.write("\n");
                    }
                }
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(item.getItemId() == R.id.addDeplasareMenu) {
            intent = new Intent(MainActivity.this,AddDeplasare.class);
            intent.putParcelableArrayListExtra("objs",deplasares);
            startActivityForResult(intent,2);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 2) {
                ArrayList<Deplasare> temp = new ArrayList<>();
                Bundle bundle = new Bundle();
                bundle = data.getBundleExtra("bundle");
                temp = bundle.getParcelableArrayList("objs");
                deplasares.clear();
                deplasares.addAll(temp);
                arrayAdapter.notifyDataSetChanged();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}