package com.codepath.example.gridimagesearch;

import java.io.Serializable;

/**
 * Created by hectormonserrate on 17/01/14.
 */
public class SearchSettings implements Serializable {
    private static final long serialVersionUID = 5177222050535318633L;

    private static final String PREFS_FILE = "MyPrefsFile";
    private static final String IMG_SIZE = "image_size";
    private static final String IMG_COLOR = "image_color";
    private static final String IMG_TYPE = "image_type";
    private static final String SITE_FILTER = "site_filter";

    private String imageColor;
    private String imageType;
    private String imageSize;
    private String siteFilter;


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

    public boolean equals(SearchSettings b){
        return imageColor == b.imageColor && imageType == b.imageType && imageSize == b.imageSize
                && siteFilter == b.siteFilter;
    }
}
