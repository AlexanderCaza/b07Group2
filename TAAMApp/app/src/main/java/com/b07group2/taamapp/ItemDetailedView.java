package com.b07group2.taamapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class ItemDetailedView extends AppCompatActivity {
    TextView lot_text, name_text, category_text, period_text, description_text;
    TextView media_label;
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
        media_label = (TextView) findViewById(R.id.item_media_label);

        back_button = (Button) findViewById(R.id.item_back_button);
        back_button.setOnClickListener(v -> finish());

        presenter = new ItemDetailedPresenter(this);

        presenter.setInformation(item_lot);
    }

    public boolean setInformation(int lot, String name, String category, String period, String description) {
        try{
            lot_text.setText(String.format(Locale.getDefault(),"%d", lot));
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

    public void addPicture(String link) {
        try {
            ImageView imageView = new ImageView(this);
            Picasso.get().load(link).into(imageView);
            Log.w("ItemDetailedView", "Picture: " + link);
            media_layout.addView(imageView);
        }
        catch (Exception e){
            // log the error
            Log.e("ItemDetailedView", "Error adding picture: " + e.getMessage());
        }
    }

    public void addVideo(String link) {
        try {
            VideoView videoView = new VideoView(this);
            videoView.setVideoURI(Uri.parse(link));
            media_layout.addView(videoView);
        }
        catch (Exception e){
            // log the error
            Log.e("ItemDetailedView", "Error adding video: " + e.getMessage());
        }
    }

    public void setNoMedia(){
        media_label.setText(R.string.item_no_media_label);

        ConstraintLayout constraintLayout = findViewById(R.id.item_inner_constraint);
        ConstraintSet constraintSet = new ConstraintSet();

        constraintSet.clone(constraintLayout);

        // remove app:layout_constraintEnd_toEndOf="parent" from item_media_label
        constraintSet.clear(R.id.item_media_label, ConstraintSet.END);

        // apply changes
        constraintSet.applyTo(constraintLayout);

    }

    public void showWarning(String message) {
        // Show alert with message to user

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        AlertDialog alert = builder.create();
        alert.show();

    }

    public Context getContextForPresenter() {
        return this.getApplicationContext();
    }
}