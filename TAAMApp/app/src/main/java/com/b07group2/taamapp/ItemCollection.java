package com.b07group2.taamapp;

import android.net.Uri;

import com.google.firebase.database.DataSnapshot;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemCollection implements Serializable {

    private int lotNumber;
    private String name;
    private String category;
    private String period;
    private String description;
    private List<String> media;
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
        this.media = media;
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
        if (this.description == null || this.description.isEmpty()) this.description = " ";
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

    public List<String> getMedia() {
        return media;
    }

    public void setMedia(List<String> media) {
        this.media = media;
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

    public static List<Uri> mediaToUri(List<String> strings) {
        ArrayList<Uri> uriMedia = new ArrayList<Uri>();
        if(strings==null){
            return null;
        }
        for (String string : strings) {
            uriMedia.add(Uri.parse(string));
        }
        return uriMedia;
    }

}