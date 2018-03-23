package com.example.android.news;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;


/**
 * Created by Justas on 2/12/2018.
 */

public class articleLoader extends AsyncTaskLoader<List<article>> {
    private String mUrl;

    public articleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<article> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<article> articles = datafletcher.fetchArticleData(mUrl);
        return articles;
    }
}
