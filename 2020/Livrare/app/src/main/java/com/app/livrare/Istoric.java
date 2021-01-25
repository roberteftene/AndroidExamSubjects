package com.app.livrare;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.livrare.models.Livrare;

import java.util.ArrayList;
import java.util.Comparator;

public class Istoric extends AppCompatActivity {

    private ListView livrariLv;
    ArrayList<Livrare> livrares = new ArrayList<>();
    ArrayAdapter<Livrare> livrareArrayAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istoric);
        livrariLv = findViewById(R.id.livrariLV);
        Intent intent = new Intent();
        intent = getIntent();
        livrares = intent.getParcelableArrayListExtra("livrari");
        livrares.sort(new Comparator<Livrare>() {
            @Override
            public int compare(Livrare o1, Livrare o2) {
                return -o1.getData().compareTo(o2.getData());
            }
        });

        livrareArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,livrares);
        livrariLv.setAdapter(livrareArrayAdapter);

        livrariLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(Istoric.this,AddLivrare.class);
                intent1.putExtra("livrare",livrares.get(position));
                intent1.putExtra("livrarePos",position);
                startActivityForResult(intent1,3);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3) {
            if(resultCode == RESULT_OK) {
                if(data.getParcelableExtra("livrare")!=null) {

                int pos = data.getIntExtra("livrarePos",1);
                Livrare livrare = data.getParcelableExtra("livrare");
                livrares.get(pos).setDestinatar(livrare.getDestinatar());
                livrares.get(pos).setAdresa(livrare.getAdresa());
                livrares.get(pos).setData(livrare.getData());
                livrares.get(pos).setTip(livrare.getTip());
                livrareArrayAdapter.notifyDataSetChanged();

                } else {
                    int pos = data.getIntExtra("livrarePos",1);
                    livrares.remove(pos);
                    livrareArrayAdapter.notifyDataSetChanged();
                }

            }
        }
    }
}