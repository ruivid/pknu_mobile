package com.example.term_project.main;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.SharedPreferences;
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
        SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE); // 간단한 설정을 저장할 수 있는 로컬 영역에 isFirst 파일 생성
        boolean first = pref.getBoolean("isFirst", true); // isFirst 값 받아오기. 값이 없을 경우 true 리턴.
        if(first){ // 값이 true = 최초 실행
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", false); // isFirst 에 false 저장.
            editor.apply();
            try { // 최초 일 때 DB에 기본 더미데이터 생성.
                database.insertRecordParam("phone_table", new String[]{"kim친구1", "010-1000-1000", "test@test.com"});
                database.insertRecordParam("phone_table", new String[]{"kim친구2", "010-2000-2000", "test@test.com"});
                database.insertRecordParam("phone_table", new String[]{"이친구3", "010-3000-3000", "test@test.com"});
                database.insertRecordParam("phone_table", new String[]{"최친구4", "010-4000-4000", "test@test.com"});
                database.insertRecordParam("phone_table", new String[]{"하친구5", "010-5000-5000", "test@test.com"});
                database.insertRecordParam("schedule_table", new String[]{"my일정1", "2022-11-25", "", "", ""});
                database.insertRecordParam("schedule_table", new String[]{"my일정2", "2022-11-26", "", "", ""});
                database.insertRecordParam("schedule_table", new String[]{"그룹일정3", "2022-11-26", "", "", ""});
                database.insertRecordParam("schedule_table", new String[]{"팀일정4", "2022-11-27", "", "", ""});
                database.insertRecordParam("schedule_table", new String[]{"학과일정5", "2022-11-28", "", "", ""});
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        //Adapter
        final ViewPager2 viewPager = findViewById(R.id.viewpager);
        final FragmentStateAdapter TabAdapter = new Main_Tab(this, 2);  // 탭은 2개
        viewPager.setAdapter(TabAdapter);

        //Tablayout
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("일정"));
        tabs.addTab(tabs.newTab().setText("연락처"));  // 탭 추가
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);      // 탭 정렬 및 탭 양쪽 꽉차게

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