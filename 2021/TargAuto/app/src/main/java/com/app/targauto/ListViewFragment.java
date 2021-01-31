package com.app.targauto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListViewFragment extends Fragment {

    private static final String ARG_PARAM1 = "objs";
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayAdapter<Car> carArrayAdapter;
    private ListView carsLV;
    private Intent intent;

    public ListViewFragment() { }

    public static ListViewFragment newInstance(ArrayList<Car> cars) {
        ListViewFragment fragment = new ListViewFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, cars);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cars = getArguments().getParcelableArrayList("objs");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_list_view, container, false);
        carsLV = view.findViewById(R.id.carsLV);
        carArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,cars);
        carsLV.setAdapter(carArrayAdapter);

        carsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getContext(),AddCar.class);
                intent.putExtra("obj",cars.get(position));
                intent.putExtra("pos",position);
                startActivityForResult(intent,3);
            }
        });

        carsLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cars.remove(position);
                        carArrayAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setCancelable(true);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == getActivity().RESULT_OK) {
            if(requestCode == 3) {
                Car car = data.getParcelableExtra("obj");
                int pos = data.getIntExtra("pos",1);
                cars.get(pos).setStareMasina(car.getStareMasina());
                cars.get(pos).setValutaAcceptata(car.getValutaAcceptata());
                cars.get(pos).setRating(car.getRating());
                cars.get(pos).setPretNegociabil(car.isPretNegociabil());
                cars.get(pos).setCategorieMasina(car.getCategorieMasina());
                cars.get(pos).setPret(car.getPret());
                cars.get(pos).setOraAdaugare(car.getOraAdaugare());
                cars.get(pos).setDataAdaugare(car.getDataAdaugare());
                cars.get(pos).setMarca(car.getMarca());
                cars.get(pos).setNumar(car.getNumar());
                carArrayAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}