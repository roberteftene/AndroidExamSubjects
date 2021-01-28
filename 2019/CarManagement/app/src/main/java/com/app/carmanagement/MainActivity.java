package com.app.carmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayAdapter<Car> arrayAdapter;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getInstance(this);
        Log.v("Cars:",appDatabase.carDao().getAll().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater  = new MenuInflater(MainActivity.this);
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addCar) {
            intent = new Intent(MainActivity.this,AddCar.class);
            intent.putParcelableArrayListExtra("cars",cars);
            startActivityForResult(intent,2);
        }
        if(item.getItemId() == R.id.about) {
            Toast.makeText(this, R.string.authorName, Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.syncCars) {
            for (Car car : cars) {
                appDatabase.carDao().insertCar(car);
            }
        }
        if(item.getItemId() == R.id.listCars) {
            intent = new Intent(MainActivity.this, ListCars.class);
            intent.putParcelableArrayListExtra("cars",cars);
            startActivityForResult(intent,4);
        }
        if(item.getItemId() == R.id.report) {
            intent = new Intent(MainActivity.this,Report.class);
            intent.putParcelableArrayListExtra("cars",cars);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 2) {
                cars.clear();
                cars = data.getParcelableArrayListExtra("cars");
            }
            if(requestCode==4) {
                cars.clear();
                cars = data.getParcelableArrayListExtra("cars");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}