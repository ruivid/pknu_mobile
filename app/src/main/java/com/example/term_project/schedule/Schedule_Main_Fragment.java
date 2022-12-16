package com.example.term_project.schedule;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    ImageView MenuImage;
    ScheduleAdapter adapter;
    DBHandler database;
    List<String[]> data = new ArrayList<>();

    String sort_sql = "_id";
    String sort_sql_asc = "asc";

    ContextMenu tmpMenu;
    int selectedItemOrder = -1;

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
        MenuImage = view.findViewById(R.id.schedule_main_SortImage);   // 검색 이미지 버튼

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
                search(s.toString().trim());
            }
        });

        //registerForContextMenu(MenuImage);    // 롱터치를 원할 때
        MenuImage.setOnClickListener(new View.OnClickListener() {   // 숏터치를 원할 때ㄹ
            @Override
            public void onClick(View view) {
                registerForContextMenu(view);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // API 레벨 24 이상에서만 동작
                    view.showContextMenu(view.getX(), view.getY()+view.getHeight());
                }
                //getActivity().openContextMenu(view);  // 팝업창으로 띄우는 방법
                unregisterForContextMenu(view);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.sort_menu_schedule, menu);
        tmpMenu = menu; // 외부에서도 ContextMenu를 쓰기위해 임시변수에 menu 삽입
        if (selectedItemOrder != -1) {  // 초기 메뉴 오픈은 제외, 메뉴가 선택되었을 때만
            itemChangeColor(menu, selectedItemOrder, false);
        }
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.OldSort:
                sort_sql = "_id";
                sort_sql_asc = "asc";
                sort(sort_sql, sort_sql_asc);
                selectedItemOrder = 0;          // 선택한 메뉴 항목 순서 = 0
                itemChangeColor(tmpMenu, selectedItemOrder, true);
                return true;
            case R.id.CurrentSort:
                sort_sql = "_id";
                sort_sql_asc = "desc";
                sort(sort_sql, sort_sql_asc);
                selectedItemOrder = 1;          // 선택한 메뉴 항목 순서 = 1
                itemChangeColor(tmpMenu, selectedItemOrder, true);
                return true;
            case R.id.TitleSort:
                sort_sql = "title";
                sort_sql_asc = "asc";
                sort(sort_sql, sort_sql_asc);
                selectedItemOrder = 2;          // 선택한 메뉴 항목 순서 = 2
                itemChangeColor(tmpMenu, selectedItemOrder, true);
                return true;
            case R.id.TitleReverseSort:
                sort_sql = "title";
                sort_sql_asc = "desc";
                sort(sort_sql, sort_sql_asc);
                selectedItemOrder = 3;          // 선택한 메뉴 항목 순서 = 3
                itemChangeColor(tmpMenu, selectedItemOrder, true);
                return true;
            case R.id.OldDateSort:
                sort_sql = "date";
                sort_sql_asc = "desc";
                sort(sort_sql, sort_sql_asc);
                selectedItemOrder = 4;          // 선택한 메뉴 항목 순서 = 4
                itemChangeColor(tmpMenu, selectedItemOrder, true);
                return true;
            case R.id.CurrentDateSort:
                sort_sql = "date";
                sort_sql_asc = "asc";
                sort(sort_sql, sort_sql_asc);
                selectedItemOrder = 5;          // 선택한 메뉴 항목 순서 = 5
                itemChangeColor(tmpMenu, selectedItemOrder, true);
                return true;
        }
        return super.onContextItemSelected(item);
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
            data = database.selectData("schedule_table", "select * from schedule_table where title like \"%"+s_text+"%\""
                                                                        +" order by "+sort_sql+" "+sort_sql_asc+""); // DB에서 검색 데이터 가져오기
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

    public void sort(String col, String asc){
        adapter.clearItem(); // 문자 입력할 때마다 다 지우고 새로 생성
        data = database.selectData("schedule_table", "select * from schedule_table" + " order by "+col+" "+asc+""); // DB에서 전체 데이터 가져오기
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
        schedule_listView.setAdapter(adapter); // 리스트뷰에 세팅
    }

    /**
     * 메뉴 - 선택된 메뉴 항목를 표시해주기 위해 선택된 메뉴항목의 색 변경을 하는 함수
     * @param menu       : 메뉴
     * @param item_order : 선택한 메뉴 항목 순서
     * @param selected   : True = 메뉴 선택 시 / False = 메뉴 오픈 시
     */
    public void itemChangeColor(ContextMenu menu, int item_order, boolean selected){
        if(selected) {  // 메뉴 항목 선택시 항목색 초기화
            for (int i = 0; i < menu.size(); i++) {
                MenuItem AllItem = menu.getItem(i);
                SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
                spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spanString.length(), 0); //fix the color to white
                AllItem.setTitle(spanString);
            }
        }
        /** 선택한 메뉴 색 변경
         *  Item = 선택된 메뉴 항목
         */
        MenuItem Item = menu.getItem(item_order);
        SpannableString spanString = new SpannableString(menu.getItem(item_order).getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, spanString.length(), 0); //fix the color to white
        Item.setTitle(spanString);
    }

}