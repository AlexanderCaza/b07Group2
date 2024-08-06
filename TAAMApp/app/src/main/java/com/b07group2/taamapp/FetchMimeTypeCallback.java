package com.b07group2.taamapp;

import android.net.Uri;

import java.util.List;

public interface FetchMimeTypeCallback {
    void onMimeTypeFetched(Uri media, String mimeType);
    void onMimeTypeFetched(Uri media, String mimeType, List<Uri> imageList);
}