package com.app.casadelicitatii;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Licitatie> licitaties = new ArrayList<>();
    private ArrayAdapter<Licitatie> arrayAdapter;
    private Intent intent;
    private ListView listView;
    private Button addLicitatieNoua,sortareBtn,saveBtn;
    private Spinner spinnerFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.licitatieLV);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,licitaties);
        listView.setAdapter(arrayAdapter);

        spinnerFilter = findViewById(R.id.filterSpinner);
        ArrayAdapter<CharSequence> arrayAdapterFilter = ArrayAdapter.createFromResource(this,R.array.filterCateg, android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(arrayAdapterFilter);




        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPostExecute(String s) {
                try {
                    if(s!=null) {
                        JSONObject jsonObject = new JSONObject(s);
                        JSONArray jsonArray = jsonObject.getJSONArray("licitatii");
                        for(int i = 0 ; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String descriere = obj.getString("descriere");
                            double val = obj.getDouble("valoare");
                            String categ = obj.getString("categorie");
                            String metoda = obj.getString("metodaPlata");
                            String data = obj.getString("data");

                            Licitatie licitatie = new Licitatie(descriere,val,categ,data,metoda);
                            licitaties.add(licitatie);

                        }


                    }
                    arrayAdapter.notifyDataSetChanged();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
                super.onPostExecute(s);
            }
        };
        asyncTask.execute("https://api.mocki.io/v1/f8b8ebcd");

        sortareBtn = findViewById(R.id.sortBtn);
        sortareBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                licitaties.sort(new Comparator<Licitatie>() {
                    @Override
                    public int compare(Licitatie o1, Licitatie o2) {
                        if(o1.getValoare() < o2.getValoare()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
                arrayAdapter.notifyDataSetChanged();
            }
        });

        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                for (Licitatie licitaty : licitaties) {
                    editor.putString("L" + licitaty.getValoare(),licitaty.toString());
                }
                editor.commit();
                editor.apply();
            }
        });

        addLicitatieNoua = findViewById(R.id.addLicitatie);
        addLicitatieNoua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,AddLicititatie.class);
                intent.putParcelableArrayListExtra("licitatii",licitaties);
                startActivityForResult(intent,2);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MainActivity.this,AddLicititatie.class);
                intent.putExtra("licitatie",licitaties.get(position));
                intent.putExtra("pos",position);
                startActivityForResult(intent,3);
            }
        });

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (arrayAdapterFilter.getItem(position).toString()) {
                    case "Toate":
                        arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,licitaties);
                        listView.setAdapter(arrayAdapter);
                        break;
                    case "bingo":
                        ArrayList<Licitatie> temp = new ArrayList<>();
                        for (Licitatie licitaty : licitaties) {
                            if(licitaty.getCategorie().equals("bingo")) {
                                temp.add(licitaty);
                            }
                        }
                        arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,temp);
                        listView.setAdapter(arrayAdapter);
                        break;
                    case "6/49":
                        ArrayList<Licitatie> tempNew = new ArrayList<>();
                        for (Licitatie licitaty : licitaties) {
                            if(licitaty.getCategorie().equals("6/49")) {
                                tempNew.add(licitaty);
                            }
                        }
                        arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,tempNew);
                        listView.setAdapter(arrayAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 2) {
                Bundle bundle = new Bundle();
                bundle = data.getBundleExtra("bundle");
                ArrayList<Licitatie>licitatiesNew = new ArrayList<>();
                licitatiesNew = bundle.getParcelableArrayList("licitatii");
                licitaties.clear();
                licitaties.addAll(licitatiesNew);
                arrayAdapter.notifyDataSetChanged();
            }
            if(requestCode == 3) {
                Bundle bundle = new Bundle();
                bundle = data.getBundleExtra("bundle");
                Licitatie licitatie = bundle.getParcelable("licitatie");
                int pos = bundle.getInt("pos",1);

                licitaties.get(pos).setDescriere(licitatie.getDescriere());
                licitaties.get(pos).setMetodaPlata(licitatie.getMetodaPlata());
                licitaties.get(pos).setValoare(licitatie.getValoare());
                licitaties.get(pos).setData(licitatie.getData());
                licitaties.get(pos).setCategorie(licitatie.getCategorie());
                arrayAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}