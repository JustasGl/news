package com.example.android.news;

/**
 * Created by Justas on 2/21/2018.
 */

public class browseObject {

    String Murl = null;
    String Mid = null;
    String Mlanguage = null;
    String Mcountry = null;
    String Mcategory = null;
    String Mname = null;
    String Mdescription = null;

    public browseObject(String url, String id, String language, String country, String category, String name, String description) {
        Murl = url;
        Mid = id;
        Mlanguage = language;
        Mcountry = country;
        Mcategory = category;
        Mname = name;
        Mdescription = description;
    }

    public String getMurl() {
        return Murl;
    } // Not used right now but leave it there for future updates

    public String getMid() {
        return Mid;
    }

    public String getMlanguage() {
        return Mlanguage;
    }

    public String getMcountry() {
        return Mcountry;
    }// Not used right now but leave it there for future updates

    public String getMcategory() {
        return Mcategory;
    }

    public String getMname() {
        return Mname;
    }

    public String getMdescription() {
        return Mdescription;
    }
}
