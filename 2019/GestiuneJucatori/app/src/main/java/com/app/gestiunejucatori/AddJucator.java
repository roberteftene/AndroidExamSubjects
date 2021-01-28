package com.app.gestiunejucatori;

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

import com.app.gestiunejucatori.models.Jucator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddJucator extends AppCompatActivity {

    private EditText nrJucatorTxt,numeJucatorTxt;
    private Spinner pozitieJucatorSpinner;
    private DatePicker dataNasteriiJucator;
    private Button addJucator;
    private ArrayList<Jucator> jucators;
    private Intent intent;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private boolean isEdit = false;
    private Jucator jucator;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jucator);
        intent = getIntent();
        jucators = intent.getParcelableArrayListExtra("jucatori");
        jucator = intent.getParcelableExtra("jucator");
        pos = intent.getIntExtra("pos",1);

        addJucator = findViewById(R.id.addJucatorBtn);
        nrJucatorTxt = findViewById(R.id.nrJucatorTxt);
        numeJucatorTxt = findViewById(R.id.numeJucatorTxt);
        dataNasteriiJucator = findViewById(R.id.dataNasteriiPicker);
        pozitieJucatorSpinner = findViewById(R.id.pozitieJucatorSpinner);

        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.pozitieJucator, android.R.layout.simple_spinner_dropdown_item);
        pozitieJucatorSpinner.setAdapter(arrayAdapter);

        if(jucator != null) {
            isEdit = true;
            numeJucatorTxt.setText(jucator.getNume());
            nrJucatorTxt.setText(String.valueOf(jucator.getNumar()));
            int posSpinner = arrayAdapter.getPosition(jucator.getPozitie());
            pozitieJucatorSpinner.setSelection(posSpinner);
            String[] date = jucator.getDataNasterii().split("-");
            dataNasteriiJucator.updateDate(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
        }

        addJucator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                if(numeJucatorTxt.getText().toString().length() < 2) {
                    isValid = false;
                    Toast.makeText(AddJucator.this, "Nume invalid", Toast.LENGTH_SHORT).show();
                }

                if(nrJucatorTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddJucator.this, "Numar invalid", Toast.LENGTH_SHORT).show();
                }

                if(isValid) {

                    String numeJucator = numeJucatorTxt.getText().toString();
                    int nrJucator = Integer.parseInt(nrJucatorTxt.getText().toString());
                    String pozJucator = pozitieJucatorSpinner.getSelectedItem().toString();
                    StringBuilder s = new StringBuilder();
                    s.append(dataNasteriiJucator.getDayOfMonth());
                    s.append("-");
                    s.append(dataNasteriiJucator.getMonth());
                    s.append("-");
                    s.append(dataNasteriiJucator.getYear());

                    if(isEdit) {
                        jucator.setNumar(nrJucator);
                        jucator.setNume(numeJucator);
                        jucator.setDataNasterii(s.toString());
                        jucator.setPozitie(pozJucator);
                        intent = new Intent(AddJucator.this,MainActivity.class);
                        intent.putExtra("jucator",jucator);
                        intent.putExtra("pos",pos);
                        setResult(Activity.RESULT_OK,intent);
                        finish();
                    } else {



                Jucator jucator = new Jucator(nrJucator,numeJucator,s.toString(),pozJucator);
                intent = new Intent(AddJucator.this,MainActivity.class);
                jucators.add(jucator);
                intent.putParcelableArrayListExtra("jucatori",jucators);
                setResult(Activity.RESULT_OK,intent);
                finish();
                    }

                }





            }
        });
    }
}