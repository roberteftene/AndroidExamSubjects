package com.app.gestiunerezervari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddRezervare extends AppCompatActivity {

    private EditText numeClient, sumaPlata, durataSejur;
    private Spinner tipCamera;
    private DatePicker dataCazare;
    private ArrayList<Rezervare> rezervares;
    private Intent intent;
    private Rezervare rezervare;
    private int pos;
    private boolean isEdit = false;
    private Button addRezervare;
    private ArrayAdapter<CharSequence>arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rezervare);

        numeClient = findViewById(R.id.numeClientTxt);
        sumaPlata = findViewById(R.id.sumaPlataTxt);
        durataSejur = findViewById(R.id.durataSejur);
        tipCamera = findViewById(R.id.tipCamera);
        dataCazare = findViewById(R.id.dataSejur);
        addRezervare = findViewById(R.id.addRezervare);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.tipCameraArray, android.R.layout.simple_spinner_dropdown_item);
        tipCamera.setAdapter(arrayAdapter);
        intent = getIntent();
        rezervares = intent.getParcelableArrayListExtra("rezervari");
        rezervare = intent.getParcelableExtra("rezervare");
        pos = intent.getIntExtra("pos",1);

        if(rezervare != null) {
            isEdit = true;
            numeClient.setText(rezervare.getNumeClient());
            sumaPlata.setText(String.valueOf(rezervare.getSumaPlata()));
            durataSejur.setText(String.valueOf(rezervare.getDurataSejur()));
            int posSpinner =arrayAdapter.getPosition(rezervare.getTipCamera());
            tipCamera.setSelection(posSpinner);
            String[] date = rezervare.getDataCazare().split("/");
            dataCazare.updateDate(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
        }

        addRezervare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                if(numeClient.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddRezervare.this, "Nume invalid", Toast.LENGTH_SHORT).show();
                }
                if(sumaPlata.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddRezervare.this, "Suma plata invalida", Toast.LENGTH_SHORT).show();
                }
                if(durataSejur.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddRezervare.this, "Suma plata invalida", Toast.LENGTH_SHORT).show();
                }

                if(isValid) {

                    String nume = numeClient.getText().toString();
                    Double suma = Double.parseDouble(sumaPlata.getText().toString());
                    int durata = Integer.parseInt(durataSejur.getText().toString());
                    String tip = tipCamera.getSelectedItem().toString();

                    StringBuilder s = new StringBuilder();
                    s.append(dataCazare.getDayOfMonth());
                    s.append("/");
                    s.append(dataCazare.getMonth() + 1);
                    s.append("/");
                    s.append(dataCazare.getYear());
                    String data = s.toString();

                    if(isEdit) {

                        rezervare.setNumeClient(nume);
                        rezervare.setDataCazare(data);
                        rezervare.setDurataSejur(durata);
                        rezervare.setSumaPlata(suma);
                        rezervare.setTipCamera(tip);
                        intent = new Intent(AddRezervare.this,MainActivity.class);
                        intent.putExtra("rezervare",rezervare);
                        intent.putExtra("pos",pos);
                        setResult(RESULT_OK,intent);
                        finish();

                    }else {

                        Rezervare rezervare = new Rezervare(nume,tip,durata,suma,data);
                        rezervares.add(rezervare);
                        intent= new Intent(AddRezervare.this,MainActivity.class);
                        intent.putParcelableArrayListExtra("rezervari",rezervares);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }

            }
        });


    }
}