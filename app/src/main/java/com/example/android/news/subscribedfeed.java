package com.example.android.news;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justas on 2/28/2018.
 */
public class subscribedfeed extends Fragment implements LoaderManager.LoaderCallbacks<List<article>> {
    String country = "", test = "";
    WebView web;
    SwipeRefreshLayout swipeRefreshLayout;
    mAdapter adapter;
    RecyclerView list;
    int LoaderID = 0;
    ImageView closeweb;
    TextView errortext;
    ImageView errorimage;
    Animation zoomin, zoomout;
    RelativeLayout weblayout;
    ProgressBar pbar;
    String URL = "https://newsapi.org/v2/everything?";

    public subscribedfeed() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.contentplace, container, false);
        swipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                isinternet();
            }
        });
        LoaderManager loaderManager = getLoaderManager();
        errorimage = rootView.findViewById(R.id.alertpic);
        errortext = rootView.findViewById(R.id.alerttext);
        loaderManager.initLoader(LoaderID, null, this);
        weblayout = rootView.findViewById(R.id.weblayout);
        web = rootView.findViewById(R.id.webview);
        pbar = rootView.findViewById(R.id.pB1);
        closeweb = rootView.findViewById(R.id.closeweb);
        list = rootView.findViewById(R.id.recyclerview);
        list.hasFixedSize();
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        isinternet();
        return rootView;
    }

    public void refresh() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(LoaderID, null, this);
    }

    @Override
    public Loader<List<article>> onCreateLoader(int i, Bundle bundle) {
        SubscribedListSave subscribed = new SubscribedListSave(getContext());
        String made = "";
        ArrayList<String> values = subscribed.getValues();
        Uri baseUri = Uri.parse(URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        for (int j = 0; j < values.size(); j++) {
            if (j == 0) {
                uriBuilder.appendQueryParameter(getString(R.string.src), values.get(0));
                made = uriBuilder.toString();
            } else
                made += values.get(j) + ",";

        }
        made = made + "&" + getString(R.string.apiKey) + "=" + getString(R.string.apikeyValue);
        if (values.size() > 0)
            return new articleLoader(getActivity(), made);
        else return null;
    }

    @Override
    public void onLoadFinished(Loader<List<article>> loader, List<article> articles) {
        swipeRefreshLayout.setRefreshing(false);
        ArrayList<article> artcl = (ArrayList<article>) articles;
        if (artcl == null || artcl.isEmpty() || artcl.size() == 0) {
            issubscribed();
        }
        adapter = new mAdapter(getActivity(), artcl, weblayout, web, pbar, list, closeweb);
        list.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<List<article>> loader) {
    }

    public boolean onBackPressed() {
        if (adapter.isactiveweb()) {
            adapter.clearwebview();
            return true;
        } else
            return false;
    }

    private void isinternet() {
        if (!Utils.isNetworkAvailable(getContext())) {
            errorimage.setVisibility(View.VISIBLE);
            errortext.setVisibility(View.VISIBLE);
            errorimage.setImageResource(R.drawable.country);
            errortext.setText(R.string.no_internet);
        } else {
            issubscribed();
        }
    }

    private void issubscribed() {
        if (Utils.isNetworkAvailable(getContext())) {
            errorimage.setVisibility(View.VISIBLE);
            errortext.setVisibility(View.VISIBLE);
            errorimage.setImageResource(R.drawable.newspaper);
            errortext.setText(R.string.nosubscribed);
        } else {
            isinternet();
        }
    }

}
