package com.b07group2.taamapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeUserFragment extends HomeFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        admin = false;

        View view = super.onCreateView(inflater, container, savedInstanceState);

        Button buttonAdmin = view.findViewById(R.id.buttonReport);

        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Switching to fragment","home admin");
                loadFragment(new HomeAdminFragment());
                //put fragment name here instead of BlankFragment
            }
        });

        return view;
    }
}
