package com.example.term_project.schedule;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.term_project.R;

import java.util.ArrayList;

public class Schedule_Main_Fragment extends Fragment {

    ListView schedule_listView;
    ScheduleAdapter adapter;

    public Schedule_Main_Fragment() {
        // Required empty public constructor
    }

    public static Schedule_Main_Fragment newInstance() {
        Schedule_Main_Fragment fragment = new Schedule_Main_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, // view 생성
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.schedule_main_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle SavedInstanceState) { // view 생성 후
        schedule_listView = (ListView) view.findViewById(R.id.schedule_main_ScheduleListview);

        adapter = new ScheduleAdapter();

        adapter.addItem(new Schedule_Item("my일정1", "2022-11-25"));
        adapter.addItem(new Schedule_Item("my일정2", "2022-11-26"));
        adapter.addItem(new Schedule_Item("그룹일정3", "2022-11-26"));
        adapter.addItem(new Schedule_Item("팀일정4", "2022-11-27"));
        adapter.addItem(new Schedule_Item("학과일정5", "2022-11-28"));

        schedule_listView.setAdapter(adapter);

        /*
            터치 이벤트 (버튼[추가] / 리스트[조회])
         */

        Button button = (Button) view.findViewById(R.id.schedule_main_btnPlus);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ScheduleInputIntent = new Intent(getContext(), Schedule_Input_Activity.class);   // 추가화면(=개별 조회화면)
                startActivity(ScheduleInputIntent);
            }
        });


        schedule_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getContext(), "선택 : " , Toast.LENGTH_LONG).show();
                Intent Selected_Intent = new Intent(getContext(), Schedule_Detail_Activity.class); // 개별 조회화면(=[데이터첨부된]추가화면)
                startActivity(Selected_Intent);
            }
        });
    }

    class ScheduleAdapter extends BaseAdapter {
        ArrayList<Schedule_Item> items = new ArrayList<Schedule_Item>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Schedule_Item item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            Schedule_Item_View view = new Schedule_Item_View(getContext());

            Schedule_Item item = items.get(position);
            view.setName(item.getName());
            view.setDate(item.getSchedule_Date());

            return view;
        }
    }

}