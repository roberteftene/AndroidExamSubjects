package com.app.curs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.curs.models.Curs;

import java.util.ArrayList;

public class AddCurs extends AppCompatActivity {

    private EditText denumireCursTxt, denumireProfTxt, salaTxt, nrParticipantiTxt;
    private Button addCurs;
    private ArrayList<Curs> cursuri;
    private Intent intent;
    private Curs curs;
    private int pos;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_curs);

        denumireCursTxt = findViewById(R.id.denumireTxt);
        denumireProfTxt = findViewById(R.id.profesorTxt);
        salaTxt = findViewById(R.id.salaTxt);
        nrParticipantiTxt = findViewById(R.id.nrParticipantiTxt);
        addCurs = findViewById(R.id.addCursBtn);

        intent = getIntent();
        cursuri = intent.getParcelableArrayListExtra("cursuri");
        curs = intent.getParcelableExtra("curs");
        pos = intent.getIntExtra("pos",1);
        if(curs != null) {
            isEdit = true;
            denumireCursTxt.setText(curs.getDenumire());
            denumireProfTxt.setText(curs.getProfesorTitular());
            salaTxt.setText(curs.getSala());
            nrParticipantiTxt.setText(String.valueOf(curs.getNrParticipanti()));
            salaTxt.setEnabled(false);
        }

        addCurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                String denCurs = denumireCursTxt.getText().toString();
                String prof = denumireProfTxt.getText().toString();
                String sala = salaTxt.getText().toString();
                int nrPart = Integer.parseInt(nrParticipantiTxt.getText().toString());

                if(denCurs.length() < 2) {
                    isValid = false;
                    Toast.makeText(AddCurs.this, "Denumire curs invalida", Toast.LENGTH_SHORT).show();
                }
                if(prof.length() < 2) {
                    isValid = false;
                    Toast.makeText(AddCurs.this, "Profu invalid", Toast.LENGTH_SHORT).show();
                }
                if(sala.length() < 1) {
                    isValid = false;
                    Toast.makeText(AddCurs.this, "Sala invalida", Toast.LENGTH_SHORT).show();
                }
                if(nrPart < 10) {
                    isValid = false;
                    Toast.makeText(AddCurs.this, "Participanti invalizi", Toast.LENGTH_SHORT).show();
                }

                if(isValid) {
                    if(isEdit) {

                        curs.setDenumire(denCurs);
                        curs.setProfesorTitular(prof);
                        curs.setSala(sala);
                        curs.setNrParticipanti(nrPart);
                        intent = new Intent(AddCurs.this,MainActivity.class);
                        intent.putExtra("curs",curs);
                        intent.putExtra("pos",pos);
                        setResult(Activity.RESULT_OK,intent);
                        finish();

                    }else {

                    Curs curs = new Curs(denCurs,nrPart,sala,prof);
                    intent = new Intent(AddCurs.this, MainActivity.class);
                    cursuri.add(curs);
                    intent.putParcelableArrayListExtra("cursuri",cursuri);
                    setResult(Activity.RESULT_OK,intent);
                    finish();

                    }

                }
            }
        });
    }
}