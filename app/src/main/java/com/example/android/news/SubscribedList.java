package com.example.android.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Justas on 2/24/2018.
 */

public class SubscribedList   {
    Context mcontext;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> values = new ArrayList<>();

    public SubscribedList(Context context)
    {
        mcontext=context;
        loadNames();
        loadValues();
    }

    public void put (String name, String value)
    {
        names.add(name);
        values.add(value);
        saveNames();
        saveValues();
    }
    public int size()
    {
        return names.size();
    }
    public void remove(String name, String value)
    {
        values.remove(value);
        names.remove(name);
        saveValues();
        saveNames();
    }
    public ArrayList<String> getNames()
    {
        return names;
    }
    public ArrayList<String> getValues() {return values;}

    public void clearList()
    {
        names.clear();
        values.clear();
        saveValues();
        saveNames();
    }
    public void saveNames()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : names)
        {
            stringBuilder.append(s);
            stringBuilder.append(",");
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFS",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("names",stringBuilder.toString()).apply();
    }
    public void loadNames()
    {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFS",0);
        String namesString = preferences.getString("names", "");
        String[] items = namesString.split(",");
        ArrayList<String> vardai = new ArrayList<>();
        vardai.addAll(Arrays.asList(items));
        try {
            String a= vardai.get(0);
            a.charAt(0);
        }
        catch (Exception IndexOutOfBoundsException)
        {
            vardai.clear();
        }
        names = vardai;
    }
    public void saveValues()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : values)
        {
            stringBuilder.append(s);
            stringBuilder.append(",");
        }
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("values",stringBuilder.toString()).apply();
    }
    public void loadValues()
    {
        SharedPreferences preferences = mcontext.getSharedPreferences("PREFSofValues",0);
        String namesString = preferences.getString("values", "");

        String[] items = namesString.split(",");
        ArrayList<String> reiksmes = new ArrayList<>();
        try {
            String a= reiksmes.get(0);
            a.charAt(0);
        }
        catch (Exception IndexOutOfBoundsException)
        {
            reiksmes.clear();
        }
        reiksmes.addAll(Arrays.asList(items));

        values=reiksmes;
    }
}
