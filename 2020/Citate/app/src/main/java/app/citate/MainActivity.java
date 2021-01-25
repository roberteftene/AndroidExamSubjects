package app.citate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    ArrayList<Citat> citate = new ArrayList<>();
    ArrayAdapter<Citat> arrayAdapter;
    ListView citateLv;
    Spinner filterCitateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final AppDatabase appDatabase = AppDatabase.getInstance(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Current date: " + new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(System.currentTimeMillis()) + "\n" + "Username: Robert");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok, Close!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    
        filterCitateSpinner = findViewById(R.id.filterCitateSpinner);
        ArrayAdapter<CharSequence> citatType = ArrayAdapter.createFromResource(this,R.array.filterCitate, android.R.layout.simple_spinner_dropdown_item);
        filterCitateSpinner.setAdapter(citatType);

        
        citateLv = findViewById(R.id.citateListView);
        Citat citex = new Citat("Robert","trlalala",20,Category.values()[2]);
        citate.add(citex);
        List<Citat> citatse = new ArrayList<>();
        citatse.addAll(appDatabase.citatDao().getListaCitate());
        for (Citat citat : citatse) {
            citat.setCategory(Category.values()[1]);
        }
        citate.addAll(citatse);
        arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, citate);
        citateLv.setAdapter(arrayAdapter);
        citateLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AddCitat.class);
                intent.putExtra("citat",citate.get(position));
                intent.putExtra("citatPos",position);
                startActivityForResult(intent,3);
            }
        });

        citateLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                appDatabase.citatDao().insertCitat(citate.get(position));
                return true;
            }
        });

        filterCitateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = filterCitateSpinner.getSelectedItem().toString();
                switch (value) {
                    case "T-toate":
                        getCitatByType(4);
                        break;
                    case "F-familie":
                        getCitatByType(0);
                        break;
                    case "S-sport":
                        getCitatByType(1);
                        break;
                    case "F-filozofie":
                         getCitatByType(2);
                        break;
                    case "M-motivationale":
                        getCitatByType(3);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addCitat) {
            Intent intent = new Intent(MainActivity.this,AddCitat.class);
            intent.putParcelableArrayListExtra("citate",citate);
            startActivityForResult(intent,2);
        }
        if(item.getItemId() == R.id.sync) {
            citate.clear();
            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected void onPostExecute(String s) {
                    if(s!= null) {

                        super.onPostExecute(s);
                        try {
                            JSONArray jsonArray = new JSONArray(s);
                            int size = jsonArray.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject citat = jsonArray.getJSONObject(i);
                                String author = citat.getString("autor");
                                String text = citat.getString("text");
                                int nrAprecieri = citat.getInt("numarAprecieri");
                            String categ = citat.getString("categorie");
                                Citat citat1 = new Citat(author, text, nrAprecieri, Category.valueOf(categ));
                                citate.add(citat1);
                            }
                            arrayAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            asyncTask.execute("http://pdm.ase.ro/examen/citate.json");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2) {
            if(resultCode == Activity.RESULT_OK) {
                ArrayList<Citat> arrayList = new ArrayList<>();
                arrayList = data.getParcelableArrayListExtra("citate");
                citate.clear();
                citate.addAll(arrayList);
                arrayAdapter.notifyDataSetChanged();
            }
        }
        if(requestCode == 3) {
            if(resultCode == Activity.RESULT_OK && !data.getBooleanExtra("deleteCitat",false))  {
                int pos = data.getIntExtra("citatPos",1);
                AppDatabase appDatabase = AppDatabase.getInstance(MainActivity.this);
                Citat citatEditat = data.getParcelableExtra("citat");
                citate.get(pos).setAutor(citatEditat.getAutor());
                citate.get(pos).setNrAprecieri(citatEditat.getNrAprecieri());
                citate.get(pos).setText(citatEditat.getText());
                appDatabase.citatDao().updateCitat(citate.get(pos));
                arrayAdapter.notifyDataSetChanged();
            } else  {
                ArrayList<Citat> arrayList = new ArrayList<>();
                AppDatabase appDatabase = AppDatabase.getInstance(MainActivity.this);
                appDatabase.citatDao().deleteCitat(citate.get(data.getIntExtra("citatPos",1)));
                citate.remove(data.getIntExtra("citatPos",1));
                arrayList.addAll(citate);
                citate.clear();
                citate.addAll(arrayList);
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

    protected void getCitatByType(int type) {
        ArrayList<Citat> citats = new ArrayList<>();
        if(type == 4 ) {
            arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, citate);
        } else {
            for (Citat citat : citate) {
                if(citat.getCategory().toString() == Category.values()[type].toString()) {
                    citats.add(citat);
                }
            }
            arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, citats);
        }
       citateLv.setAdapter(arrayAdapter);
    }
}