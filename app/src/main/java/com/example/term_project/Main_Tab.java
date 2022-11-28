package com.example.term_project;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class Main_Tab extends FragmentStateAdapter {

    int mNumOfTabs; //탭의 갯수

    public Main_Tab(FragmentActivity fa, int numTabs) {
        super(fa);
        this.mNumOfTabs = numTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //int index = getRealPosition(position);
        switch (position){
            case 0 :
                Phone_Main_Fragment tab1 = new Phone_Main_Fragment();
                return tab1;
            case 1:
                Schedule_Main_Fragment tab2 = new Schedule_Main_Fragment();
                return tab2;
            default:
                return null;
        }
        //return null;
    }

    @Override
    public int getItemCount() {
        return mNumOfTabs;
    }
    /*
    public int getRealPosition(int position){
        return position % mNumOfTabs;
    }*/

}
