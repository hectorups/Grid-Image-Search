package com.codepath.example.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    final private String TAG = "SearchActivity";
    final private int REQUEST_CODE = 1;

    private EditText etQuery;
    private ImageButton btnSearch;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults = new ArrayList<>();
    private ImageResultArrayAdapter imageAdapter;

    private EndlessScrollListener endlessScrollListener;

    private ImageManager imageManager;

    private SearchSettings searchSettings;
    private ImageManager.ImageManagerCallback imageManagerCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchSettings = new SearchSettings();

        imageManager = ImageManager.getInstance(searchSettings);

        setContentView(R.layout.activity_main);
        setupViews();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_search, menu);
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
        etQuery = (EditText) findViewById(R.id.etQuery);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        gvResults = (GridView) findViewById(R.id.gvResults);

        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), ImageDisplayActivity.class);
                ImageResult imageResult = imageResults.get(position);
                intent.putExtra("result", imageResult);
                startActivity(intent);
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
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            searchSettings = (SearchSettings) data.getExtras().getSerializable(SettingsActivity.SETTINGS);
            imageManager.setSearchSettings(searchSettings);
        }
    }

    public void onImageSearch(View v){

        String query = getQuery();
        if( query == null) return;
        Toast.makeText(this, "Search for " + query, Toast.LENGTH_SHORT).show();
        imageResults.clear();
        endlessScrollListener.reset();

        imageManager.queryImages(query, 0, imageManagerCallback);

    }


    private String getQuery(){
        if(etQuery.getText() == null ) return null;
        return etQuery.getText().toString();
    }



}
