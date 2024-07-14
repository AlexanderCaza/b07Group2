package com.b07group2.taamapp;

import java.io.File;

public class ItemDetailedPresenter {
    ItemDetailedView view;
    ItemDetailedModel model;

    public ItemDetailedPresenter(ItemDetailedView view, ItemDetailedModel model) {
        this.view = view;
        this.model = model;
    }

    public void setInformation(int item_id) {
        // get the ItemCollection from model as item_id
        ItemCollection item = model.getItem(item_id);

        // set the information in the view
        String lot = Integer.toString(item.getLotNumber());
        String name = item.getName();
        String category = item.getCategory();
        String period = item.getPeriod();
        String description = item.getDescription();

        view.setInformation(lot, name, category, period, description);

        File [] files = item.getMedia();
        for (File file : files) {
            if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {
                view.addPicture(file);
            } else if (file.getName().endsWith(".mp4")) {
                view.addVideo(file);
            }
        }
    }
}
