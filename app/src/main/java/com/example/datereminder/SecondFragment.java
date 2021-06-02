package com.example.datereminder;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {
    private Context context;
    Button btnOK2;
    TimePicker timepicker;
    MainActivity activity;


    public static SecondFragment newInstance() {
        SecondFragment fragment = new SecondFragment();
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
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        context = container.getContext();
        btnOK2 = view.findViewById(R.id.btnOK2);
        timepicker = view.findViewById(R.id.timepickerview);

        activity = (MainActivity) getActivity();

        btnOK2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                activity.dateHM = String.format(":%02d:%02d", timepicker.getHour(), timepicker.getMinute());
                Toast.makeText(context, activity.dateHM,Toast.LENGTH_LONG).show();
                Log.d("chkTime",activity.dateHM);



            }
        });
        return view;
    }
}
