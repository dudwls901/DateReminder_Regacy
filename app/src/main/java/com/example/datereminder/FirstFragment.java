package com.example.datereminder;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {
    private Context context;
    DatePicker datepicker;
    Button btnOK1;
    MainActivity activity;




    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();//fragment생성
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the view for the fragment based on layout XML

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);
        context = container.getContext();
        btnOK1 = view.findViewById(R.id.btnOK1);
        datepicker = view.findViewById(R.id.datepickerview);
        activity = (MainActivity) getActivity();


        btnOK1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.dateYMD = String.format("%d:%02d:%02d", datepicker.getYear(), datepicker.getMonth() + 1, datepicker.getDayOfMonth());
                Toast.makeText(context, activity.dateYMD,Toast.LENGTH_LONG).show();
                Log.d("chkdate",activity.dateYMD);
            }
        });



        return view;

    }


}
