package com.app.colectionaritimbre;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Timbru> timbrus = new ArrayList<>();
    private ArrayList<Timbru> timbrusBD = new ArrayList<>();
    private ArrayAdapter<Timbru> arrayAdapter;
    private Intent intent;
    private ListView listView;
    private Button buttonAdd,saveFilter;
    private Spinner filterCateg;
    private EditText filterTxt;
    private AppDatabase appDatabase;
    private ArrayAdapter<CharSequence> arrayAdapterFilter;
    private int compare = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = findViewById(R.id.addTimbru);
        saveFilter = findViewById(R.id.saveFiltreBtn);
        filterTxt = findViewById(R.id.filterTxt);
        filterCateg = findViewById(R.id.filterCateg);

        arrayAdapterFilter = ArrayAdapter.createFromResource(this,R.array.filterCateg, android.R.layout.simple_spinner_dropdown_item);
        filterCateg.setAdapter(arrayAdapterFilter);

        appDatabase =AppDatabase.getInstance(this);
        timbrusBD = (ArrayList<Timbru>) appDatabase.timbruDao().getAll();

        listView = findViewById(R.id.timbruLV);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,timbrusBD);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                timbrusBD.sort(new Comparator<Timbru>() {
                    @Override
                    public int compare(Timbru o1, Timbru o2) {
                        if(o1.getValoare() < o2.getValoare()) {
                            return -1;
                        }else {
                            return 1;
                        }
                    }

                });
                arrayAdapter.notifyDataSetChanged();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,AddTimbru.class);
                Timbru timbru = new Timbru();
                intent.putExtra("obj",timbru);
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {

            if(requestCode == 2) {
                Timbru timbru = data.getParcelableExtra("obj");
                timbrus.add(timbru);
                appDatabase.timbruDao().insertTimbru(timbru);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(MainActivity.this);
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.deleteAll) {
            timbrusBD.clear();
            appDatabase.timbruDao().deleteAll();
            arrayAdapter.notifyDataSetChanged();
        }
        if(item.getItemId() == R.id.deleteFiltre) {
            ArrayList<Timbru> tempBD = new ArrayList<>();
            timbrusBD.clear();
            tempBD = (ArrayList<Timbru>) appDatabase.timbruDao().getAll();
            timbrusBD.addAll(tempBD);
            arrayAdapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }
}