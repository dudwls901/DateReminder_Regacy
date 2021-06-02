package com.example.datereminder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.jar.Attributes;

//import com.example.datereminder.R;
//import android.R;

public class ListItemView extends LinearLayout {
    TextView dateText;
    ImageView delete, modify;

    public ListItemView(Context context) {
        super(context);
        init(context);
    }

    public ListItemView(Context context, Attributes attrs) {
        super(context, (AttributeSet) attrs);
        init(context);

    }

    //onCreate()같은 역할
    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View inflate = inflater.inflate(R.layout.ist_item, this, true);
        inflater.inflate(R.layout.list_item, this, true);
        dateText = findViewById(R.id.dateText);
        delete = findViewById(R.id.delete);
        modify = findViewById(R.id.modify);

    }//init 끝

    public void setDate(String date)
    {
        dateText.setText(date);
    }

}
