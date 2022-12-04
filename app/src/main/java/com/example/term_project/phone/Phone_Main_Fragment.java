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
import java.util.Objects;

public class Phone_Main_Fragment extends Fragment {

    ListView listView;
    PhoneAdapter adapter;
    DBHandler database;

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
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.phone_main_fragment, container, false);

        listView = (ListView) rootview.findViewById(R.id.phone_main_PhoneListview);

        adapter = new PhoneAdapter();
        adapter.addItem(new Phone_Item("kim친구1", "010-1000-1000", R.drawable.phone_user_image));
        database.insertRecordParam("phone_table", new String[]{"kim친구1", "010-1000-1000", "test@test.com"});
        adapter.addItem(new Phone_Item("kim친구2", "010-2000-2000", R.drawable.phone_user_image));
        database.insertRecordParam("phone_table", new String[]{"kim친구2", "010-2000-2000", "test@test.com"});
        adapter.addItem(new Phone_Item("이친구3", "010-3000-3000", R.drawable.phone_user_image));
        database.insertRecordParam("phone_table", new String[]{"이친구3", "010-3000-3000", "test@test.com"});
        adapter.addItem(new Phone_Item("최친구4", "010-4000-4000", R.drawable.phone_user_image));
        database.insertRecordParam("phone_table", new String[]{"최친구4", "010-4000-4000", "test@test.com"});
        adapter.addItem(new Phone_Item("하친구5", "010-5000-5000", R.drawable.phone_user_image));
        database.insertRecordParam("phone_table", new String[]{"하친구5", "010-5000-5000", "test@test.com"});

        listView.setAdapter(adapter);

        /*
            터치 이벤트 (버튼[추가] / 리스트[조회])
         */
        Button button = (Button) rootview.findViewById(R.id.phone_main_btnPlus);
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

        return rootview;
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