package com.example.android.news;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.example.android.news.datafletcher;
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
