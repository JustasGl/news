package com.example.android.news;

/**
 * Created by Justas on 2/12/2018.
 */

public class article { // sorry couldin't refractor some class names ecouse of errors, but I will keep this in the future ;)
    private String mImgUrl, // Image url
            mdescription, //Short piece of article
            mauthor, // Publisher ex. Abc news...
            mtitle, // Title of article
            mUrl, // Url to whole article
            mDateP, // Date when article was published
            mHumanAuthor; // The author of this article

    public article(String ImgUrl, String url, String description, String author, String title, String dateP, String HumanAuthor) {
        mImgUrl = ImgUrl;
        mdescription = description;
        mauthor = author;
        mtitle = title;
        mUrl = url;
        mDateP = dateP;
        mHumanAuthor = HumanAuthor;
    }

    public String getMauthor() {
        return mauthor;
    }

    public String getMdes() {
        return mdescription;
    }

    public String getMtitle() {
        return mtitle;
    }

    public String getmImgUrl() {
        return mImgUrl;
    }

    public String getmDateP() {
        return mDateP;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmHumanAuthor() {
        return mHumanAuthor;
    }
}
