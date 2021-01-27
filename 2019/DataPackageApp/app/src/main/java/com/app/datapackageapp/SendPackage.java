package com.app.datapackageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.datapackageapp.models.DataPackage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SendPackage extends AppCompatActivity {
    private EditText latitudeTxt, longitudeTxt;
    private Spinner typeSpinner;
    private DatePicker timestampPicker;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private Button sendPackageBtn, deletePackageBtn;
    private Intent i;
    private ArrayList<DataPackage> dataPackages = new ArrayList<>();
    private DataPackage dataPackage;
    private int position;
    private  boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_package);

        latitudeTxt = findViewById(R.id.latitudeTxt);
        longitudeTxt = findViewById(R.id.longitudeTxt);
        typeSpinner = findViewById(R.id.packageTypeSpinner);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.packageType, android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(arrayAdapter);
        timestampPicker = findViewById(R.id.timestampPicker);
        deletePackageBtn = findViewById(R.id.deletePackage);
        sendPackageBtn = findViewById(R.id.sendPackage);

        i = getIntent();
        dataPackages = i.getParcelableArrayListExtra("packages");
        dataPackage = i.getParcelableExtra("package");
        position = i.getIntExtra("pos",1);
        if(dataPackage != null) {
            isEdit = true;
            latitudeTxt.setText(String.valueOf(dataPackage.getLatitude()));
            longitudeTxt.setText(String.valueOf(dataPackage.getLongitude()));
            typeSpinner.setSelection(arrayAdapter.getPosition(dataPackage.getPackageType()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataPackage.getTimestamp());
            timestampPicker.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        }


        sendPackageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                if(latitudeTxt.getText().toString().length() == 0) {
                    valid = false;
                    Toast.makeText(SendPackage.this, "Latitude invalid", Toast.LENGTH_SHORT).show();
                }
                if(longitudeTxt.getText().toString().length() == 0) {
                    valid = false;
                    Toast.makeText(SendPackage.this, "longitudeTxt invalid", Toast.LENGTH_SHORT).show();
                }
                if(valid) {
                    String time = new SimpleDateFormat("hh:mm").format(System.currentTimeMillis());
                    StringBuilder s = new StringBuilder();
                    s.append(timestampPicker.getDayOfMonth());
                    s.append("-");
                    s.append(timestampPicker.getMonth() + 1);
                    s.append("-");
                    s.append(timestampPicker.getYear());
                    s.append(" "+time);
                    Date timestampDate = null;
                    try {
                        timestampDate = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.US).parse(String.valueOf(s));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (isEdit) {

                        dataPackage.setLatitude(Double.parseDouble(latitudeTxt.getText().toString()));
                        dataPackage.setLongitude(Double.parseDouble(longitudeTxt.getText().toString()));
                        dataPackage.setPackageType(typeSpinner.getSelectedItem().toString());
                        dataPackage.setTimestamp(timestampDate);
                        i = new Intent(SendPackage.this,PackageList.class);
                        i.putExtra("package",dataPackage);
                        i.putExtra("position",position);
                        setResult(Activity.RESULT_OK,i);
                        finish();

                    } else {

                        DataPackage dataPackage = new DataPackage(typeSpinner.getSelectedItem().toString(), Double.parseDouble(latitudeTxt.getText().toString()), Double.parseDouble(longitudeTxt.getText().toString()), timestampDate);
                        dataPackages.add(dataPackage);
                        i = new Intent(SendPackage.this, MainActivity.class);
                        i.putParcelableArrayListExtra("packages", dataPackages);
                        setResult(Activity.RESULT_OK, i);
                        finish();

                    }
                }
            }
        });

    }
}