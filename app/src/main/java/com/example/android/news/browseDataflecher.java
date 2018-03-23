package com.example.android.news;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justas on 2/21/2018.
 */

public class browseDataflecher {
    private static final String LOG_TAG = datafletcher.class.getSimpleName();

    private browseDataflecher() {
    } // Changed Class structure. Moved from OkHttp to raw code.

    public static List<browseObject> fetchsource(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {

            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {

            Log.e(LOG_TAG, "Problem making the HTTP request.", e);

        }

        List<browseObject> browseObjects = extractFeatureFromJson(jsonResponse);

        return browseObjects;
    }

    private static URL createUrl(String stringUrl) {

        URL url = null;

        try {

            url = new URL(stringUrl);

        } catch (MalformedURLException e) {

            Log.e(LOG_TAG, "Problem building the URL ", e);
        }

        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {

                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the article JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();

        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();

            while (line != null) {

                output.append(line);

                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<browseObject> extractFeatureFromJson(String articleJson) {
        if (TextUtils.isEmpty(articleJson)) {
            return null;
        }
        List<browseObject> objects = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(articleJson);
            JSONArray sources = baseJsonResponse.getJSONArray("sources");
            for (int i = 0; i < sources.length(); i++) {
                JSONObject currentsource = sources.getJSONObject(i);
                String id = currentsource.getString("id");
                String url = currentsource.getString("url");
                String des = currentsource.getString("description");
                String name = currentsource.getString("name");
                String category = currentsource.getString("category");
                String language = currentsource.getString("language");
                String country = currentsource.getString("country");
                browseObject thisobject = new browseObject(url, id, language, country, category, name, des);
                objects.add(thisobject);
            }
        } catch (JSONException e) {
            Log.e("browseDataflecher", "Problem parsing the articleJson JSON results", e);
        }

        return objects;
    }
}
