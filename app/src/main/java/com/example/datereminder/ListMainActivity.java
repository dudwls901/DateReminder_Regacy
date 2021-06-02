package com.example.datereminder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ListMainActivity extends AppCompatActivity {
    ListView listView;
    DBHelper dbhelper;
    SQLiteDatabase sqlDB;
    ListAdapter adapter;
    String year, month, day, hour, minute;
    Cursor cursor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_main);




        TabLayout tabs = findViewById(R.id.tabs);

        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab(), true);

        tabs.getTabAt(0).setIcon(R.drawable.ic_1);
        tabs.getTabAt(1).setIcon(R.drawable.ic_2);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    Intent intent1 = new Intent(ListMainActivity.this, MainActivity.class);
                    startActivity(intent1);
                    finish();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        dbhelper = new DBHelper(this);


//데이터베이스 인덱스 탐색하여 밑에 추가 생성
        listView = findViewById(R.id.listView);
        adapter = new ListAdapter();

        sqlDB = dbhelper.getReadableDatabase();

        cursor = sqlDB.rawQuery("SELECT * FROM DATE_TB;", null);
        String date = "";


        //리스트뷰 생성
        //데이터베이스의 값을 가져와 출력
        while (cursor.moveToNext()) {
            year = cursor.getString(1).substring(0,4)+"년 ";
            Log.d("length1",year);
            month = cursor.getString(1).substring(5,7)+"월 ";
            Log.d("length2",month);
            day = cursor.getString(1).substring(8,10)+"일 ";
            Log.d("length3",day);
            hour = cursor.getString(1).substring(11,13)+"시 ";
            Log.d("length4",hour);
            minute = cursor.getString(1).substring(14)+"분 ";
            date = year + month + day + hour + minute + "\n" + cursor.getString(2) + "\n" + cursor.getString(3);

            adapter.addItem(new ListItem(date));

        }
        //   adapter.items.

        cursor.close();
        sqlDB.close();


        listView.setAdapter(adapter);
    }//onCreate 끝


    //어댑터
    public class ListAdapter extends BaseAdapter {


        ArrayList<ListItem> items = new ArrayList<ListItem>();

        //리스트 항목의 개수를 안드로이드 os에게 알려줌
        @Override
        public int getCount() {
            return items.size();
        }

        //리스트에 아이템 담기
        public void addItem(ListItem item) {
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

        //아이템을 뷰로 띄움
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final int pos = position;
            final Context context = parent.getContext();

            // 'listview_custom' Layout을 inflate하여 convertView 참조 획득
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item, parent, false);
            }

            // 'listview_custom'에 정의된 위젯에 대한 참조 획득
            TextView datetext = (TextView) convertView.findViewById(R.id.dateText);
            final ImageButton delete = (ImageButton) convertView.findViewById(R.id.delete);
            final ImageButton modify = (ImageButton) convertView.findViewById(R.id.modify);

            // 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용
            ListItem item = items.get(pos);

            // 각 위젯에 세팅된 아이템을 뿌려준다
            datetext.setText(item.getDate());

            // DATE 삭제!
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, pos+1 + "번째 이미지 선택", Toast.LENGTH_SHORT).show();

                    //커서가 가리키는 인덱스와 버튼이 클릭된 인덱스 비교하여 해당 인덱스의 DATE_CODE값 가져오기
                    sqlDB = dbhelper.getReadableDatabase(); //datereminderDB를 읽기 전용으로 연다
                    cursor = sqlDB.rawQuery("SELECT * FROM DATE_TB;", null);
                    int selected_code = 0;
                    while (cursor.moveToNext()) {
                        if (cursor.getPosition() == pos)
                            selected_code = cursor.getInt(0);

                    }
                    cursor.close();
                    sqlDB.close();

                    sqlDB = dbhelper.getWritableDatabase();// datereminderDB를 쓰기 전용으로 연다
                    sqlDB.execSQL("delete from DATE_TB where DATE_CODE='" + selected_code + "';");
                    sqlDB.close();


                    Intent intent2 = new Intent(ListMainActivity.this, ListMainActivity.class);
                    finish();
                    startActivity(intent2);
                }
            });
            //DATE 수정!
            modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, pos+1 + "선택: " + "번째 이미지 선택", Toast.LENGTH_SHORT).show();
                    //커서가 가리키는 인덱스와 버튼이 클릭된 인덱스 비교하여 해당 인덱스의 DATE_CODE값 가져오기
                    sqlDB = dbhelper.getReadableDatabase(); //datereminderDB를 읽기 전용으로 연다
                    cursor = sqlDB.rawQuery("SELECT * FROM DATE_TB;", null);
                    int selected_code = 0;
                    while (cursor.moveToNext()) {
                        if (cursor.getPosition() == pos)
                            selected_code = cursor.getInt(0);

                    }
                    cursor.close();
                    sqlDB.close();
                    Intent intent3 = new Intent(ListMainActivity.this, MainActivity.class);
                    intent3.putExtra("BOOL",true);
                    intent3.putExtra("selectedcode",selected_code);
                    startActivity(intent3);
                    finish();



                }
            });








            return convertView;
        }
    }//Listadapter끝
    //Todo db delete, update하기(뷰페이저 띄어서 현재 값에 체인지체인지)
    //TODO OPTIONSMENU에 시간제외,정렬(ADDITEM하기 전에 DATABASE에서 ORDERBY), sound on
    //TODO 다이얼로그로 퀴즈~

    // sqlDB.execSQL("update Habit set habit_count='" + (habit_count + 1) + "'where habit_num='" + habit_num + "';");//1증가시킴
    //sqlDB.execSQL("delete from Habit where habit_num='"+habit_num+"';");//습관 삭제
}
