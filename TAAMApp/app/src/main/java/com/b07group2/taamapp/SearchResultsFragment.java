package com.b07group2.taamapp;

import static org.apache.commons.lang3.SerializationUtils.deserialize;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

public class SearchResultsFragment extends Fragment {

        private RecyclerView recyclerView;
        private BoxAdapter boxAdapter;
        private int currentPage = 0;
        private static final int PAGE_SIZE = 4; //boxes per page
        private CollectionsDatabase collectionsDatabase;
        private List<ItemCollection> itemCollections;

        @Nullable
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.search_results_fragment, container, false);

            Button buttonView = view.findViewById(R.id.buttonView);
            Button buttonNext = view.findViewById(R.id.buttonNext);

            Bundle arguments = getArguments();

            if (arguments != null) {
                byte[] data = arguments.getByteArray("searchresults");
                if (data != null){
                    SearchQuery query = deserialize(data);
                    TextView queryInfo = view.findViewById(R.id.queryInfo);
                    queryInfo.setText(query.toString());

                    itemCollections = query.getResults();
                }
            }

            if (itemCollections == null) {
                itemCollections = new ArrayList<>();
            }

            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            boxAdapter = new BoxAdapter(getCurrentPageItems());
            recyclerView.setAdapter(boxAdapter);

            buttonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (boxAdapter.clickCount() != 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Select only one item to be viewed!");
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        // Start ItemDetailedView with item lot number

                        Intent intent = new Intent(getContext(), ItemDetailedView.class);
                        ItemCollection item = boxAdapter.getFirstClickedItem();

                        if (item != null) {
                            intent.putExtra("item_lot", item.getLotNumber());
                        } else {
                            // Show error message
                            Log.w("SearchResultFragment", "No item was clicked");
                            return;
                        }

                        startActivity(intent);
                    }
                }
            });

            buttonNext.setOnClickListener(v -> {
                goToNextPage();
            });

            updateRecyclerView();

            return view;
        }

    public void goToPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            updateRecyclerView();
        }
    }

    private void goToNextPage() {
        int maxPage = (int) Math.ceil((double) itemCollections.size() / PAGE_SIZE);
        if (currentPage < maxPage - 1) {
            currentPage++;
            updateRecyclerView();
        }
    }

    private void updateRecyclerView() {
        boxAdapter.setBoxList(getCurrentPageItems());
        boxAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
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

    private List<ItemCollection> getCurrentPageItems() {
        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, itemCollections.size());
        return new ArrayList<>(itemCollections.subList(start, end));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (canGoBack()) {
                    goToPreviousPage();
                } else {
                    requireActivity().onBackPressed();
                }
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}

