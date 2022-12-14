package com.example.term_project.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.term_project.phone.Phone_Main_Fragment;
import com.example.term_project.schedule.Schedule_Main_Fragment;


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

                return new Schedule_Main_Fragment();
            case 1:
                return new Phone_Main_Fragment();
            default:
                return new Fragment();
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
