package com.example.matteobellinaso.listspesaandroid.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.matteobellinaso.listspesaandroid.ui.fragment.FragmentTutorialOne;
import com.example.matteobellinaso.listspesaandroid.ui.fragment.FragmentTutorialSecond;
import com.example.matteobellinaso.listspesaandroid.ui.fragment.FragmentTutorialThird;

/**
 * Created by matteobellinaso on 09/04/18.
 */

public class SimpleFragmentPageAdapter extends FragmentPagerAdapter {

    private Context myContext;
    public SimpleFragmentPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        myContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        FragmentTutorialOne fragmentTutorialOne = new FragmentTutorialOne();
        FragmentTutorialSecond fragmentTutorialSecond = new FragmentTutorialSecond();
        FragmentTutorialThird fragmentTutorialThird = new FragmentTutorialThird();

        if (position == 0) {
            return  fragmentTutorialOne;
        } else if (position == 1){
            return fragmentTutorialSecond;
        } else if (position == 2){
            return fragmentTutorialThird;
        }else{
            return fragmentTutorialOne;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }




}
