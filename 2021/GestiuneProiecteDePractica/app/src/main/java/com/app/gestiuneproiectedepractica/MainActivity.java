package com.app.gestiuneproiectedepractica;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ProiectDePractica> proiecte = new ArrayList<>();
    private Intent intent;
    private ListView listView;
    private Button addProiect;
    private ProiectAdapter proiectDePracticaProiectAdapter;
    private Spinner filterProiecte;
    private ArrayAdapter<CharSequence> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addProiect = findViewById(R.id.addProiect);
        addProiect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,AddProiect.class);
                intent.putParcelableArrayListExtra("proiecte",proiecte);
                startActivityForResult(intent,2);
            }
        });

        listView = findViewById(R.id.proiectLV);
        proiectDePracticaProiectAdapter = new ProiectAdapter(this,proiecte);
        listView.setAdapter(proiectDePracticaProiectAdapter);

        filterProiecte = findViewById(R.id.filterProiecte);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.filterProiecte, android.R.layout.simple_spinner_dropdown_item);
        filterProiecte.setAdapter(arrayAdapter);

        filterProiecte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String option = (String) arrayAdapter.getItem(position);
                switch (option) {
                    case "Toate":
                        proiectDePracticaProiectAdapter = new ProiectAdapter(MainActivity.this,proiecte);
                        listView.setAdapter(proiectDePracticaProiectAdapter);
                        break;
                    case "Angajati":
                        ArrayList<ProiectDePractica> temp = new ArrayList<>();
                        for (ProiectDePractica proiectDePractica : proiecte) {
                            if(proiectDePractica.getIsAngajat()) {
                                temp.add(proiectDePractica);
                            }
                        }
                        proiectDePracticaProiectAdapter = new ProiectAdapter(MainActivity.this,temp);
                        listView.setAdapter(proiectDePracticaProiectAdapter);
                        break;
                    case "Aproape gata":
                        ArrayList<ProiectDePractica> temp2 = new ArrayList<>();
                        for(ProiectDePractica proiectDePractica : proiecte) {
                            if(proiectDePractica.getNrSaptamani() > 80) {
                                temp2.add(proiectDePractica);
                            }
                        }
                        proiectDePracticaProiectAdapter = new ProiectAdapter(MainActivity.this,temp2);
                        listView.setAdapter(proiectDePracticaProiectAdapter);
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
                ArrayList<ProiectDePractica> temp = new ArrayList<>();
                Bundle bundle = new Bundle();
                bundle = data.getBundleExtra("bundle");
                temp = bundle.getParcelableArrayList("proiecte");
                proiecte.clear();
                proiecte.addAll(temp);
                proiectDePracticaProiectAdapter.notifyDataSetChanged();
            }
            if(requestCode == 3) {
                Bundle bundle = new Bundle();
                bundle = data.getBundleExtra("bundle");
                int pos = bundle.getInt("pos",1);
                ProiectDePractica proiectDePractica = bundle.getParcelable("obj");

                proiecte.get(pos).setTitlu(proiectDePractica.getTitlu());
                proiecte.get(pos).setIsAngajat(proiectDePractica.getIsAngajat());
                proiecte.get(pos).setProfCoordonator(proiectDePractica.getProfCoordonator());
                proiecte.get(pos).setNrSaptamani(proiectDePractica.getNrSaptamani());
                proiecte.get(pos).setDataPredare(proiectDePractica.getDataPredare());

                proiectDePracticaProiectAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}