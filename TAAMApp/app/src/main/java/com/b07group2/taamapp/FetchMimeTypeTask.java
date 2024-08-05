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
    private FetchMimeTypeCallback callback;

    public FetchMimeTypeTask(Uri media, FetchMimeTypeCallback callback) {
        this.media = media;
        this.callback = callback;
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
        if (callback != null) {
            callback.onMimeTypeFetched(media, mimeType);
        }
    }
}
