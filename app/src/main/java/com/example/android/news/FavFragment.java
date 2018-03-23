package com.example.android.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Justas on 3/1/2018.
 */

public class FavFragment extends Fragment {
    FavoritesSave save;
    RecyclerView list;
    mAdapter adapter;
    WebView web;
    TextView errortext;
    ImageView closeweb;
    ImageView errorimage;
    String imgurl, url, description, author, titles, date, humanauthor;
    ProgressBar pbar;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout weblayout;
    ArrayList<article> favorites = new ArrayList<>();

    public FavFragment() {
    }

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
        list = rootView.findViewById(R.id.recyclerview);
        closeweb = rootView.findViewById(R.id.closeweb);
        errorimage = rootView.findViewById(R.id.alertpic);
        errortext = rootView.findViewById(R.id.alerttext);
        list.hasFixedSize();
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new mAdapter(getActivity(), favorites, weblayout, web, pbar, list, closeweb);
        list.setAdapter(adapter);
        isemptylist();
        return rootView;
    }

    void refresh() {
        favorites.clear();
        createlist();
        adapter = new mAdapter(getActivity(), favorites, weblayout, web, pbar, list, closeweb);
        list.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    void createlist() {
        save = new FavoritesSave(getContext());
        for (int i = 0; i < save.size(); i++) {
            try {
                imgurl = save.getImgUrls().get(i);
                url = save.getUrl().get(i);
                description = save.getDescription().get(i);
                author = save.getAuthor().get(i);
                titles = save.getTitles().get(i);
                date = save.getDate().get(i);
                humanauthor = save.getHumanauthors().get(i);
            } catch (Exception e) {
                Log.i("Fav_Fragment", "Failed to get item from saved class");
            }

            article thisarticle = new article(imgurl, url, description, author, titles, date, humanauthor);
            favorites.add(thisarticle);
        }
    }

    private void isemptylist() {
        if (favorites.isEmpty() || favorites == null || favorites.size() == 0) {
            errorimage.setVisibility(View.VISIBLE);
            errortext.setVisibility(View.VISIBLE);
            errorimage.setImageResource(R.drawable.ic_saved);
            errortext.setText(R.string.no_saved);
        } else {
            errortext.setVisibility(View.GONE);
            errorimage.setVisibility(View.GONE);
        }
    }
}
