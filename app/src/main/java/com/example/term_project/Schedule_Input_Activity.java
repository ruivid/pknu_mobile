package com.example.term_project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        /*
            터치 이벤트 (취소 버튼 / 저장 버튼)
         */
        Button CancelButton = (Button) findViewById(R.id.schedule_input_cancelbutton);
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "취소 클릭", Toast.LENGTH_LONG).show();/*
                Intent PhoneInputIntent = new Intent(getApplicationContext(), Phone_Input_Activity.class);   // 추가화면(=개별 조회화면)
                startActivity(PhoneInputIntent);*/
                finish();
            }
        });

        Button SaveButton = (Button) findViewById(R.id.schedule_input_savebutton);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    DataBase에 입력한 값 넣기
                 */
                insert_data[0] = phone_title_edittext.getText().toString();     // 제목 삽입
                insert_data[3] = phone_place_edittext.getText().toString();     // 장소 삽입
                insert_data[4] = phone_email_edittext.getText().toString();     // 이메일 삽입

                database.insertRecordParam("schedule_table", insert_data);

                Toast.makeText(getApplicationContext(), "저장 클릭"
                        + insert_data[0] + " "
                        + insert_data[1] + " "
                        + insert_data[2] + " "
                        + insert_data[3] + " "
                        + insert_data[4] + " ", Toast.LENGTH_LONG).show();

                //int age = Integer.parseInt(age_str);
                //insertData(Database_table, name, phone_number, email);
                //finish();
            }
        });

    }
}
