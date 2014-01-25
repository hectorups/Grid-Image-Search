package com.codepath.example.gridimagesearch;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hectormonserrate on 15/01/14.
 */
public class ImageResult implements Parcelable {

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


    protected ImageResult(Parcel in) {
        fullUrl = in.readString();
        thumbUrl = in.readString();
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullUrl);
        dest.writeString(thumbUrl);
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ImageResult> CREATOR = new Parcelable.Creator<ImageResult>() {
        @Override
        public ImageResult createFromParcel(Parcel in) {
            return new ImageResult(in);
        }

        @Override
        public ImageResult[] newArray(int size) {
            return new ImageResult[size];
        }
    };
}
