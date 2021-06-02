package com.example.datereminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
                        //DB이름              version
        super(context, "datereminderDB", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS DATE_TB (DATE_CODE INTEGER PRIMARY KEY AUTOINCREMENT , DATE_TIME VARCHAR(30) NOT NULL, DATE_PLACE VARCHAR(50) NOT NULL, DATE_TODO VARCHAR(50) NOT NULL, DATE_QUIZNUM INTEGER NOT NULL);");
        //db.execSQL("INSERT INTO DATE_TB VALUES ('1','1','1','1','5','6','7','8','abc','adf','11' );");
        db.execSQL("CREATE TABLE IF NOT EXISTS EXCEPTIONTIME_TB (EX_CODE INTEGER PRIMARY KEY NOT NULL, EX_STARTTIME INTEGER NOT NULL, EX_FINISHTIME INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DATE_TB");
        db.execSQL("DROP TABLE IF EXISTS EXCEPTIONTIME_TB");
    }

}
