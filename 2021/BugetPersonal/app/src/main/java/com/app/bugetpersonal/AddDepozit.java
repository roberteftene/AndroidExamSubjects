package com.app.bugetpersonal;

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
import android.widget.Toast;

import java.util.ArrayList;

public class AddDepozit extends AppCompatActivity {

    private Intent intent;
    private ArrayList<Depozit> depozits = new ArrayList<>();
    private EditText numeDepozitTxt, sumaInitialaTxt;
    private Spinner valutaTxt;
    private TimePicker oraAdaugare;
    private Button addDepozit;
    private CheckBox checkEconomy;
    private ArrayAdapter<CharSequence>arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_depozit);

        numeDepozitTxt = findViewById(R.id.numeDepozitTxt);
        sumaInitialaTxt = findViewById(R.id.sumaInitialaTxt);
        valutaTxt = findViewById(R.id.valutaSpinner);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.valutaCateg, android.R.layout.simple_spinner_dropdown_item);
        valutaTxt.setAdapter(arrayAdapter);
        oraAdaugare = findViewById(R.id.oraAdaugare);
        checkEconomy = findViewById(R.id.checkEconomy);

        intent = getIntent();
        depozits = intent.getParcelableArrayListExtra("objs");

        addDepozit = findViewById(R.id.addDepozitBtnForm);
        addDepozit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                if(numeDepozitTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddDepozit.this, "Nume invalid", Toast.LENGTH_SHORT).show();
                }
                if(sumaInitialaTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddDepozit.this, "Suma initiala nevalida", Toast.LENGTH_SHORT).show();
                }
                if(isValid) {
                    String nume = numeDepozitTxt.getText().toString();
                    double suma = Double.parseDouble(sumaInitialaTxt.getText().toString());
                    String valuta = valutaTxt.getSelectedItem().toString();
                    StringBuilder s = new StringBuilder();
                    s.append(oraAdaugare.getHour());
                    s.append(":");
                    s.append(oraAdaugare.getMinute());
                    boolean isEconomy = checkEconomy.isSelected();

                    Depozit depozit = new Depozit(nume,valuta,isEconomy,s.toString(),suma);
                    depozits.add(depozit);
                    intent.putParcelableArrayListExtra("objs",depozits);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}