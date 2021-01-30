package com.app.colectionaritimbre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTimbru extends AppCompatActivity {

    private EditText nrTimbruTxt, formaTxt;
    private DatePicker data;
    private SeekBar grad;
    private CheckBox isAdeziv;
    private ArrayList<Timbru> timbrus= new ArrayList<>();
    private Timbru timbru;
    private Intent intent;
    private Button addTimbru;
    private int gradDe = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timbru);

    intent = getIntent();
    timbru = intent.getParcelableExtra("obj");

    nrTimbruTxt = findViewById(R.id.timbruNoTxt);
    formaTxt = findViewById(R.id.formaTxt);
    data = findViewById(R.id.date);
    grad = findViewById(R.id.gradDegradareSeek);
    gradDe = grad.getProgress();
    grad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            gradDe = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    });

    isAdeziv = findViewById(R.id.checkAdeziv);

    addTimbru = findViewById(R.id.addTimbruForm);
    addTimbru.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean isvalid = true;

            if(nrTimbruTxt.getText().toString().length() < 1) {
                isvalid = false;
                Toast.makeText(AddTimbru.this, "Numar invalid", Toast.LENGTH_SHORT).show();
            }
            if(formaTxt.getText().toString().length() < 1) {
                isvalid = false;
                Toast.makeText(AddTimbru.this, "Forma invalida", Toast.LENGTH_SHORT).show();
            }

            if(isvalid) {

                String forma = formaTxt.getText().toString();
                double valoare = Double.parseDouble(nrTimbruTxt.getText().toString());
                boolean isAdezivCheck = isAdeziv.isChecked();
                StringBuilder s = new StringBuilder();
                s.append(data.getDayOfMonth());
                s.append("-");
                s.append(data.getMonth());
                s.append("-");
                s.append(data.getYear());

                timbru.setCuAdeziv(isAdezivCheck);
                timbru.setData(s.toString());
                timbru.setValoare(valoare);
                timbru.setForma(forma);
                timbru.setGradDegradare(gradDe);
                intent = new Intent(AddTimbru.this,MainActivity.class);
                intent.putExtra("obj",timbru);
                setResult(RESULT_OK,intent);
                finish();
            }
        }
    });
    }
}