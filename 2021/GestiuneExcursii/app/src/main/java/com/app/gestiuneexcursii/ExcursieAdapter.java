package com.app.gestiuneexcursii;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ExcursieAdapter extends ArrayAdapter<Excursie> {

    Context context;
    ArrayList<Excursie> dataset;


    public ExcursieAdapter(@NonNull Context context, ArrayList<Excursie> dataset) {
        super(context, R.layout.excursie_item, dataset);
        this.context = context;
        this.dataset = dataset;
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    @Nullable
    @Override
    public Excursie getItem(int position) {
        return dataset.get(position);
    }

    @Override
    public int getPosition(@Nullable Excursie item) {
        int pos = 0;
        for(int i = 0; i < dataset.size();i++){
            if(dataset.get(pos).equals(item)) {
                pos = i;
            }
        }
        return pos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Excursie excursie = getItem(position);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.excursie_item,parent,false);
            viewHolder.cheltuialaTxtItem = convertView.findViewById(R.id.cheltuialaTxtItem);
            viewHolder.locatieTxtItem = convertView.findViewById(R.id.locatieTxtItem);
            viewHolder.visitedTxtItem = convertView.findViewById(R.id.isVisitedTxtItem);
            viewHolder.aSwitch = convertView.findViewById(R.id.switchItem);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.cheltuialaTxtItem.setText(String.valueOf(excursie.getCheltuiala()));
        viewHolder.locatieTxtItem.setText(excursie.getLocatie());
        if(excursie.isVisited()) {
            viewHolder.visitedTxtItem.setText("A fost vizitat");
        } else {
            viewHolder.visitedTxtItem.setText("Nu fost vizitat");
        }

        ViewHolder finalViewHolder = viewHolder;
        viewHolder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                double temp = excursie.getCheltuiala();
                if(isChecked) {
                    excursie.setCheltuiala(excursie.getCheltuiala() * 0.5);
                    finalViewHolder.cheltuialaTxtItem.setText(String.valueOf(excursie.getCheltuiala()));
                    excursie.setDelete(true);
                } else {
                    excursie.setCheltuiala(temp * 2);
                    finalViewHolder.cheltuialaTxtItem.setText(String.valueOf(excursie.getCheltuiala()));
                    excursie.setDelete(false);

                }
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView locatieTxtItem, cheltuialaTxtItem,visitedTxtItem;
        Switch aSwitch;
    }
}
