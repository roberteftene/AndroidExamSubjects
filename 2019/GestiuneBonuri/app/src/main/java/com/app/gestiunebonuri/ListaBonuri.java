package com.app.gestiunebonuri;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaBonuri extends AppCompatActivity {

    private Intent intent;
    private ArrayAdapter<Bon> bonArrayAdapter;
    private ArrayList<Bon> bons = new ArrayList<>();
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_bonuri);
        listView = findViewById(R.id.bonuriLV);
        intent = getIntent();
        bons = intent.getParcelableArrayListExtra("bonuri");
        bonArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,bons);
        listView.setAdapter(bonArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(ListaBonuri.this,AddBon.class);
                intent.putExtra("bon",bons.get(position));
                intent.putExtra("poz",position);
                startActivityForResult(intent,4);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 4) {
                Bon bon = data.getParcelableExtra("bon");
                int pos = data.getIntExtra("poz",1);
                bons.get(pos).setNumar(bon.getNumar());
                bons.get(pos).setServiciu(bon.getServiciu());
                bons.get(pos).setData(bon.getData());
                bons.get(pos).setGhiseu(bon.getGhiseu());
                bonArrayAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(ListaBonuri.this,MainActivity.class);
        intent.putParcelableArrayListExtra("bonuri",bons);
        setResult(RESULT_OK,intent);
        finish();
        super.onBackPressed();
    }
}