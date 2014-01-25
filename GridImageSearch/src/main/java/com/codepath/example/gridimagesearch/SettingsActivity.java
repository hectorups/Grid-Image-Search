package com.codepath.example.gridimagesearch;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {

    public static final String SETTINGS = "com.codepath.example.gridimagesearch.settingsactivity.settings";

    Spinner spImageSize;
    ArrayAdapter<CharSequence> spImageSizeAdapter;

    Spinner spColor;
    ArrayAdapter<CharSequence> spImageColorAdapter;

    Spinner spImageType;
    ArrayAdapter<CharSequence> spImageTypeAdapter;

    EditText etSiteFilter;

    Button btSave;

    SearchSettings searchSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        searchSettings = (SearchSettings) getIntent().getSerializableExtra(SETTINGS);
        setupViews();
        ancestralNavigation();
    }

    @TargetApi(11)
    public void ancestralNavigation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupViews() {

        spImageSize = (Spinner)findViewById(R.id.spImageSize);
        spImageSizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_sizes,
                android.R.layout.simple_spinner_item);
        setupSpinner(spImageSize, spImageSizeAdapter, searchSettings.getImageSize());

        spImageType = (Spinner)findViewById(R.id.spImageType);
        spImageTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_types,
                android.R.layout.simple_spinner_item);
        setupSpinner(spImageType, spImageTypeAdapter, searchSettings.getImageType());

        spColor = (Spinner)findViewById(R.id.spColor);
        spImageColorAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_colors,
                android.R.layout.simple_spinner_item);
        setupSpinner(spColor, spImageColorAdapter, searchSettings.getImageColor());

        etSiteFilter = (EditText)findViewById(R.id.etSiteFilter);
        etSiteFilter.setText(searchSettings.getSiteFilter());

        btSave = (Button) findViewById(R.id.saveButton);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
            }
        });
    }

    private void setupSpinner(Spinner spinner, ArrayAdapter<CharSequence> adapter, String defaultValue){
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if(defaultValue != null) spinner.setSelection( adapter.getPosition(defaultValue) );
    }

    public void saveSettings(){
        int position;

        position = spColor.getSelectedItemPosition();
        searchSettings.setImageColor( valueOfSetting( spImageColorAdapter.getItem(position).toString() ) );

        position = spImageType.getSelectedItemPosition();
        searchSettings.setImageType( valueOfSetting(spImageTypeAdapter.getItem(position).toString()) );

        position = spImageSize.getSelectedItemPosition();
        searchSettings.setImageSize( valueOfSetting(spImageSizeAdapter.getItem(position).toString()) );

        searchSettings.setSiteFilter( etSiteFilter.getText().toString() );

        finishActivity();

    }

    private void finishActivity(){
        Intent i = new Intent();
        i.putExtra(SETTINGS, searchSettings);
        setResult(RESULT_OK, i);
        finish();
    }

    private String valueOfSetting(String value){
        if(value.trim() == "all") return "";

        return value;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
