package com.example.android.news;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Justas on 2/27/2018.
 */

public class bookmartarticlesClass {
    Context mcontext;
    ArrayList<String> titles = new ArrayList<>(); // Articles Titles list
    ArrayList<String> imgUrls = new ArrayList<>(); // Articles Image Urls list
    ArrayList<String> description = new ArrayList<>();// Articles Description list
    ArrayList<String> url = new ArrayList<>();// Articles Urls list
    ArrayList<String> date = new ArrayList<>();// Articles dates list
    ArrayList<String> author = new ArrayList<>();// Articles publishers list
    ArrayList<String> humanauthors = new ArrayList<>();// Articles authors list

    public bookmartarticlesClass(Context context) {
        mcontext = context;
        loadtitles();
        loadimgUrls();
        loaddescription();
        loaddates();
        loadauthors();
        loadurls();
        loadhumanauthors();
        //When costructor get's called all lists get's loaded, now i would do this with database, but back then didin't knew about databases...
    }

    public void put(String title, String imgurl, String des, String urls, String dates, String authors, String humanauthor) {
        titles.add(title);
        imgUrls.add(imgurl);
        description.add(des);
        url.add(urls);
        date.add(dates);
        author.add(authors);
        humanauthors.add(humanauthor);

        //Adds item to list and then saves it

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

        //Removes certain item from lists and then saves them

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

    public ArrayList<String> getDate() {
        return date;
    }

    public ArrayList<String> gethumanauthors() {
        return humanauthors;
    }

    public ArrayList<String> getAuthor() {
        return author;
    }

    public void clearList() { //Currently not used but I'll leavit for the future updates
        titles.clear();
        imgUrls.clear();
        description.clear();
        url.clear();
        date.clear();
        author.clear();
        humanauthors.clear();

        //Clears lists and then saves them

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
        for (String s : titles) { //Builds one large string out off list and seperates with ✲ symbol (Choose this becouse i never saw any newspaper use it.
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("titles", stringBuilder.toString()).apply();
    }

    public void loadtitles() {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFS", 0);
        String namesString = preferences.getString("titles", "");
        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> vardai = new ArrayList<>();
        vardai.addAll(Arrays.asList(items));
        try { //Had some problem with this part, because there sometimes been symbol/empty sapce/null inside string so had to check it
            String a = vardai.get(0);
            char c = a.charAt(0);
        } catch (Exception IndexOutOfBoundsException) {
            vardai.clear(); // if there is that symbol/null then we clear the list somehow this helps :D
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
        editor.putString("imgUrls", stringBuilder.toString()).apply();
    }

    public void loadimgUrls() {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        String namesString = preferences.getString("imgUrls", "");

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
        editor.putString("description", stringBuilder.toString()).apply();
    }

    public void loaddescription() {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        String namesString = preferences.getString("description", "");

        String[] items = namesString.split(mcontext.getString(R.string.regex));
        ArrayList<String> descriptions = new ArrayList<>();
        descriptions.addAll(Arrays.asList(items));
        try {
            String a = descriptions.get(0);
            char c = a.charAt(0);
        } catch (Exception IndexOutOfBoundsException) {
            descriptions.clear();
        }

        description = descriptions;
    }

    public void saveurls() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : url) {
            stringBuilder.append(s);
            stringBuilder.append(mcontext.getString(R.string.regex));
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("urls", stringBuilder.toString()).apply();
    }

    public void loadurls() {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        String namesString = preferences.getString("urls", "");

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
        editor.putString("dates", stringBuilder.toString()).apply();
    }

    public void loaddates() {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        String namesString = preferences.getString("dates", "");

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
        editor.putString("authors", stringBuilder.toString()).apply();
    }

    public void loadauthors() {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues", 0);
        String namesString = preferences.getString("authors", "");

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
