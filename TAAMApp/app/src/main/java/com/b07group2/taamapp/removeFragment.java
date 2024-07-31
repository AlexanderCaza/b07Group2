package com.b07group2.taamapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class removeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remove, container, false);
        populateInfo(view);

        Button buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_home_container, new HomeUserFragment());
                transaction.addToBackStack(null); // Add the transaction to the back stack
                transaction.commit();
            }
        });

        return view;
    }

    private void populateInfo(View view) {
        TextView lotView = view.findViewById(R.id.lotText);
        TextView nameView = view.findViewById(R.id.nameText);
        TextView categoryView = view.findViewById(R.id.categoryText);
        TextView periodView = view.findViewById(R.id.periodText);
        TextView descriptionView = view.findViewById(R.id.descriptionText);

        /*
        Firebase stuff here
         */

        //temp values
        lotView.setText("Temp lot num");
        nameView.setText("Temp name");
        categoryView.setText("Temp category");
        periodView.setText("Temp period");
        descriptionView.setText("Temp description");

    }
}