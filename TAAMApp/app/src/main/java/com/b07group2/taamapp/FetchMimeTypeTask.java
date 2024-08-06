package com.b07group2.taamapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.b07group2.taamapp.ItemDetailedView;

public class FetchMimeTypeTask extends AsyncTask<Void, Void, ArrayList<String>> {
    private ArrayList<Uri> mediaList;
    private FetchMimeTypeCallback callback;

    public FetchMimeTypeTask(ArrayList<Uri> media, FetchMimeTypeCallback callback){
        this.mediaList = media;
        this.callback = callback;
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        if (mediaList == null || mediaList.get(0) == null)return null;
        ArrayList<String> mimeTypes = new ArrayList<>();
        for (Uri media : mediaList) {
            String mimeType = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(media.toString()).openConnection();
                connection.setRequestMethod("HEAD");
                connection.connect();
                mimeType = connection.getContentType();
            } catch (IOException e) {
                e.printStackTrace();
                mimeType = "unknown";
            }
            mimeTypes.add(mimeType);
        }
        return mimeTypes;
    }

    @Override
    protected void onPostExecute(ArrayList<String> mimeTypes) {
        Log.w("FetchMimeTypeTask", "MediaList: " + mediaList.toString() + " " + mimeTypes);
        if (callback != null) {
            callback.onMimeTypeFetched(mediaList, mimeTypes);
        }
    }
}
