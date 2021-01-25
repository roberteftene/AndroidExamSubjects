package app.citate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static app.citate.Category.familie;

public class AddCitat extends AppCompatActivity {

    EditText autorTxt, citatTxt, nrAprecieriTxt;
    Spinner categorySpinner;
    Button addCitatBtn,deleteCitatBtn;
    Category category;
    ArrayList<Citat> arrayListCitate;
    Citat citat;
    boolean isEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_citat);
        autorTxt = findViewById(R.id.autorInput);
        citatTxt = findViewById(R.id.textInput);
        nrAprecieriTxt = findViewById(R.id.nrAprecieriInput);
        categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this,R.layout.support_simple_spinner_dropdown_item,category.values());
        categorySpinner.setAdapter(adapter);

        Intent intent = getIntent();
        arrayListCitate = intent.getParcelableArrayListExtra("citate");
        citat = intent.getParcelableExtra("citat");
        int pos = intent.getIntExtra("citatPos",1);

        if(citat != null) {
            autorTxt.setText(citat.getAutor());
            citatTxt.setText(citat.getText());
            nrAprecieriTxt.setText(String.valueOf(citat.getNrAprecieri()));
            int categoryNo;
            switch (citat.getCategory().toString()) {
                case "familie":
                    categoryNo = 0;
                    break;
                case "sport":
                    categoryNo = 1;
                    break;
                case "filozofie":
                    categoryNo = 2;
                    break;
                case "motivationale":
                    categoryNo = 3;
                    break;
                default:
                    categoryNo = 0;
                    break;
            }
            categorySpinner.setSelection(categoryNo);
            isEdit = true;
        }

        addCitatBtn = findViewById(R.id.addCitatBtn);
        addCitatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean valid = true;
                if(autorTxt.getText().length() < 2) {
                    valid = false;
                    Toast.makeText(AddCitat.this, "Numele autorului prea scurt", Toast.LENGTH_SHORT).show();
                }
                if(citatTxt.getText().length() < 1) {
                    valid = false;
                    Toast.makeText(AddCitat.this, "Citat prea scurt", Toast.LENGTH_SHORT).show();
                }
                if(nrAprecieriTxt.getText().length() == 0 || Integer.parseInt(nrAprecieriTxt.getText().toString()) == 0) {
                    valid = false;
                    Toast.makeText(AddCitat.this, "Numar aprecieri invalid", Toast.LENGTH_SHORT).show();
                }
                if(valid) {
                    int categoryNo;
                    switch (categorySpinner.getSelectedItem().toString()) {
                        case "familie":
                            categoryNo = 0;
                            break;
                        case "sport":
                            categoryNo = 1;
                            break;
                        case "filozofie":
                            categoryNo = 2;
                            break;
                        case "motivationale":
                            categoryNo = 3;
                            break;
                        default:
                            categoryNo = 0;
                            break;
                    }
                    if(isEdit) {
                        citat.setAutor(autorTxt.getText().toString());
                        citat.setText(citatTxt.getText().toString());
                        citat.setNrAprecieri(Integer.parseInt(nrAprecieriTxt.getText().toString()));
                        citat.setCategory(Category.values()[categoryNo]);
                        Intent intent1 = new Intent();
                        intent1.putExtra("citat",citat);
                        intent1.putExtra("citatPos",pos);
                        setResult(Activity.RESULT_OK,intent);
                        finish();
                    } else {
                        Citat citatAdaugat = new Citat(autorTxt.getText().toString(),citatTxt.getText().toString(),Integer.parseInt(nrAprecieriTxt.getText().toString()),category.values()[categoryNo]);
                        Toast.makeText(AddCitat.this, citatAdaugat.toString(), Toast.LENGTH_SHORT).show();
                        arrayListCitate.add(citatAdaugat);
                        Intent intent = new Intent();
                        intent.putParcelableArrayListExtra("citate",arrayListCitate);
                        setResult(Activity.RESULT_OK,intent);
                        finish();
                    }

                }
            }
        });

        deleteCitatBtn = findViewById(R.id.deleteCitat);
        deleteCitatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent.putExtra("citatPos",pos);
                intent.putExtra("deleteCitat",true);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}