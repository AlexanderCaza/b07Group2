package com.b07group2.taamapp;

import android.content.ContentResolver;

import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailedPresenter {
    ItemDetailedView view;
    ArrayList<ItemCollection> items;
    private ItemCollection item;

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

            this.item = item;

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
            while (!res) {
                res = view.setInformation(lot, name, category, period, description);
            }

            // if No media is present
            if (item.getMedia() == null) {
                view.setNoMedia();
                return;
            }


            List<Uri> medias = ItemCollection.mediaToUri(item.getMedia());

            for (Uri media : medias) {
                new FetchMimeTypeTask(media, view).execute(media.toString());
            }

        });
    }

    public ItemCollection getItem() {
        return item;
    }
}
