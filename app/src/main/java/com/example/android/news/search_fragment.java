package com.example.android.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justas on 2/16/2018.
 */

public class search_fragment extends Fragment implements LoaderManager.LoaderCallbacks<List<article>> {
    Animation zoomin, zoomout, slidedown, slideup;
    RelativeLayout weblayout;
    WebView web;
    int fromcounter = 0, tocounter = 0;
    EditText edit;
    ImageView closeweb;
    ImageView image, errorimage;
    ProgressBar pbar;
    String query = "", test = "", sort = "publishedAt", fromstring = "", tostring = "";
    mAdapter adapter;
    DatePicker from, to;
    RadioGroup radio;
    TextView results, fromtxt, totxt;
    SlidingUpPanelLayout up;
    RecyclerView list;
    TextView errortext;
    RelativeLayout hid;
    Boolean isinternet = false, islist = false;
    int LoaderID = 2, count = 0, pageSize = 20;
    String URL = "https://newsapi.org/v2/everything?";
    private SeekBar seekbar;

    public search_fragment() {
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.searchlayout, container, false);
        weblayout = rootView.findViewById(R.id.weblayout);
        web = rootView.findViewById(R.id.webview);
        radio = rootView.findViewById(R.id.radiogroup);
        zoomin = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in);
        zoomout = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_out);
        slidedown = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slidedown);
        slideup = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_up);
        edit = rootView.findViewById(R.id.edtext);
        image = rootView.findViewById(R.id.searchbutton);
        pbar = rootView.findViewById(R.id.pB1);
        closeweb = rootView.findViewById(R.id.closeweb);
        from = rootView.findViewById(R.id.datepickerfrom);
        to = rootView.findViewById(R.id.datepickerto);
        fromtxt = rootView.findViewById(R.id.from);
        totxt = rootView.findViewById(R.id.to);
        up = rootView.findViewById(R.id.slidingup);
        errortext = rootView.findViewById(R.id.alerttext);
        errorimage = rootView.findViewById(R.id.alertpic);
        list = rootView.findViewById(R.id.recyclerview);
        list.hasFixedSize();
        isinternet();
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        hid = rootView.findViewById(R.id.hidlayout);
        up.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        seek(rootView);
        fromtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                from();
            }
        });
        totxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked();
            }
        });
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    buttonClicked();
                    handled = true;
                }
                return handled;
            }
        });

        if (savedInstanceState != null) {
            query = savedInstanceState.getString("Query");
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LoaderID, null, this);
            test = query;
        }
        return rootView;
    }

    public void from() {
        if (fromcounter % 2 == 0) {
            to.clearAnimation();
            from.setVisibility(View.VISIBLE);
            from.startAnimation(slidedown);

            fromcounter++;
        } else {
            from.startAnimation(slideup);
            slideup.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    from.setVisibility(View.GONE);
                    from.clearAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            fromcounter++;
        }
    }

    public void to() {
        if (tocounter % 2 == 0) {
            from.clearAnimation();
            to.setVisibility(View.VISIBLE);
            to.startAnimation(slidedown);

            tocounter++;
        } else {
            to.startAnimation(slideup);
            slideup.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    to.setVisibility(View.GONE);
                    to.clearAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            tocounter++;
        }
    }

    public void seek(View rootview) {
        seekbar = rootview.findViewById(R.id.seekbar);
        results = rootview.findViewById(R.id.results);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progresas;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progresas = i;
                results.setText("Results " + progresas + "/ " + seekbar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                results.setText(getString(R.string.results) + progresas + "/ " + seekbar.getMax());
                pageSize = progresas;
            }
        });
    }

    public void reset() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(LoaderID, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("Query", query);

        super.onSaveInstanceState(savedInstanceState);
    }

    public void buttonClicked() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        query = edit.getText().toString();
        if (count > 0) {
            reset();
        }
        if (!query.equals("") && !query.equals(test) && networkInfo != null && networkInfo.isConnected()) {
            test = query;
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LoaderID, null, this);
        }
        count++;
    }

    @Override
    public Loader<List<article>> onCreateLoader(int i, Bundle bundle) {
        switch (radio.getCheckedRadioButtonId()) {
            case R.id.publishedAt:
                sort = "publishedAt";
                break;
            case R.id.popularity:
                sort = "popularity";
                break;
            case R.id.relevancy:
                sort = "relevancy";
                break;
        }

        fromstring = String.valueOf(from.getYear()) + "-";
        fromstring = fromstring + from.getMonth() + 1 + "-";
        fromstring = fromstring + from.getDayOfMonth();

        tostring = String.valueOf(to.getYear()) + "-";
        tostring = tostring + to.getMonth() + 1 + "-";
        tostring = tostring + to.getDayOfMonth();
        Uri baseUri = Uri.parse(URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", query);
        uriBuilder.appendQueryParameter(getString(R.string.SortBy), sort);
        uriBuilder.appendQueryParameter(getString(R.string.pageSize), String.valueOf(pageSize));
        if (!tostring.equals(fromstring)) {
            uriBuilder.appendQueryParameter(getString(R.string.dateFrom), fromstring);
            uriBuilder.appendQueryParameter(getString(R.string.dateTo), tostring);
        }
        uriBuilder.appendQueryParameter(getString(R.string.apiKey), getString(R.string.apikeyValue));
        return new articleLoader(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<article>> loader, List<article> articles) {

        ArrayList<article> artcl = (ArrayList<article>) articles;
        if (artcl != null)
            if (artcl.isEmpty() || artcl.size() == 0) {
                errortext.setText(R.string.noresults);
                errorimage.setVisibility(View.GONE);
                errortext.setVisibility(View.VISIBLE);
                islist = true;
            } else {
                adapter = new mAdapter(getActivity(), artcl, weblayout, web, pbar, list, closeweb);
                list.setAdapter(adapter);
            }
        if (isinternet && islist)
            hid.setVisibility(View.GONE);
    }

    private void isinternet() {
        if (!Utils.isNetworkAvailable(getContext())) {
            errorimage.setVisibility(View.VISIBLE);
            errortext.setVisibility(View.VISIBLE);
            errorimage.setImageResource(R.drawable.country);
            errortext.setText(R.string.no_internet);
        } else {
            isinternet = true;
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
