package com.codepath.example.gridimagesearch;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");

        ImageView ivImage = (ImageView) findViewById(R.id.ivResult);

        Picasso.with(this)
                .load(Uri.parse(result.getFullUrl()))
                .into(ivImage);

    }


}
