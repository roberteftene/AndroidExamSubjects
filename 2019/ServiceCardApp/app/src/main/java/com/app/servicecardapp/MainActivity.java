package com.app.servicecardapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.servicecardapp.database.AppDatabase;
import com.app.servicecardapp.models.ServiceCard;
import com.app.servicecardapp.utils.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button addCardBtn, listCardsBtn, downloadCardBtn, statisticsBtn, saveCardsBtn;
    private Intent intent;
    private ArrayList<ServiceCard> serviceCards = new ArrayList<>();
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getInstance(this);
        addCardBtn = findViewById(R.id.addServiceBtn);
        listCardsBtn = findViewById(R.id.serviceListBtn);
        downloadCardBtn = findViewById(R.id.downloadCardsBtn);
        statisticsBtn = findViewById(R.id.statisticsBtn);
        saveCardsBtn = findViewById(R.id.saveCardsBtn);

        ArrayList<ServiceCard>serviceCardsFromDb = (ArrayList<ServiceCard>) appDatabase.serviceCardDao().getAll();
        if(serviceCardsFromDb != null) {
            Log.v("ServiceCardsFromDb",serviceCardsFromDb.toString());
        }

        addCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putParcelableArrayListExtra("cards",serviceCards);
                startActivityForResult(intent,2);
            }
        });

        listCardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,ListCardsActivity.class);
                intent.putParcelableArrayListExtra("cards",serviceCards);
                startActivityForResult(intent,3);
            }
        });

        downloadCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected void onPostExecute(String s) {
                        if(s!=null) {

                            try {
                                JSONArray jsonArray = new JSONArray(s);
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    int serviceNo = jsonObject.getInt("serviceNumber");
                                    double ServiceCost = jsonObject.getDouble("serviceCost");
                                    String serviceDepartment = jsonObject.getString("serviceDepartment");
                                    String serviceDate = jsonObject.getString("serviceDate");
                                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(serviceDate);
                                    ServiceCard serviceCard = new ServiceCard(serviceNo,serviceDepartment,ServiceCost,date);
                                    serviceCards.add(serviceCard);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                        super.onPostExecute(s);
                    }
                };
                asyncTask.execute("https://api.mocki.io/v1/c5ff46db");
            }
        });

        saveCardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ServiceCard serviceCard : serviceCards) {
                    appDatabase.serviceCardDao().insertService(serviceCard);
                }
            }
        });

        statisticsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,BarChartActivity.class);
                intent.putParcelableArrayListExtra("cards",serviceCards);
                startActivityForResult(intent,5);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 2) {
                serviceCards = data.getParcelableArrayListExtra("cards");
                Toast.makeText(this, serviceCards.toString(), Toast.LENGTH_SHORT).show();
            }
            if(requestCode == 3) {
                serviceCards.clear();
                serviceCards = data.getParcelableArrayListExtra("cards");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}