package com.app.carmanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class AddCar extends AppCompatActivity {

    private EditText carNoTxt, parkingIdTxt;
    private CheckBox isPayedCheck;
    private DatePicker date;
    private TimePicker time;
    private ArrayList<Car> cars = new ArrayList<>();
    private Intent intent;
    private Car car;
    private boolean isEdit = false;
    private int pos;
    private Button addCar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        carNoTxt = findViewById(R.id.carNoTxt);
        parkingIdTxt = findViewById(R.id.parkingIdTxt);
        isPayedCheck = findViewById(R.id.checkIsPayed);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);

        intent = getIntent();
        cars = intent.getParcelableArrayListExtra("cars");
        car = intent.getParcelableExtra("car");
        pos = intent.getIntExtra("pos",1);

        if(car != null) {
            isEdit = true;
            carNoTxt.setText(String.valueOf(car.getCarNo()));
            parkingIdTxt.setText(String.valueOf(car.getIdParkingPosition()));
            if(car.isPayed()) {
                isPayedCheck.setChecked(true);
            } else {
                isPayedCheck.setChecked(false);
            }

            String[] dateAndTime = car.getRegistrationDate().split(" ");
            String[] carDate = dateAndTime[0].split("-");

            date.updateDate(Integer.parseInt(carDate[2]),Integer.parseInt(carDate[1]),Integer.parseInt(carDate[0]));
            time.setHour(Integer.parseInt(dateAndTime[1]));
        }

        addCar = findViewById(R.id.addCarBtnForm);
        addCar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                if(carNoTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddCar.this, "Car no invalid", Toast.LENGTH_SHORT).show();
                }
                if(parkingIdTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddCar.this, "Parking no invalid", Toast.LENGTH_SHORT).show();
                }

                if(isValid) {

                    int carNo = Integer.parseInt(carNoTxt.getText().toString());
                    int parkPos = Integer.parseInt(parkingIdTxt.getText().toString());
                    boolean isPayed = isPayedCheck.isChecked();

                    StringBuilder s = new StringBuilder();
                    s.append(date.getDayOfMonth());
                    s.append("-");
                    s.append(date.getMonth() + 1);
                    s.append("-");
                    s.append(date.getYear());
                    s.append(" ");

                    s.append(time.getHour());

                    if(isEdit) {

                        car.setCarNo(carNo);
                        car.setIdParkingPosition(parkPos);
                        car.setPayed(isPayed);
                        car.setRegistrationDate(s.toString());
                        intent = new Intent(AddCar.this,ListCars.class);
                        intent.putExtra("car",car);
                        intent.putExtra("pos",pos);
                        setResult(RESULT_OK,intent);
                        finish();
                    }else {
                        Car carNew = new Car(carNo,s.toString(),parkPos,isPayed);
                        cars.add(carNew);
                        intent = new Intent(AddCar.this,MainActivity.class);
                        intent.putParcelableArrayListExtra("cars",cars);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }

            }
        });
    }
}