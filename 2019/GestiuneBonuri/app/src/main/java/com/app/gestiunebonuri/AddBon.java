package com.app.gestiunebonuri;

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

public class AddBon extends AppCompatActivity {

    private EditText nrBonTxt,ghiseuTxt;
    private Spinner serviciuSpinner;
    private DatePicker data;
    private ArrayList<Bon> bons = new ArrayList<>();
    private Intent intent;
    private int pos = 0;
    private Bon bon;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private Button addBon;
    private  boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bon);

        nrBonTxt = findViewById(R.id.nrBonTxt);
        ghiseuTxt = findViewById(R.id.ghiseuTxt);
        serviciuSpinner = findViewById(R.id.serviciuSpinner);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.serviciu, android.R.layout.simple_spinner_dropdown_item);
        serviciuSpinner.setAdapter(arrayAdapter);
        data = findViewById(R.id.dataInput);

        intent = getIntent();
        bons = intent.getParcelableArrayListExtra("bonuri");
        bon = intent.getParcelableExtra("bon");
        pos = intent.getIntExtra("poz",1);

        if(bon != null) {
            isEdit = true;
            nrBonTxt.setText(String.valueOf(bon.getNumar()));
            ghiseuTxt.setText(bon.getGhiseu());
            serviciuSpinner.setSelection(
            arrayAdapter.getPosition(bon.getServiciu())
            );
            String[] dataSplitted = bon.getData().split("-");
            data.updateDate(Integer.parseInt(dataSplitted[2]),Integer.parseInt(dataSplitted[1]),Integer.parseInt(dataSplitted[0]));
        }
        addBon = findViewById(R.id.addBonBtnForm);

        addBon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                if(nrBonTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddBon.this, "Numar invalid", Toast.LENGTH_SHORT).show();
                }

                if(ghiseuTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddBon.this, "Ghiseu invalid", Toast.LENGTH_SHORT).show();
                }

                if(isValid) {

                    int nrBon = Integer.parseInt(nrBonTxt.getText().toString());
                    String ghiseu = ghiseuTxt.getText().toString();
                    String serviciu = serviciuSpinner.getSelectedItem().toString();
                    StringBuilder s = new StringBuilder();
                    s.append(data.getDayOfMonth());
                    s.append("-");
                    s.append(data.getMonth()+1);
                    s.append("-");
                    s.append(data.getYear());

                    if(isEdit) {
                        bon.setGhiseu(ghiseu);
                        bon.setNumar(nrBon);
                        bon.setServiciu(serviciu);
                        bon.setData(s.toString());
                        intent = new Intent(AddBon.this,ListaBonuri.class);
                        intent.putExtra("bon",bon);
                        intent.putExtra("poz",pos);
                        setResult(RESULT_OK,intent);
                        finish();
                    }else {
                        Bon bon = new Bon(nrBon,serviciu,s.toString(),ghiseu);
                        bons.add(bon);
                        intent = new Intent(AddBon.this,MainActivity.class);
                        intent.putParcelableArrayListExtra("bonuri",bons);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }

            }
        });

    }
}