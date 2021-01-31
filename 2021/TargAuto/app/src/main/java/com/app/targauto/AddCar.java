package com.app.targauto;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddCar extends AppCompatActivity {

    private Intent intent;
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayAdapter<CharSequence> arrayAdapter;
    private EditText marcaTxt, nrTxt, pretTxt;
    private DatePicker dataAdaugare;
    private TimePicker oraAdaugare;
    private Spinner categMasina;
    private RadioGroup valutaGroup;
    private RadioButton euroValuta, ronValuta;
    private CheckBox isNegociabil;
    private SeekBar stareSeekBar;
    private RatingBar ratingCar;
    private Button addCarBtnForm;
    private int stare;
    private Car car;
    private int pos;
    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        marcaTxt = findViewById(R.id.marcaTxt);
        nrTxt = findViewById(R.id.numarTxt);
        pretTxt = findViewById(R.id.pretTxt);
        dataAdaugare = findViewById(R.id.dataAdaugare);
        oraAdaugare = findViewById(R.id.oraAdaugare);
        categMasina = findViewById(R.id.categMasinaSpinner);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.categMasina, android.R.layout.simple_spinner_dropdown_item);
        categMasina.setAdapter(arrayAdapter);
        valutaGroup = findViewById(R.id.valutaGroup);
        euroValuta = findViewById(R.id.valutaEuroBtn);
        ronValuta = findViewById(R.id.valutaRonBtn);
        isNegociabil = findViewById(R.id.checkNegociabil);
        stareSeekBar = findViewById(R.id.stareMasinaSeekBar);
        stare = stareSeekBar.getProgress();
        stareSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                stare = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ratingCar = findViewById(R.id.ratingMasina);
        addCarBtnForm = findViewById(R.id.addCarBtnForm);

        intent = getIntent();
        cars = intent.getParcelableArrayListExtra("objs");
        car = intent.getParcelableExtra("obj");
        pos = intent.getIntExtra("pos",1);
        if(car != null) {
            isEdit = true;
            marcaTxt.setText(car.getMarca());
            pretTxt.setText(String.valueOf(car.getPret()));
        }

        addCarBtnForm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                boolean isValid = true;

                if(marcaTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddCar.this, "Marca invalida", Toast.LENGTH_SHORT).show();
                }
                if(nrTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddCar.this, "Numar invalid", Toast.LENGTH_SHORT).show();
                }
                if(pretTxt.getText().toString().length() < 1) {
                    isValid = false;
                    Toast.makeText(AddCar.this, "Pret invalid", Toast.LENGTH_SHORT).show();
                }

                if(isValid) {

                    String marca = marcaTxt.getText().toString();
                    String nr = nrTxt.getText().toString();
                    double pret = Double.parseDouble(pretTxt.getText().toString());
                    boolean negociabil = isNegociabil.isSelected();

                    StringBuilder s = new StringBuilder();
                    s.append(dataAdaugare.getDayOfMonth());
                    s.append("-");
                    s.append(dataAdaugare.getMonth());
                    s.append("-");
                    s.append(dataAdaugare.getYear());
                    StringBuilder time = new StringBuilder();
                    time.append(oraAdaugare.getHour());
                    time.append(":");
                    time.append(oraAdaugare.getMinute());

                    String categ = categMasina.getSelectedItem().toString();
                    int rating = ratingCar.getNumStars();
                    int valutaSelectedId = valutaGroup.getCheckedRadioButtonId();
                    String valuta = null;
                    if(valutaSelectedId == euroValuta.getId()) {
                        valuta = euroValuta.getText().toString();
                    } else if(valutaSelectedId == ronValuta.getId()) {
                        valuta = ronValuta.getText().toString();
                    }

                    if(isEdit) {

                        car.setCategorieMasina(categ);
                        car.setNumar(nr);
                        car.setDataAdaugare(s.toString());
                        car.setOraAdaugare(time.toString());
                        car.setMarca(marca);
                        car.setPret(pret);
                        car.setPretNegociabil(negociabil);
                        car.setRating(rating);
                        car.setValutaAcceptata(valuta);
                        car.setStareMasina(stare);
                        intent = new Intent(AddCar.this,MainActivity.class);
                        intent.putExtra("obj",car);
                        intent.putExtra("pos",pos);
                        setResult(RESULT_OK,intent);
                        finish();

                    } else {

                        Car car = new Car(marca, nr, rating, stare, categ, pret, negociabil, valuta, s.toString(), time.toString());
                        cars.add(car);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("objs", cars);
                        intent = new Intent(AddCar.this, MainActivity.class);
                        intent.putExtra("bundle", bundle);
                        setResult(RESULT_OK, intent);
                        finish();

                    }

                }

            }
        });

    }
}