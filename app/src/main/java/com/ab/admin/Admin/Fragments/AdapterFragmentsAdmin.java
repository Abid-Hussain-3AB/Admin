package com.ab.admin.Admin.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class AdapterFragmentsAdmin extends FragmentStatePagerAdapter {
    private int tabCount;
    public AdapterFragmentsAdmin(@NonNull FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;

    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position)
        {
            case 0:
                return new ApprovedShops();
            case 1:
                return new RequestedShops();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
