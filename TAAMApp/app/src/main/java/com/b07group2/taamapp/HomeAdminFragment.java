package com.b07group2.taamapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeAdminFragment extends HomeFragment {

    private RecyclerView recyclerView;
    private BoxAdapter boxAdapter;
    private int currentPage = 0;
    private static final int PAGE_SIZE = 5; //boxes per page
    private List<String> boxList;
    private List<List<String>> boxListHistory = new ArrayList<>(); //keep history of previous pages

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_admin_home_fragment, container, false);

        Button buttonAdmin = view.findViewById(R.id.buttonReport);

        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new BlankFragment());
                //put fragment name here instead of BlankFragment
            }
        });

        return  super.onCreateView(inflater, container, savedInstanceState, view);
    }
}
