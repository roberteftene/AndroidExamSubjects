package com.app.magazin;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Produs> produsesDB = new ArrayList<>();
    private ArrayList<Produs> produses = new ArrayList<>();
    private Intent intent;
    private ArrayAdapter<Produs> produsArrayAdapter;
    private Button addProduct;
    private ListView productsLV;
    private AppDatabase appDatabase;
    private Produs produs = new Produs();
    private RadioGroup filterGroup;
    private EditText searchTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = AppDatabase.getInstance(this);
        produsesDB = (ArrayList<Produs>) appDatabase.productDao().getAll();
        addProduct = findViewById(R.id.addProduct);
        productsLV = findViewById(R.id.productsLV);
        produsArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,produsesDB);
        productsLV.setAdapter(produsArrayAdapter);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,AddProduct.class);
                startActivityForResult(intent,2);
            }
        });


        filterGroup = findViewById(R.id.filterData);


        filterGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.alfabeticOption) {
                    produsesDB.sort(new Comparator<Produs>() {
                        @Override
                        public int compare(Produs o1, Produs o2) {
                            return o1.getNumeProdus().compareTo(o2.getNumeProdus());
                        }
                    });
                    produsArrayAdapter.notifyDataSetChanged();
                }
                if(checkedId == R.id.inversAlfabeticOption) {
                    produsesDB.sort(new Comparator<Produs>() {
                        @Override
                        public int compare(Produs o1, Produs o2) {
                            return -o1.getNumeProdus().compareTo(o2.getNumeProdus());
                        }
                    });
                    produsArrayAdapter.notifyDataSetChanged();
                }
                if(checkedId == R.id.crescatorOption) {
                    produsesDB.sort(new Comparator<Produs>() {
                        @Override
                        public int compare(Produs o1, Produs o2) {
                            if(o1.getPret() < o2.getPret()) {
                                return -1;
                            } else {
                                return 1;
                            }
                        }
                    });
                    produsArrayAdapter.notifyDataSetChanged();
                }
                if(checkedId == R.id.descrescatorOption) {
                    produsesDB.sort(new Comparator<Produs>() {
                        @Override
                        public int compare(Produs o1, Produs o2) {
                            if(o1.getPret() < o2.getPret()) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                    });
                    produsArrayAdapter.notifyDataSetChanged();
                }
                if(checkedId == R.id.alfabeticCrescatorOption) {
                    produsesDB.sort(new Comparator<Produs>() {
                        @Override
                        public int compare(Produs o1, Produs o2) {
                            if(o1.getNumeProdus().compareTo(o2.getNumeProdus()) == 0) {
                                if(o1.getPret() < o2.getPret()) {
                                    return -1;
                                } else {
                                    return 1;
                                }
                            } else  {
                                return o1.getNumeProdus().compareTo(o2.getNumeProdus());
                            }
                        }
                    });
                    produsArrayAdapter.notifyDataSetChanged();
                }
                if(checkedId == R.id.alfabeticDescrescatorOption) {
                    produsesDB.sort(new Comparator<Produs>() {
                        @Override
                        public int compare(Produs o1, Produs o2) {
                            int i = o1.getNumeProdus().compareTo(o2.getNumeProdus());
                            if(i!=0){
                                return i;
                            }else {
                                if(o1.getPret() < o2.getPret()) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        }
                    });
                    produsArrayAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 2) {
                Produs produsNew = data.getParcelableExtra("obj");
                appDatabase.productDao().insertProduct(produsNew);
                produses.add(produsNew);
                produsesDB.clear();
                produsesDB.addAll(appDatabase.productDao().getAll());
                produsArrayAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}