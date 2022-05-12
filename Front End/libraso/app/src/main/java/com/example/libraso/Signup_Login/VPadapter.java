package com.example.libraso.Signup_Login;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VPadapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> frag= new ArrayList<>();
    private final ArrayList<String> fragname= new ArrayList<>();

    public VPadapter(FragmentManager fm, int behavior){
        super(fm,behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return frag.get(position);
    }


    @Override
    public int getCount() {
        return frag.size();
    }

    public void addFragment(Fragment fr, String title){
        frag.add(fr);
        fragname.add(title);
    }
    @NonNull
    @Override
    public CharSequence getPageTitle(int position){
        return fragname.get(position);
    }
}
