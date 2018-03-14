package com.example.android.news;

/**
 * Created by Justas on 2/12/2018.
 */

public class article {
    private String mImgUrl,mdes,mauthor,mtitle,mUrl,mDateP;
    public article(String ImgUrl, String url, String des, String author,String title, String dateP)
    {
        mImgUrl=ImgUrl;
        mdes=des;
        mauthor=author;
        mtitle=title;
        mUrl=url;
        mDateP=dateP;
    }

    public String getMauthor() {
        return mauthor;
    }

    public String getMdes() {
        return mdes;
    }

    public String getMtitle(){return mtitle;}

    public String getmImgUrl() {
        return mImgUrl;
    }

    public String getmDateP(){return mDateP;}

    public String getmUrl (){return mUrl;}
}
