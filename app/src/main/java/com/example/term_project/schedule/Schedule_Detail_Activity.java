package com.example.term_project.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.term_project.R;

public class Schedule_Detail_Activity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_detail);

        /*
            하단 버튼 터치 이벤트 (뒤로가기 버튼 / 편집 버튼 / 삭제 버튼)
         */
        /* 이거 필요한지 의문이에요    // 뒤로가기 버튼
        Button BackButton = (Button) findViewById(R.id.schedule_detail_backbutton);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "뒤로가기 클릭", Toast.LENGTH_LONG).show();
                finish();
            }
        });*/

        // 편집 버튼
        Button EditButton = (Button) findViewById(R.id.schedule_detail_editbutton);
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ScheduleInputIntent = new Intent(getApplicationContext(), Schedule_Input_Activity.class);   // 추가화면(=개별 조회화면)
                //PhoneInputIntent.putExtra("전화번호", "010-1111-1111");
                startActivity(ScheduleInputIntent);
            }
        });

        // 삭제 버튼
        Button DeleteButton = (Button) findViewById(R.id.schedule_detail_deletebutton);
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "삭제 클릭", Toast.LENGTH_LONG).show();/*
                Intent ScheduleInputIntent = new Intent(getApplicationContext(), Schedule_Input_Activity.class);   // 추가화면(=개별 조회화면)
                startActivity(ScheduleInputIntent);*/
            }
        });

    }
}
