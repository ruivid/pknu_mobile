package com.example.term_project.phone;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.term_project.DBHandler;
import com.example.term_project.R;
import com.example.term_project.main.MainActivity;

public class Phone_Detail_Activity extends AppCompatActivity {
    TextView nameView;
    TextView phoneView;
    TextView emailView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_detail);
        DBHandler database = DBHandler.getInstance(this);

        Intent intent = getIntent();
        String[] detail = intent.getStringArrayExtra("detail"); // 정보 가져오기

        String name = detail[0];
        String phone_number = detail[1];
        String email = detail[2];
        String id = detail[3];

        nameView = findViewById(R.id.phone_detail_name);
        phoneView = findViewById(R.id.phone_detail_phonenumber);
        emailView = findViewById(R.id.phone_detail_email);

        nameView.setText(name);
        phoneView.setText(phone_number);
        emailView.setText(email);

        ImageView CallButton = (ImageView) findViewById(R.id.phone_detail_Call);
        CallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntend = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone_number));
                startActivity(callIntend);
            }
        });

        ImageView MessageButton = (ImageView) findViewById(R.id.phone_detail_SendMSG);
        MessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri smsUri = Uri.parse("sms:" + phone_number);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
                startActivity(sendIntent);  // 메시지 앱으로 이동(해당 전화번호)
            }
        });


        /*
            하단 버튼 터치 이벤트 (뒤로가기 버튼 / 편집 버튼 / 삭제 버튼)
         */
        /* 이거 필요한지 의문이에요 // 뒤로가기 버튼
        Button BackButton = (Button) findViewById(R.id.phone_detail_backbutton);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "뒤로가기 클릭", Toast.LENGTH_LONG).show();
                finish();
            }
        });*/

        // 편집 버튼
        Button EditButton = (Button) findViewById(R.id.phone_detail_editbutton); //---------------------------
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent PhoneInputIntent = new Intent(getApplicationContext(), Phone_Input_Activity.class);   // 추가화면(=개별 조회화면)
                PhoneInputIntent.putExtra("type", "edit");
                PhoneInputIntent.putExtra("id", id);
                PhoneInputIntent.putExtra("editDetail", detail); // 아이템의 정보를 detail에 담음
                startActivity(PhoneInputIntent);
                finish();
            }
        });

        // 삭제 버튼
        Button DeleteButton = (Button) findViewById(R.id.phone_detail_deletebutton);
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.deleteRecordParam("phone_table", new String[]{id});
                finish();
            }
        });

    }

}