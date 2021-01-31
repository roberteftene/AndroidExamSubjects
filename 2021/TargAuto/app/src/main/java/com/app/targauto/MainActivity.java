package com.app.targauto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Car> cars = new ArrayList<>();
    private Intent intent;
    private Button addCarBtn;
    private ListViewFragment listViewFragment = new ListViewFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCarBtn = findViewById(R.id.addCarBtn);
        addCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,AddCar.class);
                intent.putParcelableArrayListExtra("objs",cars);
                startActivityForResult(intent,2);

            }
        });

        if(savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("objs",cars);
            listViewFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.lvFragment,listViewFragment).commit();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 2) {
                Bundle bundle = new Bundle();
                bundle = data.getBundleExtra("bundle");
                ArrayList<Car> temp = new ArrayList<>();
                temp = bundle.getParcelableArrayList("objs");
                cars.clear();
                cars.addAll(temp);
                listViewFragment = ListViewFragment.newInstance(cars);
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("objs",cars);
                listViewFragment.setArguments(bundle1);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.lvFragment,listViewFragment)
                        .commitAllowingStateLoss();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("objs", cars);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cars = savedInstanceState.getParcelableArrayList("objs");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(MainActivity.this);
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.saveToSharedPrefs) {
            SharedPreferences sharedPref = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            for (Car car : cars) {
                editor.putString(car.getNumar(),car.toString());
            }
            editor.apply();
        }
        if(item.getItemId() == R.id.barChart) {
            intent = new Intent(MainActivity.this,BarChart.class);
            intent.putParcelableArrayListExtra("objs",cars);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.pieChart) {
            intent = new Intent(MainActivity.this,PieChart.class);
            intent.putParcelableArrayListExtra("objs",cars);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.saveTxt) {
            File file = new File(MainActivity.this.getFilesDir(),"text");
            if(!file.exists()) {
                file.mkdir();
            }
            try {
                File deplasariReport = new File(file,"items.txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(deplasariReport));
                for (Car car : cars) {
                        writer.write(car.toString());
                        writer.write("\n");
                }
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}