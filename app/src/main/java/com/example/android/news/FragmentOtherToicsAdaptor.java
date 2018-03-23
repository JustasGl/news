package com.example.android.news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * Created by Justas on 2/19/2018.
 */

public class FragmentOtherToicsAdaptor extends FragmentStatePagerAdapter {


    public FragmentOtherToicsAdaptor(Context context, FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new business_fragment();
        } else if (position == 1) {
            return new entertainment_fragment();
        } else if (position == 2) {
            return new health_fragment();
        } else if (position == 3)
            return new science_fragment();
        else if (position == 4)
            return new sports_fragment();
        else if (position == 5)
            return new technology_fragment();
        return null;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

}
