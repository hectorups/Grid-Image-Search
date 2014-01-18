package com.codepath.example.gridimagesearch;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hectormonserrate on 16/01/14.
 */
public class ImageManager {
    private final String TAG = "ImageManager";

    public static interface ImageManagerCallback {
      public void onSuccess(ArrayList<ImageResult> results);
    };

    private final int GOOGLE_MAX_IMAGES = 8;
    private static ImageManager instance;

    private SearchSettings searchSettings;

    public static ImageManager getInstance(SearchSettings searchSettings){
        if(instance == null) {
            instance = new ImageManager(searchSettings);
        }
        return instance;
    }

    private ImageManager(SearchSettings searchSettings){
        this.searchSettings = searchSettings;
    }




    public void queryImages(String query, final int page, final ImageManagerCallback imageManagerCallback){
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(getUrl(query, page * GOOGLE_MAX_IMAGES), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                JSONArray imageJsonResults;
                try {
                    imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
                    Log.d(TAG, "PAGE " + page + " RESULT COUNT: " + imageJsonResults.length());
                    imageManagerCallback.onSuccess(ImageResult.fromJSONArray(imageJsonResults));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private String getUrl(String query, int start){
        String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=" + GOOGLE_MAX_IMAGES
                + "&start=" + start
                + "&q=" + query
                + imageColorParam()
                + imageSizeParam()
                + imageTypeParam()
                + siteFilterParam();

        Log.d(TAG, url);

        return url;
    }

    private String imageSizeParam(){
        if(!isValidSetting(searchSettings.getImageSize())){
           return "";
        }
        return "&imgsz=" + searchSettings.getImageSize();
    }

    private String imageTypeParam(){
        if(!isValidSetting(searchSettings.getImageType())){
            return "";
        }
        return "&imgtype=" + searchSettings.getImageType();
    }

    private String imageColorParam(){
        if(!isValidSetting(searchSettings.getImageColor()) ){
            return "";
        }
        return "&imgcolor=" + searchSettings.getImageColor();
    }

    private String siteFilterParam(){
        if(!isValidSetting(searchSettings.getSiteFilter())){
            return "";
        }
        return "&as_sitesearch=" + searchSettings.getSiteFilter();
    }

    private boolean isValidSetting(String setting){
        return setting != null && setting.trim() != "";
    }


}
