package com.example.term_project.schedule;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.term_project.DBHandler;
import com.example.term_project.R;
import com.example.term_project.phone.Phone_Detail_Activity;
import com.example.term_project.phone.Phone_Input_Activity;
import com.example.term_project.phone.Phone_Item;

import java.util.ArrayList;
import java.util.List;

public class Schedule_Main_Fragment extends Fragment {

    ListView schedule_listView;
    EditText editText;
    ImageView searchImage;
    ScheduleAdapter adapter;
    DBHandler database;
    List<String[]> data = new ArrayList<>();

    public Schedule_Main_Fragment() {
        // Required empty public constructor
    }

    public static Schedule_Main_Fragment newInstance() {
        return new Schedule_Main_Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            database = DBHandler.getInstance(requireActivity().getApplicationContext());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, // view 생성
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.schedule_main_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle SavedInstanceState) { // view 생성 후
        editText = view.findViewById(R.id.schedule_main_SearchName);       // 검색 창
        searchImage = view.findViewById(R.id.schedule_main_SearchImage);   // 검색 이미지 버튼

        schedule_listView = view.findViewById(R.id.schedule_main_ScheduleListview);
        adapter = new ScheduleAdapter();
        schedule_listView.setAdapter(adapter);

        /*
            검색 이벤트
         */
        editText.addTextChangedListener(new TextWatcher() { // editText change 이벤트
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) { // 텍스트 입력 받은 후
                adapter.clearItem(); // 문자 입력할 때마다 다 지우고 새로 생성
                editText.addTextChangedListener(new TextWatcher() { // editText change 이벤트
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) { // 텍스트 입력 받은 후
                        search(s.toString().trim());
                    }
                });
            }
        });
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serach_text = editText.getText().toString();
                search(serach_text);
            }
        });

        /*
            터치 이벤트 (버튼[추가] / 리스트[조회])
         */

        Button button = (Button) view.findViewById(R.id.schedule_main_btnPlus);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ScheduleInputIntent = new Intent(getContext(), Schedule_Input_Activity.class);   // 추가화면(=개별 조회화면)
                ScheduleInputIntent.putExtra("type", "new");
                startActivity(ScheduleInputIntent);
            }
        });


        schedule_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Schedule_Item item = (Schedule_Item) adapter.getItem(position); // 클릭한 포지션을 바탕으로 아이템 객체 가져오기
                Intent Selected_Intent = new Intent(getContext(), Schedule_Detail_Activity.class); // 이동할 디테일 액티비티 객체 생성
                Selected_Intent.putExtra("detail", item.getDetail()); // 아이템의 정보를 detail에 담음
                startActivity(Selected_Intent); // 화면 이동
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        data = database.selectData("schedule_table", "select * from schedule_table");
        adapter.clearItem();
        for(String[] item : data) {
            adapter.addItem(Schedule_Item.builder()
                    .name(item[0])
                    .date(item[1])
                    .time(item[2])
                    .place(item[3])
                    .email(item[4])
                    .Id(item[5])
                    .build());
        }
        schedule_listView.setAdapter(adapter);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction(); // DB 업데이트를 갱신하기 위한 프래그먼트 새로고침
        ft.detach(this).attach(this).commit();
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

        public void clearItem() { items.clear(); }
    }

    public void search(String s_text){
        adapter.clearItem(); // 문자 입력할 때마다 다 지우고 새로 생성
        if(s_text.length() == 0){   // 문자 입력이 없을 때는 모든 데이터 보여줌
            data = database.selectData("schedule_table", "select * from schedule_table"); // DB에서 전체 데이터 가져오기
            for(String[] item : data) { // Db에서 가져온 데이터로 Phone_item 객체 만들어 어댑터에 등록
                adapter.addItem(Schedule_Item.builder()
                        .name(item[0])
                        .date(item[1])
                        .time(item[2])
                        .place(item[3])
                        .email(item[4])
                        .Id(item[5])
                        .build());
            }
        }
        else{   // 문자 입력 시
            data = database.selectData("schedule_table", "select * from schedule_table where title like \"%"+s_text+"%\""); // DB에서 검색 데이터 가져오기
            for(String[] item : data) { // Db에서 가져온 데이터로 Phone_item 객체 만들어 어댑터에 등록
                adapter.addItem(Schedule_Item.builder()
                        .name(item[0])
                        .date(item[1])
                        .time(item[2])
                        .place(item[3])
                        .email(item[4])
                        .Id(item[5])
                        .build());
            }
        }
        schedule_listView.setAdapter(adapter); // 리스트뷰에 세팅
    }

}