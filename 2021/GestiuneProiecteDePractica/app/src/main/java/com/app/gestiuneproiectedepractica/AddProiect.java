package com.app.gestiuneproiectedepractica;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class AddProiect extends AppCompatActivity {

    private EditText titluTxt, profTxt;
    private CheckBox checkIsAngajat;
    private DatePicker dataIncepere;
    private SeekBar gradCompletare;
    private Intent intent;
    private ArrayList<ProiectDePractica> proiectDePracticas = new ArrayList<>();
    private ProiectDePractica proiectDePractica;
    private int pos = 0;
    private boolean isEdit = false;
    private Button addProiect;
    private int gradCompletareValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_proiect);

        intent = getIntent();
        proiectDePracticas = intent.getParcelableArrayListExtra("proiecte");
        proiectDePractica = intent.getParcelableExtra("obj");
        pos = intent.getIntExtra("pos",1);

        profTxt = findViewById(R.id.profCoordTxt);
        titluTxt = findViewById(R.id.titluTxt);
        checkIsAngajat = findViewById(R.id.checkAngajat);
        dataIncepere = findViewById(R.id.dataIncepere);
        gradCompletare = findViewById(R.id.gradCompletare);

        if(proiectDePractica!=null) {
            isEdit = true;
            profTxt.setText(proiectDePractica.getProfCoordonator());
            titluTxt.setText(proiectDePractica.getTitlu());
            checkIsAngajat.setChecked(proiectDePractica.getIsAngajat());
            String[] date = proiectDePractica.getDataPredare().split("-");
            dataIncepere.updateDate(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0]));
            gradCompletare.setProgress(proiectDePractica.getNrSaptamani());
        }

        addProiect = findViewById(R.id.addProiectBtnForm);

        gradCompletareValue = gradCompletare.getProgress();

        gradCompletare.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gradCompletareValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        addProiect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                if(profTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddProiect.this, "Prof invalid", Toast.LENGTH_SHORT).show();
                }
                if(titluTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddProiect.this, "Titlu invalid", Toast.LENGTH_SHORT).show();
                }

                if(isValid) {

                    String numeProf = profTxt.getText().toString();
                    String titlu = titluTxt.getText().toString();
                    StringBuilder s = new StringBuilder();
                    s.append(dataIncepere.getDayOfMonth());
                    s.append("-");
                    s.append(dataIncepere.getMonth());
                    s.append("-");
                    s.append(dataIncepere.getYear());
                    boolean isAngajat = checkIsAngajat.isChecked();

                    if(isEdit) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(AddProiect.this);
                        builder.setTitle("Sigur editati?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                proiectDePractica.setDataPredare(s.toString());
                                proiectDePractica.setNrSaptamani(gradCompletareValue);
                                proiectDePractica.setProfCoordonator(numeProf);
                                proiectDePractica.setTitlu(titlu);
                                proiectDePractica.setIsAngajat(isAngajat);
                                intent = new Intent(AddProiect.this,MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("obj",proiectDePractica);
                                bundle.putInt("pos",pos);
                                intent.putExtra("bundle",bundle);
                                setResult(RESULT_OK,intent);

                                finish();
                                dialog.cancel();
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.setCancelable(true);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();


                    } else {
                        ProiectDePractica proiectDePractica = new ProiectDePractica(titlu,numeProf,s.toString(),gradCompletareValue,isAngajat);
                        proiectDePracticas.add(proiectDePractica);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("proiecte",proiectDePracticas);
                        intent = new Intent(AddProiect.this,MainActivity.class);
                        intent.putExtra("bundle",bundle);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
            }
        });

    }
}