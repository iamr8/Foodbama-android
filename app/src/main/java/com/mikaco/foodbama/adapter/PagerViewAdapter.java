package com.mikaco.foodbama.adapter;

import android.content.res.Resources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.mikaco.foodbama.R;
import com.mikaco.foodbama.fragment.AboutFragment;
import com.mikaco.foodbama.fragment.HomeFragment;
import com.mikaco.foodbama.fragment.ProfileFragment;
import com.mikaco.foodbama.fragment.SearchFragment;
import com.mikaco.foodbama.r8.App;

/**
 * Created by Arash on 2/2/2018.
 */

public class PagerViewAdapter extends FragmentPagerAdapter {
    private final String titles[] = new String[4];
    private final Fragment frags[] = new Fragment[titles.length];

    public PagerViewAdapter(FragmentManager fm) {
        super(fm);

        frags[0] = new HomeFragment();
        frags[1] = new SearchFragment();
        frags[2] = new ProfileFragment();
        frags[3] = new AboutFragment();

        Resources resources = App.getAppContext().getResources();

        titles[0] = resources.getString(R.string.Home);
        titles[1] = resources.getString(R.string.Search);
        titles[2] = resources.getString(R.string.Profile);
        titles[3] = resources.getString(R.string.app_name);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return frags[position];
    }


    @Override
    public int getCount() {
        return frags.length;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        frags[position] = (Fragment) super.instantiateItem(container, position);
        return frags[position];
    }
}
