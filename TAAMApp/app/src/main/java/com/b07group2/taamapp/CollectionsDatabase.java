package com.b07group2.taamapp;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CollectionsDatabase {
    private static final String TAG = "TAAM App";
    private DatabaseReference dbRef;
    private ArrayList<ItemCollection> itemCollections;
    private ValueEventListener dbListener;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    public CollectionsDatabase() {
        this.storage = FirebaseStorage.getInstance();
        // DEBUG
        System.out.println("DB Data Changed");
        this.storageRef = storage.getReference();
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

    public void addItemCollection(ItemCollection toAdd) throws dbException {
        for (ItemCollection existing : itemCollections) {
            if (existing.getLotNumber() == existing.getLotNumber())
                throw new dbException("Error: Duplicate id!");
        }
        ArrayList<Uri> cloudMedia = new ArrayList<Uri>();
        for (Uri mediaFile : toAdd.getMedia()) {
            StorageReference mediaStorageRef = storageRef.child(toAdd.getLotNumber() + mediaFile.getLastPathSegment());
            UploadTask uploadTask = mediaStorageRef.putFile(mediaFile);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    throw new dbException("Error: Media upload failed!");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    cloudMedia.add(taskSnapshot.getStorage().getDownloadUrl().getResult());
                }
            });
        }
        ItemCollection newItem = new ItemCollection(toAdd.getLotNumber(), toAdd.getName(),
                toAdd.getCategory(), toAdd.getPeriod(), toAdd.getDescription(), cloudMedia);
        dbRef.child(Integer.toString(toAdd.getLotNumber())).setValue(newItem)
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

    public ArrayList<ItemCollection> search(String lotNumber, String name, String category,
                                            String period, String description, String hasMedia) {
        ArrayList<ItemCollection> results = new ArrayList<>();
        for (ItemCollection collection: itemCollections) {
            if (!lotNumber.isEmpty() &&
                    !Integer.toString(collection.getLotNumber()).equals(lotNumber)) {
                continue;
            }
            else if (!name.isEmpty() &&
                    !collection.getName().contains(name)) {
                continue;
            }
            else if (!category.isEmpty() &&
                    !collection.getCategory().equals(category)) {
                continue;
            }
            else if (!period.isEmpty() &&
                    !collection.getPeriod().equals(period)) {
                continue;
            }
            else if (!description.isEmpty() &&
                        !collection.getDescription().contains(description)) {
                    continue;
            }
            else if (!hasMedia.isEmpty() &&
                    collection.getMedia().length == 0) {
                continue;
            }
            results.add(collection);
        }
        return results;
    }

    public void deleteItemCollection(ItemCollection collectionToDelete) {
        int lotNumberToDelete = collectionToDelete.getLotNumber();
        storageRef.child(Integer.toString(collectionToDelete.getLotNumber())).delete();
        dbRef.child(Integer.toString(collectionToDelete.getLotNumber())).removeValue();
    }
}