package com.b07group2.taamapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URI;

public class ItemDetailedView extends AppCompatActivity {
    TextView lot_text, name_text, category_text, period_text, description_text;
    LinearLayout media_layout;
    Button back_button;

    ItemDetailedPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_detailed_view);

        // Get item id from previous activity
        int item_lot = getIntent().getIntExtra("item_lot", 0);

        lot_text = (TextView) findViewById(R.id.lot_number);
        name_text = (TextView) findViewById(R.id.item_name);
        category_text = (TextView) findViewById(R.id.item_category);
        period_text = (TextView) findViewById(R.id.item_period);
        description_text = (TextView) findViewById(R.id.item_description);

        // We can have several files (both picture and video)
        media_layout = (LinearLayout) findViewById(R.id.item_files_layout);

        back_button = (Button) findViewById(R.id.item_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        presenter = new ItemDetailedPresenter(this);
        presenter.connectDatabase();

        presenter.setInformation(item_lot);
    }

    public boolean setInformation(int lot, String name, String category, String period, String description) {
        try{
            lot_text.setText(Integer.toString(lot));
            name_text.setText(name);
            category_text.setText(category);
            period_text.setText(period);
            description_text.setText(description);
            return true;
        }
        catch (Exception e){
            // log the error
            Log.e("ItemDetailedView", "Error setting information: " + e.getMessage());

            return false;
        }
    }

    public boolean addPicture(File file) {
        try {
            ImageView imageView = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageView.setImageBitmap(bitmap);
            media_layout.addView(imageView);

            return true;
        }
        catch (Exception e){
            // log the error
            Log.e("ItemDetailedView", "Error adding picture: " + e.getMessage());

            return false;
        }
    }

    public boolean addVideo(File file) {
        try {
            VideoView videoView = new VideoView(this);
            videoView.setVideoURI(Uri.parse(file.getAbsolutePath()));
            media_layout.addView(videoView);
            return true;
        }
        catch (Exception e){
            // log the error
            Log.e("ItemDetailedView", "Error adding video: " + e.getMessage());

            return false;
        }
    }

    public void showWarning(String message) {
        // Show alert with message to user

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        AlertDialog alert = builder.create();
        alert.show();

    }
}