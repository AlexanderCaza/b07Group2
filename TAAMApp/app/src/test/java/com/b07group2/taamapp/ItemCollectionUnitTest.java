package com.b07group2.taamapp;

import org.junit.Test;
import org.mockito.Mock;
import java.util.Arrays;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import android.net.Uri;

/**
 * local unit test for the itemCollection class, which will execute on the development
 * machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemCollectionUnitTest {

    @Test
    public void noFilesConstructor_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        assertEquals(testCollection.getLotNumber(), 10002);
        assertEquals(testCollection.getName(), "Teapot");
        assertEquals(testCollection.getCategory(), "Enamels");
        assertEquals(testCollection.getPeriod(), "Shang");
        assertEquals(testCollection.getDescription(), "A teapot.");
    }

// Test does not run: requires android.net.Uri to be mocked
//    public void FilesConstructor_isCorrect() {
//        String[] files = new String[1];
//        files[0] = "google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png";
//        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
//                "Enamels", "Shang", "A teapot.", files);
//        assertEquals(testCollection.getLotNumber(), 10002);
//        assertEquals(testCollection.getName(), "Teapot");
//        assertEquals(testCollection.getCategory(), "Enamels");
//        assertEquals(testCollection.getPeriod(), "Shang");
//        assertEquals(testCollection.getDescription(), "A teapot.");
//        assertEquals(testCollection.getMedia()[0], Uri.parse(files[0]));
//    }

    @Test
    public void setLotNumber_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        testCollection.setLotNumber(10003);
        assertEquals(testCollection.getLotNumber(), 10003);
    }

    @Test
    public void setName_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        testCollection.setName("Teacup");
        assertEquals(testCollection.getName(), "Teacup");
    }

    @Test
    public void setCategory_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        testCollection.setCategory("Bronze");
        assertEquals(testCollection.getCategory(), "Bronze");
    }

    @Test
    public void setPeriod_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        testCollection.setPeriod("Ji");
        assertEquals(testCollection.getPeriod(), "Ji");
    }

    @Test
    public void setDescription_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        testCollection.setDescription("A coffee cup");
        assertEquals(testCollection.getDescription(), "A coffee cup");
    }

    // Test does not run: requires net.android.Uri
    /*
    @Test
    public void setMedia_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        Uri[] media = new Uri[1];
        media[0] = Uri.parse("google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
        testCollection.setMedia(media);
        assertEquals(testCollection.getMedia(), media);
    }
    */

    @Test
    public void getValidCategories_isCorrect() {
        assertTrue(Arrays.asList(ItemCollection.getValidCategories()).contains("Brass and Copper"));
    }

    @Test
    public void getValidPeriods_isCorrect() {
        assertTrue(Arrays.asList(ItemCollection.getValidPeriods()).contains("Qing"));
    }

    @Test
    public void isValidCategory_isCorrect() {
        assertTrue(ItemCollection.isValidCategory("Paintings"));
    }

    @Test
    public void isValidPeriod_isCorrect() {
        assertTrue(ItemCollection.isValidPeriod("Xia"));
    }

    @Test
    public void isEqualNull_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        assertFalse(testCollection.equals(null));
    }

    @Test
    public void isEqualDiffObj_isCorrect() {
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        String badObj = "Hello";
        assertFalse(testCollection.equals(badObj));
    }

    @Test
    public void isEqualDiffLotNumber_isCorrect() {
        ItemCollection testCollection1 = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        ItemCollection testCollection2 = new ItemCollection(10003, "Teapot",
                "Enamels", "Shang", "A teapot.");
        String badObj = "Hello";
        assertFalse(testCollection1.equals(testCollection2));
    }

    @Test
    public void isEqualSameLotNumber_isCorrect() {
        ItemCollection testCollection1 = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        ItemCollection testCollection2 = new ItemCollection(10002, "Tea Kettle",
                "Gold and Bronze", "Xia", "A kettle.");
        String badObj = "Hello";
        assertTrue(testCollection1.equals(testCollection2));
    }

    @Test
    public void hashCode_isCorrect() {
        ItemCollection testCollection1 = new ItemCollection(10010, "Teapot",
                "Enamels", "Shang", "A teapot.");
        assertEquals(testCollection1.hashCode(), 10010);
    }
}