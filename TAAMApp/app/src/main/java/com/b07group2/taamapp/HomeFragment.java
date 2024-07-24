package com.b07group2.taamapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BoxAdapter boxAdapter;
    private int currentPage = 0;
    private static final int PAGE_SIZE = 5; //boxes per page
    private List<String> boxList;
    private List<List<String>> boxListHistory = new ArrayList<>(); //keep history of previous pages

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        Button buttonView = view.findViewById(R.id.buttonView);
        Button buttonSearch = view.findViewById(R.id.buttonSearch);
        Button buttonAdmin = view.findViewById(R.id.buttonAdmin);
        Button buttonNext = view.findViewById(R.id.buttonNext);

        boxList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            boxList.add("Box " + i);
        }

        int maxPage = (int) Math.ceil((double) boxList.size() / PAGE_SIZE);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        boxAdapter = new BoxAdapter(boxList);
        recyclerView.setAdapter(boxAdapter);



        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //placeholder until i figure out the whole seeing if box is clicked thing
                if (boxAdapter.checkClick()) {
                    loadFragment(new BlankFragment());
                }
                //put fragment name here instead of BlankFragment
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new removeFragment());
                //put fragment name here instead of BlankFragment
            }
        });

        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadFragment(new BlankFragment());

                System.out.println("adding add fragment");
                loadFragment(new addFragment());
                //put fragment name here instead of BlankFragment
            }
        });

        buttonNext.setOnClickListener(v -> {
            if (currentPage < maxPage - 1) {
                currentPage++;
                boxListHistory.add(new ArrayList<>(boxAdapter.getBoxList())); // Save current box list
                List<String> nextPageItems = getCurrentPageItems();
                boxAdapter.setBoxList(nextPageItems);
                boxAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(0);
            }
        });

        boxListHistory.add(new ArrayList<>(boxList)); // Initial box list history

        return view;
    }

    public void goToPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            List<String> previousPageItems = getCurrentPageItems();
            boxAdapter.setBoxList(previousPageItems);
            boxAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(0);
        }
    }

    public boolean canGoBack() {
        return currentPage > 0;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_home_container, fragment);
        transaction.addToBackStack(null); // Add the transaction to the back stack
        transaction.commit();
    }

    private List<String> getCurrentPageItems() {
        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, boxList.size());
        return new ArrayList<>(boxList.subList(start, end));
    }
}
