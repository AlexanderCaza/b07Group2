package com.b07group2.taamapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeAdminFragment extends HomeFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        admin = true;

        View view = super.onCreateView(inflater, container, savedInstanceState);

        Button buttonReport = view.findViewById(R.id.buttonReport);
        Button buttonAdd = view.findViewById(R.id.buttonAdd);
        Button buttonRemove = view.findViewById(R.id.buttonRemove);

        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new BlankFragment());
                //put fragment name here instead of BlankFragment
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new AddFragment());
                //put fragment name here instead of BlankFragment
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boxAdapter.clickCount() != 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Select only one item to be viewed!");
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    // Start ItemDetailedView with item lot number

                    Intent intent = new Intent(getContext(), RemoveItemView.class);
                    ItemCollection item = boxAdapter.getFirstClickedItem();

                    if (item != null) {
                        intent.putExtra("item_lot", item.getLotNumber());
                    } else {
                        // Show error message
                        Log.w("HomeFragment", "No item was clicked");
                        return;
                    }

                    startActivity(intent);
                }
            }
        });

        return view;
    }

}