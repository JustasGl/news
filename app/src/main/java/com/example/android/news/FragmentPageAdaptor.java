package com.example.android.news;

/**
 * Created by Justas on 2/13/2018.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;


public class FragmentPageAdaptor extends FragmentStatePagerAdapter {

    private Context mContext;

    public FragmentPageAdaptor(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new topstories_fragment();
        } else if (position == 1) {
            return new favoritestories_fragment();
        }
        else if (position == 2) {
            return new search_fragment();
        }
        else if (position == 3)
            return new settings();

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}