package com.app.bugetpersonal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Depozit> depozits = new ArrayList<>();
    private Intent intent;
    private Button deleteSearchBtn;
    private Button dublicaSearchBtn, addDepozitBtn;
    private EditText searchValue;
    private GraficFragment graficFragment = new GraficFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        depozits.add(new Depozit("muie","lei",true,"18:40",100));
        depozits.add(new Depozit("vacanta","lei",true,"18:40",50));
        depozits.add(new Depozit("pizda","lei",true,"18:40",20));
        if(savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("objs",depozits);
            graficFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.grafic,graficFragment).commit();
        }

        searchValue = findViewById(R.id.searchTxt);
        deleteSearchBtn = findViewById(R.id.deleteSearchItemBtn);
        dublicaSearchBtn = findViewById(R.id.dublicaElemCautat);

        addDepozitBtn = findViewById(R.id.addDepozitBtn);
        addDepozitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,AddDepozit.class);
                intent.putParcelableArrayListExtra("objs",depozits);
                startActivityForResult(intent,2);
            }
        });

        deleteSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = 0;
                for (Depozit depozit : depozits) {
                    if(depozit.getSumaIntiala() == Double.parseDouble(searchValue.getText().toString())) {
                        depozits.remove(depozit);
                        counter++;

                        Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                    }
                }
                if(counter==0) {
                    Toast.makeText(MainActivity.this, "No item found", Toast.LENGTH_SHORT).show();
                } else {

                graficFragment = GraficFragment.newInstance(depozits);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("objs",depozits);
                graficFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.grafic,graficFragment)
                        .commitAllowingStateLoss();
                }

            }
        });

        dublicaSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = 0;
                Depozit depozit1 = null;
                for (Depozit depozit : depozits) {
                    if(depozit.getSumaIntiala() == Double.parseDouble(searchValue.getText().toString()))  {
                        depozit1 = new Depozit(depozit.getNumeDepozit(),depozit.getValuta(),depozit.isEconomie(),depozit.getOraCreare(),depozit.getSumaIntiala());
                        counter++;
                        Toast.makeText(MainActivity.this, "Item created", Toast.LENGTH_SHORT).show();
                    }
                }
                if(counter==0) {
                    Toast.makeText(MainActivity.this, "No item found", Toast.LENGTH_SHORT).show();
                }else {
                    depozits.add(depozit1);
                    graficFragment = GraficFragment.newInstance(depozits);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("objs",depozits);
                    graficFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.grafic,graficFragment)
                            .commitAllowingStateLoss();
                }

            }
        });



    }
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 2) {
                ArrayList<Depozit> temp = new ArrayList<>();
                temp = data.getParcelableArrayListExtra("objs");
                depozits.clear();
                depozits.addAll(temp);
                graficFragment = GraficFragment.newInstance(depozits);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("objs",depozits);
                graficFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.grafic,graficFragment)
                        .commitAllowingStateLoss();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }
}