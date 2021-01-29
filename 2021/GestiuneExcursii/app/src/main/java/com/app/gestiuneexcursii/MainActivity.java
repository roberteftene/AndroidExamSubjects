package com.app.gestiuneexcursii;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ListView listViewDefault;
    private Intent intent;
    private Button buttonAdd;
    public ArrayList<Excursie> excursies = new ArrayList<>();
    public ArrayList<Excursie> excursiesTemp = new ArrayList<>();
    private ArrayAdapter<Excursie> arrayAdapter;
    private ExcursieAdapter excursieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.excursiiLV);
        listViewDefault= findViewById(R.id.excursiiLVDefault);
        excursieAdapter = new ExcursieAdapter(this,excursies);
        listView.setAdapter(excursieAdapter);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,excursiesTemp);
        listViewDefault.setAdapter(arrayAdapter);


        listViewDefault.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                excursies.add(excursiesTemp.get(position));
                excursieAdapter.notifyDataSetChanged();
                return false;
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                excursiesTemp.add(excursies.get(position));
                arrayAdapter.notifyDataSetChanged();
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(excursies.get(position).isDelete()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Are you sure?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            excursies.remove(position);
                            excursieAdapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        buttonAdd = findViewById(R.id.addExcursieBtn);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,AddExcursie.class);
                intent.putParcelableArrayListExtra("objs",excursies);
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 2) {
                ArrayList<Excursie> temp = new ArrayList<>();
                Bundle bundle = new Bundle();
                bundle = data.getBundleExtra("bundle");
                temp = bundle.getParcelableArrayList("objs");
                excursies.clear();
                excursies.addAll(temp);
                excursieAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}