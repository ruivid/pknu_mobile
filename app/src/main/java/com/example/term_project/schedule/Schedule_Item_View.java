package com.example.term_project.schedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
//import android.widget.ImageView;  // 이미지 추가 고려
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.term_project.R;

public class Schedule_Item_View extends LinearLayout {
    TextView textView_Schedule_Name;
    TextView textView_Schedule_Date;

    public Schedule_Item_View(Context context) {
        super(context);
        init(context);
    }

    public Schedule_Item_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.schedule_list_item, this, true);

        textView_Schedule_Name  = (TextView) findViewById(R.id.schedule_list_item_name);
        textView_Schedule_Date  = (TextView) findViewById(R.id.schedule_list_item_Date);
    }

    public void setName(String name) {
        textView_Schedule_Name.setText(name);
    }
    public void setDate(String schedule_date) {
        textView_Schedule_Date.setText(schedule_date);
    }
}
