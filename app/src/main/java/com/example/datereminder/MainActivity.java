package com.example.datereminder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
 String dateYMD,dateHM,date_finalTime;
    int selected_code = 0; //ListMainActivity에서 modify를 눌렀을 때의 해당 레코드의 code값을 받아오기
    boolean modify = false;
    String date_place, date_todo;
    int alarmindex;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentPagerAdapter adapterViewpager;
        adapterViewpager = new MyPagerAdapter(getSupportFragmentManager());
        TabLayout tabs = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(adapterViewpager);
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());

        //tab에 icon 삽입
        tabs.getTabAt(0).setIcon(R.drawable.ic_1);
        tabs.getTabAt(1).setIcon(R.drawable.ic_2);

//우측 탭 클릭 시 list_main.xml 띄우기
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) { //Toast.makeText(MainActivity.this,String.valueOf(year)+String.valueOf(month)+String.valueOf(date), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, ListMainActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        Log.d("chkcode1",String.valueOf(selected_code));
        Intent inIntent = getIntent();

            selected_code = inIntent.getIntExtra("selectedcode", 0);
            Log.d("chkcode2",String.valueOf(selected_code));
            modify = inIntent.getBooleanExtra("BOOL",false);
            Log.d("chk",String.valueOf(modify));



        //TODO optionsmenu만들기
        //TODO 제외시간 받아오기


    }//oncreate 끝

    public void pushAlarmIndex(int pushdata)
    {
        Log.d("pushdata",String.valueOf(pushdata));
        Intent pushintent = new Intent(MainActivity.this, AlarmActivity.class);
        pushintent.putExtra("AlarmIndex", pushdata);

    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FirstFragment.newInstance();
                case 1:
                    return SecondFragment.newInstance();
                case 2:
                    return ThirdFragment.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
        //   Toast.makeText(MainActivity.this,FirstFragment.context,Toast.LENGTH_SHORT).show();

    }

}

