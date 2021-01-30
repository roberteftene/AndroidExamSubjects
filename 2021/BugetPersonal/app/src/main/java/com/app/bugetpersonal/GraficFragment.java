package com.app.bugetpersonal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GraficFragment extends Fragment {

    private static final String ARG_PARAM1 = "objs";
    private LinearLayout linearLayout;
    private Map<String, Integer> source;

    private ArrayList<Depozit> depozits = new ArrayList<>();

    public GraficFragment() {}

    public static GraficFragment newInstance(ArrayList<Depozit> depozits) {
        GraficFragment fragment = new GraficFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1,depozits);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Toast.makeText(getContext(), "Muie", Toast.LENGTH_SHORT).show();
            depozits = getArguments().getParcelableArrayList("objs");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grafic, container, false);
        linearLayout = v.findViewById(R.id.barLayout);
        source = getSource(depozits);
        linearLayout.addView(new BarChartView(getContext(),source));
        return v;

    }

    private Map<String, Integer> getSource(List<Depozit> depozitList){
        if(depozitList == null || depozitList.isEmpty()){
            return new HashMap<>();
        } else {
            Map<String, Integer> results = new HashMap<>();

            for(Depozit depozit: depozitList){
                if(results.containsKey(depozit.getNumeDepozit())){
                    results.put(depozit.getNumeDepozit(), (int) (results.get(depozit.getNumeDepozit()) + depozit.getSumaIntiala()));

                } else {
                    results.put(depozit.getNumeDepozit(), (int) depozit.getSumaIntiala());
                }
            }
            return results;
        }
    }
}