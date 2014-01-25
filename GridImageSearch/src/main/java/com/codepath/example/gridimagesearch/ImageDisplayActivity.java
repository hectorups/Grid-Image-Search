package com.codepath.example.gridimagesearch;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageDisplayActivity extends Activity {

    public static final String RESULT = "com.codepath.example.gridimagesearch.imagedisplayactivity.result";

    ImageResult imageResult;
    ShareActionProvider miShareAction;
    ImageView ivImage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        imageResult = getIntent().getParcelableExtra(RESULT);

        ivImage = (ImageView) findViewById(R.id.ivResult);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Picasso.with(this)
                .load(Uri.parse(imageResult.getFullUrl()))
                .into(target);

        ancestralNavigation();
    }

    @TargetApi(11)
    public void ancestralNavigation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @TargetApi(14)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_image_display, menu);
        MenuItem item = menu.findItem(R.id.menuShare);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            miShareAction = (ShareActionProvider) item.getActionProvider();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @TargetApi(14)
    private void setShareIntent(Intent shareIntent){
        if (miShareAction != null) {
            miShareAction.setShareIntent(shareIntent);
        } else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_image)));
        }
    }


    private void prepareImageForShare(Bitmap bitmap) {
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image.png");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");

            setShareIntent(shareIntent);

        } else {
            // ...sharing failed, handle error
        }

    }


    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            ivImage.setImageBitmap(bitmap);
            prepareImageForShare(bitmap);
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onBitmapFailed(Drawable d) {
        }

        @Override
        public void onPrepareLoad(android.graphics.drawable.Drawable drawable){}
    };




}
