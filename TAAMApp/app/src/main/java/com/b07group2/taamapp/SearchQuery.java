package com.b07group2.taamapp;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class SearchQuery implements Serializable {
    private String lotNumber;
    private String name;
    private String category;
    private String period;
    private String description;
    // hasMedia: Yes = only those with media, "" = those with and without media
    private String hasMedia;

    SearchQuery(String lotNumber, String name, String period, String category, String description,
                String hasMedia) {
        this.lotNumber = lotNumber;
        this.name = name;
        this.category = category;
        this.period = period;
        this.description = description;
        this.hasMedia = hasMedia;
    }

    // Getters and setters
    public String getLotNumber() {
        return lotNumber;
    }


    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getPeriod() {
        return period;
    }

    public String getDescription() {
        return description;
    }

    public String getHasMedia() {
        return hasMedia;
    }

    @Override
    public String toString() {
        String output = "Showing results for: ";
        if (!lotNumber.isEmpty()) output = output + "Lot Number: " + lotNumber + ", ";
        if (!name.isEmpty()) output = output + "Name: " + name + ", ";
        if (!category.isEmpty()) output = output + "Category: " + category + ", ";
        if (!period.isEmpty()) output = output + "Period: " + period + ", ";
        if (!description.isEmpty()) output = output + "Description: " + description + ", ";
        if (!hasMedia.isEmpty()) output = output + "Exclude No Media: " + hasMedia + ", ";
        return output;
    }

    public ArrayList<ItemCollection> getResults() {
        CollectionsDatabase db = new CollectionsDatabase();
        return db.search(this.lotNumber, this.name,
                this.category, this.description, this.description, this.hasMedia);
    }
}
