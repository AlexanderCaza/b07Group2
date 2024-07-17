package com.b07group2.taamapp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CollectionsDatabaseUnitTest {

    @Test
    public void addItemCollectionIsCorrect() {
        CollectionsDatabase db = new CollectionsDatabase();
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        db.addItemCollection(testCollection);
        assertTrue(db.getItemCollections().contains(testCollection));
    }

    @Test
    public void deleteItemCollectionIsCorrect() {
        CollectionsDatabase db = new CollectionsDatabase();
        String[] mediaArray = {"https://www.google.com"};
        ItemCollection testCollection = new ItemCollection(10007, "Teapot",
                "Enamels", "Shang", "A teapot.", mediaArray);
        db.addItemCollection(testCollection);
        db.deleteItemCollection(testCollection);
        assertFalse(db.getItemCollections().contains(testCollection));
    }
}
