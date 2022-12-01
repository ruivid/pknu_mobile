package com.example.term_project.phone;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.term_project.R;

public class Phone_Detail_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_detail);

        ImageView CallButton = (ImageView) findViewById(R.id.phone_detail_Call);
        CallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "010-1111-1111";
                Intent callIntend = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                startActivity(callIntend);
            }
        });

        ImageView MessageButton = (ImageView) findViewById(R.id.phone_detail_SendMSG);
        MessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "010-1111-1111";
                Uri smsUri = Uri.parse("sms:"+number);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
                startActivity(sendIntent);  // 메시지 앱으로 이동(해당 전화번호)
            }
        });


        /*
            하단 버튼 터치 이벤트 (뒤로가기 버튼 / 편집 버튼 / 삭제 버튼)
         */
        // 뒤로가기 버튼
        Button BackButton = (Button) findViewById(R.id.phone_detail_backbutton);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "뒤로가기 클릭", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        // 편집 버튼
        Button EditButton = (Button) findViewById(R.id.phone_detail_editbutton);
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent PhoneInputIntent = new Intent(getApplicationContext(), Phone_Input_Activity.class);   // 추가화면(=개별 조회화면)
                //PhoneInputIntent.putExtra("전화번호", "010-1111-1111");
                startActivity(PhoneInputIntent);
            }
        });

        // 삭제 버튼
        Button DeleteButton = (Button) findViewById(R.id.phone_detail_deletebutton);
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "삭제 클릭", Toast.LENGTH_LONG).show();/*
                Intent PhoneInputIntent = new Intent(getApplicationContext(), Phone_Input_Activity.class);   // 추가화면(=개별 조회화면)
                startActivity(PhoneInputIntent);*/
            }
        });

    }

}