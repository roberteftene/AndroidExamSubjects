package com.app.agentieimobiliara;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;

public class AddImobil extends AppCompatActivity {

    private EditText imobilNoTxt,adresaTxt;
    private Spinner imobilCategSpinner;
    private TimePicker oraAdaugare;
    private CheckBox isDisponibilCheck;
    private Intent intent;
    private ArrayList<Imobil> imobils = new ArrayList<>();
    private ArrayAdapter<CharSequence> arrayAdapter;
    private Button addImobil;
    private boolean isEdit = false;
    private Imobil imobil;
    private int pos = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_imobol);

        imobilNoTxt = findViewById(R.id.imobilNoTxt);
        adresaTxt = findViewById(R.id.adresaTxt);
        oraAdaugare = findViewById(R.id.adugareTime);
        isDisponibilCheck = findViewById(R.id.checkIsDisponibil);
        imobilCategSpinner = findViewById(R.id.categSpinner);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.imobilCateg, android.R.layout.simple_spinner_dropdown_item);
        imobilCategSpinner.setAdapter(arrayAdapter);

        addImobil = findViewById(R.id.addBtnForm);

        intent = getIntent();
        imobils = intent.getParcelableArrayListExtra("imobile");
        imobil = intent.getParcelableExtra("imobil");
        pos = intent.getIntExtra("pos",1);

        if(imobil != null) {
            isEdit = true;
            adresaTxt.setText(imobil.getAdresa());
            imobilNoTxt.setText(String.valueOf(imobil.getNumar()));
            String[] time = imobil.getOraAdaugare().split(":");
            oraAdaugare.setHour(Integer.parseInt(time[0]));
            oraAdaugare.setMinute(Integer.parseInt(time[1]));
            imobilCategSpinner.setSelection( arrayAdapter.getPosition(imobil.getCategorie()));
            isDisponibilCheck.setChecked(imobil.isEsteDisponibil());
        }

        addImobil.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                String adresa = adresaTxt.getText().toString();
                int imobilNo = Integer.parseInt(imobilNoTxt.getText().toString());
                String categ = imobilCategSpinner.getSelectedItem().toString();
                StringBuilder s = new StringBuilder();
                s.append(oraAdaugare.getHour());
                s.append(":");
                s.append(oraAdaugare.getMinute());
                boolean isDipsonibil =isDisponibilCheck.isChecked();

                if(isEdit) {
                    imobil.setAdresa(adresa);
                    imobil.setCategorie(categ);
                    imobil.setNumar(imobilNo);
                    imobil.setEsteDisponibil(isDipsonibil);
                    imobil.setOraAdaugare(s.toString());
                    intent = new Intent(AddImobil.this,MainActivity.class);
                    intent.putExtra("imobil",imobil);
                    intent.putExtra("pos",pos);
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    Imobil imobil = new Imobil(adresa,imobilNo,categ,isDipsonibil,s.toString());
                    imobils.add(imobil);
                    intent = new Intent(AddImobil.this,MainActivity.class);
                    intent.putParcelableArrayListExtra("imobile",imobils);
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });
    }
}