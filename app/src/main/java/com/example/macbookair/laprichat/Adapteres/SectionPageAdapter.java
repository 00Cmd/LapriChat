package com.example.macbookair.laprichat.Adapteres;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbookair on 12/07/2017.
 */

public class SectionPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmenTitleList = new ArrayList<>();


    public void addFragment(Fragment fr,String title) {
        mFragmentList.add(fr);
        mFragmenTitleList.add(title);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
