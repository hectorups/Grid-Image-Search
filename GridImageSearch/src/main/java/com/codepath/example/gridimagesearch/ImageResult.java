package com.codepath.example.gridimagesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by hectormonserrate on 15/01/14.
 */
public class ImageResult implements Serializable {

    private static final long serialVersionUID = 2636389073439281291L;
    private String fullUrl;
    private String thumbUrl;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageResult(JSONObject json){
        try{
            fullUrl = json.getString("url");
            thumbUrl = json.getString("tbUrl");
            title = json.getString("titleNoFormatting");
        } catch (JSONException e) {

        }
    }

    @Override
    public String toString() {
        return thumbUrl;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public static ArrayList<ImageResult> fromJSONArray(JSONArray array){
        ArrayList<ImageResult> results = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            try{
                results.add(new ImageResult(array.getJSONObject(i)));
            }catch(JSONException e){e.printStackTrace();}
        }

        return results;
    }


}
