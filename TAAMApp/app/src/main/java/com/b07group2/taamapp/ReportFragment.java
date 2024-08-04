package com.b07group2.taamapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class ReportFragment extends Fragment {

    private static final String[] validCategories = {"Jade", "Paintings", "Calligraphy", "Rubbings",
            "Bronze", "Brass and Copper", "Gold and Silvers", "Lacquer", "Enamels"};
    private static final String[] validPeriods = {"Xia", "Shang", "Zhou", "Chuanqiu", "Zhanggou", "Qin",
            "hang", "Shangou", "Ji", "South and North", "Shui", "Tang", "Liao", "Song",
            "Jin", "Yuan", "Ming", "Qing", "Modern"};

    private AutoCompleteTextView autoCC;
    private ArrayAdapter<String> adapterC;

    private AutoCompleteTextView autoCCDP;
    private ArrayAdapter<String> adapterCDP;


    private AutoCompleteTextView autoCP;
    private ArrayAdapter<String> adapterP;

    private AutoCompleteTextView autoCPDP;
    private ArrayAdapter<String> adapterPDP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report, container, false);

        autoCC = view.findViewById(R.id.autoCC);
        adapterC = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_item, validCategories);
        autoCC.setAdapter(adapterC);
        autoCC.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity().getApplicationContext(), "Category: " + item, Toast.LENGTH_SHORT).show();

            }
        });

        autoCCDP = view.findViewById(R.id.autoCCDP);
        adapterCDP = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_item, validCategories);
        autoCCDP.setAdapter(adapterCDP);
        autoCCDP.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity().getApplicationContext(), "Category: " + item, Toast.LENGTH_SHORT).show();

            }
        });

        autoCP = view.findViewById(R.id.autoCP);
        adapterP = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_item, validPeriods);
        autoCP.setAdapter(adapterP);
        autoCP.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity().getApplicationContext(), "Period: " + item, Toast.LENGTH_SHORT).show();

            }
        });

        autoCPDP = view.findViewById(R.id.autoCPDP);
        adapterPDP = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.list_item, validPeriods);
        autoCPDP.setAdapter(adapterPDP);
        autoCPDP.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity().getApplicationContext(), "Period: " + item, Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }
}