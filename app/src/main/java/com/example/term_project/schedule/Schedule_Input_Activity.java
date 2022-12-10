package com.example.term_project.schedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.term_project.DBHandler;
import com.example.term_project.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Schedule_Input_Activity extends AppCompatActivity {

    EditText phone_title_edittext;
    EditText phone_place_edittext;
    EditText phone_email_edittext;

    TextView phone_date_textview;
    TextView phone_time_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_input);
        DBHandler database = DBHandler.getInstance(this);
        Intent intent = getIntent() ;
        String type = intent.getStringExtra("type");

        String[] insert_data = new String[5];   // {제목[title-0] / 날짜[date-1] / 시간[time-2] / 장소[place-3] / 이메일[Email-4] }

        phone_title_edittext  = (EditText) findViewById(R.id.schedule_input_title);     // 제목
        phone_place_edittext  = (EditText) findViewById(R.id.schedule_input_place);     // 장소
        phone_email_edittext  = (EditText) findViewById(R.id.schedule_input_email);     // 이메일

        phone_date_textview = (TextView) findViewById(R.id.schedule_input_date);        // 날짜
        phone_time_textview = (TextView) findViewById(R.id.schedule_input_time);        // 시간

        // 날짜 출력
        Calendar cal = Calendar.getInstance();
        phone_date_textview.setText(cal.get(Calendar.YEAR)+ "-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));

        // 날짜 선택 터치 이벤트
        phone_date_textview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 달력 Dialog 생성 및 리스너
                DatePickerDialog dataPickerDialog = new DatePickerDialog(Schedule_Input_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        phone_date_textview.setText(String.format("%d-%d-%d", i, i1+1,i2));
                        // DB에 선택한 날짜 삽입
                        insert_data[1] = String.format("%d-%d-%d", i, i1+1,i2);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dataPickerDialog.show();    // DatePickerDialog 보여주기
            }
        });

        long current_time = System.currentTimeMillis(); // 현재 시간 가져오기
        Date Time_date = new Date(current_time);        // Date형식으로 변환
        SimpleDateFormat dateFormat_Hour   = new SimpleDateFormat("hh");    // 시간
        SimpleDateFormat dateFormat_Minute = new SimpleDateFormat("mm");    // 분
        String getTime_Hour = dateFormat_Hour.format(Time_date);        // 시간 저장
        String getTime_Minute = dateFormat_Minute.format(Time_date);    // 분 저장

        phone_time_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Schedule_Input_Activity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        String AMPM = "AM";
                        if(i > 12){
                            i -= 12;        // "시"가 12시 넘겼을 때
                            AMPM = "PM";    // PM
                        }
                        phone_time_textview.setText(AMPM + " " + String.format("%d : %02d", i, i1));
                        // DB에 선택한 시간 삽입
                        insert_data[2] = AMPM + " " + String.format("%d : %02d", i, i1);
                    }
                }, Integer.parseInt(getTime_Hour), Integer.parseInt(getTime_Minute), false);
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);    // Dialog 배경 투명설정
                timePickerDialog.show();
            }
        });

        // 조회에서 편집버튼을 누른 경우, 해당 데이터 가져오기
        if(type.equals("edit")){
            String[] detail = intent.getStringArrayExtra("editDetail"); // 편집 정보 가져오기

            String title = detail[0];
            String date = detail[1];
            String time = detail[2];
            String place = detail[3];
            String email = detail[4];

            phone_title_edittext.setText(title);
            phone_date_textview.setText(date);
            phone_time_textview.setText(time);
            phone_place_edittext.setText(place);
            phone_email_edittext.setText(email);

            insert_data[1] = phone_date_textview.getText().toString();
            insert_data[2] = phone_time_textview.getText().toString();
        }

        /*
            터치 이벤트 (취소 버튼 / 저장 버튼)
         */
        Button CancelButton = (Button) findViewById(R.id.schedule_input_cancelbutton);
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button SaveButton = (Button) findViewById(R.id.schedule_input_savebutton); // 입력받은 값 저장
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = intent.getStringExtra("id");
                insert_data[0] = phone_title_edittext.getText().toString();     // 제목 삽입
                insert_data[3] = phone_place_edittext.getText().toString();     // 장소 삽입
                insert_data[4] = phone_email_edittext.getText().toString();     // 이메일 삽입
                if(!insert_data[0].equals("") && insert_data[1] != null) {
                    if(type.equals("new")) {
                        database.insertRecordParam("schedule_table",
                                insert_data);
                        finish();
                    }
                    else if(type.equals("edit")) {
                        database.updateRecordParam("schedule_table",
                                insert_data,
                                new String[]{id});
                        finish();
                    }
                    else { Toast.makeText(getApplicationContext(),
                            "잘못된 접근입니다.", Toast.LENGTH_LONG).show(); }
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "이름과 날짜는\n필수 입력 값입니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
