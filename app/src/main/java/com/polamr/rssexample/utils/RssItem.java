package com.polamr.rssexample.utils;

public class RssItem {

    private String mTitle;
    private String mId;
    private String mDescription;
    private String mImageUrl;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String _mDescription) {
        this.mDescription = _mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String _mImageUrl) {
        this.mImageUrl = _mImageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String _mTitle) {
        this.mTitle = _mTitle;
    }

    public String getId() {
        return mId;
    }

    public void setId(String _mId) {
        this.mId = _mId;
    }


}
