package com.example.android.news;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

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
