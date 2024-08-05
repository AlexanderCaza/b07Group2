package com.b07group2.taamapp;

import android.net.Uri;

public interface FetchMimeTypeCallback {
    void onMimeTypeFetched(Uri media, String mimeType);
}