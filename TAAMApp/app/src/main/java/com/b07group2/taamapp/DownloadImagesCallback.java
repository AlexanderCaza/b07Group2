package com.b07group2.taamapp;

import com.itextpdf.layout.element.Image;
import java.util.List;

interface DownloadImagesCallback {
    void onImagesDownloaded(List<Image> images);
}