package com.example.term_project.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.term_project.DBHandler;
import com.example.term_project.R;

public class Schedule_Detail_Activity  extends AppCompatActivity {
    TextView nameView;
    TextView dateView;
    TextView timeView;
    TextView placeView;
    TextView emailView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_detail);
        DBHandler database = DBHandler.getInstance(this);

        Intent intent = getIntent() ;
        String[] detail = intent.getStringArrayExtra("detail"); // 정보 가져오기

        String name = detail[0];
        String date = detail[1];
        String time = detail[2];
        String place = detail[3];
        String email = detail[4];
        String id = detail[5];

        nameView = findViewById(R.id.schedule_detail_title);
        dateView = findViewById(R.id.schedule_detail_date);
        timeView = findViewById(R.id.schedule_detail_time);
        placeView = findViewById(R.id.schedule_detail_place);
        emailView = findViewById(R.id.schedule_detail_email);

        nameView.setText(name);
        dateView.setText(date);
        timeView.setText(time);
        placeView.setText(place);
        emailView.setText(email);
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
                ScheduleInputIntent.putExtra("type", "edit");
                ScheduleInputIntent.putExtra("id", id);
                ScheduleInputIntent.putExtra("editDetail", detail); // 아이템의 정보를 detail에 담음
                startActivity(ScheduleInputIntent);
                finish();
            }
        });

        // 삭제 버튼
        Button DeleteButton = (Button) findViewById(R.id.schedule_detail_deletebutton);
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.deleteRecordParam("schedule_table", new String[]{id});
                finish();
            }
        });

    }
}
