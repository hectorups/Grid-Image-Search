package com.codepath.example.gridimagesearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class SearchActivity extends ActionBarActivity {

    final private String TAG = "SearchActivity";
    final private int REQUEST_CODE = 1;
    private static final String SEARCH_SETTINGS = "searchsettings";
    private static final String IMAGE_RESULTS = "imageresults";

    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultArrayAdapter imageAdapter;
    private String query;

    private EndlessScrollListener endlessScrollListener;

    private ImageManager imageManager;

    private SearchSettings searchSettings;
    private ImageManager.ImageManagerCallback imageManagerCallback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            searchSettings = (SearchSettings) savedInstanceState.getSerializable(SEARCH_SETTINGS);
            imageResults = savedInstanceState.getParcelableArrayList(IMAGE_RESULTS);

        } else {
            searchSettings = new SearchSettings();
            imageResults = new ArrayList<>();
        }

        imageManager = ImageManager.getInstance(searchSettings);

        setContentView(R.layout.activity_main);
        setupViews();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(queryListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menuSetting:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                intent.putExtra(SettingsActivity.SETTINGS, searchSettings);
                startActivityForResult(intent, REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViews(){
        gvResults = (GridView) findViewById(R.id.gvResults);

        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), ImageDisplayActivity.class);
                ImageResult imageResult = imageResults.get(position);
                intent.putExtra(ImageDisplayActivity.RESULT, imageResult);
                startActivityForResult(intent, RESULT_OK);
            }
        });


        imageManagerCallback = new ImageManager.ImageManagerCallback() {
            @Override
            public void onSuccess(ArrayList<ImageResult> results) {
                for(ImageResult result: results){
                    imageAdapter.add(result);
                }
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFinished(){
                // btnSearch.setEnabled(true);
            }
        };

        endlessScrollListener = new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                String query = getQuery();
                if( query == null) return;

                imageManager.queryImages(query, page - 1, imageManagerCallback);
            }
        };

        gvResults.setOnScrollListener(endlessScrollListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            searchSettings = (SearchSettings) data.getExtras().getSerializable(SettingsActivity.SETTINGS);
            imageManager.setSearchSettings(searchSettings);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putSerializable(SEARCH_SETTINGS, searchSettings);
        savedInstanceState.putParcelableArrayList(IMAGE_RESULTS, imageResults);

    }

    private String getQuery(){
        return query;
    }

    private void setQuery(String query){
        this.query = query.trim();
    }


    private SearchView.OnQueryTextListener queryListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            setQuery(query);
            if( getQuery().length() == 0) return false;
            imageResults.clear();
            endlessScrollListener.reset();

            // Hide keyboard
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(gvResults.getWindowToken(), 0);

            imageManager.queryImages(query, 0, imageManagerCallback);

            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    };


}
