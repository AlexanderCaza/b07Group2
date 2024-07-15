package com.b07group2.taamapp;

import android.util.Log;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionsDatabase {

    private DatabaseReference dbRef;
    private ArrayList<ItemCollection> itemCollections;
    private ValueEventListener dbListener;

    public CollectionsDatabase() {
        this.dbRef = FirebaseDatabase.getInstance().
                getReference("collections");
        this.dbListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("demo", "data changed");
                itemCollections = new ArrayList<ItemCollection>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    itemCollections.add(child.getValue(ItemCollection.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled",
                        databaseError.toException());
            }
        };
        dbRef.addValueEventListener(this.dbListener);
    }

    public boolean addItemCollection(ItemCollection toAdd) throws dbException {
        Map<String, Object> toAddMap = new HashMap<>();
        toAddMap.put("lotNumber", toAdd.lotNumber);
        toAddMap.put("name", toAdd.getName());
        toAddMap.put("category", toAdd.getCategory())
        toAddMap.put("period", toAdd.getPeriod());
        toAddMap.put("media", toAdd.getMedia());
        for (Collection existing : itemCollections) {
            if (existing.id == toAddMap.id) throw dbException("Error: Duplicate id!");
        }

        dbRef.document(Integer.toString(toAddMap.get(id))).set(toAddMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Collection successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing Collection", e);
                    }
                });
    }

    public ArrayList<ItemCollection> getItemCollections() {
        return itemCollections;
    }

    getItemCollections
}
