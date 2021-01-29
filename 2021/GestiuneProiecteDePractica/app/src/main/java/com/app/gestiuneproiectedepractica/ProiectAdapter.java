package com.app.gestiuneproiectedepractica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProiectAdapter extends ArrayAdapter<ProiectDePractica> {

    private Context context;
    private ArrayList<ProiectDePractica> proiectDePracticas = new ArrayList<>();

    public ProiectAdapter(@NonNull Context context, ArrayList<ProiectDePractica> proiectDePracticas) {
        super(context, R.layout.proiect_item, proiectDePracticas);
        this.context = context;
        this.proiectDePracticas = proiectDePracticas;
    }

    @Override
    public int getCount() {
        return proiectDePracticas.size();
    }

    @Nullable
    @Override
    public ProiectDePractica getItem(int position) {
        return proiectDePracticas.get(position);
    }

    @Override
    public int getPosition(@Nullable ProiectDePractica item) {
       int pos = 0;
       for(int i = 0; i < proiectDePracticas.size(); i++) {
           if(proiectDePracticas.get(i).equals(item)) {
               pos = i;
           }
       }
       return  pos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProiectDePractica proiectDePractica = getItem(position);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.proiect_item,parent,false);
            viewHolder.profTxtItem = convertView.findViewById(R.id.numeProfItem);
            viewHolder.titluTxtItem = convertView.findViewById(R.id.titluTxtItem);
            viewHolder.progresTxtItem = convertView.findViewById(R.id.gradCompletareItem);
            viewHolder.clasif = convertView.findViewById(R.id.clasificareItem);
            viewHolder.edit = convertView.findViewById(R.id.editareItem);

            convertView.setTag(viewHolder);
        } else  {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.titluTxtItem.setText(proiectDePractica.getTitlu());
        viewHolder.profTxtItem.setText(proiectDePractica.getProfCoordonator());
        viewHolder.progresTxtItem.setText(String.valueOf(proiectDePractica.getNrSaptamani()));

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddProiect.class);
                intent.putExtra("obj",proiectDePractica);
                intent.putExtra("pos",position);
                ((Activity) context).startActivityForResult(intent, 3);
            }
        });

        View finalConvertView = convertView;
        viewHolder.clasif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(proiectDePractica.getNrSaptamani() > 50) {
                    finalConvertView.setBackgroundColor(Color.GREEN);
                }
                if(proiectDePractica.getNrSaptamani() == 50) {
                    finalConvertView.setBackgroundColor(Color.WHITE);
                }
                if(proiectDePractica.getNrSaptamani() < 50) {
                    finalConvertView.setBackgroundColor(Color.RED);
                }
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView titluTxtItem, profTxtItem, progresTxtItem;
        Button clasif,edit;
    }

}
