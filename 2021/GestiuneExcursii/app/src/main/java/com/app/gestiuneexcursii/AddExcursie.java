package com.app.gestiuneexcursii;

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

public class AddExcursie extends AppCompatActivity {

    private EditText locatieTxt, cheltuialaTxt;
    private DatePicker dataPlecare;
    private CheckBox isVisited;
    private SeekBar prioritate;
    private boolean isEdit = false;
    private Excursie excursie;
    private ArrayList<Excursie> excursies = new ArrayList<>();
    private Button addExcursie;
    private int pos;
    private Intent intent;
    private int priotitateValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_excursie);

        locatieTxt = findViewById(R.id.locatieTxt);
        cheltuialaTxt = findViewById(R.id.cheltuialaTxt);
        isVisited  =findViewById(R.id.checkVisited);
        dataPlecare = findViewById(R.id.dataPlecare);
        prioritate = findViewById(R.id.prioritate);

        intent = getIntent();
        excursies = intent.getParcelableArrayListExtra("objs");

        priotitateValue = prioritate.getProgress();

        prioritate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                priotitateValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        addExcursie = findViewById(R.id.addExcursieBtnForm);
        addExcursie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean isValid = true;
                if(locatieTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddExcursie.this, "Locatie gresita", Toast.LENGTH_SHORT).show();
                }

                if(cheltuialaTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddExcursie.this, "Cheltuiala gresita", Toast.LENGTH_SHORT).show();
                }

                if(isValid) {
                    String locatie = locatieTxt.getText().toString();
                    Double cheltuiala = Double.parseDouble(cheltuialaTxt.getText().toString());
                    boolean isVisit = isVisited.isChecked();
                    StringBuilder s = new StringBuilder();
                    s.append(dataPlecare.getDayOfMonth());
                    s.append("-");
                    s.append(dataPlecare.getMonth());
                    s.append("-");
                    s.append(dataPlecare.getYear());


                    if(isEdit) {

                    } else  {
                        Excursie excursie = new Excursie(locatie,s.toString(),isVisit,priotitateValue,cheltuiala);
                        excursies.add(excursie);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("objs",excursies);
                        intent = new Intent(AddExcursie.this,MainActivity.class);
                        intent.putExtra("bundle",bundle);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }

            }
        });

    }
}