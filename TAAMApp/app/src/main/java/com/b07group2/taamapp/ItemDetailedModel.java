package com.b07group2.taamapp;

public class ItemDetailedModel {
    public ItemDetailedModel(){
        // TODO: DB class initiation
    }

    public ItemCollection getItem(int itemLot) {
        // TODO: Get Item from DB with itemLot

        // Test data
        ItemCollection item = new ItemCollection(1, "name", "category", "period", "description");

        return item;
    }
}
