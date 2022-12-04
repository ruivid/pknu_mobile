package com.example.term_project.phone;

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

import com.example.term_project.DBHandler;
import com.example.term_project.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Phone_Main_Fragment extends Fragment {

    ListView listView;
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
            database = DBHandler.getInstance(requireActivity().getApplicationContext());
            data = database.selectData("phone_table", "select * from phone_table");
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
        listView = view.findViewById(R.id.phone_main_PhoneListview);
        adapter = new PhoneAdapter();
        for(String[] item : data) {
            adapter.addItem(Phone_Item.builder()
                    .name(item[0])
                    .phone_number(item[1])
                    .email(item[2])
                    .imageId(R.drawable.phone_default_image)
                    .build());
        }
        listView.setAdapter(adapter);

        /*
            터치 이벤트 (버튼[추가] / 리스트[조회])
         */

        Button button = (Button) view.findViewById(R.id.phone_main_btnPlus);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "추가 클릭", Toast.LENGTH_LONG).show();
                Intent PhoneInputIntent = new Intent(getContext(), Phone_Input_Activity.class);   // 추가화면(=개별 조회화면)
                startActivity(PhoneInputIntent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Phone_Item item = (Phone_Item) adapter.getItem(position);
                Toast.makeText(getContext(), "선택 : " + item.getName(), Toast.LENGTH_LONG).show();
                Intent Selected_Intent = new Intent(getContext(), Phone_Detail_Activity.class); // 개별 조회화면(=[데이터첨부된]추가화면)
                startActivity(Selected_Intent);
            }
        });
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
    }
}