package com.depuisletemps.mynews.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.depuisletemps.mynews.controllers.fragments.SectionFragment;
import com.depuisletemps.mynews.controllers.fragments.TopStoryFragment;
import com.depuisletemps.mynews.controllers.fragments.MostPopularFragment;

public class PageAdapter extends FragmentPagerAdapter {
    // Tabs management
    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        return(8);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TopStoryFragment.newInstance();
            case 1:
                return MostPopularFragment.newInstance();
            case 2:
                return SectionFragment.newInstance("Arts");
            case 3:
                return SectionFragment.newInstance("Books");
            case 4:
                return SectionFragment.newInstance("Science");
            case 5:
                return SectionFragment.newInstance("Sports");
            case 6:
                return SectionFragment.newInstance("Technology");
            case 7:
                return SectionFragment.newInstance("World");
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
                return "Arts";
            case 3:
                return "Books";
            case 4:
                return "Science";
            case 5:
                return "Sports";
            case 6:
                return "Technology";
            case 7:
                return "World";

            default:
                return null;
        }
    }
}

