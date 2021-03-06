package com.example.android.news;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
 * Created by Justas on 2/19/2018.
 */

public class science_fragment extends Fragment implements LoaderManager.LoaderCallbacks<List<article>> {
    String country = "", test = "";
    WebView web;
    SwipeRefreshLayout swipeRefreshLayout;
    mAdapter adapter;
    int LoaderID = 6;
    ProgressBar pbar;
    TextView errortext;
    ImageView errorimage;
    Animation zoomin, zoomout;
    RecyclerView list;
    RelativeLayout weblayout;
    ImageView closeweb;
    String URL = "https://newsapi.org/v2/top-headlines?";
    private TextView emptyView;

    public science_fragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contentplace, container, false);
        swipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                isinternet();
            }
        });
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LoaderID, null, this);
        weblayout = rootView.findViewById(R.id.weblayout);
        web = rootView.findViewById(R.id.webview);
        pbar = rootView.findViewById(R.id.pB1);
        emptyView = rootView.findViewById(R.id.empty_view);
        closeweb = rootView.findViewById(R.id.closeweb);
        errorimage = rootView.findViewById(R.id.alertpic);
        errortext = rootView.findViewById(R.id.alerttext);
        list = rootView.findViewById(R.id.recyclerview);
        list.hasFixedSize();
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        isinternet();
        return rootView;
    }

    public void onResume() {
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        test = sharedPrefs.getString(getString(R.string.Country), getString(R.string.Country_default));
        if (!test.equals(country) && !test.equals("")) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(LoaderID, null, this);
        }
    }

    private void isinternet() {
        if (!Utils.isNetworkAvailable(getContext())) {
            errorimage.setVisibility(View.VISIBLE);
            errortext.setVisibility(View.VISIBLE);
            errorimage.setImageResource(R.drawable.country);
            errortext.setText(R.string.no_internet);
        } else {
            errortext.setVisibility(View.GONE);
            errorimage.setVisibility(View.GONE);
        }
    }

    public void refresh() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(LoaderID, null, this);

    }

    @Override
    public Loader<List<article>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        country = sharedPrefs.getString(
                getString(R.string.Country),
                getString(R.string.Country_default)
        );
        Uri baseUri = Uri.parse(URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter(getString(R.string.country), country);
        uriBuilder.appendQueryParameter(getString(R.string.category), getActivity().getString(R.string.Science));
        uriBuilder.appendQueryParameter(getString(R.string.apiKey), getString(R.string.apikeyValue));
        return new articleLoader(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<article>> loader, List<article> articles) {
        swipeRefreshLayout.setRefreshing(false);
        ArrayList<article> artcl = (ArrayList<article>) articles;
        if (artcl != null) {
            if (artcl.isEmpty()) {
                list.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                adapter = new mAdapter(getActivity(), artcl, weblayout, web, pbar, list, closeweb);
                list.setAdapter(adapter);
            }
        }
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
}