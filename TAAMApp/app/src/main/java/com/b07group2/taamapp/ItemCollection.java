package com.b07group2.taamapp;

import android.net.Uri;

import com.google.firebase.database.DataSnapshot;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ItemCollection {

    private int lotNumber;
    private String name;
    private String category;
    private String period;
    private String description;
    private List<Uri> media;
    final public static String[] validCategories = {"Jade", "Paintings", "Calligraphy", "Rubbings",
            "Bronze", "Brass and Copper", "Gold and Silvers", "Lacquer", "Enamels"};
    final public static String[] validPeriods = {"Xia", "Shang", "Zhou", "Chuanqiu", "Zhanggou", "Qin",
            "hang", "Shangou", "Ji", "South and North", "Shui", "Tang", "Liao", "Song",
            "Jin", "Yuan", "Ming", "Qing", "Modern"};

    // Special constructor required by Firebase
    public ItemCollection() {

    }

    public ItemCollection(int lotNumber, String name, String category, String period, String description,
                          List<String> media) {
        this.lotNumber = lotNumber;
        this.name = name;
        this.category = category;
        this.period = period;
        this.description = description;
        for (String inputMedia : media) {
            this.media.add(Uri.parse(inputMedia));
        }
    }

    // Constructor with media field
    public ItemCollection(int lotNumber, String name, String category, String period,
                          String description, String[] media) {
        this.lotNumber = lotNumber;
        this.name = name;
        this.category = category;
        this.period = period;
        this.description = description;
        for (String inputMedia : media) {
            this.media.add(Uri.parse(inputMedia));
        }
    }

    // Constructor without media field
    public ItemCollection(int lotNumber, String name, String category, String period,
                          String description) {
        this.lotNumber = lotNumber;
        this.name = name;
        if (this.name == null || this.name.isEmpty()) this.name = " ";
        this.category = category;
        this.period = period;
        this.description = description;
        if (this.description == null || || this.description.isEmpty()) this.description = " ";
    }

    // Getters and setters
    public int getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(int lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri[] getMedia() {
        if (this.media == null) {
            return new Uri[0];
        }

        Uri[] media = new Uri[this.media.size()];
        for (int i = 0; i < this.media.size(); i++) {
            media[i] = this.media.get(i);
        }
        return media;
    }

    public void setMedia(Uri[] media) {
        this.media = Arrays.asList(media);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        else if (!(obj instanceof ItemCollection)) return false;
        ItemCollection other = (ItemCollection) obj;
        return (other.getLotNumber() == this.lotNumber);
    }

    @Override
    public int hashCode() {
        return lotNumber;
    }

    public static String[] getValidCategories() {
        return validCategories;
    }

    public static String[] getValidPeriods() {
        return validPeriods;
    }

    public static boolean isValidCategory(String category) {
        return Arrays.asList(validCategories).contains(category);
    }

    public static boolean isValidPeriod(String period) {
        return Arrays.asList(validPeriods).contains(period);
    }

}