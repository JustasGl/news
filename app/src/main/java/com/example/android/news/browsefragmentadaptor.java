package com.example.android.news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * Created by Justas on 2/28/2018.
 */

public class browsefragmentadaptor  extends FragmentStatePagerAdapter {

    private Context mContext;

    public browsefragmentadaptor(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new subscribedfeed();
        } else if (position == 1) {
            return new sourcesListFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.My_subs);
        }
        else return  mContext.getString(R.string.sources_title);
    }
}

