package com.depuisletemps.mynews.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.depuisletemps.mynews.Controllers.Fragments.BusinessFragment;
import com.depuisletemps.mynews.Controllers.Fragments.MainFragment;
import com.depuisletemps.mynews.Controllers.Fragments.MostPopularFragment;

public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        return(3); // Number of page to show
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MainFragment.newInstance();
            case 1:
                return MostPopularFragment.newInstance();
            case 2:
                return BusinessFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Top Stories";
            case 1:
                return "Most Popular";
            case 2:
                return "Business";
            default:
                return null;
        }
    }
}

