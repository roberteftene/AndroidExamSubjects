package com.app.servicecardapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.servicecardapp.models.ServiceCard;

import java.util.ArrayList;

public class ListCardsActivity extends AppCompatActivity {

    private ListView cardsLV;
    private ArrayAdapter<ServiceCard> arrayAdapter;
    private Intent intent;
    private ArrayList<ServiceCard> serviceCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cards);
        cardsLV = findViewById(R.id.serviceCardsLV);
        intent = getIntent();
        serviceCards = intent.getParcelableArrayListExtra("cards");

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,serviceCards);
        cardsLV.setAdapter(arrayAdapter);

        cardsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(ListCardsActivity.this, AddCardActivity.class);
                intent.putExtra("card",serviceCards.get(position));
                intent.putExtra("position",position);
                startActivityForResult(intent,4);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 4) {
                int pos = data.getIntExtra("position",1);
                ServiceCard serviceCard = data.getParcelableExtra("card");
                serviceCards.get(pos).setServiceDepartment(serviceCard.getServiceDepartment());
                serviceCards.get(pos).setServiceDate(serviceCard.getServiceDate());
                serviceCards.get(pos).setServiceNumber(serviceCard.getServiceNumber());
                serviceCards.get(pos).setServiceCost(serviceCard.getServiceCost());
                arrayAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(ListCardsActivity.this,MainActivity.class);
        intent.putParcelableArrayListExtra("cards",serviceCards);
        setResult(Activity.RESULT_OK,intent);
        finish();
        super.onBackPressed();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("packages", serviceCards);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceCards = savedInstanceState.getParcelableArrayList("packages");
        arrayAdapter.notifyDataSetChanged();
    }

}