package com.b07group2.taamapp;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.AspectRatioFrameLayout;
import androidx.media3.ui.PlayerView;

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
        back_button.setOnClickListener(v -> {
            // release all players if exist
            for (int i = 0; i < media_layout.getChildCount(); i++) {
                android.view.View view = media_layout.getChildAt(i);
                if (view instanceof PlayerView) {
                    PlayerView playerView = (PlayerView) view;
                    ExoPlayer player = (ExoPlayer) playerView.getPlayer();
                    player.release();
                }
            }
            finish();
        });

        presenter = new ItemDetailedPresenter(this);

        presenter.setInformation(item_lot);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // release all players if exist
                for (int i = 0; i < media_layout.getChildCount(); i++) {
                    android.view.View view = media_layout.getChildAt(i);
                    if (view instanceof PlayerView) {
                        PlayerView playerView = (PlayerView) view;
                        ExoPlayer player = (ExoPlayer) playerView.getPlayer();
                        player.release();
                    }
                }
                finish();
            }
        });
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

    @OptIn(markerClass = UnstableApi.class)
    public void addVideo(String link) {
        try {
            PlayerView playerView = new PlayerView(this);
            ExoPlayer player = new ExoPlayer.Builder(this).build();

            playerView.setPlayer(player);

            MediaItem mediaItem = MediaItem.fromUri(link);

            player.setMediaItem(mediaItem);

            player.prepare();

            // make the player more beautiful
            playerView.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS);
            playerView.setControllerShowTimeoutMs(3000);
            playerView.setControllerHideOnTouch(false);

            // set zoom of the PlayerView of the content
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

            // set min height of the PlayerView
            playerView.setMinimumHeight(1000);


            media_layout.addView(playerView);
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