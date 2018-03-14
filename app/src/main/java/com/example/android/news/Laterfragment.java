package com.example.android.news;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Justas on 2/28/2018.
 */

public class Laterfragment extends Fragment {
    public Laterfragment(){}
    bookmartarticlesClass book;
    RecyclerView list;
    mAdapter adapter;
    WebView web;
    TextView errortext;
    ImageView closeweb;
    ImageView errorimage;
    String imgurl,url,des,author,titles,date;
    ProgressBar pbar;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout weblayout;
    ArrayList <article> favorites = new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.contentplace, container, false);
          createlist();
        swipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            refresh();
            isemptylist();
            }
        });
        weblayout = rootView.findViewById(R.id.weblayout);
        web = rootView.findViewById(R.id.webview);
        pbar = rootView.findViewById(R.id.pB1);
        errorimage = rootView.findViewById(R.id.alertpic);
        errortext = rootView.findViewById(R.id.alerttext);
        list = rootView.findViewById(R.id.recyclerview);
        closeweb = rootView.findViewById(R.id.closeweb);
        list.hasFixedSize();
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new mAdapter(getActivity(), favorites,weblayout,web,pbar,list,closeweb);
        list.setAdapter(adapter);
        isemptylist();
        return rootView;
    }
    void refresh()
    {
        favorites.clear();
        createlist();
        adapter = new mAdapter(getActivity(), favorites,weblayout,web,pbar,list,closeweb);
        list.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }
    void createlist()
    {
        book = new bookmartarticlesClass(getContext());
        for(int i=0; i<book.size(); i++)
        {
            imgurl= book.getImgUrls().get(i);
            url= book.getUrl().get(i);
            des= book.getDescription().get(i);
            author= book.getAuthor().get(i);
            titles= book.getTitles().get(i);
            date= book.getDate().get(i);

            article thisarticle = new article(imgurl,url,des,author,titles,date);
            favorites.add(thisarticle);
        }
    }
    public boolean onBackPressed() {
        if(adapter.isactiveweb())
        {
            adapter.clearwebview();
            return true;
        }
        else
            return false;
    }
    private void isemptylist()
    {
        if (favorites.isEmpty()||favorites == null||favorites.size()==0)
        {
            errorimage.setVisibility(View.VISIBLE);
            errortext.setVisibility(View.VISIBLE);
            errorimage.setImageResource(R.drawable.ic_laterd);
            errortext.setText(R.string.no_watchlater);
        }
        else {errortext.setVisibility(View.GONE); errorimage.setVisibility(View.GONE);}
    }
}
