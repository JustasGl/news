package com.example.android.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * Created by Justas on 2/28/2018.
 */

public class FavoritesAndWatchLaterFragmentdAdaptor extends FragmentStatePagerAdapter {

    public FavoritesAndWatchLaterFragmentdAdaptor(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FavFragment();
        } else if (position == 1) {
            return new Laterfragment();
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
}

