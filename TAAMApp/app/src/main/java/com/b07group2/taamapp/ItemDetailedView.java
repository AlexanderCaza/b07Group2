package com.b07group2.taamapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
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

        presenter = new ItemDetailedPresenter(this, new ItemDetailedModel());

        presenter.setInformation(item_lot);
    }

    public void setInformation(String lot, String name, String category, String period, String description) {
        lot_text.setText(lot);
        name_text.setText(name);
        category_text.setText(category);
        period_text.setText(period);
        description_text.setText(description);
    }

    public void addPicture(File file) {
        ImageView imageView = new ImageView(this);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        imageView.setImageBitmap(bitmap);
        media_layout.addView(imageView);
    }

    public void addVideo(File file) {
        VideoView videoView = new VideoView(this);
        videoView.setVideoURI(Uri.parse(file.getAbsolutePath()));
        media_layout.addView(videoView);
    }
}