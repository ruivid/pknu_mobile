package com.example.term_project.phone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.term_project.DBHandler;
import com.example.term_project.R;

public class Phone_Input_Activity extends AppCompatActivity {

    EditText phone_name_edittext;
    EditText phone_phonenumber_edittext;
    EditText phone_email_edittext;
    ImageView phone_image;
    String imagePath;
    Uri imageUri = Uri.parse("content://com.android.externalstorage.documents/document/primary%3ADCIM%2FCamera%");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_input);
        DBHandler database = DBHandler.getInstance(this);

        Intent intent = getIntent() ;
        String type = intent.getStringExtra("type");

        phone_name_edittext         = (EditText) findViewById(R.id.phone_input_name);
        phone_phonenumber_edittext  = (EditText) findViewById(R.id.phone_input_phonenumber);
        phone_email_edittext        = (EditText) findViewById(R.id.phone_input_email);
        phone_image = (ImageView) findViewById(R.id.Selected_imageView);

        if(type.equals("edit")){
            String[] detail = intent.getStringArrayExtra("editDetail"); // 편집 정보 가져오기
            String name = detail[0];
            String phone_number = detail[1];
            String email = detail[2];
            String imagePath = detail[4];
            phone_name_edittext.setText(name);
            phone_phonenumber_edittext.setText(phone_number);
            phone_email_edittext.setText(email);
            phone_image.setImageURI(Uri.parse(imagePath));
        }

        phone_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, imageUri);
                startActivityForResult(intent, 1);
            }
        });

        Button SaveButton = (Button) findViewById(R.id.phone_input_savebutton);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String id = intent.getStringExtra("id");
            if(!phone_name_edittext.getText().toString().equals("") &&
                    !phone_phonenumber_edittext.getText().toString().equals("")) {
                if(type.equals("new")) {
                    database.insertRecordParam("phone_table",
                            new String[]{phone_name_edittext.getText().toString(),
                                    phone_phonenumber_edittext.getText().toString(),
                                    phone_email_edittext.getText().toString(),
                                    imagePath});
                    finish();
                }
                else if(type.equals("edit")) {
                    database.updateRecordParam("phone_table",
                            new String[]{phone_name_edittext.getText().toString(),
                                    phone_phonenumber_edittext.getText().toString(),
                                    phone_email_edittext.getText().toString(),
                                    imagePath},
                                    new String[]{id});
                    finish();
                }
                else { Toast.makeText(getApplicationContext(),
                        "잘못된 접근입니다.", Toast.LENGTH_LONG).show(); }
                }
            else {
                Toast.makeText(getApplicationContext(),
                        "이름과 전화번호는\n필수 입력 값입니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        /*
            터치 이벤트 (취소 버튼 / 저장 버튼)
         */
        Button CancelButton = (Button) findViewById(R.id.phone_input_cancelbutton);
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    @SuppressLint("WrongConstant")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    ContentResolver resolver = this.getContentResolver();
                    resolver.takePersistableUriPermission(uri, takeFlags);
                    phone_image.setImageURI(uri);
                    imagePath = uri.toString();
                }
        }
    }
}