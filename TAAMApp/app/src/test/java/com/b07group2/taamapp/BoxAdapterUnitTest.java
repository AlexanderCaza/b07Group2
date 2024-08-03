package com.b07group2.taamapp;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class BoxAdapterUnitTest {

    private BoxAdapter boxAdapter;
    private List<ItemCollection> boxList;

    @Before
    public void setUp() {
        boxList = Arrays.asList(
                new ItemCollection(1, "Item 1", "Category 1", "Period 1", "Description 1"),
                new ItemCollection(2, "Item 2", "Category 2", "Period 2", "Description 2"),
                new ItemCollection(3, "Item 3", "Category 3", "Period 3", "Description 3"),
                new ItemCollection(4, "Item 4", "Category 4", "Period 4", "Description 4"),
                new ItemCollection(5, "Item 5", "Category 5", "Period 5", "Description 5")
        );
        boxAdapter = new BoxAdapter(boxList);
    }

    @Test
    public void testItemCount() {
        assertEquals(5, boxAdapter.getItemCount()); // Ensure this matches the list size
    }

    @Test
    public void testCurrentPage(){
        assertEquals(0, boxAdapter.getCurrentPage());
    }

    @Test
    public void testGetBoxList(){
        List<ItemCollection> boxes = boxAdapter.getBoxList();
        assertEquals("Item 2", boxes.get(1).getName());
    }


}