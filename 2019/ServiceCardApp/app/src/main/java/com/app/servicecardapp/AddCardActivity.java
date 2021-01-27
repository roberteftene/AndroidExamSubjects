package com.app.servicecardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.servicecardapp.models.ServiceCard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddCardActivity extends AppCompatActivity {

    private EditText serviceNoTxt, serviceCostTxt;
    private Spinner serviceDepSpinner;
    private DatePicker serviceDate;
    private ArrayList<ServiceCard> serviceCards;
    private Intent i;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private Button addService;
    private ServiceCard serviceCard;
    private int pos;
    private  boolean isEdit= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        i = getIntent();
        serviceCards = i.getParcelableArrayListExtra("cards");
        serviceCard = i.getParcelableExtra("card");
        pos = i.getIntExtra("position",1);

        serviceCostTxt = findViewById(R.id.serviceCostTxt);
        serviceNoTxt = findViewById(R.id.serviceNoTxt);
        serviceDate = findViewById(R.id.serviceDate);

        serviceDepSpinner = findViewById(R.id.serviceDepSpinner);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.serviceDepartmnet, android.R.layout.simple_spinner_dropdown_item);
        serviceDepSpinner.setAdapter(arrayAdapter);

        if(serviceCard != null) {
            isEdit = true;
            serviceNoTxt.setText(String.valueOf(serviceCard.getServiceNumber()));
            serviceNoTxt.setActivated(false);
            serviceCostTxt.setText(String.valueOf(serviceCard.getServiceCost()));
            int posSpinner =  arrayAdapter.getPosition(serviceCard.getServiceDepartment());
            serviceDepSpinner.setSelection(posSpinner);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(serviceCard.getServiceDate());
            serviceDate.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        }

        addService = findViewById(R.id.addServiceCardFormBtn);
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                if(serviceNoTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddCardActivity.this, "Service no invalid", Toast.LENGTH_SHORT).show();
                }
                if(serviceCostTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddCardActivity.this, "serviceCostTxt no invalid", Toast.LENGTH_SHORT).show();
                }
                if(isValid) {
                    StringBuilder s = new StringBuilder();
                    s.append(serviceDate.getDayOfMonth());
                    s.append("-");
                    s.append(serviceDate.getMonth());
                    s.append("-");
                    s.append(serviceDate.getYear());
                    Date date = null;
                    try {
                        date = new SimpleDateFormat("dd-MM-yyyy").parse(String.valueOf(s));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Double serviceCost = Double.parseDouble(serviceCostTxt.getText().toString());
                    int serviceNo = Integer.parseInt(serviceNoTxt.getText().toString());
                    String categ = serviceDepSpinner.getSelectedItem().toString();

                    if(isEdit) {

                        serviceCard.setServiceNumber(serviceNo);
                        serviceCard.setServiceCost(serviceCost);
                        serviceCard.setServiceDate(date);
                        serviceCard.setServiceDepartment(categ);
                        i = new Intent(AddCardActivity.this,ListCardsActivity.class);
                        i.putExtra("card",serviceCard);
                        i.putExtra("position",pos);
                        setResult(Activity.RESULT_OK,i);
                        finish();

                    } else {

                    ServiceCard serviceCard = new ServiceCard(Integer.parseInt(serviceNoTxt.getText().toString()), serviceDepSpinner.getSelectedItem().toString(), Double.parseDouble(serviceCostTxt.getText().toString()), date);
                    serviceCards.add(serviceCard);
                    i = new Intent(AddCardActivity.this, MainActivity.class);
                    i.putParcelableArrayListExtra("cards",serviceCards);
                    setResult(Activity.RESULT_OK,i);
                    finish();
                    }

                }

            }
        });

    }
}