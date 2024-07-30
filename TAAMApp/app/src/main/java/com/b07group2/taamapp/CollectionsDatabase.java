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
        this.storage = FirebaseStorage.getInstance("gs://cscb07-70b84.appspot.com");
        this.storageRef = storage.getReference();
        this.dbRef = FirebaseDatabase.getInstance("https://cscb07-70b84-default-rtdb.firebaseio.com/").
                getReference();
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
            if (existing.getLotNumber() == existing.getLotNumber()) throw new dbException("Error: Duplicate id!");
        }
        Map<String, Object> toAddMap = new HashMap<>();
        toAddMap.put("lotNumber", toAdd.getLotNumber());
        toAddMap.put("name", toAdd.getName());
        toAddMap.put("category", toAdd.getCategory());
        toAddMap.put("period", toAdd.getPeriod());
        ArrayList<Uri> cloudMedia = new ArrayList<Uri>();
        for (Uri mediaFile: toAdd.getMedia()) {
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
        toAddMap.put("media", cloudMedia);
        dbRef.child(Integer.toString(toAdd.getLotNumber())).setValue(toAddMap)
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

    public void deleteItemCollection(ItemCollection collectionToDelete) {
        int lotNumberToDelete = collectionToDelete.getLotNumber();
        storageRef.child(Integer.toString(collectionToDelete.getLotNumber())).delete();
        dbRef.child(Integer.toString(collectionToDelete.getLotNumber())).removeValue();
    }
}
