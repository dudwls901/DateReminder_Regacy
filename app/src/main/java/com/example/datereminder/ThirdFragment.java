package com.example.datereminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {
    MainActivity activity;

    DBHelper dbhelper;
    SQLiteDatabase sqlDB;
    EditText editwhere, editwhat;
    private Context context;
    Button btnCreate;
    Calendar dateCal, currentCal, firstCal, secondCal;
    SimpleDateFormat timeFormat;


    Cursor cursor;

    // newInstance constructor for creating fragment with arguments
    public static ThirdFragment newInstance() {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        context = container.getContext();
        activity = (MainActivity) getActivity();
//        listMainActivity = (ListMainActivity)getActivity();
        editwhere = view.findViewById(R.id.editWHERE);
        editwhat = view.findViewById(R.id.editWHAT);
        btnCreate = view.findViewById(R.id.btnCreate);
        dbhelper = new DBHelper(context);//생성자와 onCreate까지 호출됨

        //데이트 생성과 동시에 알람 설정
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //설정한 데이트 날짜 및 시간
                activity.date_finalTime = activity.dateYMD + activity.dateHM;
                activity.date_place = editwhere.getText().toString();
                activity.date_todo = editwhat.getText().toString();
                Log.d("chkfinalTime", activity.date_finalTime);

                //데이터 성공적으로 들어갔을 때
                //startactivity ListMainactivity
                //db에 값들 insert하기
                //date 생성시 날짜 및 시간 가져오기

                //현재날짜 및 시간 ==currentCal (date타입)
                timeFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm", Locale.KOREA);
                currentCal = Calendar.getInstance();
                currentCal.setTime(new Date());
                timeFormat.format(currentCal.getTime());

                //데이트 날짜 및 시간 ==dateCal (date타입)
                dateCal = Calendar.getInstance();
                try {
                    dateCal.setTime(timeFormat.parse(activity.date_finalTime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Long diff;
                diff = dateCal.getTimeInMillis() - currentCal.getTimeInMillis();
                Log.v("aaaa", String.valueOf(diff));
                long sec = diff / 1000;
                long minute = diff / (1000 * 60);
                Log.d("aaaaa", String.valueOf(sec));
                Log.d("aaaaaa", String.valueOf(minute));

                //DATE는 현재 시간보다 이후여야 한다
                if (diff > 0) {


                    try {


                        String date = "";

                        //수정하기
                        if (activity.modify == true) {
                            Log.d("chk1", String.valueOf(activity.modify));
                            sqlDB = dbhelper.getWritableDatabase();// datereminderDB를 쓰기 전용으로 연다

                            sqlDB.execSQL("UPDATE DATE_TB SET DATE_TIME='" + activity.date_finalTime + "', DATE_PLACE='" + activity.date_place + "',DATE_TODO='" + activity.date_todo + "',DATE_QUIZNUM=0  WHERE DATE_CODE='" + activity.selected_code + "';");


                            sqlDB.close();

                            //db의 DATE_CODE값 받아와서 AlarmActivity에 넘기기
                            sqlDB = dbhelper.getReadableDatabase();
                            cursor = sqlDB.rawQuery("SELECT * FROM DATE_TB;", null);
                            cursor.moveToLast();
                            activity.alarmindex = cursor.getInt(0) * 10;

                            sqlDB.close();
                            currentCal.add(Calendar.MINUTE, (int) (minute / 3)); //1번째 알람
                            firstCal = currentCal;
                            Log.d("aaaaaafirstTime", timeFormat.format(firstCal.getTime()));
                            setAlarm(firstCal);

                            currentCal.add(Calendar.MINUTE, (int) (minute / 3)); //2번째 알람
                            secondCal = currentCal;
                            setAlarm(secondCal);
                            Log.d("aaaaaasecondTime", timeFormat.format(secondCal.getTime()));
                            Log.v("aaaaaacurrentplus", timeFormat.format(currentCal.getTime()));

                            setAlarm(dateCal);  //3번째 알람

                        }
                        //삽입하기
                        else {
                            //데이트 생성
                            Log.d("chk2", String.valueOf(activity.modify));
                            sqlDB = dbhelper.getWritableDatabase();// datereminderDB를 쓰기 전용으로 연다
                            sqlDB.execSQL("INSERT INTO DATE_TB VALUES ( null,'" + activity.date_finalTime + "','" + activity.date_place + "','" + activity.date_todo + "',0);");

                            sqlDB.close();


                            //db의 DATE_CODE값 받아와서 AlarmActivity에 넘기기
                            sqlDB = dbhelper.getReadableDatabase();
                            cursor = sqlDB.rawQuery("SELECT * FROM DATE_TB;", null);
                            cursor.moveToLast();
                            activity.alarmindex = cursor.getInt(0) * 10;

                            sqlDB.close();
                            currentCal.add(Calendar.MINUTE, (int) (minute / 3)); //1번째 알람
                            firstCal = currentCal;
                            Log.d("aaaaaafirstTime", timeFormat.format(firstCal.getTime()));
                            setAlarm(firstCal);

                            currentCal.add(Calendar.MINUTE, (int) (minute / 3)); //2번째 알람
                            secondCal = currentCal;
                            setAlarm(secondCal);
                            Log.d("aaaaaasecondTime", timeFormat.format(secondCal.getTime()));
                            Log.v("aaaaaacurrentplus", timeFormat.format(currentCal.getTime()));

                            setAlarm(dateCal);  //3번째 알람


                        }//삽입문 끝

                        Toast.makeText(context, "입력됐습니다", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.d("abc", String.valueOf(e));
                        Toast.makeText(context, "값을 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(context, ListMainActivity.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(context, "DATE는 미래 일정만 생성해주세요..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    void setAlarm(Calendar alarmCal) {
        Log.d("aaaaaaalarmTime", timeFormat.format(alarmCal.getTime()));
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        Intent codeIntent = new Intent(context, AlarmActivity.class);
//db데이트코드 *10 + alarmindex1,2,3

        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmIntent.setAction(AlarmReceiver.ACTION_RESTART_SERVICE);

        PendingIntent alarmCallPendingIntent = PendingIntent.getBroadcast(context, activity.alarmindex, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("bbb~", String.valueOf(activity.alarmindex));
        activity.alarmindex++;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), alarmCallPendingIntent);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), alarmCallPendingIntent);

    }
}
