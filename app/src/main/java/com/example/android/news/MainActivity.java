package com.example.android.news;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

import static com.example.android.news.mAdapter.webatctive;

public final class MainActivity extends AppCompatActivity {
    FragmentPageAdaptor adaptor;
    FragmentOtherToicsAdaptor adapter;
    FAVnLATERadapter faVnLATERadapter;
    browsefragmentadaptor browsefragmentadaptor;
    TabLayout tabLayout;
    int LoaderID = 0;
    DrawerLayout drawer;
    RecyclerView recyclerView;
    RecyclerView.Adapter recycleradaptor;
    NavigationView nv;
    ViewPager viewPager;
    ViewPager viewPager1;
    ImageView menu;
    ViewPager browsePager;
    String URL = "https://newsapi.org/v2/sources?apiKey=5f539f96f2b34e40a12a38ceaa6865b9";
    LinearLayout browse;
    ScrollView credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleradaptor = new RecyclermAdaptor(this, new ArrayList<browseObject>());
        recyclerView.setAdapter(recycleradaptor);
        browsefragmentadaptor = new browsefragmentadaptor(getApplicationContext(), getSupportFragmentManager());
        faVnLATERadapter = new FAVnLATERadapter(getApplicationContext(), getSupportFragmentManager());
        viewPager = findViewById(R.id.viewpager);
        viewPager1 = findViewById(R.id.viewpager1);
        browsePager = findViewById(R.id.browseviewpager);
        adaptor = new FragmentPageAdaptor(this, getSupportFragmentManager());
        viewPager.setAdapter(adaptor);
        adapter = new FragmentOtherToicsAdaptor(this, getSupportFragmentManager());
        tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        drawer = findViewById(R.id.drawer_layout);
        viewPager1.setAdapter(adapter);
        viewPager.setAdapter(adaptor);
        credits = findViewById(R.id.credits);
        browse = findViewById(R.id.browsing);
        nv = findViewById(R.id.navigation);
        menu = findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);
        Log.i("Viewpager", viewPager.getCurrentItem()+"");
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                webatctive = false;
                if(viewPager.getCurrentItem()==2|| viewPager.getCurrentItem()==3)
                    menu.setVisibility(View.GONE);
                else
                    menu.setVisibility(View.VISIBLE);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(nv);
            }
        });
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawer,
                null, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(mDrawerToggle);
        tabLayout.getTabAt(0).setIcon(R.drawable.main_selector1);
        tabLayout.getTabAt(1).setIcon(R.drawable.main_selector2);
        tabLayout.getTabAt(2).setIcon(R.drawable.main_selector3);
        tabLayout.getTabAt(3).setIcon(R.drawable.main_selector4);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.mainscreen):
                        for (int i = 0; i < 5; i++)
                            nv.getMenu().getItem(i).setChecked(false);
                        nv.getMenu().getItem(0).setChecked(true);
                        tabLayout.setupWithViewPager(viewPager);
                        viewPager.setAdapter(adaptor);
                        viewPager.setVisibility(View.VISIBLE);
                        credits.setVisibility(View.GONE);
                        browse.setVisibility(View.GONE);
                        menu.setVisibility(View.VISIBLE);
                        browsePager.setVisibility(View.GONE);
                        viewPager1.setVisibility(View.GONE);
                        tabLayout.setVisibility(View.VISIBLE);
                        tabLayout.getTabAt(0).setIcon(R.drawable.main_selector1);
                        tabLayout.getTabAt(1).setIcon(R.drawable.main_selector2);
                        tabLayout.getTabAt(2).setIcon(R.drawable.main_selector3);
                        tabLayout.getTabAt(3).setIcon(R.drawable.main_selector4);
                        drawer.closeDrawers();
                        break;
                    case (R.id.othertopics):
                        for (int i = 0; i < 5; i++)
                            nv.getMenu().getItem(i).setChecked(false);
                        nv.getMenu().getItem(3).setChecked(true);
                        viewPager1.setVisibility(View.VISIBLE);
                        viewPager.setVisibility(View.GONE);
                        credits.setVisibility(View.GONE);
                        browse.setVisibility(View.GONE);
                        menu.setVisibility(View.VISIBLE);
                        tabLayout.setVisibility(View.VISIBLE);
                        browsePager.setVisibility(View.GONE);
                        tabLayout.setupWithViewPager(viewPager1);
                        tabLayout.getTabAt(0).setIcon(R.drawable.my_selector);
                        tabLayout.getTabAt(1).setIcon(R.drawable.my_selector1);
                        tabLayout.getTabAt(2).setIcon(R.drawable.my_selector2);
                        tabLayout.getTabAt(3).setIcon(R.drawable.my_selector3);
                        tabLayout.getTabAt(4).setIcon(R.drawable.my_selector4);
                        tabLayout.getTabAt(5).setIcon(R.drawable.my_selector5);
                        drawer.closeDrawers();
                        break;
                    case R.id.browse:
                        menu.setVisibility(View.VISIBLE);
                        for (int i = 0; i < 5; i++)
                            nv.getMenu().getItem(i).setChecked(false);
                        nv.getMenu().getItem(1).setChecked(true);
                        viewPager1.setVisibility(View.GONE);
                        viewPager.setVisibility(View.GONE);
                        browsePager.setVisibility(View.VISIBLE);
                        browsePager.setAdapter(browsefragmentadaptor);
                        credits.setVisibility(View.GONE);
                        tabLayout.setVisibility(View.VISIBLE);
                        tabLayout.setupWithViewPager(browsePager);
                        drawer.closeDrawers();
                        break;
                    case R.id.favnlat:
                        menu.setVisibility(View.VISIBLE);
                        for (int i = 0; i < 5; i++) nv.getMenu().getItem(i).setChecked(false);
                        nv.getMenu().getItem(2).setChecked(true);
                        viewPager.setAdapter(faVnLATERadapter);
                        tabLayout.setupWithViewPager(viewPager);
                        viewPager.setVisibility(View.VISIBLE);
                        credits.setVisibility(View.GONE);
                        browse.setVisibility(View.GONE);
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_save);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_later);

                        browsePager.setVisibility(View.GONE);
                        viewPager1.setVisibility(View.GONE);
                        tabLayout.setVisibility(View.VISIBLE);
                        drawer.closeDrawers();
                        break;
                    case (R.id.credits):
                        menu.setVisibility(View.VISIBLE);
                        nv.getMenu().getItem(4).setChecked(true);
                        for (int i = 0; i < 4; i++)
                            nv.getMenu().getItem(i).setChecked(false);
                        viewPager1.setVisibility(View.GONE);
                        viewPager.setVisibility(View.GONE);
                        browsePager.setVisibility(View.GONE);
                        browse.setVisibility(View.GONE);
                        credits.setVisibility(View.VISIBLE);
                        tabLayout.setVisibility(View.GONE);
                        drawer.closeDrawers();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(nv))
        {
            drawer.closeDrawers();
        }
        else {
        List fragmentList = getSupportFragmentManager().getFragments();

        boolean handled = false;
        for (Object f : fragmentList) {
            if (f instanceof topstories_fragment) {
                handled = ((topstories_fragment) f).onBackPressed();
                if (handled) {
                    break;
                } else {
                    finish();
                    System.exit(0);
                }
            }
            if (f instanceof business_fragment) {
                handled = ((business_fragment) f).onBackPressed();
                if (handled) {
                    break;
                } else {
                    finish();
                    System.exit(0);
                }
            }
            if (f instanceof health_fragment) {
                handled = ((health_fragment) f).onBackPressed();
                if (handled) {
                    break;
                } else {
                    finish();
                    System.exit(0);
                }
            }
            if (f instanceof entertainment_fragment) {
                handled = ((entertainment_fragment) f).onBackPressed();
                if (handled) {
                    break;
                } else {
                    finish();
                    System.exit(0);
                }
            }
            if (f instanceof favoritestories_fragment) {
                handled = ((favoritestories_fragment) f).onBackPressed();
                if (handled) {
                    break;
                } else {
                    finish();
                    System.exit(0);
                }
            }
            if (f instanceof science_fragment) {
                handled = ((science_fragment) f).onBackPressed();
                if (handled) {
                    break;
                } else {
                    finish();
                    System.exit(0);
                }
            }
            if (f instanceof search_fragment) {
                handled = ((search_fragment) f).onBackPressed();
                if (handled) {
                    break;
                } else {
                    finish();
                    System.exit(0);
                }
            }
            if (f instanceof sports_fragment) {
                handled = ((sports_fragment) f).onBackPressed();
                if (handled) {
                    break;
                } else {
                    finish();
                    System.exit(0);
                }
            }
            if (f instanceof technology_fragment) {
                handled = ((technology_fragment) f).onBackPressed();
                if (handled) {
                    break;
                } else {
                    finish();
                    System.exit(0);
                }
            }
            if (f instanceof subscribedfeed) {
                handled = ((subscribedfeed) f).onBackPressed();
                if (handled) {
                    break;
                } else {
                    finish();
                    System.exit(0);
                }
            }
            }
        }
    }
}
