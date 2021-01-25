package com.app.livrare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.livrare.models.Livrare;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView introdTxt;
    private Button addLivareBtn, istoricBtn, importBTn, despreBtn;
    ArrayList<Livrare> livrares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        introdTxt = findViewById(R.id.introdTxt);
        addLivareBtn = findViewById(R.id.addLivareBtn);
        istoricBtn = findViewById(R.id.istoricBtn);
        despreBtn = findViewById(R.id.despreBtn);
        importBTn = findViewById(R.id.importBtn);
        livrares = new ArrayList<>();
        introdTxt.setText("Aplicatia a fost accesata la ora: " + new SimpleDateFormat("hh:mm").format(System.currentTimeMillis()));

        despreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Aplicatie realizata de: Robert Eftene", Toast.LENGTH_SHORT).show();
            }
        });

        addLivareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddLivrare.class);
                intent.putParcelableArrayListExtra("livrari",livrares);
                startActivityForResult(intent,2);
            }
        });

        istoricBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Istoric.class);
                intent.putParcelableArrayListExtra("livrari",livrares);
                startActivity(intent);
            }
        });

        importBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        if(s!=null) {

                            try {
                            JSONArray jsonArray = new JSONArray(s);
                            int size = jsonArray.length();
                            for(int i = 0; i < size; i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String tip = jsonObject.getString("tip");
                                String data = jsonObject.getString("data");
                                String adresa = jsonObject.getString("adresa");
                                double value = jsonObject.getDouble("valoare");
                                String destinatar = jsonObject.getString("destinatar");
                                Date dateFormat = new SimpleDateFormat("dd-MM-yyyy").parse(data);
                                Livrare livrare = new Livrare(destinatar,adresa,dateFormat,value,tip);
                                livrares.add(livrare);
                            }
                            }catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                    }
                };
                asyncTask.execute("https://api.mocki.io/v1/ef3b8a9c");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2) {
            if(resultCode == RESULT_OK) {
                livrares = data.getParcelableArrayListExtra("livrari");
                Toast.makeText(this, livrares.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}