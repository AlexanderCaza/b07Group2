package com.b07group2.taamapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.b07group2.taamapp.ItemDetailedView;

public class FetchMimeTypeTask extends AsyncTask<String, Void, String> {
    private Uri media;
    private ItemDetailedView view;

    public FetchMimeTypeTask(Uri media, ItemDetailedView view) {
        this.media = media;
        this.view = view;
    }

    @Override
    protected String doInBackground(String... urls) {
        String mimeType = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urls[0]).openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            mimeType = connection.getContentType();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mimeType;
    }

    @Override
    protected void onPostExecute(String mimeType) {
        Log.w("FetchMimeTypeTask", "Media: " + media.toString() + " " + mimeType);

        if (mimeType != null && mimeType.startsWith("image")) {
            view.addPicture(media.toString());
        } else if (mimeType != null && mimeType.startsWith("video")) {
            view.addVideo(media.toString());
        } else {
            view.showWarning("Media type not supported");
        }
    }
}
