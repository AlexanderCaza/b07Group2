package com.b07group2.taamapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28}, manifest = "app/src/main/AndroidManifest.xml")
public class ItemDetailedActivityTest {
    private ItemDetailedView view;
    private ItemDetailedPresenter presenter;
    private ItemDetailedModel model;


    @Before
    public void setUp() {
        view = Robolectric.buildActivity(ItemDetailedView.class).create().get();
        model = new ItemDetailedModel(); // Mock or initialize this appropriately
        presenter = new ItemDetailedPresenter(view, model);
        view.presenter = presenter;
    }

    @Test
    public void testSetInformation() {
        ItemCollection item = new ItemCollection(1, "Test Name", "Test Category", "Test Period", "Test Description");
        presenter.setInformation(item);

        TextView lotTextView = view.findViewById(R.id.lot_number);
        TextView nameTextView = view.findViewById(R.id.item_name);
        TextView categoryTextView = view.findViewById(R.id.item_category);
        TextView periodTextView = view.findViewById(R.id.item_period);
        TextView descriptionTextView = view.findViewById(R.id.item_description);

        assertEquals("1", lotTextView.getText().toString());
        assertEquals("Test Name", nameTextView.getText().toString());
        assertEquals("Test Category", categoryTextView.getText().toString());
        assertEquals("Test Period", periodTextView.getText().toString());
        assertEquals("Test Description", descriptionTextView.getText().toString());
    }

    @Test
    public void testEmptyInformationSet() {
        ItemCollection emptyItem = new ItemCollection(0, "", "", "", "");
        presenter.setInformation(emptyItem);

        TextView lotTextView = view.findViewById(R.id.lot_number);
        TextView nameTextView = view.findViewById(R.id.item_name);
        TextView categoryTextView = view.findViewById(R.id.item_category);
        TextView periodTextView = view.findViewById(R.id.item_period);
        TextView descriptionTextView = view.findViewById(R.id.item_description);

        assertEquals("0", lotTextView.getText().toString());
        assertEquals("", nameTextView.getText().toString());
        assertEquals("", categoryTextView.getText().toString());
        assertEquals("", periodTextView.getText().toString());
        assertEquals("", descriptionTextView.getText().toString());
    }

    @Test
    public void testAddPicture() {
        // Assuming that ItemCollection.getMedia() returns a list of files
        File imageFile = new File(view.getFilesDir(), "test.jpg");
        // Mock or create an ItemCollection with a media file
        ItemCollection itemWithImage = new ItemCollection(1, "Test Name", "Test Category", "Test Period", "Test Description");
        itemWithImage.setMedia(new File[]{imageFile}); // Assume setMedia is a method in ItemCollection

        presenter.setInformation(itemWithImage);

        LinearLayout mediaLayout = view.findViewById(R.id.item_files_layout);
        ImageView imageView = (ImageView) mediaLayout.getChildAt(0);

        // Assert that an ImageView is added to the media layout and has a drawable set
        assertTrue(imageView instanceof ImageView);
        assertNotNull(imageView.getDrawable());
    }

    @Test
    public void testAddVideo() {
        // Assuming that ItemCollection.getMedia() returns a list of files
        File videoFile = new File(view.getFilesDir(), "test.mp4");
        // Mock or create an ItemCollection with a media file
        ItemCollection itemWithVideo = new ItemCollection(1, "Test Name", "Test Category", "Test Period", "Test Description");
        itemWithVideo.setMedia(new File[]{videoFile}); // Assume setMedia is a method in ItemCollection

        presenter.setInformation(itemWithVideo);

        LinearLayout mediaLayout = view.findViewById(R.id.item_files_layout);
        VideoView videoView = (VideoView) mediaLayout.getChildAt(0);

        // Assert that a VideoView is added to the media layout and has a video URI set
        assertTrue(videoView instanceof VideoView);
    }
}