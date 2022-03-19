package com.example.libraso.Signup_Login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ls_adapter extends FragmentPagerAdapter {
    private Context context;
    int totaltabs;
    public ls_adapter(FragmentManager fm, Context context, int totaltabs){
        super(fm);
        this.context=context;
        this.totaltabs=totaltabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                login_fragment fra1 = new login_fragment();
                return fra1;
            case 1:
                Signup_fragment fra2= new Signup_fragment();
                return fra2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}
