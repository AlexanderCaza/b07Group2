package com.b07group2.taamapp;

import android.net.Uri;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemDetailedPresenter {
    ItemDetailedView view;
    ArrayList<ItemCollection> items;

    public ItemDetailedPresenter(ItemDetailedView view) {
        this.view = view;
    }

    public void setInformation(int item_id) {
        // get the ItemCollection from model as item_id

        CollectionsDatabase.getCollections(collectionsList -> {
            items = collectionsList;

//                Log.i("ItemDetailedPresenter1", "Items" + items);

            if (items == null) {
                view.showWarning("Item not found");
                view.setInformation(0, "Item not found", "Item not found", "Item not found", "Item not found");
                return;
            }

            ItemCollection item = null;
            for (ItemCollection i : items) {
                if (i.getLotNumber() == item_id) {
                    item = i;
                    break;
                }
            }

            if (item == null) {
                view.showWarning("Item not found");
                view.setInformation(0, "Item not found", "Item not found", "Item not found", "Item not found");
                return;
            }


            // set the information in the view
            int lot = item.getLotNumber();
            String name = item.getName();
            String category = item.getCategory();
            String period = item.getPeriod();
            String description = item.getDescription();

            boolean res = view.setInformation(lot, name, category, period, description);
            while (!res){
                res =view.setInformation(lot, name, category, period, description);
            }

            // Show media
            List<Uri> files = ItemCollection.mediaToUri(item.getMedia());
            for (Uri file : files) {
                if (file.toString().endsWith(".jpg") || file.toString().endsWith(".png")) {
                    view.addPicture(new File(Objects.requireNonNull(file.getPath())));
                } else if (file.toString().endsWith(".mp4")) {
                    view.addVideo(new File(Objects.requireNonNull(file.getPath())));
                }
            }
        });
    }
}
