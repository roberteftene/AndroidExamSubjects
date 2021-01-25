package com.app.livrare;

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

import com.app.livrare.models.Livrare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddLivrare extends AppCompatActivity {

    private Button addLivrareBtn,stergereLivrareBtn;
    private EditText destinatarTxt, valoareTxt, adresaTxt;
    private DatePicker data;
    private Spinner tip;
    ArrayList<Livrare> livrares = new ArrayList<>();
    Livrare livrare;
    int pos = 0;
    ArrayAdapter<CharSequence> adapter;
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_livrare);
        final AppDatabase appDatabase = AppDatabase.getInstance(this);
        addLivrareBtn = findViewById(R.id.addLivareBtnForm);
        stergereLivrareBtn = findViewById(R.id.deleteLivrare);
        destinatarTxt = findViewById(R.id.destinatarTxt);
        valoareTxt = findViewById(R.id.valoareTxt);
        adresaTxt = findViewById(R.id.adresaTxt);
        data = findViewById(R.id.dataSpinner);
        tip = findViewById(R.id.tipSpinner);

        adapter = ArrayAdapter.createFromResource(this, R.array.tip, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tip.setAdapter(adapter);
        Intent intent = new Intent();
        intent = getIntent();
        livrares = intent.getParcelableArrayListExtra("livrari");
        livrare = intent.getParcelableExtra("livrare");
        pos = intent.getIntExtra("livrarePos",1);

        if(livrare != null) {
            destinatarTxt.setText(livrare.getDestinatar());
            adresaTxt.setText(livrare.getAdresa());
            valoareTxt.setText(String.valueOf(livrare.getValoare()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(livrare.getData());
            data.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            int pos = adapter.getPosition(livrare.getTip());
            tip.setSelection(pos);
            isEdit = true;
        }


        addLivrareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                if(destinatarTxt.getText().length() < 2) {
                    valid = false;
                    Toast.makeText(AddLivrare.this, "Destinatar invalid", Toast.LENGTH_SHORT).show();
                }
                if(adresaTxt.getText().length() < 2) {
                    valid = false;
                    Toast.makeText(AddLivrare.this, "Adresa invalida", Toast.LENGTH_SHORT).show();
                }
                if(valoareTxt.getText().length() < 1) {
                    valid = false;
                    Toast.makeText(AddLivrare.this, "Valoare invalida", Toast.LENGTH_SHORT).show();
                }
                if(valid) {
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
                        livrare.setDestinatar(destinatarTxt.getText().toString());
                        livrare.setAdresa(adresaTxt.getText().toString());
                        livrare.setData(dateFormat);
                        livrare.setTip(tip.getSelectedItem().toString());
                        Intent intent2 = new Intent(AddLivrare.this,Istoric.class);
                        intent2.putExtra("livrare",livrare);
                        intent2.putExtra("livrarePos", pos);
                        setResult(Activity.RESULT_OK,intent2);
                        finish();
                    } else {

                    Livrare livrareNoua = new Livrare(destinatarTxt.getText().toString(),adresaTxt.getText().toString(),dateFormat,Double.parseDouble(valoareTxt.getText().toString()),tip.getSelectedItem().toString());
                        livrares.add(livrareNoua);
                        appDatabase.livrareDao().addLivrare(livrareNoua);
                        Intent intent1 = new Intent(AddLivrare.this,MainActivity.class);
                        intent1.putParcelableArrayListExtra("livrari",livrares);
                        setResult(Activity.RESULT_OK,intent1);
                        finish();
                    }
                }
            }
        });

        stergereLivrareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(AddLivrare.this,Istoric.class);
                intent3.putExtra("livrarePos",pos);
                setResult(Activity.RESULT_OK,intent3);
                finish();
            }
        });



    }
}