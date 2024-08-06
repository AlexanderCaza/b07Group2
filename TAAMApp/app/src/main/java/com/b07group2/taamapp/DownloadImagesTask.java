package com.b07group2.taamapp;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DownloadImagesTask extends AsyncTask<Void, Void, List<Image>> {
    private ArrayList<Uri> imageRefs;
    private DownloadImagesCallback callback;

    public DownloadImagesTask(ArrayList<Uri> imageUris, DownloadImagesCallback callback) {
        this.imageRefs = imageUris;
        this.callback = callback;
    }

    @Override
    protected List<Image> doInBackground(Void... voids) {
        List<Image> images = new ArrayList<>();
        if (imageRefs == null || imageRefs.isEmpty()) return null;

        for (Uri uri : imageRefs) {
            try {
                File localFile = File.createTempFile("images", "jpg");
                StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(uri.toString());

                Task<FileDownloadTask.TaskSnapshot> downloadTask = storageRef.getFile(localFile);

                try {
                    Tasks.await(downloadTask);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (downloadTask.isSuccessful()) {
                    Image image = new Image(ImageDataFactory.create(localFile.getAbsolutePath()));
                    images.add(image);
                } else {
                    Log.e("DownloadImagesTask", "Failed to download image: " + uri.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    @Override
    protected void onPostExecute(List<Image> images) {
        if (callback != null) {
            callback.onImagesDownloaded(images);
        }
    }
}
