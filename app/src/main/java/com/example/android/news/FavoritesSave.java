package com.example.android.news;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Justas on 3/1/2018.
 */

public class FavoritesSave {
    Context mcontext;
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> imgUrls = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> author = new ArrayList<>();
    ArrayList<String> humanauthors = new ArrayList<>();

    public FavoritesSave(Context context) {
        mcontext = context;
        loadtitles();
        loadimgUrls();
        loaddescription();
        loaddates();
        loadauthors();
        loadurls();
        loadhumanauthors();
    }

    public void put(String title, String imgurl, String des, String urls, String dates, String authors, String humanauthor) {

        titles.add(title);
        imgUrls.add(imgurl);
        description.add(des);
        url.add(urls);
        date.add(dates);
        author.add(authors);
        humanauthors.add(humanauthor);

        savetitles();
        saveimgUrls();
        savedescription();
        saveurls();
        savedates();
        saveauthors();
        savehumanauthors();
    }

    public int size() {
        return titles.size();
    }

    public void remove(String title, String imgurl, String des, String urls, String dates, String authors, String humanauthor) {
        imgUrls.remove(imgurl);
        titles.remove(title);
        description.remove(des);
        url.remove(urls);
        date.remove(dates);
        author.remove(authors);
        humanauthors.remove(humanauthor);

        saveimgUrls();
        savetitles();
        savedescription();
        saveurls();
        savedates();
        saveauthors();
        savehumanauthors();
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public ArrayList<String> getImgUrls() {
        return imgUrls;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public ArrayList<String> getHumanauthors() {
        return humanauthors;
    }

    public ArrayList<String> getDate()

    {
        return date;
    }

    public ArrayList<String> getAuthor() {
        return author;
    }

    public void clearList() {
        titles.clear();
        imgUrls.clear();
        description.clear();
        url.clear();
        date.clear();
        author.clear();
        humanauthors.clear();

        saveimgUrls();
        savetitles();
        savedescription();
        saveurls();
        savedates();
        saveauthors();
        savehumanauthors();
    }

    public void savetitles() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : titles) {
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("titles1", stringBuilder.toString()).apply();
    }

    public void loadtitles() {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFS", 0);
        String namesString = preferences.getString("titles1", "");
        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> vardai = new ArrayList<>();
        vardai.addAll(Arrays.asList(items));
        try {
            String a = vardai.get(0);
            char c = a.charAt(0);
        } catch (Exception IndexOutOfBoundsException) {
            vardai.clear();
        }
        titles = vardai;
    }

    public void saveimgUrls() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : imgUrls) {
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("imgUrls1", stringBuilder.toString()).apply();
    }

    public void loadimgUrls() {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        String namesString = preferences.getString("imgUrls1", "");

        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> imig = new ArrayList<>();
        imig.addAll(Arrays.asList(items));
        try {
            String a = imig.get(0);
            char c = a.charAt(0);
        } catch (Exception IndexOutOfBoundsException) {
            imig.clear();
        }

        imgUrls = imig;
    }

    public void savedescription() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : description) {
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("description1", stringBuilder.toString()).apply();
    }

    public void loaddescription() {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        String namesString = preferences.getString("description1", "");

        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> desai = new ArrayList<>();
        desai.addAll(Arrays.asList(items));
        try {
            String a = desai.get(0);
            char c = a.charAt(0);
        } catch (Exception IndexOutOfBoundsException) {
            desai.clear();
        }

        description = desai;
    }

    public void saveurls() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : url) {
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("urls1", stringBuilder.toString()).apply();
    }

    public void loadurls() {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        String namesString = preferences.getString("urls1", "");

        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> reiksmes = new ArrayList<>();
        reiksmes.addAll(Arrays.asList(items));
        try {
            String a = reiksmes.get(0);
            char c = a.charAt(0);
        } catch (Exception IndexOutOfBoundsException) {
            reiksmes.clear();
        }

        url = reiksmes;
    }

    public void savedates() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : date) {
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("dates1", stringBuilder.toString()).apply();
    }

    public void loaddates() {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        String namesString = preferences.getString("dates1", "");

        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> reiksmes = new ArrayList<>();
        reiksmes.addAll(Arrays.asList(items));
        try {
            String a = reiksmes.get(0);
            char c = a.charAt(0);
        } catch (Exception IndexOutOfBoundsException) {
            reiksmes.clear();
        }

        date = reiksmes;
    }

    public void saveauthors() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : author) {
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("authors1", stringBuilder.toString()).apply();
    }

    public void loadauthors() {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        String namesString = preferences.getString("authors1", "");

        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> reiksmes = new ArrayList<>();
        reiksmes.addAll(Arrays.asList(items));
        try {
            String a = reiksmes.get(0);
            char c = a.charAt(0);
        } catch (Exception IndexOutOfBoundsException) {
            reiksmes.clear();
        }

        author = reiksmes;
    }

    public void savehumanauthors() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : humanauthors) {
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("humanauthors", stringBuilder.toString()).apply();
    }

    public void loadhumanauthors() {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        String namesString = preferences.getString("humanauthors", "");

        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> reiksmes = new ArrayList<>();
        reiksmes.addAll(Arrays.asList(items));
        try {
            String a = reiksmes.get(0);
            char c = a.charAt(0);
        } catch (Exception IndexOutOfBoundsException) {
            reiksmes.clear();
        }


        humanauthors = reiksmes;
    }
}
