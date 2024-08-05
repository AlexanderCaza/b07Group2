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

public class SearchQueryFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_query_fragment, container, false);

        Button buttonSearch = view.findViewById(R.id.searchButton);
        EditText lotNumberField = view.findViewById(R.id.searchLotNumberField);
        EditText nameField = view.findViewById(R.id.searchNameField);


        Spinner categoriesSpinner = view.findViewById(R.id.searchSpinnerCategory);
        Spinner periodsSpinner = view.findViewById(R.id.searchSpinnerPeriod);

        List<String> categories = new ArrayList<>(Arrays.asList(ItemCollection.getValidCategories()));
        List<String> periods = new ArrayList<>(Arrays.asList(ItemCollection.getValidPeriods()));

        categories.add(0, ""); // Adds empty string at the beginning
        periods.add(0, ""); // Adds empty string at the beginning

        // Adding the dropdown options for Categories Spinner
        ArrayAdapter<String> categoryOptions = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                categories);
        categoryOptions.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(categoryOptions);

        // Adding the dropdown options for Periods Spinner
        ArrayAdapter<String> periodOptions = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                periods);
        periodOptions.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        periodsSpinner.setAdapter(periodOptions);

        categoriesSpinner.setOnItemSelectedListener(this);
        periodsSpinner.setOnItemSelectedListener(this);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SearchQuery query = new SearchQuery(lotNumberField.getText().toString(),
                        nameField.getText().toString(),
                        categoriesSpinner.getSelectedItem().toString(),
                        periodsSpinner.getSelectedItem().toString(),
                        "", "");

                        byte[] data = SerializationUtils.serialize(query);

                        Fragment searchResultsFragment = new SearchResultsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putByteArray("searchresults", data);
                        searchResultsFragment.setArguments(bundle);

                        loadFragment(searchResultsFragment);

                // loadFragment((new SearchResultsFragment(@NonNull LayoutInflater inflater,
                //        @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View view)));
                // put search fragment name here instead of BlankFragment
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(), "Nothing Selected", Toast.LENGTH_SHORT).show();
    }


    protected void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_home_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}