package com.example.android.news;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Justas on 2/21/2018.
 */

public class browseDataflecher {

    private browseDataflecher() {
    }

    public static List<browseObject> fetchsource(String url) {
        String jsonResponse= null;
        try {
            jsonResponse = run(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<browseObject> objects = extractFeatureFromJson(jsonResponse);

        return objects;
    }


    public static String run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private static List<browseObject> extractFeatureFromJson(String articleJson) {
        if (TextUtils.isEmpty(articleJson)) {
            return null;
        }
        List<browseObject> objects = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(articleJson);
            JSONArray sources = baseJsonResponse.getJSONArray("sources");
            for (int i=0; i<sources.length(); i++)
            {
                JSONObject currentsource = sources.getJSONObject(i);
                String id = currentsource.getString("id");
                String url = currentsource.getString("url");
                String des = currentsource.getString("description");
                String name =currentsource.getString("name");
                String category = currentsource.getString("category");
                String language =currentsource.getString("language");
                String country = currentsource.getString("country");
                browseObject thisobject = new browseObject(url,id,language,country,category,name,des);
                objects.add(thisobject);
            }
        }
        catch (JSONException e) {
            Log.e("browseDataflecher", "Problem parsing the articleJson JSON results", e);
        }

        return objects;
    }
}
