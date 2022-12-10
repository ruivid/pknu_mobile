package com.example.term_project.phone;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import com.example.term_project.schedule.Schedule_Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Phone_Main_Fragment extends Fragment {
    ListView listView;
    EditText editText;
    ImageView searchImage;
    PhoneAdapter adapter;
    DBHandler database;
    List<String[]> data = new ArrayList<>();

    public Phone_Main_Fragment() {
        // Required empty public constructor
    }

    public static Phone_Main_Fragment newInstance() {
        return new Phone_Main_Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            database = DBHandler.getInstance(requireActivity().getApplicationContext()); // DB 핸들링 객체 불러오기.
        } catch(Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { // view 생성
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.phone_main_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) { // view 생성 후
        editText = view.findViewById(R.id.phone_main_SearchName);       // 검색 창
        searchImage = view.findViewById(R.id.phone_main_SearchImage);   // 검색 이미지 버튼

        listView = view.findViewById(R.id.phone_main_PhoneListview);
        adapter = new PhoneAdapter();
        listView.setAdapter(adapter);

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

        Button button = (Button) view.findViewById(R.id.phone_main_btnPlus);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent PhoneInputIntent = new Intent(getContext(), Phone_Input_Activity.class);   // 추가화면(=개별 조회화면)
                PhoneInputIntent.putExtra("type", "new");
                startActivity(PhoneInputIntent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // position으로 넘기는게 아니라 DB 내 컬럼 값으로---------------------------
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Phone_Item item = (Phone_Item) adapter.getItem(position); // 클릭한 포지션을 바탕으로 아이템 객체 가져오기
                Intent Selected_Intent = new Intent(getContext(), Phone_Detail_Activity.class); // 이동할 디테일 액티비티 객체 생성
                Selected_Intent.putExtra("detail", item.getDetail()); // 아이템의 정보를 detail에 담음
                startActivity(Selected_Intent); // 화면 이동
            }
        });
    }

    @Override
    public void onStart() { // view 사용자에게 보이기 시작. 상호작용 불가능. 프래그먼트가 백그라운드에서 돌아오다가 사용자에게 다시 노출되었을 때 가장 먼저 호출 되는 지점.
        super.onStart();
        data = database.selectData("phone_table", "select * from phone_table"); // DB에서 데이터 가져오기

        adapter.clearItem(); // 어댑터 비우기
        for(String[] item : data) { // Db에서 가져온 데이터로 Phone_item 객체 만들어 어댑터에 등록
            adapter.addItem(Phone_Item.builder()
                    .name(item[0])
                    .phone_number(item[1])
                    .email(item[2])
                    .imageId(R.drawable.phone_user_image)
                    .Id(item[3])
                    .build());
        }
        listView.setAdapter(adapter); // 리스트뷰에 세팅
        FragmentTransaction ft = getParentFragmentManager().beginTransaction(); // DB 업데이트를 갱신하기 위한 프래그먼트 새로고침
        ft.detach(this).attach(this).commit();
    }

    class PhoneAdapter extends BaseAdapter {
        ArrayList<Phone_Item> items = new ArrayList<Phone_Item>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Phone_Item item) {
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
            Phone_Item_View view = new Phone_Item_View(getContext());

            Phone_Item item = items.get(position);
            view.setName(item.getName());
            view.setPhone_number(item.getPhone_number());
            view.setImage(item.getImageIdId());

            return view;
        }

        public void clearItem() { items.clear(); }
    }

    public void search(String s_text){
        adapter.clearItem(); // 문자 입력할 때마다 다 지우고 새로 생성
        if(s_text.length() == 0){   // 문자 입력이 없을 때는 모든 데이터 보여줌
            data = database.selectData("phone_table", "select * from phone_table"); // DB에서 전체 데이터 가져오기
            for(String[] item : data) { // Db에서 가져온 데이터로 Phone_item 객체 만들어 어댑터에 등록
                adapter.addItem(Phone_Item.builder()
                        .name(item[0])
                        .phone_number(item[1])
                        .email(item[2])
                        .imageId(R.drawable.phone_user_image)
                        .Id(item[3])
                        .build());
            }
        }
        else{   // 문자 입력 시
            data = database.selectData("phone_table", "select * from phone_table where name like \"%"+s_text+"%\""); // DB에서 검색 데이터 가져오기
            for(String[] item : data) { // Db에서 가져온 데이터로 Phone_item 객체 만들어 어댑터에 등록
                adapter.addItem(Phone_Item.builder()
                        .name(item[0])
                        .phone_number(item[1])
                        .email(item[2])
                        .imageId(R.drawable.phone_user_image)
                        .Id(item[3])
                        .build());
            }
        }
        listView.setAdapter(adapter); // 리스트뷰에 세팅
    }
}