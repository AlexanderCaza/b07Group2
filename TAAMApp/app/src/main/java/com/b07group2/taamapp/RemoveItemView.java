package com.b07group2.taamapp;



import static com.google.android.material.internal.ViewUtils.dpToPx;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import android.widget.Button;

import android.widget.LinearLayout;


public class RemoveItemView extends ItemDetailedView {

    CollectionsDatabase database;
    Button remove_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = new CollectionsDatabase();

        super.onCreate(savedInstanceState);
        Button remove_button = new Button(this);
        remove_button.setTransformationMethod(null);
        remove_button.setText("Remove");
        remove_button.setTextColor(Color.WHITE);

        @SuppressLint("RestrictedApi") LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) dpToPx(this, 96),
                (int) dpToPx(this, 40));

        remove_button.setLayoutParams(params);
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(Color.parseColor("#6750a4"));
        shape.setCornerRadius(100);
        shape.setSize(32,32);

        remove_button.setBackground(shape);

        remove_button.setPadding(16,0,16,0);

        remove_button.setOnClickListener(v -> remove_item());
        LinearLayout layout = (LinearLayout) findViewById(R.id.button_linear_layout);
        layout.addView(remove_button);
    }

    private void remove_item() {
        ItemCollection item = presenter.getItem();
        database.deleteItemCollection(item);
        //go back to previous screen
        finish();

    }
}