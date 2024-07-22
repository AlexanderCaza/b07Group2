package com.b07group2.taamapp;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ItemDetailedPresenter {
    ItemDetailedView view;
    CollectionsDatabase model;

    public ItemDetailedPresenter(ItemDetailedView view) {
        this.view = view;
        this.model = null;
    }

    public void connectDatabase(){
        model = new CollectionsDatabase();
    }

    public void setInformation(int item_id) {
        // get the ItemCollection from model as item_id

        ArrayList<ItemCollection> items = model.getItemCollections();

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
        Uri[] files = item.getMedia();
        for (Uri file : files) {
            if (file.toString().endsWith(".jpg") || file.toString().endsWith(".png")) {
                view.addPicture(new File(Objects.requireNonNull(file.getPath())));
            } else if (file.toString().endsWith(".mp4")) {
                view.addVideo(new File(Objects.requireNonNull(file.getPath())));
            }
        }
    }

    public void setInformation(ItemCollection item) {
        // set the information in the view
        int lot = item.getLotNumber();
        String name = item.getName();
        String category = item.getCategory();
        String period = item.getPeriod();
        String description = item.getDescription();

        boolean res =view.setInformation(lot, name, category, period, description);
        while (!res){
            res =view.setInformation(lot, name, category, period, description);
        }

        Uri[] files = item.getMedia();
        for (Uri file : files) {
            if (file.toString().endsWith(".jpg") || file.toString().endsWith(".png")) {
                view.addPicture(new File(Objects.requireNonNull(file.getPath())));
            } else if (file.toString().endsWith(".mp4")) {
                view.addVideo(new File(Objects.requireNonNull(file.getPath())));
            }
        }
    }
}
