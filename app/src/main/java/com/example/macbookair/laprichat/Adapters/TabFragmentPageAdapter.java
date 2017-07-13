package com.example.macbookair.laprichat.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.macbookair.laprichat.Fragments.ChatFragment;
import com.example.macbookair.laprichat.Fragments.FriendsFragment;
import com.example.macbookair.laprichat.Fragments.RequestsFragment;

/**
 * Created by macbookair on 13/07/2017.
 */

public class TabFragmentPageAdapter extends FragmentPagerAdapter {


    private String tabTitles[] = {"Requests","Chat","Friends"};
    private Context mCtx;

    public TabFragmentPageAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.mCtx = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RequestsFragment.newInstance(position);
            case 1:
                return ChatFragment.newInstance(position);
            case 2:
                return FriendsFragment.newInstance(position);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
