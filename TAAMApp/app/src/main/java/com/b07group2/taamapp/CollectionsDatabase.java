package com.b07group2.taamapp;

import static java.lang.Thread.sleep;
import static java.nio.file.Files.createDirectory;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

public class CollectionsDatabase {
    private static final String TAG = "TAAM App";
    private static DatabaseReference dbRef;
    private static ArrayList<ItemCollection> itemCollections;
    private static ValueEventListener dbListener;
    private static FirebaseStorage storage;
    private static StorageReference storageRef;

    public static void getCollections(CollectionsCallback callback) {
        storage = FirebaseStorage.getInstance("gs://cscb07-70b84.appspot.com");
        storageRef = storage.getReference();
        dbRef = FirebaseDatabase.getInstance("https://cscb07-70b84-default-rtdb.firebaseio.com/").
                getReference("collections");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("demo", "data changed");
                itemCollections = new ArrayList<ItemCollection>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    itemCollections.add(child.getValue(ItemCollection.class));
                }
                callback.onCallback(itemCollections);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled",
                        databaseError.toException());
            }
        });
        // dbRef.addValueEventListener(dbListener);
    }

    /*public CollectionsDatabase() {
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
*/

    public void addItemCollection(ItemCollection toAdd) throws dbException {
        for (ItemCollection existing : itemCollections) {
            if (toAdd.getLotNumber() == existing.getLotNumber())
                throw new dbException("Error: Duplicate id!");
        }
        /*if (toAdd.getMedia() != null) {
            uploadMedia(toAdd.getMedia(), new StorageCallback() {
                @Override
                public void onCallback(ArrayList<Uri> cloudMedia) {
                    ItemCollection newItem = new ItemCollection(toAdd.getLotNumber(), toAdd.getName(),
                            toAdd.getCategory(), toAdd.getPeriod(), toAdd.getDescription(),
                            (String[]) cloudMedia.toArray());
                    dbRef.child(Integer.toString(toAdd.getLotNumber())).setValue(newItem)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Files successfully uploaded!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing Files", e);
                                }
                            });
                }
            });
        }
        */
        ItemCollection newItem;
        if (!(toAdd.getMedia() == null) && !toAdd.getMedia().isEmpty()) {
            newItem = new ItemCollection(toAdd.getLotNumber(), toAdd.getName(),
                    toAdd.getCategory(), toAdd.getPeriod(), toAdd.getDescription(), toAdd.getMedia());
        }
        else {
            newItem = new ItemCollection(toAdd.getLotNumber(), toAdd.getName(),
                    toAdd.getCategory(), toAdd.getPeriod(), toAdd.getDescription());
        }
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
                    collection.getMedia().isEmpty()) {
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

    public boolean uniqueLotNumber(int number) {
        for (ItemCollection existing : itemCollections) {
            if (number == existing.getLotNumber()) {
                return false;
            }
        }
        return true;
    }

    /*public void uploadMedia(List<Uri> mediaToUpload) {
        StorageReference imageSite = FirebaseStorage.getInstance(
                "gs://cscb07-70b84.appspot.com").getReference().child("media")
                .child(UUID.randomUUID().toString());
        Uri uploadedMedia = new ArrayList<Uri>();
        for (Uri uri : mediaToUpload) {
            StorageReference storageSite = storageRef.child(UUID.randomUUID().toString());
            storageSite.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageSite.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String result = task.getResult().toString();
                            uploadedMedia.add(Uri.parse(result));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    throw new RuntimeException("Media upload to Storage failed");
                }
            });
        }
        callback.onCallback(uploadedMedia);
    }

     */
}