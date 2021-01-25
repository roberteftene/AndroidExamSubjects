package com.app.gestiuneobiecteinventar;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdaugareObiect extends AppCompatActivity {

    private EditText denumireTxt, nrInventarTxt, valoareTxt;
    private Spinner uzuraSpinner;
    private DatePicker dataAdaugare;
    private ArrayAdapter<CharSequence> adapter;
    private Button addObiectBtn, delObiectBtn;
    ArrayList<ObiectInventar> obiectInventars;
    ObiectInventar obiectInventar;
    int posObiect;
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_obiect);

        Intent i = new Intent();
        i = getIntent();
        obiectInventars = i.getParcelableArrayListExtra("obiecte");
        obiectInventar = i.getParcelableExtra("obiect");
        posObiect = i.getIntExtra("pozObiect",1);



        denumireTxt = findViewById(R.id.denumireTxt);
        nrInventarTxt = findViewById(R.id.nrInventarTxt);
        valoareTxt = findViewById(R.id.valoareTxt);
        uzuraSpinner = findViewById(R.id.uzuraSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.uzura, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uzuraSpinner.setAdapter(adapter);
        dataAdaugare = findViewById(R.id.date);

        if(obiectInventar != null) {
            denumireTxt.setText(obiectInventar.getDenumire());
            nrInventarTxt.setText(String.valueOf(obiectInventar.getNumarInventar()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(obiectInventar.getDataAdaugarii());
            dataAdaugare.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            int spinnerPos = adapter.getPosition(obiectInventar.getUzura());
            uzuraSpinner.setSelection(spinnerPos);
            valoareTxt.setText(String.valueOf(obiectInventar.getValoare()));
            isEdit = true;
        }

        addObiectBtn = findViewById(R.id.addObiectBtn);
        addObiectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder s = new StringBuilder();
                s.append(dataAdaugare.getDayOfMonth());
                s.append("-");
                s.append(dataAdaugare.getMonth()+1);
                s.append("-");
                s.append(dataAdaugare.getYear());
                Date dateFormat = null;
                try {
                    dateFormat = new SimpleDateFormat("dd-MM-yyyy").parse(s.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(isEdit) {

                    obiectInventar.setDenumire(denumireTxt.getText().toString());
                    obiectInventar.setNumarInventar(Integer.parseInt(nrInventarTxt.getText().toString()));
                    obiectInventar.setDataAdaugarii(dateFormat);
                    obiectInventar.setValoare(Double.parseDouble(valoareTxt.getText().toString()));
                    obiectInventar.setUzura(uzuraSpinner.getSelectedItem().toString());

                    Intent intentEdit = new Intent(AdaugareObiect.this,ListaObiecteInventar.class);
                    intentEdit.putExtra("obiect",obiectInventar);
                    intentEdit.putExtra("pozObiect",posObiect);
                    setResult(Activity.RESULT_OK,intentEdit);
                    finish();
                } else {
                ObiectInventar obiectInventar = new ObiectInventar(denumireTxt.getText().toString(),Integer.parseInt(nrInventarTxt.getText().toString()),dateFormat,Double.parseDouble(valoareTxt.getText().toString()),uzuraSpinner.getSelectedItem().toString());
                obiectInventars.add(obiectInventar);
                Intent intent = new Intent(AdaugareObiect.this,MainActivity.class);
                intent.putParcelableArrayListExtra("obiecte",obiectInventars);
                setResult(Activity.RESULT_OK,intent);
                finish();
                }
            }
        });

        delObiectBtn = findViewById(R.id.deleteObiectBtn);
        delObiectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delIntent = new Intent(AdaugareObiect.this,ListaObiecteInventar.class);
                delIntent.putExtra("pozObiect",posObiect);
                setResult(Activity.RESULT_OK,delIntent);
                finish();
            }
        });
    }
}