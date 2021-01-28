package com.app.carmanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListCars extends AppCompatActivity {

    private ListView carsLV;
    private Intent intent;
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayAdapter<Car> carArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cars);
        intent = getIntent();
        cars = intent.getParcelableArrayListExtra("cars");
        carArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,cars);
        carsLV = findViewById(R.id.carsLV);
        carsLV.setAdapter(carArrayAdapter);

        carsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(ListCars.this,AddCar.class);
                intent.putExtra("car",cars.get(position));
                intent.putExtra("pos",position);
                startActivityForResult(intent,3);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 3) {
                Car car = data.getParcelableExtra("car");
                int pos = data.getIntExtra("pos",1);
                cars.get(pos).setRegistrationDate(car.getRegistrationDate());
                cars.get(pos).setPayed(car.isPayed());
                cars.get(pos).setIdParkingPosition(car.getIdParkingPosition());
                cars.get(pos).setCarNo(car.getCarNo());
                carArrayAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        intent= new Intent(ListCars.this,MainActivity.class);
        intent.putParcelableArrayListExtra("cars",cars);
        setResult(RESULT_OK,intent);
        finish();
        super.onBackPressed();
    }
}