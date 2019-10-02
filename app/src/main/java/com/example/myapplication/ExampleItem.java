package com.example.myapplication;

public class ExampleItem {

    private String mImageUrl;
    private String mtitle;
    private String mDescription;
    private String mprice;

    public ExampleItem(String mImageUrl, String mtitle, String mDescription, String mprice) {
        this.mImageUrl = mImageUrl;
        this.mtitle = mtitle;
        this.mDescription = mDescription;
        this.mprice = mprice;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getMtitle() {
        return mtitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getMprice() {
        return mprice;
    }
}
