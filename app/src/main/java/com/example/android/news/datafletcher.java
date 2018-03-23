package com.example.android.news;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
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
 * Created by Justas on 2/12/2018.
 */

public class datafletcher {
    private static final String LOG_TAG = datafletcher.class.getSimpleName();

    private datafletcher() {
    }

    public static List<article> fetchArticleData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {

            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {

            Log.e(LOG_TAG, "Problem making the HTTP request.", e);

        }

        List<article> articles = extractFeatureFromJson(jsonResponse);

        return articles;
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

    private static List<article> extractFeatureFromJson(String articleJson) {
        if (TextUtils.isEmpty(articleJson)) {
            return null;
        }
        List<article> articles = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(articleJson);
            JSONArray artic = baseJsonResponse.getJSONArray("articles");
            for (int i = 0; i < artic.length(); i++) {
                String humanauthor = "null";
                JSONObject articl = artic.getJSONObject(i);
                JSONObject source = articl.getJSONObject("source");
                String author = source.getString("name");
                if (articl.has("author")) {
                    humanauthor = articl.getString("author");
                }
                String title = articl.getString("title");
                String des = articl.getString("description");
                String imgUrl = articl.getString("urlToImage");
                String Url = articl.getString("url");
                String date = articl.getString("publishedAt");
                article thisarticle = new article(imgUrl, Url, des, author, title, date, humanauthor);
                articles.add(thisarticle);
            }
        } catch (Exception e) {
            Log.e("datafletcher", "Problem parsing the articleJson JSON results", e);
        }

        return articles;
    }
}
