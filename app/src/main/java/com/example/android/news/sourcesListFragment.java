package com.example.android.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justas on 2/28/2018.
 */

public class sourcesListFragment extends Fragment implements  LoaderManager.LoaderCallbacks<List<browseObject>> {
    public sourcesListFragment(){
    }
    private static final String BUNDLE_RECYCLER_LAYOUT = "sourcesListFragment.recycler.layout";
    String URL="https://newsapi.org/v2/sources?apiKey=5f539f96f2b34e40a12a38ceaa6865b9";
    RecyclermAdaptor adapter;
    int LoaderID = 6;
    SwipeRefreshLayout swipe;
    ProgressBar pbar;
    TextView errortext;
    ImageView errorimage;
    Animation zoomin,zoomout;
    RecyclerView list;
    RelativeLayout weblayout;
    RecyclermAdaptor recycleradaptor;
    boolean loaded=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contentplace, container, false);
        swipe = rootView.findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loaded=false;
                refresh();
                isinternet();
            }
        });
        list = rootView.findViewById(R.id.recyclerview);
        list.hasFixedSize();
        errorimage = rootView.findViewById(R.id.alertpic);
        errortext = rootView.findViewById(R.id.alerttext);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        createloadermanaer();
        isinternet();
        return rootView;
    }
    public void refresh()
    {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(LoaderID, null, this);

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
    public void createloadermanaer(){
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LoaderID, null, this);
    }
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            list.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, list.getLayoutManager().onSaveInstanceState());
    }
    @Override
    public Loader<List<browseObject>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter(getString(R.string.apiKey), getString(R.string.apikeyValue));
        return new BrowseSouceloader(getContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<browseObject>> loader, List<browseObject> objects) {
        swipe.setRefreshing(false);
        if(!loaded) {
            ArrayList<browseObject> object = (ArrayList<browseObject>) objects;
            recycleradaptor = new RecyclermAdaptor(getContext(), object);
            list.setAdapter(recycleradaptor);
            loaded=true;
        }
    }
    @Override
    public void onLoaderReset(Loader<List<browseObject>> loader) {}

}