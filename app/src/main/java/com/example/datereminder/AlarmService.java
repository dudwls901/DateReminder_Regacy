package com.example.datereminder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class AlarmService extends Service
{

    String TAG = "TAG+Service";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"AlarmService");
        Intent alarmIntent = new Intent(AlarmService.this,AlarmActivity.class);
        alarmIntent.putExtra("AlarmIndex",startId);
        startActivity(alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        Log.d("aaaaa","alarmService");
        return super.onStartCommand(intent, flags, startId);
    }
}
