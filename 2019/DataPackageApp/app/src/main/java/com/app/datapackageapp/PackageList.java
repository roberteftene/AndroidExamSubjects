package com.app.datapackageapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.app.datapackageapp.models.DataPackage;

import java.util.ArrayList;

public class PackageList extends AppCompatActivity {

    private ListView listView;
    private ArrayList<DataPackage>dataPackages;
    private Intent i;
    private ArrayAdapter<DataPackage>arrayAdapter;
    private Spinner filterPackages;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private String[] types = {"position","state"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_list);
        i = getIntent();
        dataPackages = i.getParcelableArrayListExtra("packages");

        listView = findViewById(R.id.packagesLV);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,dataPackages);
        listView.setAdapter(arrayAdapter);

        filterPackages = findViewById(R.id.filterPackages);
        spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.packageTypeFilter, android.R.layout.simple_spinner_dropdown_item);
        filterPackages.setAdapter(spinnerAdapter);

        filterPackages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String valueSelected = filterPackages.getSelectedItem().toString();
                switch (valueSelected) {
                    case "All packages":
                        getPackagesByType(2);
                        break;
                    case "State packages":
                        getPackagesByType(1);
                        break;
                    case "Position packages":
                        getPackagesByType(0);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i = new Intent(PackageList.this,SendPackage.class);
                i.putExtra("package",dataPackages.get(position));
                i.putExtra("pos",position);
                startActivityForResult(i,4);

            }
        });

    }


    @Override
    public void onBackPressed() {
        i = new Intent(PackageList.this,MainActivity.class);
        i.putParcelableArrayListExtra("packages",dataPackages);
        setResult(Activity.RESULT_OK,i);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 4) {
                int pos = data.getIntExtra("position",1);
                DataPackage dataPackage = data.getParcelableExtra("package");
                dataPackages.get(pos).setLatitude(dataPackage.getLatitude());
                dataPackages.get(pos).setLongitude(dataPackage.getLongitude());
                dataPackages.get(pos).setTimestamp(dataPackage.getTimestamp());
                dataPackages.get(pos).setPackageType(dataPackage.getPackageType());
                arrayAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void getPackagesByType(int type) {
        ArrayList<DataPackage> dataPackagesFiltered = new ArrayList<>();
        if(type == 2 ) {
            arrayAdapter = new ArrayAdapter<>(PackageList.this, android.R.layout.simple_list_item_1, dataPackages);
        } else {
            for (DataPackage dataPackage : dataPackages) {
                if(dataPackage.getPackageType().toString().equals(types[type])) {
                    dataPackagesFiltered.add(dataPackage);
                }
            }
            arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, dataPackagesFiltered);
        }
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("packages", dataPackages);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dataPackages = savedInstanceState.getParcelableArrayList("packages");
        arrayAdapter.notifyDataSetChanged();
    }

}