package com.b07group2.taamapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * local unit test for the itemCollection class, which will execute on the development
 * machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ItemCollectionUnitTest {
    @Test
    public void noFilesConstructor_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        assertEquals(testCollection.getLotNumber(), 10002);
        assertEquals(testCollection.getName(), "Teapot");
        assertEquals(testCollection.getCategory(), "Enamels");
        assertEquals(testCollection.getPeriod(), "Shang");
        assertEquals(testCollection.getDescription(), "A teapot");
    }

    @Test
    public void setLotNumber_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        testCollection.setLotNumber(10003);
        assertEquals(testCollection.getLotNumber(), 10003);
    }

    public void setName_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        testCollection.setName("Teacup");
        assertEquals(testCollection.getName(), "Teacup");
    }

    public void setName_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        testCollection.setName("Teacup");
        assertEquals(testCollection.getName(), "Teacup");
    }

    public void setCategory_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        testCollection.setCategory("Bronze");
        assertEquals(testCollection.getCategory(), "Bronze");
    }

    public void setPeriod_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        testCollection.setPeriod("Ji");
        assertEquals(testCollection.getPeriod(), "Ji");
    }

    public void setDescription_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        testCollection.setPeriod("Ji");
        assertEquals(testCollection.getPeriod(), "Ji");
    }
}