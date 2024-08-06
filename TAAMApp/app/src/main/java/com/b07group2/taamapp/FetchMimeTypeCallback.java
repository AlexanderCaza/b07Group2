package com.b07group2.taamapp;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public interface FetchMimeTypeCallback {
    void onMimeTypeFetched(ArrayList<Uri> mediaList, ArrayList<String> mimeTypes);
}