package com.codepath.example.gridimagesearch;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hectormonserrate on 17/01/14.
 */
public class SearchSettings {
    private Context context;

    private static final String PREFS_FILE = "MyPrefsFile";
    private static final String IMG_SIZE = "image_size";
    private static final String IMG_COLOR = "image_color";
    private static final String IMG_TYPE = "image_type";
    private static final String SITE_FILTER = "site_filter";

    private String imageColor;
    private String imageType;
    private String imageSize;
    private String siteFilter;
    private static SearchSettings instance;

    public static SearchSettings getInstance(Context context){
        if( instance == null ) {
            instance = new SearchSettings(context.getApplicationContext());
        }

        return instance;
    }

    private SearchSettings(Context context){
        this.context = context;
        loadSettings();
    }

    private void loadSettings(){
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE, 0);
        imageColor = settings.getString(IMG_COLOR, "");
        imageSize = settings.getString(IMG_SIZE, "");
        imageType = settings.getString(IMG_TYPE, "");
        siteFilter = settings.getString(SITE_FILTER, "");
    }

    public void saveSettings(){
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(IMG_COLOR,  imageColor);

        editor.putString(IMG_TYPE, imageType );

        editor.putString(IMG_SIZE, imageSize);

        editor.putString(SITE_FILTER, siteFilter );

        editor.commit();

    }

    public String getImageColor() {
        return imageColor;
    }

    public void setImageColor(String imageColor) {
        this.imageColor = imageColor;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }
}
