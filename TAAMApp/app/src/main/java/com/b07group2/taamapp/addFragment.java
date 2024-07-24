package com.b07group2.taamapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Array;

public class addFragment extends Fragment {

    private TextInputLayout lotInput;
    private TextInputLayout nameInput;
    private TextInputLayout categoryInput;
    private TextInputLayout periodInput;
    private EditText descriptionInput;
    private static String[] validCategories = {"Jade", "Paintings", "Calligraphy", "Rubbings",
            "Bronze", "Brass and Copper", "Gold and Silvers", "Lacquer", "Enamels"};
    private static String[] validPeriods = {"Xia", "Shang", "Zhou", "Chuanqiu", "Zhanggou", "Qin",
            "hang", "Shangou", "Ji", "South and North", "Shui", "Tang", "Liao", "Song",
            "Jin", "Yuan", "Ming", "Qing", "Modern"};

    private AutoCompleteTextView autoCompleteCategories;
    private ArrayAdapter<String> adapterCategories;

    private AutoCompleteTextView autoCompletePeriods;
    private ArrayAdapter<String> adapterPeriods;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        return view;
//        autoCompleteCategories = view.findViewById(R.id.autoCompleteCategories);
//        adapterCategories = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item, validCategories);
//        autoCompleteCategories.setAdapter(adapterCategories);
//        autoCompleteCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String item = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(getActivity().getApplicationContext(), "Category: " + item, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        autoCompletePeriods = view.findViewById(R.id.autoCompletePeriod);
//        adapterPeriods = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item, validPeriods);
//        autoCompletePeriods.setAdapter(adapterPeriods);
//        autoCompletePeriods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String item = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(getActivity().getApplicationContext(), "Period: " + item, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        lotInput = view.findViewById(R.id.lotInput);
//        nameInput = view.findViewById(R.id.nameInput);
//        categoryInput = view.findViewById(R.id.categoryInput);
//        periodInput = view.findViewById(R.id.periodInput);
//        descriptionInput = view.findViewById(R.id.descriptionInput);
//
//        //add event listener for submit Button
//        Button submitButton = view.findViewById(R.id.submitButton);
//
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean verifiedInput = true;
//                verifiedInput = verifiedInput && verifyLotInput();
//                verifiedInput = verifiedInput && verifyNameInput();
//                verifiedInput = verifiedInput && verifyCategoryInput();
//                verifiedInput = verifiedInput && verifyPeriodInput();
//                Log.d("Verified Input", String.valueOf(verifiedInput));
//
//            }
//        });
//        return view;
    }
    private boolean verifyLotInput() {
        if(lotInput.getEditText().getText().length() > 0) {
            lotInput.setError(null);
            return true;
        }
        lotInput.setError("Required*");
        return false;
    }
    private boolean verifyNameInput() {
        if(nameInput.getEditText().getText().length() > 0) {
            nameInput.setError(null);
            return true;
        }
        nameInput.setError("Required*");

        return false;
    }
    private boolean verifyCategoryInput() {
        if(categoryInput.getEditText().getText().length() > 0) {
            categoryInput.setError(null);
            return true;
        }
        categoryInput.setError("Required*");
        return false;
    }
    private boolean verifyPeriodInput() {
        if(periodInput.getEditText().getText().length() > 0) {
            periodInput.setError(null);
            return true;
        }
        periodInput.setError("Required*");
        return false;
    }
}