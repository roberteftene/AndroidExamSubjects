package com.app.gestiuneexamene;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.gestiuneexamene.models.Examen;

import java.util.ArrayList;

public class AddExamen extends AppCompatActivity {

    private EditText denMaterieTxt,nrStudentiTxt,supraveghetorTxt,salaTxt;
    private Button addExamenBtn;
    private ArrayList<Examen> examene;
    private Intent intent;
    private Examen examen;
    private int pos;
    private boolean isEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_examen);

        denMaterieTxt = findViewById(R.id.denMaterieTxt);
        nrStudentiTxt = findViewById(R.id.nrStudentiTxt);
        supraveghetorTxt = findViewById(R.id.supraveghetorTxt);
        salaTxt = findViewById(R.id.salaTxt);
        addExamenBtn = findViewById(R.id.addExamenBtn);

        intent = getIntent();
        examene = intent.getParcelableArrayListExtra("examene");
        examen = intent.getParcelableExtra("examen");
        pos = intent.getIntExtra("pos",1);

        if(examen != null) {
            isEdit = true;
            denMaterieTxt.setText(examen.getDenumireMaterie());
            nrStudentiTxt.setText(String.valueOf(examen.getNrStudenti()));
            supraveghetorTxt.setText(examen.getSupraveghetor());
            salaTxt.setText(examen.getSala());
        }

        addExamenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                if(denMaterieTxt.getText().toString().length() < 1) {
                    isValid =false;
                    Toast.makeText(AddExamen.this, "Denumire invalida", Toast.LENGTH_SHORT).show();
                }
                if(supraveghetorTxt.getText().toString().length() < 1) {
                    isValid =false;
                    Toast.makeText(AddExamen.this, "Denumire invalida", Toast.LENGTH_SHORT).show();
                }
                if(salaTxt.getText().toString().length() < 1) {
                    isValid =false;
                    Toast.makeText(AddExamen.this, "Denumire invalida", Toast.LENGTH_SHORT).show();
                }
                if(nrStudentiTxt.getText().toString().length() < 1) {
                    isValid =false;
                    Toast.makeText(AddExamen.this, "Denumire invalida", Toast.LENGTH_SHORT).show();
                }
                if(isValid) {

                    String den = denMaterieTxt.getText().toString();
                    String supra = supraveghetorTxt.getText().toString();
                    String sala = salaTxt.getText().toString();
                    int nrStud = Integer.parseInt(nrStudentiTxt.getText().toString());

                    if(isEdit) {

                        examen.setDenumireMaterie(den);
                        examen.setNrStudenti(nrStud);
                        examen.setSala(sala);
                        examen.setSupraveghetor(supra);
                        intent = new Intent(AddExamen.this,MainActivity.class);
                        intent.putExtra("examen",examen);
                        intent.putExtra("pos",pos);
                        setResult(Activity.RESULT_OK,intent);
                        finish();

                    } else {
                    Examen examen = new Examen(den,nrStud,sala,supra);
                    intent = new Intent(AddExamen.this,MainActivity.class);
                    examene.add(examen);
                    intent.putParcelableArrayListExtra("examene",examene);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                    }

                }

            }
        });
    }
}