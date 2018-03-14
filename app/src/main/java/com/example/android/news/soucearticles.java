package com.example.android.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class soucearticles extends AppCompatActivity implements  android.support.v4.app.LoaderManager.LoaderCallbacks<List<article>> {
    ArrayList curento = new ArrayList();
    SubscribedList subscribedList;
    int LoaderID = 7;
    private Menu menu;
    WebView web;
    ProgressBar pbar;
    SwipeRefreshLayout swipeRefreshLayout;
    mAdapter adapter;
    RecyclerView list;
    Animation zoomin,zoomout;
    RelativeLayout weblayout;
    ImageView closeweb;
    Toolbar mActionBarToolbar;
    String souceId, sourcename;
    ActionBar ab;
    String URL = "https://newsapi.org/v2/everything?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contentplace);
        subscribedList = new SubscribedList(getApplicationContext());
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        weblayout = findViewById(R.id.weblayout);
        web = findViewById(R.id.webview);
        pbar = findViewById(R.id.pB1);
        closeweb = findViewById(R.id.closeweb);
        sourcename = getIntent().getStringExtra("souceName");

        mActionBarToolbar = findViewById(R.id.confirm_order_toolbar_layout);
        mActionBarToolbar.setTitle(sourcename);
        setSupportActionBar(mActionBarToolbar);
        mActionBarToolbar.setVisibility(View.VISIBLE);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mActionBarToolbar.setTitle(sourcename);
        souceId = getIntent().getStringExtra("souceId");
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(LoaderID, null, this);
        list = findViewById(R.id.recyclerview);
        list.hasFixedSize();
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        checkedpreviosly();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        curento = subscribedList.getNames();
        if (id == R.id.mybutton) {

            if(!curento.contains(sourcename)) {
                subscribedList.put(sourcename, souceId);
                checked();
                Toast.makeText(this, getIntent().getStringExtra("souceName")+ getString(R.string.successfully_subscribed), Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getApplicationContext(), R.string.already_subscribed, Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.minusbutton)
        {
            if(!curento.contains(sourcename)) {
                Toast.makeText(getApplicationContext(), R.string.havent_subscribed_yet, Toast.LENGTH_SHORT).show();

            }
            else
            {
                subscribedList.remove(sourcename, souceId);
                unchecked();
                Toast.makeText(this, getIntent().getStringExtra("souceName")+ getString(R.string.successfuly_unsubscribed), Toast.LENGTH_SHORT).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }
    public void refresh()
    {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(LoaderID, null, this);

    }
    public void checkedpreviosly ()
    {
        curento = subscribedList.getNames();
        if(curento.contains(sourcename))
        {
            checked();
        }
    }
    public void unchecked()
    {
        menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_subscribe));
        mActionBarToolbar.setTitleTextColor( getResources().getColor(R.color.white));
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_up);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }
    public void checked()
    {
        menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_checked));
        mActionBarToolbar.setTitleTextColor( getResources().getColor(R.color.colorAccent));
       final Drawable upArrow = getResources().getDrawable(R.drawable.ic_up);
        upArrow.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
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
    public Loader<List<article>> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("sources", souceId);
        uriBuilder.appendQueryParameter("apiKey", "5f539f96f2b34e40a12a38ceaa6865b9");
        return new articleLoader(this, uriBuilder.toString());
    }
    @Override
    public void onLoadFinished(Loader<List<article>> loader, List<article> articles) {
        swipeRefreshLayout.setRefreshing(false);
        ArrayList<article> artcl = (ArrayList<article>) articles;
        adapter = new mAdapter(getApplicationContext(), artcl,weblayout,web,pbar,list,closeweb);
        list.setAdapter(adapter);
    }
    @Override
    public void onLoaderReset(Loader<List<article>> loader) {
    }

}
