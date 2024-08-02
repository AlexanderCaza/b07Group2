package com.b07group2.taamapp;

import static org.apache.commons.lang3.SerializationUtils.deserialize;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class SearchResultsFragment extends Fragment {

        private RecyclerView recyclerView;
        private BoxAdapter boxAdapter;
        private int currentPage = 0;
        private static final int PAGE_SIZE = 4; //boxes per page
        private List<String> boxList;
        private List<List<String>> boxListHistory = new ArrayList<>(); //keep history of previous pages

        @Nullable

        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View view) {

            Button buttonView = view.findViewById(R.id.buttonView);
            Button buttonNext = view.findViewById(R.id.buttonNext);
            SearchQuery query = deserialize(Objects.requireNonNull(savedInstanceState
                    .getByteArray("searchresults")));

            TextView queryInfo = view.findViewById(R.id.queryInfo);
            queryInfo.setText(query.toString());

            ArrayList<ItemCollection> collectionsResults = query.getResults();

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

            buttonNext.setOnClickListener(v -> {
                if (currentPage < maxPage - 1) {
                    currentPage++;
                    boxListHistory.add(new ArrayList<>(boxAdapter.getBoxList()));
                    List<String> nextPageItems = getCurrentPageItems();
                    boxAdapter.setBoxList(nextPageItems);
                    boxAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(0);
                }
            });

            boxListHistory.add(new ArrayList<>(boxList));

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

        protected void loadFragment(Fragment fragment) {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_home_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        private List<String> getCurrentPageItems() {
            int start = currentPage * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, boxList.size());
            return new ArrayList<>(boxList.subList(start, end));
        }
    }

}
