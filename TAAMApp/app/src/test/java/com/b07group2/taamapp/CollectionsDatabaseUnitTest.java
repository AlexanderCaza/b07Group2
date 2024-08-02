// CollectionsDatabase Class Unit Tests - Cannot run without fully compiled app, leaving in commit
// in case they can be converted to tests that can run. Remove before full release.
/*
package com.b07group2.taamapp;

import static android.os.Process.myPid;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mock;

public class CollectionsDatabaseUnitTest {

    @Mock
    android.os.Process process;

    @Test
    public void addItemCollectionIsCorrect() {
        when(process.myPid()).thenReturn(1);
        CollectionsDatabase db = new CollectionsDatabase();
        ItemCollection testCollection = new ItemCollection(10002, "Teapot",
                "Enamels", "Shang", "A teapot.");
        db.addItemCollection(testCollection);
        assertTrue(db.getItemCollections().contains(testCollection));
    }

    @Test
    public void deleteItemCollectionIsCorrect() {
        when(process.myPid()).thenReturn(1);
        CollectionsDatabase db = new CollectionsDatabase();
        String[] mediaArray = {"https://www.google.com"};
        ItemCollection testCollection = new ItemCollection(10007, "Teapot",
                "Enamels", "Shang", "A teapot.", mediaArray);
        db.addItemCollection(testCollection);
        db.deleteItemCollection(testCollection);
        assertFalse(db.getItemCollections().contains(testCollection));
    }
}
*/
