package com.app.gestiuneobiecteinventar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomInventar extends ArrayAdapter<ObiectInventar> {
    private Context context;
    ArrayList<ObiectInventar> obiectInventars;

    public CustomInventar(@NonNull Context context, ArrayList<ObiectInventar> obiectInventars) {
        super(context, R.layout.inventar_item, obiectInventars);
        this.context = context;
        this.obiectInventars = obiectInventars;
    }

    @Nullable
    @Override
    public ObiectInventar getItem(int position) {
        return obiectInventars.get(position);
    }

    @Override
    public int getCount() {
        return obiectInventars.size();
    }

    @Override
    public int getPosition(@Nullable ObiectInventar item) {
        int pos = 0;
        for (int i = 0; i < obiectInventars.size(); i++) {
            if(obiectInventars.get(i).equals(item)) {
                pos = i;
            }
        }
        return pos;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ObiectInventar obiectInventar =  getItem(position);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.inventar_item,parent,false);
            viewHolder.denumireTxt = convertView.findViewById(R.id.denumireTxtItem);
            viewHolder.uzuraTxt = convertView.findViewById(R.id.uzuraItem);
            viewHolder.valoareTxt = convertView.findViewById(R.id.valoareTxtItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.valoareTxt.setText(String.valueOf(obiectInventar.getValoare()));
        viewHolder.denumireTxt.setText(obiectInventar.getDenumire());
        viewHolder.uzuraTxt.setText(obiectInventar.getUzura());

        return convertView;
    }

     private static class ViewHolder {
        TextView denumireTxt, uzuraTxt, valoareTxt;
    }

}
