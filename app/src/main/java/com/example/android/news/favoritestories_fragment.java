package com.example.android.news;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.news.mAdapter.webatctive;

/**
 * Created by Justas on 2/13/2018.
 */

public class favoritestories_fragment extends Fragment implements  LoaderManager.LoaderCallbacks<List<article>> {
    String country="",test="",category="",cattest="";
    Animation zoomin,zoomout;
    RelativeLayout weblayout;
    WebView web;
    TextView errortext;
    ImageView errorimage;
    ProgressBar pbar;
    ImageView closeweb;
    RecyclerView list;
    public favoritestories_fragment() {
    }
    SwipeRefreshLayout swipeRefreshLayout;
    mAdapter adapter;
    int LoaderID = 0;
    String URL = "https://newsapi.org/v2/top-headlines?";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contentplace, container, false);
        webatctive = false;

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LoaderID, null, this);
        swipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                isinternet();
            }
        });
        weblayout = rootView.findViewById(R.id.weblayout);
        web = rootView.findViewById(R.id.webview);
        pbar = rootView.findViewById(R.id.pB1);
        list = rootView.findViewById(R.id.recyclerview);
        errorimage = rootView.findViewById(R.id.alertpic);
        closeweb = rootView.findViewById(R.id.closeweb);
        errortext = rootView.findViewById(R.id.alerttext);
        list.hasFixedSize();
        list.setItemViewCacheSize(0);
        ArrayList<article> artcl = new ArrayList<>();
        adapter = new mAdapter(getActivity(), artcl,weblayout,web,pbar,list,closeweb);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        isinternet();
        return rootView;
    }
    private void isinternet()
    {
        if (!Utils.isNetworkAvailable(getContext()))
        {
            errorimage.setVisibility(View.VISIBLE);
            errortext.setVisibility(View.VISIBLE);
            errorimage.setImageResource(R.drawable.country);
            errortext.setText(R.string.no_internet);
        }
        else {errortext.setVisibility(View.GONE); errorimage.setVisibility(View.GONE);}
    }
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        test = sharedPrefs.getString(getString(R.string.Country), getString(R.string.Country_default));
        cattest = sharedPrefs.getString(getString(R.string.Fav_topic), getString(R.string.Fav_topic_default));
        if(!test.equals(country)&& !test.equals("") || !cattest.equals(category) && !cattest.equals("")) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(LoaderID, null, this);
        }
    }
    public void refresh()
    {
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

        category = sharedPrefs.getString(getString(R.string.Fav_topic),"business");
        Uri baseUri = Uri.parse(URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter(getString(R.string.country), country);
        uriBuilder.appendQueryParameter(getString(R.string.category), category);
        uriBuilder.appendQueryParameter(getString(R.string.apiKey), getString(R.string.apikeyValue));
        Log.i("",uriBuilder+"");
        return new articleLoader(getActivity(), uriBuilder.toString());
    }
        public void clearwebview()
        {
            web.startAnimation(zoomout);
            web.stopLoading();
            zoomout.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation n) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    weblayout.setVisibility(View.GONE);
                    weblayout.setOnClickListener(null);
                    zoomout.setAnimationListener(null);
                }
                @Override
                public void onAnimationRepeat(Animation n) {
                }
            });
        }
    @Override
    public void onLoadFinished(Loader<List<article>> loader, List<article> articles) {
        swipeRefreshLayout.setRefreshing(false);
        ArrayList<article> artcl = (ArrayList<article>) articles;
        adapter = new mAdapter(getActivity(), artcl,weblayout,web,pbar,list,closeweb);
        list.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<List<article>> loader) {
    }
    public boolean onBackPressed() {

            adapter.clearwebview();
            return true;
    }
}
