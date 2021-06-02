package com.example.datereminder;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivity extends AppCompatActivity {
    boolean flag = true;
    View dialogView;
    Button ok, hint;
    MediaPlayer mediaPlayer;
    Dialog dlg;
    Cursor cursor;
    DBHelper dbHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_main);

        //잠금 화면 위로 activity를 띄운다
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        dlg = new Dialog(this);
        dialogView = View.inflate(AlarmActivity.this, R.layout.quiz_dialog, null);
        dlg.setContentView(dialogView);

        WindowManager.LayoutParams params = dlg.getWindow().getAttributes();
        params.width = 900;
        params.height = 1320;
        dlg.getWindow().setAttributes(params);

        //다이얼로그 크기 조정

        ok = dialogView.findViewById(R.id.ok);
        hint = dialogView.findViewById(R.id.hint);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song2);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Log.d("aaaaa", "alarmactivity");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag == true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }).start();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
                mediaPlayer.stop();
                finish();
            }
        });


        dlg.show();

    }
}
