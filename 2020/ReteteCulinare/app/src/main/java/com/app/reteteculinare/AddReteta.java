package com.app.reteteculinare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.reteteculinare.database.AppDatabase;
import com.app.reteteculinare.models.Reteta;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddReteta extends AppCompatActivity {

    private EditText denumireTxt,descriereTxt,nrCaloriiTxt;
    private DatePicker data;
    private Spinner categorieSpinner;
    private ArrayList<Reteta> retetas;
    private Button addReteta,deleteReteta;
    private ArrayAdapter<CharSequence> arrayAdapter;
    Intent i = new Intent();
    private Reteta reteta;
    private int pos;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reteta);
        denumireTxt = findViewById(R.id.denumireTxt);
        descriereTxt = findViewById(R.id.descriereTxt);
        nrCaloriiTxt = findViewById(R.id.caloriiTxt);
        data = findViewById(R.id.date);
        categorieSpinner = findViewById(R.id.categorieSpinner);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.categorie,android.R.layout.simple_spinner_dropdown_item);
        categorieSpinner.setAdapter(arrayAdapter);
        i = getIntent();
        retetas = i.getParcelableArrayListExtra("retete");
        reteta = i.getParcelableExtra("reteta");
        pos = i.getIntExtra("pos",1);
        if(reteta != null) {
            denumireTxt.setText(reteta.getDenumire());
            descriereTxt.setText(reteta.getDescriere());
            nrCaloriiTxt.setText(String.valueOf(reteta.getCalorii()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(reteta.getDataAdaugare());
            data.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            int posSpinner =  arrayAdapter.getPosition(reteta.getCategorie());
            categorieSpinner.setSelection(posSpinner);
            isEdit = true;
        }
        addReteta = findViewById(R.id.addRetetaBtn);
        addReteta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder s = new StringBuilder();
                s.append(data.getDayOfMonth());
                s.append("-");
                s.append(data.getMonth()+1);
                s.append("-");
                s.append(data.getYear());
                Date dateFormat = null;
                try {
                    dateFormat = new SimpleDateFormat("dd-MM-yyyy").parse(s.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(isEdit) {
                    reteta.setDenumire(denumireTxt.getText().toString());
                    reteta.setDescriere(descriereTxt.getText().toString());
                    reteta.setCalorii(Double.parseDouble(nrCaloriiTxt.getText().toString()));
                    reteta.setCategorie(categorieSpinner.getSelectedItem().toString());
                    reteta.setDataAdaugare(dateFormat);
                    i = new Intent(AddReteta.this,MainActivity.class);
                    i.putExtra("reteta",reteta);
                    i.putExtra("pos",pos);
                    setResult(Activity.RESULT_OK,i);
                    finish();

                } else {
                    Reteta reteta = new Reteta(denumireTxt.getText().toString(), descriereTxt.getText().toString(), dateFormat, Double.parseDouble(nrCaloriiTxt.getText().toString()), categorieSpinner.getSelectedItem().toString());
                    retetas.add(reteta);
                    i = new Intent(AddReteta.this, MainActivity.class);
                    i.putParcelableArrayListExtra("retete", retetas);
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }
            }
        });

        deleteReteta = findViewById(R.id.deleteRetetaBtn);
        deleteReteta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(AddReteta.this,MainActivity.class);
                i.putExtra("pos",pos);
                setResult(Activity.RESULT_OK,i);
                finish();
            }
        });
    }
}