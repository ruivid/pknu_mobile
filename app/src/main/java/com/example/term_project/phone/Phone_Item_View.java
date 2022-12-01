package com.example.term_project.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.term_project.R;

public class Phone_Item_View extends LinearLayout {
    TextView textView_Name;
    TextView textView_PhoneNumber;
    ImageView imageView;

    public Phone_Item_View(Context context) {
        super(context);
        init(context);
    }

    public Phone_Item_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.phone_list_item, this, true);

        textView_Name           = (TextView) findViewById(R.id.phone_list_item_name);
        textView_PhoneNumber    = (TextView) findViewById(R.id.phone_list_item_PhoneNumber);
        imageView               = (ImageView) findViewById(R.id.phone_list_item_user_image);
    }

    public void setName(String name) {
        textView_Name.setText(name);
    }
    public void setPhone_number(String phone_number) {
        textView_PhoneNumber.setText(phone_number);
    }
    public void setImage(int imageId) {
        imageView.setImageResource(imageId);
    }
}
