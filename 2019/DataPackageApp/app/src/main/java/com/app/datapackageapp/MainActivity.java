package com.app.datapackageapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.datapackageapp.database.AppDatabase;
import com.app.datapackageapp.models.DataPackage;
import com.app.datapackageapp.utils.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private Intent i;
    private ArrayList<DataPackage> dataPackages = new ArrayList<>();
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getInstance(this);
        ArrayList<DataPackage> packagesFromDb = (ArrayList<DataPackage>) appDatabase.dataPackageDao().getPackagesFromDb();
        for (DataPackage dataPackage : packagesFromDb) {
            Log.v("PackageFromDB",dataPackage.toString());
        }
        Log.v("Curr date", String.valueOf(new Date()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.sendPackage) {
            i = new Intent(MainActivity.this, SendPackage.class);
            i.putParcelableArrayListExtra("packages",dataPackages);
            startActivityForResult(i,2);

        }
        if(item.getItemId() == R.id.packageList) {
            i = new Intent(MainActivity.this,PackageList.class);
            i.putParcelableArrayListExtra("packages",dataPackages);
            startActivityForResult(i,3);
        }
        if(item.getItemId() == R.id.backup) {
            for (DataPackage dataPackage : dataPackages) {
                appDatabase.dataPackageDao().insertPackageToDb(dataPackage);
            }
        }
        if(item.getItemId() == R.id.update) {
            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected void onPostExecute(String s) {
                    if(s!=null) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("packages");
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                int id = jsonObject1.getInt("packageId");
                                String packageType = jsonObject1.getString("packageType");
                                Double latitude = jsonObject1.getDouble("latitude");
                                Double longitude = jsonObject1.getDouble("longitude");
                                String date = jsonObject1.getString("timestamp");
                                Date timestamp = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date);
                                Log.v("time",timestamp.toString());
                                DataPackage dp = new DataPackage(id,packageType,latitude,longitude,timestamp);
                                dataPackages.add(dp);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    super.onPostExecute(s);
                }
            };
            asyncTask.execute("http://pdm.ase.ro/examen/packages.json.txt");
        }
        if(item.getItemId() == R.id.sendingRate) {

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 2) {
                dataPackages = data.getParcelableArrayListExtra("packages");
                Toast.makeText(this, dataPackages.toString(), Toast.LENGTH_SHORT).show();
            }
            if(requestCode == 3) {
                dataPackages.clear();
                dataPackages = data.getParcelableArrayListExtra("packages");
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}