package com.example.term_project.main;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
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
                database.insertRecordParam("phone_table", new String[]{"김재호", "010-0101-0101", "AAA111@gmail.com", ""});
                database.insertRecordParam("phone_table", new String[]{"이승혁", "010-1010-1010", "ABA121@gmail.com", ""});
                database.insertRecordParam("phone_table", new String[]{"남세일", "010-1234-5678", "NSI123@gmail.com", ""});
                database.insertRecordParam("phone_table", new String[]{"송재운", "010-4444-1234", "SJW423@naver.com", ""});
                database.insertRecordParam("phone_table", new String[]{"한송운", "010-5555-5678", "HSH567@gmail.com", ""});
                database.insertRecordParam("phone_table", new String[]{"안호준", "010-6666-6789", "AHJ678@gmail.com", ""});
                database.insertRecordParam("phone_table", new String[]{"채이현", "010-7777-7654", "CYH777@naver.com", ""});
                database.insertRecordParam("schedule_table", new String[]{"모바일 과제", "2022-12-03", "PM 11:59", "부경대", "AAA111@gmail.com"});
                database.insertRecordParam("schedule_table", new String[]{"공학 과제", "2022-12-13", "PM 10:59", "부경대", "AAA111@gmail.com"});
                database.insertRecordParam("schedule_table", new String[]{"치과 치료", "2022-12-18", "AM 10:00", "DC치과", "NSI123@gmail.com"});
                database.insertRecordParam("schedule_table", new String[]{"내과 치료", "2022-12-28", "AM 11:00", "종합병원", "CYH777@naver.com"});
                database.insertRecordParam("schedule_table", new String[]{"친구 약속", "2022-12-25", "PM 2:00", "카페", "ABA121@gmail.com"});
                database.insertRecordParam("schedule_table", new String[]{"휴대폰 수리", "2022-12-01", "AM 11:30", "B수리점", "AHJ678@gmail.com"});
                database.insertRecordParam("schedule_table", new String[]{"놀이공원", "2022-12-24", "AM 8:00", "A놀이공원", "SJW423@naver.com"});
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
        // 권한ID를 가져옵니다
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int permission2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        // 권한이 열려있는지 확인
        if (permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED) {
            // 마쉬멜로우 이상버전부터 권한을 물어본다
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 권한 체크(READ_PHONE_STATE의 requestCode를 1000으로 세팅
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        1000);
            }
        }
    }
    // 권한 체크 이후로직
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        // READ_PHONE_STATE의 권한 체크 결과를 불러온다
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
        if (requestCode == 1000) {
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            // 권한 체크에 동의를 하지 않으면 안드로이드 종료
            if (check_result) {

            } else {
                finish();
            }
        }
    }
}