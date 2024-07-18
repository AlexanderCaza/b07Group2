package com.b07group2.taamapp;

import androidx.recyclerview.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class BoxAdapterUnitTest {

    private BoxAdapter boxAdapter;
    private List<String> boxList;

    @Before
    public void setUp() {
        boxList = Arrays.asList("Box 1", "Box 2", "Box 3", "Box 4", "Box 5");
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
        List<String> boxes = boxAdapter.getBoxList();
        assertEquals("Box 2", boxes.get(1));
    }


}