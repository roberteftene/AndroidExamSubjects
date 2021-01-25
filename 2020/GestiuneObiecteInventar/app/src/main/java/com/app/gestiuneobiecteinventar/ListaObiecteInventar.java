package com.app.gestiuneobiecteinventar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;

public class ListaObiecteInventar extends AppCompatActivity {

    private ListView inventarLV;
    ArrayList<ObiectInventar> obiectInventars;
    ArrayAdapter<ObiectInventar>arrayAdapter;
    private Button sortObiecte;
    CustomInventar customInventar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_obiecte_inventar);
        inventarLV = findViewById(R.id.obiecteLV);
        Intent i = new Intent();
        i = getIntent();
        obiectInventars = i.getParcelableArrayListExtra("obiecte");

        customInventar = new CustomInventar(this,obiectInventars);

        inventarLV.setAdapter(customInventar);

        inventarLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent iEdit = new Intent(ListaObiecteInventar.this,AdaugareObiect.class);
                iEdit.putExtra("obiect",obiectInventars.get(position));
                iEdit.putExtra("pozObiect",position);
                startActivityForResult(iEdit,3);
            }
        });

        inventarLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListaObiecteInventar.this);
                builder.setTitle("Nume obiect: " + obiectInventars.get(position).getDenumire());
                builder.setMessage("Valoare: " + obiectInventars.get(position).getValoare());
                builder.setMessage("A fost adaugat la: " + obiectInventars.get(position).getDataAdaugarii());
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });

        sortObiecte = findViewById(R.id.sortObiecteBtn);
        sortObiecte.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                obiectInventars.sort(new Comparator<ObiectInventar>() {
                    @Override
                    public int compare(ObiectInventar o1, ObiectInventar o2) {
                        return  o1.getDenumire().compareTo(o2.getDenumire());
                    }
                });
                customInventar.notifyDataSetChanged();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListaObiecteInventar.this,MainActivity.class);
        intent.putParcelableArrayListExtra("obiecteNoi",obiectInventars);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3) {
            if(resultCode == Activity.RESULT_OK) {
                if(data.getParcelableExtra("obiect") != null) {
                    int obiectPos = data.getIntExtra("pozObiect",1);
                    ObiectInventar obiectInventar = data.getParcelableExtra("obiect");
                    obiectInventars.get(obiectPos).setDenumire(obiectInventar.getDenumire());
                    obiectInventars.get(obiectPos).setValoare(obiectInventar.getValoare());
                    obiectInventars.get(obiectPos).setUzura(obiectInventar.getUzura());
                    obiectInventars.get(obiectPos).setNumarInventar(obiectInventar.getNumarInventar());
                    obiectInventars.get(obiectPos).setDataAdaugarii(obiectInventar.getDataAdaugarii());
                    customInventar.notifyDataSetChanged();
                } else {
                    int obiectPos = data.getIntExtra("pozObiect",1);
                    obiectInventars.remove(obiectPos);
                    customInventar.notifyDataSetChanged();

                }
            }
        }
    }
}