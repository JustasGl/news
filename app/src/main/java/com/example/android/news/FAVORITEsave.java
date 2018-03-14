package com.example.android.news;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Justas on 3/1/2018.
 */

public class FAVORITEsave {
    Context mcontext;
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> imgUrls = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();
    ArrayList <String> date = new ArrayList<>();
    ArrayList <String> author = new ArrayList<>();
    public FAVORITEsave(Context context)
    {
        mcontext=context;
        loadtitles();
        loadimgUrls();
        loaddescription();
        loaddates();
        loadauthors();
        loadurls();
    }

    public void put (String title, String imgurl, String des,String urls, String dates, String authors)
    {

        titles.add(title);
        imgUrls.add(imgurl);
        description.add(des);
        url.add(urls);
        date.add(dates);
        author.add(authors);

        savetitles();
        saveimgUrls();
        savedescription();
        saveurls();
        savedates();
        saveauthors();
    }
    public int size()
    {
        return titles.size();
    }
    public void remove(String title, String imgurl, String des,String urls, String dates, String authors)
    {
        imgUrls.remove(imgurl);
        titles.remove(title);
        description.remove(des);
        url.remove(urls);
        date.remove(dates);
        author.remove(authors);

        saveimgUrls();
        savetitles();
        savedescription();
        saveurls();
        savedates();
        saveauthors();
    }
    public ArrayList<String> getTitles()
    {
        return titles;
    }
    public ArrayList<String> getImgUrls()
    {
        return imgUrls;
    }
    public ArrayList<String> getDescription() {return description;}
    public ArrayList<String> getUrl(){return url;}
    public ArrayList<String> getDate()
    {
        return date;
    }
    public ArrayList<String> getAuthor()
    {
        return author;
    }

    public void clearList()
    {
        titles.clear();
        imgUrls.clear();
        description.clear();
        url.clear();
        date.clear();
        author.clear();

        saveimgUrls();
        savetitles();
        savedescription();
        saveurls();
        savedates();
        saveauthors();
    }
    public void savetitles()
    {
        int i=0;
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : titles)
        {
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
            i++;
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFS",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("titles1",stringBuilder.toString()).apply();
    }
    public void loadtitles()
    {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFS",0);
        String namesString = preferences.getString("titles1", "");
        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> vardai = new ArrayList<>();
        vardai.addAll(Arrays.asList(items));
        try {
            String a= vardai.get(0);
            char c =a.charAt(0);
        }
        catch (Exception IndexOutOfBoundsException)
        {
            vardai.clear();
        }
        titles = vardai;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void saveimgUrls()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : imgUrls)
        {
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("imgUrls1",stringBuilder.toString()).apply();
    }
    public void loadimgUrls()
    {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues",0);
        String namesString = preferences.getString("imgUrls1", "");

        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> imig = new ArrayList<>();
        imig.addAll(Arrays.asList(items));
        try {
            String a= imig.get(0);
            char c =a.charAt(0);
        }
        catch (Exception IndexOutOfBoundsException)
        {
            imig.clear();
        }


        imgUrls=imig;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void savedescription()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : description)
        {
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("description1",stringBuilder.toString()).apply();
    }

    public void loaddescription()
    {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues",0);
        String namesString = preferences.getString("description1", "");

        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> desai = new ArrayList<>();
        desai.addAll(Arrays.asList(items));
        try {
            String a= desai.get(0);
            char c =a.charAt(0);
        }
        catch (Exception IndexOutOfBoundsException)
        {
            desai.clear();
        }


        description=desai;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void saveurls()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : url)
        {
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("urls1",stringBuilder.toString()).apply();
    }

    public void loadurls()
    {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues",0);
        String namesString = preferences.getString("urls1", "");

        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> reiksmes = new ArrayList<>();
        reiksmes.addAll(Arrays.asList(items));
        try {
            String a= reiksmes.get(0);
            char c =a.charAt(0);
        }
        catch (Exception IndexOutOfBoundsException)
        {
            reiksmes.clear();
        }


        url=reiksmes;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void savedates()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : date)
        {
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("dates1",stringBuilder.toString()).apply();
    }

    public void loaddates()
    {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues",0);
        String namesString = preferences.getString("dates1", "");

        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> reiksmes = new ArrayList<>();
        reiksmes.addAll(Arrays.asList(items));
        try {
            String a= reiksmes.get(0);
            char c =a.charAt(0);
        }
        catch (Exception IndexOutOfBoundsException)
        {
            reiksmes.clear();
        }


        date=reiksmes;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void saveauthors()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : author)
        {
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("authors1",stringBuilder.toString()).apply();
    }

    public void loadauthors()
    {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues",0);
        String namesString = preferences.getString("authors1", "");

        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> reiksmes = new ArrayList<>();
        reiksmes.addAll(Arrays.asList(items));
        try {
            String a= reiksmes.get(0);
            char c =a.charAt(0);
        }
        catch (Exception IndexOutOfBoundsException)
        {
            reiksmes.clear();
        }


        author=reiksmes;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
