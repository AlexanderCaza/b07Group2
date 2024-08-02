package com.b07group2.taamapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BoxesFragment extends Fragment {

    private CollectionsDatabase collectionsDatabase;
    private BoxAdapter boxAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boxes, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        boxAdapter= new BoxAdapter(new ArrayList<>());
        recyclerView.setAdapter(boxAdapter);

        collectionsDatabase = new CollectionsDatabase();
        fetchCollectionsData();

        return view;
    }

    private void fetchCollectionsData(){
        List<ItemCollection> itemCollections = collectionsDatabase.getItemCollections();
        if (itemCollections != null){
            boxAdapter.setBoxList(itemCollections);
        }
    }
}
