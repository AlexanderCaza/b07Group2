package com.b07group2.taamapp;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemDetailedUnitTest {
    @Mock
    private CollectionsDatabase database;

    @Mock
    private ItemDetailedView view;

    private ItemDetailedPresenter presenter;

    @Test
    public void testSetInformation(){
        ArrayList<ItemCollection> items = new ArrayList<ItemCollection>();

        ItemCollection item1 = new ItemCollection(1, "knife", "Jade", "Modern", "description", new String[]{});
        ItemCollection item2 = new ItemCollection(2, "sword", "Jade", "Modern", "description", new String[]{});
        ItemCollection item3 = new ItemCollection(3, "axe", "Jade", "Modern", "description", new String[]{});

        items.add(item1);
        items.add(item2);
        items.add(item3);

        presenter = new ItemDetailedPresenter(view);

        when(database.getItemCollections()).thenReturn(items);

        presenter.setInformation(1);

        verify(view).setInformation(1, "knife", "Jade", "Modern", "description");

        presenter.setInformation(2);

        verify(view).setInformation(2, "sword", "Jade", "Modern", "description");
    }


}