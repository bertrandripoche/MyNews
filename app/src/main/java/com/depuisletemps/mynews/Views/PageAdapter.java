package com.depuisletemps.mynews.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.depuisletemps.mynews.Controllers.Fragments.SectionFragment;
import com.depuisletemps.mynews.Controllers.Fragments.TopStoryFragment;
import com.depuisletemps.mynews.Controllers.Fragments.MostPopularFragment;
import com.depuisletemps.mynews.R;

public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        return(5);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TopStoryFragment.newInstance();
            case 1:
                return MostPopularFragment.newInstance();
            case 2:
                return SectionFragment.newInstance("business");
            case 3:
                return SectionFragment.newInstance("books");
            case 4:
                return SectionFragment.newInstance("television");
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
            case 3:
                return "Books";
            case 4:
                return "Television";

            default:
                return null;
        }
    }
}

