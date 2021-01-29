package com.app.casadelicitatii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddLicititatie extends AppCompatActivity {

    private ArrayList<Licitatie> licitaties = new ArrayList<>();
    private EditText licitatieDescriereTxt, valoareTxt;
    private Spinner categLicitatie;
    private DatePicker data;
    private RadioGroup radioGroup;
    private RadioButton radioCash,radioCard;
    private Intent intent;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private Button addLicitatie;
    private Licitatie licitatie;
    private int pos;
    private boolean isEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_licititatie);

        licitatieDescriereTxt = findViewById(R.id.descriereTxt);
        valoareTxt = findViewById(R.id.valoareTxt);
        categLicitatie = findViewById(R.id.categSpinner);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.categ, android.R.layout.simple_spinner_dropdown_item);
        categLicitatie.setAdapter(arrayAdapter);
        data = findViewById(R.id.date);
        radioGroup = findViewById(R.id.plataMethodsGroup);
        radioCard = findViewById(R.id.cardMethod);
        radioCash = findViewById(R.id.cashMethods);

        intent = getIntent();
        licitaties = intent.getParcelableArrayListExtra("licitatii");
        licitatie = intent.getParcelableExtra("licitatie");
        pos = intent.getIntExtra("pos",1);

        if(licitatie != null) {
            isEdit = true;
            licitatieDescriereTxt.setText(licitatie.getDescriere());
            valoareTxt.setText(String.valueOf(licitatie.getValoare()));
            String[] dateArray = licitatie.getData().split("-");
            data.updateDate(Integer.parseInt(dateArray[2]),Integer.parseInt(dateArray[1]),Integer.parseInt(dateArray[0]));
            categLicitatie.setSelection( arrayAdapter.getPosition(licitatie.getCategorie()));
            if((radioCard.getText().toString()).equals(licitatie.getMetodaPlata())) {
                radioGroup.check(R.id.cardMethod);
            } else if((radioCash.getText().toString()).equals(licitatie.getMetodaPlata())) {
                radioGroup.check(R.id.cashMethods);
            }

        }
        addLicitatie = findViewById(R.id.addLicitatieBtnForm);
        addLicitatie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                if(licitatieDescriereTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddLicititatie.this, "Descriere invalida", Toast.LENGTH_SHORT).show();
                }
                if(valoareTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddLicititatie.this, "Valoare invalida", Toast.LENGTH_SHORT).show();
                }

                if(isValid) {

                    String descriere = licitatieDescriereTxt.getText().toString();
                    double valoare = Double.parseDouble(valoareTxt.getText().toString());
                    String categ = categLicitatie.getSelectedItem().toString();

                    StringBuilder s = new StringBuilder();
                    s.append(data.getDayOfMonth());
                    s.append("-");
                    s.append(data.getMonth());
                    s.append("-");
                    s.append(data.getYear());
                    String payMethodIs = null;
                    int payMethod = radioGroup.getCheckedRadioButtonId();
                    if(payMethod == radioCard.getId()) {
                         payMethodIs = radioCard.getText().toString();
                    } else if(payMethod == radioCash.getId()) {
                         payMethodIs = radioCash.getText().toString();
                    }

                    if(isEdit) {
                        licitatie.setCategorie(categ);
                        licitatie.setData(s.toString());
                        licitatie.setDescriere(descriere);
                        licitatie.setValoare(valoare);
                        licitatie.setMetodaPlata(payMethodIs);

                        intent = new Intent(AddLicititatie.this,MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("licitatie",licitatie);
                        bundle.putInt("pos",pos);
                        intent.putExtra("bundle",bundle);
                        setResult(RESULT_OK,intent);
                        finish();
                    }else {
                        Licitatie licitatie = new Licitatie(descriere,valoare,categ,s.toString(),payMethodIs);
                        licitaties.add(licitatie);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("licitatii",licitaties);
                        intent = new Intent(AddLicititatie.this,MainActivity.class);
                        intent.putExtra("bundle",bundle);
                        setResult(RESULT_OK,intent);
                        finish();
                    }

                }
            }
        });

    }
}