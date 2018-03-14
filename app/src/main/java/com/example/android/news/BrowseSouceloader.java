package com.example.android.news;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.example.android.news.datafletcher;
import java.util.List;

/**
 * Created by Justas on 2/21/2018.
 */

public class BrowseSouceloader extends AsyncTaskLoader<List<browseObject>> {
    private String mUrl;

    public BrowseSouceloader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<browseObject> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<browseObject> objects = browseDataflecher.fetchsource(mUrl);
        return objects;
    }
}
