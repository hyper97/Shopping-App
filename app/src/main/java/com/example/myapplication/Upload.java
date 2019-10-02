package com.example.myapplication;

import com.google.firebase.database.Exclude;

public class Upload  {

    private String mtitle;
    private String mdescription;
    private String mImageUrl;
    private String mprice;
    private String mkey;


    public Upload() {
    }

    public Upload(String price, String desciption, String title, String imageurl){

        mdescription = desciption;
        mprice = price;
        mtitle = title;
        mImageUrl = imageurl;

    }

    public String getMtitle() {
        return mtitle;
    }



    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getMdescription() {
        return mdescription;
    }

    public void setMdescription(String mdescription) {
        this.mdescription = mdescription;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getMprice() {
        return mprice;
    }

    public void setMprice(String mprice) {
        this.mprice = mprice;
    }




    public String getMkey() {
        return mkey;
    }



    @Exclude
    public void setMkey(String mkey) {
        this.mkey = mkey;
    }


}


