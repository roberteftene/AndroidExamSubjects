package com.app.magazin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddProduct extends AppCompatActivity {

    private Intent intent;
    private ArrayList<Produs> produses;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private EditText numeProdusTxt, pretProdusTxt;
    private Spinner categProdus;
    private CheckBox isOfertaCheck;
    private RatingBar ratingProdus;
    private Button addProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

     numeProdusTxt = findViewById(R.id.numeProdusTxt);
     pretProdusTxt = findViewById(R.id.pretTxt);
     categProdus = findViewById(R.id.categProdusSpinner);
     arrayAdapter = ArrayAdapter.createFromResource(this,R.array.categProdus, android.R.layout.simple_spinner_dropdown_item);
     categProdus.setAdapter(arrayAdapter);
     isOfertaCheck = findViewById(R.id.checkOferta);
     ratingProdus = findViewById(R.id.ratingProdus);
     addProduct = findViewById(R.id.addProductBtnForm);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                if(numeProdusTxt.getText().toString().length() <  2) {
                    isValid = false;
                    Toast.makeText(AddProduct.this, "Nume invalud", Toast.LENGTH_SHORT).show();
                }
                if(pretProdusTxt.getText().toString().length() < 2) {
                    isValid = false;
                    Toast.makeText(AddProduct.this, "Pret invalid", Toast.LENGTH_SHORT).show();
                }

                if(isValid) {

                    String nume = numeProdusTxt.getText().toString();
                    double pret = Double.parseDouble(pretProdusTxt.getText().toString());
                    String categ = categProdus.getSelectedItem().toString();
                    boolean isOferta = isOfertaCheck.isChecked();
                    int rating = ratingProdus.getNumStars();

                    Produs produs = new Produs(nume,isOferta,categ,rating,pret);
                    intent = new Intent(AddProduct.this,MainActivity.class);
                    intent.putExtra("obj",produs);
                    setResult(RESULT_OK,intent);
                    finish();

                }
            }
        });


    }
}