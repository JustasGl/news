package com.example.android.news;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Justas on 2/12/2018.
 */

public class datafletcher {
        private static final String LOG_TAG = datafletcher.class.getSimpleName();

        private datafletcher() {
        }

        public static List<article> fetchArticleData(String url) {
            String jsonResponse= null;
            try {
                jsonResponse = run(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<article> articles = extractFeatureFromJson(jsonResponse);

            return articles;
        }


    public static String run(String url) throws IOException {
            OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

        private static List<article> extractFeatureFromJson(String articleJson) {
            if (TextUtils.isEmpty(articleJson)) {
                return null;
            }
            List<article> articles = new ArrayList<>();
            try {
                JSONObject baseJsonResponse = new JSONObject(articleJson);
                JSONArray artic = baseJsonResponse.getJSONArray("articles");
                for (int i=0; i<artic.length(); i++)
                {
                    JSONObject articl = artic.getJSONObject(i);
                        JSONObject source = articl.getJSONObject("source");
                        String author = source.getString("name");

                        String title = articl.getString("title");
                        String des = articl.getString("description");
                        String imgUrl = articl.getString("urlToImage");
                        String Url = articl.getString("url");
                        String date = articl.getString("publishedAt");
                        article thisarticle = new article(imgUrl,Url,des,author,title,date);
                        articles.add(thisarticle);
                }
                }
            catch (Exception e) {
                Log.e("datafletcher", "Problem parsing the articleJson JSON results", e);
            }

            return articles;
        }
    }
