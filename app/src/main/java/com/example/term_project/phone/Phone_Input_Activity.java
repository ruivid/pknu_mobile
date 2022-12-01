package com.example.term_project.phone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.term_project.R;

public class Phone_Input_Activity extends AppCompatActivity {

    EditText phone_name_edittext;
    EditText phone_phonenumber_edittext;
    EditText phone_email_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_input);

        phone_name_edittext         = (EditText) findViewById(R.id.phone_input_name);
        phone_phonenumber_edittext  = (EditText) findViewById(R.id.phone_input_phonenumber);
        phone_email_edittext        = (EditText) findViewById(R.id.phone_input_email);

        /*
            터치 이벤트 (취소 버튼 / 저장 버튼)
         */
        Button CancelButton = (Button) findViewById(R.id.phone_input_cancelbutton);
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "취소 클릭", Toast.LENGTH_LONG).show();/*
                Intent PhoneInputIntent = new Intent(getApplicationContext(), Phone_Input_Activity.class);   // 추가화면(=개별 조회화면)
                startActivity(PhoneInputIntent);*/
                finish();
            }
        });

        Button SaveButton = (Button) findViewById(R.id.phone_input_savebutton);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "저장 클릭", Toast.LENGTH_LONG).show();/*
                Intent PhoneInputIntent = new Intent(getApplicationContext(), Phone_Input_Activity.class);   // 추가화면(=개별 조회화면)
                startActivity(PhoneInputIntent);*/
                String name             = phone_name_edittext.getText().toString();
                String phone_number     = phone_phonenumber_edittext.getText().toString();
                String email            = phone_email_edittext.getText().toString();

                //int age = Integer.parseInt(age_str);
                //insertData(Database_table, name, phone_number, email);
                finish();
            }
        });

    }
}