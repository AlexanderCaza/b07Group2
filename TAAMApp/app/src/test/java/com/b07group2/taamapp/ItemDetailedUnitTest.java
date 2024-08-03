package com.b07group2.taamapp;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;

import android.net.Uri;

import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

@RunWith(MockitoJUnitRunner.class)
public class ItemDetailedUnitTest {

    @Mock
    private CollectionsDatabase database;

    @Mock
    private ItemDetailedView view;

    private ItemDetailedPresenter presenter;

    @Before
    public void setup() {
        // Mock FirebaseApp and FirebaseStorage
        FirebaseApp firebaseApp = mock(FirebaseApp.class);
        FirebaseStorage firebaseStorage = mock(FirebaseStorage.class);

        // Ensure the static methods return the mocked instances
        mockStatic(FirebaseApp.class);
        mockStatic(FirebaseStorage.class);

        when(FirebaseApp.getInstance()).thenReturn(firebaseApp);
        when(FirebaseStorage.getInstance()).thenReturn(firebaseStorage);

        // Initialize mocks
        presenter = new ItemDetailedPresenter(view);
    }

    @Test
    public void testSetInformation() {
        ArrayList<ItemCollection> items = new ArrayList<>();
//        Uri mediaUri = Uri.parse("file:///path/to/file.jpg");

        ItemCollection item1 = new ItemCollection(1, "knife", "Jade", "Modern", "description");
        ItemCollection item2 = new ItemCollection(2, "sword", "Jade", "Modern", "description");
        ItemCollection item3 = new ItemCollection(3, "axe", "Jade", "Modern", "description");

        items.add(item1);
        items.add(item2);
        items.add(item3);

        // Mock the database callback to return the items list
        Answer<Void> answer = invocation -> {
            CollectionsCallback callback = invocation.getArgument(0);
            callback.onCallback(items);
            return null;
        };

        when(database).then(answer);

        presenter.setInformation(1);

        verify(view).setInformation(1, "knife", "Jade", "Modern", "description");
//        verify(view).addPicture(new File(Objects.requireNonNull(mediaUri.getPath())));

        presenter.setInformation(2);

        verify(view).setInformation(2, "sword", "Jade", "Modern", "description");
//        verify(view).addPicture(new File(Objects.requireNonNull(mediaUri.getPath())));
    }

    @Test
    public void testSetInformationItemNotFound() {
        ArrayList<ItemCollection> items = new ArrayList<>();

        // Mock the database callback to return an empty items list
        doAnswer((Answer<Void>) invocation -> {
            CollectionsCallback callback = invocation.getArgument(0);
            callback.onCallback(items);
            return null;
        }).when(database);
        CollectionsDatabase.getCollections(any(CollectionsCallback.class));

        presenter.setInformation(4);

        verify(view).showWarning("Item not found");
        verify(view, times(1)).setInformation(0, "Item not found", "Item not found", "Item not found", "Item not found");
    }
}
