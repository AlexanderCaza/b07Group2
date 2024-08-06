package com.b07group2.taamapp;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    protected BoxAdapter boxAdapter;
    private int currentPage = 0;
    private static final int PAGE_SIZE = 4; //boxes per page
    private CollectionsDatabase collectionsDatabase;
    private List<ItemCollection> itemCollections;
    protected boolean admin = false;

    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = null;

        if(admin) {
            view = inflater.inflate(R.layout.activity_admin_home_fragment, container, false);
        }
        else {
            view = inflater.inflate(R.layout.activity_home_fragment, container, false);
        }

        Button buttonView = view.findViewById(R.id.buttonView);
        Button buttonSearch = view.findViewById(R.id.buttonSearch);
        Button buttonNext = view.findViewById(R.id.buttonNext);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CollectionsDatabase.getCollections(new CollectionsCallback() {
            @Override
            public void onCallback(ArrayList<ItemCollection> collectionsList) {
                itemCollections = collectionsList;
                if (itemCollections == null) {
                    itemCollections = new ArrayList<>();
                }

                boxAdapter = new BoxAdapter(itemCollections);
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
                                Log.w("HomeFragment", "No item was clicked");
                                return;
                            }

                            startActivity(intent);
                        }
                    }
                });

                buttonSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadFragment(new SearchQueryFragment());
                        //put fragment name here instead of BlankFragment
                    }
                });


                buttonNext.setOnClickListener(v -> {
                    goToNextPage();
                });
            }
        });

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
                Fragment currentFragment = getParentFragmentManager().findFragmentById(R.id.fragment_home_container);

                if (canGoBack()) {
                    goToPreviousPage();
                }
                else if (currentFragment instanceof HomeAdminFragment){
                    loadFragment(new HomeUserFragment());
                }
                else {
                    requireActivity().onBackPressed();
                }
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
