package com.example.android.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Justas on 2/22/2018.
 */

public class mAdapter extends RecyclerView.Adapter<mAdapter.MyViewHolder> { // Adapter for articles

    static Boolean webatctive = false; // Used to track weather WebView is active or not for back button click if WebView is active then we close it with back button click else close activity or something else
    Context mcontext;
    ArrayList<article> marrayList = new ArrayList<>();
    ArrayList<String> titiles = new ArrayList<>();
    ArrayList<String> favtitles = new ArrayList<>();
    RelativeLayout webl;
    WebView webas;
    Animation zoomin, zoomout;
    ProgressBar pbar;
    RecyclerView list;
    bookmartarticlesClass bookmart;
    FavoritesSave fav;
    ImageView closeweb;

    mAdapter(Context context, ArrayList<article> arrayList, RelativeLayout weblayout, WebView web, ProgressBar progressBar, RecyclerView listView, ImageView closeweb) {

        marrayList = arrayList;
        mcontext = context;
        webl = weblayout;
        webas = web;
        zoomin = AnimationUtils.loadAnimation(mcontext, R.anim.zoom_in);
        zoomout = AnimationUtils.loadAnimation(mcontext, R.anim.zoom_out);
        pbar = progressBar;
        list = listView;
        bookmart = new bookmartarticlesClass(mcontext);
        fav = new FavoritesSave(mcontext);
        webatctive = false;
        this.closeweb = closeweb;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        return new MyViewHolder(view);
    }

    public Boolean isactiveweb() {
        Log.i("iswebactive", webatctive + "");
        return webatctive;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (marrayList.get(position).getMauthor().equals("null") || marrayList.get(position).getMauthor() == null)
            holder.author.setVisibility(View.GONE);
        else {
            holder.author.setVisibility(View.VISIBLE);
            holder.author.setText(marrayList.get(position).getMauthor());
        }
        if (marrayList.get(position).getMdes().equals("null") || marrayList.get(position).getMdes() == null)
            holder.des.setVisibility(View.GONE);
        else {
            holder.des.setVisibility(View.VISIBLE);
            holder.des.setText(marrayList.get(position).getMdes());
        }
        if (marrayList.get(position).getMtitle().equals("null") || marrayList.get(position).getMtitle() == null)
            holder.title.setVisibility(View.GONE);
        else {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(marrayList.get(position).getMtitle());
        }
        if (marrayList.get(position).getmDateP().equals("null") || marrayList.get(position).getmDateP() == null)
            holder.date.setVisibility(View.GONE);
        else {
            holder.date.setVisibility(View.VISIBLE);
            holder.data = marrayList.get(position).getmDateP();
            holder.parts = holder.data.split("T"); //Splits date and time in two seperate Strings and removes T and Z
            holder.parts[1] = holder.parts[1].replace("Z", "");
            holder.date.setText(holder.parts[1]);
            holder.time.setText(holder.parts[0]);
        }
        if (marrayList.get(position).getmHumanAuthor().equals("null") || marrayList.get(position).getmHumanAuthor() == null || marrayList.get(position).getmHumanAuthor().equals("") || marrayList.get(position).getmHumanAuthor().isEmpty()) {
            holder.humanauthor.setVisibility(View.GONE);
        } else {
            holder.humanauthor.setVisibility(View.VISIBLE);
            holder.humanauthor.setText(marrayList.get(position).getmHumanAuthor());
        }
        holder.imgage.setVisibility(View.VISIBLE);
        holder.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webatctive = true;
                Bitmap originalBitmap = blurBuilder.getBitmapFromView(list);
                Bitmap blurredBitmap = blurBuilder.blur(mcontext, originalBitmap);
                webl.setBackground(new BitmapDrawable(mcontext.getResources(), blurredBitmap));
                webas.startAnimation(zoomin);
                pbar.startAnimation(zoomin);
                closeweb.startAnimation(zoomin);
                webas.setWebViewClient(new WebViewClient());
                webas.setWebChromeClient(new WebChromeClient() {
                    public void onProgressChanged(WebView view, int progress) {
                        if (progress < 100 && pbar.getVisibility() == ProgressBar.GONE) {
                            pbar.setVisibility(ProgressBar.VISIBLE);
                        }
                        pbar.setProgress(progress);
                        if (progress == 100) {
                            pbar.setVisibility(ProgressBar.GONE);
                        }
                    }
                });
                webas.getSettings().setJavaScriptEnabled(true);
                webas.loadUrl(marrayList.get(position).getmUrl());
                webas.setWebChromeClient(new WebChromeClient() {
                    public void onProgressChanged(WebView view, int progress) {
                        if (progress < 100 && pbar.getVisibility() == ProgressBar.GONE) {
                            pbar.setVisibility(ProgressBar.VISIBLE);
                        }

                        pbar.setProgress(progress);
                        if (progress == 100) {
                            pbar.setVisibility(ProgressBar.GONE);
                        }
                    }
                });
                webl.setVisibility(View.VISIBLE);
                webl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clearwebview();
                    }
                });
                closeweb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clearwebview();
                    }
                });
            }
        });
        holder.laterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // Checks whether user has already clicked LATERBUTTON if yes then changes it color to Pink
                titiles = bookmart.getTitles();
                if (!titiles.contains(marrayList.get(position).getMtitle())) {
                    bookmart.put(marrayList.get(position).getMtitle(), marrayList.get(position).getmImgUrl(), marrayList.get(position).getMdes(), marrayList.get(position).getmUrl(), marrayList.get(position).getmDateP(), marrayList.get(position).getMauthor(), marrayList.get(position).getmHumanAuthor());
                    holder.laterbutton.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.colorAccent, null));
                } else {
                    bookmart.remove(marrayList.get(position).getMtitle(), marrayList.get(position).getmImgUrl(), marrayList.get(position).getMdes(), marrayList.get(position).getmUrl(), marrayList.get(position).getmDateP(), marrayList.get(position).getMauthor(), marrayList.get(position).getmHumanAuthor());
                    holder.laterbutton.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.notselected, null));
                }
            }
        });
        holder.favbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favtitles = fav.getTitles();
                if (!favtitles.contains(marrayList.get(position).getMtitle())) {
                    fav.put(marrayList.get(position).getMtitle(), marrayList.get(position).getmImgUrl(), marrayList.get(position).getMdes(), marrayList.get(position).getmUrl(), marrayList.get(position).getmDateP(), marrayList.get(position).getMauthor(), marrayList.get(position).getmHumanAuthor());
                    holder.favbutton.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.colorAccent, null));
                } else {
                    fav.remove(marrayList.get(position).getMtitle(), marrayList.get(position).getmImgUrl(), marrayList.get(position).getMdes(), marrayList.get(position).getmUrl(), marrayList.get(position).getmDateP(), marrayList.get(position).getMauthor(), marrayList.get(position).getmHumanAuthor());
                    holder.favbutton.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.notselected, null));
                }
            }
        });
        titiles = bookmart.getTitles();
        favtitles = fav.getTitles();
        if (favtitles.contains(marrayList.get(position).getMtitle())) {
            holder.favbutton.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.colorAccent, null));
        } else {
            holder.favbutton.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.notselected, null));
        }
        if (titiles.contains(marrayList.get(position).getMtitle())) {
            holder.laterbutton.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.colorAccent, null));
        } else {
            holder.laterbutton.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.notselected, null));
        }
        if (marrayList.get(position).getmImgUrl() == null || marrayList.get(position).getmImgUrl().equals("") || marrayList.get(position).getmImgUrl().equals("null") || marrayList.get(position).getmImgUrl().isEmpty()) {
            parentswitcher(holder);
        } else {
            holder.imgage.setVisibility(View.VISIBLE);
            Picasso.with(mcontext).load(marrayList.get(position).getmImgUrl()).placeholder(R.drawable.loading).error(R.drawable.error).into(holder.imgage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    parentswitcher(holder);
                } // If there is no image of image failed to load we call parentswitcher to remove image view and change views location backgrounds etc...
            });
        }
        holder.openbrowser.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.notselected, null));
        holder.openbrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startbrowser(position);
            }
        });
    }

    private void startbrowser(int position) {
        Uri articleURI = Uri.parse(marrayList.get(position).getmUrl());
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleURI);
        mcontext.startActivity(websiteIntent);
    }

    private void parentswitcher(MyViewHolder holder) {
        holder.imgage.setVisibility(View.GONE);
        holder.rel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        holder.author.setBackgroundColor(mcontext.getResources().getColor(R.color.listcolor));
        holder.author.setTextColor(mcontext.getResources().getColor(R.color.black));
        holder.author.setGravity(Gravity.CENTER);
        holder.author.setTextSize(24);

        holder.date.setTextColor(mcontext.getResources().getColor(R.color.black));

        holder.time.setBackgroundColor(mcontext.getResources().getColor(R.color.listcolor));
        holder.time.setTextColor(mcontext.getResources().getColor(R.color.black));

        ViewGroup parentas = (ViewGroup) holder.datentime.getParent();

        if (parentas != null) parentas.removeView(holder.datentime);

        ViewGroup tevas = (ViewGroup) holder.title.getParent();

        if (tevas != null) tevas.removeView(holder.title);

        holder.newparent.addView(holder.datentime);
        holder.newparent.addView(holder.title);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.datentime.setLayoutParams(params);
        holder.datentime.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams parameters = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.time.setLayoutParams(parameters);
        holder.datentime.setGravity(Gravity.CENTER);
        holder.time.setText(holder.parts[0]);
        holder.date.setText(holder.parts[1]);
    }

    public void clearwebview() {
        webatctive = false;
        webas.startAnimation(zoomout);
        pbar.startAnimation(zoomout);
        closeweb.startAnimation(zoomout);
        webas.stopLoading();
        zoomout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation n) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                webl.setVisibility(View.GONE);
                webl.setOnClickListener(null);
                closeweb.setOnClickListener(null);
                zoomout.setAnimationListener(null);
            }

            @Override
            public void onAnimationRepeat(Animation n) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return marrayList == null ? 0 : marrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        String data; // date of article
        ImageView imgage;//Image of article
        RelativeLayout rel;
        LinearLayout datentime,
                newparent,
                frame;
        String[] parts;
        TextView author,
                des,
                title,
                date,
                time,
                humanauthor;
        ImageButton favbutton,
                laterbutton,
                openbrowser;

        private MyViewHolder(View itemView) {
            super(itemView);
            favbutton = itemView.findViewById(R.id.favorites); // Favorite button for marking article as favorite
            laterbutton = itemView.findViewById(R.id.later); // Later button for marking aricle to read later
            title = itemView.findViewById(R.id.nameofarticle);
            author = itemView.findViewById(R.id.author);
            des = itemView.findViewById(R.id.des); // Begining of an article
            imgage = itemView.findViewById(R.id.img);
            date = itemView.findViewById(R.id.date);
            rel = itemView.findViewById(R.id.rel); // Image, source, date and time layout
            time = itemView.findViewById(R.id.time); // time
            datentime = itemView.findViewById(R.id.datentime); // Date and time layout
            newparent = itemView.findViewById(R.id.newparent); // place where all views are put if there is no image or it failed to load
            frame = itemView.findViewById(R.id.mainLayout);
            openbrowser = itemView.findViewById(R.id.openbrowser); // Dedicated button for opening browser
            humanauthor = itemView.findViewById(R.id.humarauthor);
        }
    }
}
