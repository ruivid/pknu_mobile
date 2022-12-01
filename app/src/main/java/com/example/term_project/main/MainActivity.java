package com.example.term_project.main;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.term_project.DBHandler;
import com.example.term_project.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHandler database = DBHandler.getInstance(this);

        //Adapter
        final ViewPager2 viewPager = findViewById(R.id.viewpager);
        final FragmentStateAdapter TabAdapter = new Main_Tab(this, 2);  // 탭은 2개
        viewPager.setAdapter(TabAdapter);

        //Tablayout
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("연락처"));
        tabs.addTab(tabs.newTab().setText("일정"));  // 탭 추가
        tabs.setTabGravity(tabs.GRAVITY_FILL);      // 탭 정렬 및 탭 양쪽 꽉차게

        // 탭 누르면 페이지 변경
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                switch(pos){
                    case 0:
                        viewPager.setCurrentItem(0);    // 0번탭 클릭 시 0번째 뷰페이지(프래그먼트)
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);    // 1번탭 클릭 시 1번째 뷰페이지(프래그먼트)
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager.setUserInputEnabled(false); // 슬라이딩으로 탭 못바꿈

    }
}