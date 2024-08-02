package com.b07group2.taamapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.SerializationUtils;

public abstract class SearchQueryFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState, View view) {
        Button buttonSearch = view.findViewById(R.id.searchButton);
        EditText lotNumberField = view.findViewById(R.id.searchLotNumberField);
        EditText nameField = view.findViewById(R.id.searchNameField);
        EditText descriptionField = view.findViewById(R.id.item_description);


        Spinner categoriesSpinner = view.findViewById(R.id.searchSpinnerCategory);
        categoriesSpinner.setOnItemSelectedListener(this);
        Spinner periodsSpinner = view.findViewById(R.id.searchSpinnerPeriod);
        periodsSpinner.setOnItemSelectedListener(this);

        // Adding the dropdown options for Categories Spinner
        ArrayAdapter<String> categoryOptions = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                ItemCollection.getValidCategories());
        categoryOptions.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(categoryOptions);

        // Adding the dropdown options for Periods Spinner
        ArrayAdapter<String> periodOptions = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                ItemCollection.getValidPeriods());
        categoryOptions.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(periodOptions);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SearchQuery query = new SearchQuery(lotNumberField.getText().toString(),
                        nameField.getText().toString(),
                        categoriesSpinner.getSelectedItem().toString(),
                        periodsSpinner.getSelectedItem().toString(),
                        descriptionField.getText().toString(), "");
                        byte[] data = SerializationUtils.serialize(query);
                        savedInstanceState.putByteArray("searchresults", data);

                // loadFragment((new SearchResultsFragment(@NonNull LayoutInflater inflater,
                //        @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View view)));
                // put search fragment name here instead of BlankFragment
            }
        });
        return view;
    }

    protected void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.view, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}