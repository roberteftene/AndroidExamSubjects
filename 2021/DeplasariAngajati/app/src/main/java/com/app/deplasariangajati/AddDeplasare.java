package com.app.deplasariangajati;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class AddDeplasare extends AppCompatActivity {

    private Intent intent;
    private ArrayList<Deplasare> deplasares = new ArrayList<>();
    private ArrayAdapter<CharSequence> arrayAdapter;
    private EditText numeAngajatTxt, durataTxt;
    private TimePicker oraPlecare;
    private Spinner categMotiv;
    private RadioGroup transportGroup;
    private RadioButton masinaFirmaBtn;
    private RadioButton transpComunBtn;
    private Button addDeplasareBtnForm;
    private boolean isEdit;
    private String tranport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deplasare);

        intent = getIntent();
        deplasares = intent.getParcelableArrayListExtra("objs");

        numeAngajatTxt = findViewById(R.id.numeAngajatTxt);
        durataTxt = findViewById(R.id.durataDeplasareTxt);
        oraPlecare = findViewById(R.id.oraPlecareTxt);
        categMotiv = findViewById(R.id.motivareSpinner);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.motivareCateg, android.R.layout.simple_spinner_dropdown_item);
        categMotiv.setAdapter(arrayAdapter);

        transportGroup = findViewById(R.id.mobilDeplasareGroup);
        masinaFirmaBtn = findViewById(R.id.masinaFirma);
        transpComunBtn = findViewById(R.id.tranportComun);

        addDeplasareBtnForm = findViewById(R.id.addDeplasareBtnForm);

        addDeplasareBtnForm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                boolean isValid = true;

                if(numeAngajatTxt.getText().toString().length() <  1) {
                    isValid = false;
                    Toast.makeText(AddDeplasare.this, "Nume invalid", Toast.LENGTH_SHORT).show();
                }
                if(durataTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddDeplasare.this, "Durata invalida", Toast.LENGTH_SHORT).show();
                }
                if(isValid) {

                    String numeAngajat = numeAngajatTxt.getText().toString();
                    int durata = Integer.parseInt(durataTxt.getText().toString());

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(oraPlecare.getHour());
                    stringBuilder.append(":");
                    stringBuilder.append(oraPlecare.getMinute());

                    int transportId = transportGroup.getCheckedRadioButtonId();
                    if(transportId == masinaFirmaBtn.getId()) {
                        tranport = masinaFirmaBtn.getText().toString();
                    } else if(transportId == transpComunBtn.getId()) {
                        tranport = transpComunBtn.getText().toString();
                    }

                    Deplasare deplasare = new Deplasare(numeAngajat,stringBuilder.toString(),categMotiv.getSelectedItem().toString(),durata,tranport);
                    deplasares.add(deplasare);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("objs",deplasares);
                    intent = new Intent(AddDeplasare.this,MainActivity.class);
                    intent.putExtra("bundle",bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}