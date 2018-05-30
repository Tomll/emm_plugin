package com.example.administrator.emmapplication.activity.Fragement;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.emmapplication.R;

import cn.jpush.android.api.JPushInterface;

public class FragmentNotes extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";
    public FragmentNotes() {
        //
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragment_notes, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.planets_array)[i];
            getActivity().setTitle(planet);

            TextView tv=rootView.findViewById(R.id.txtNotes1);
            tv.setText("用户自定义打开的Activity");
            Intent intent = getActivity().getIntent();
            if (null != intent) {
                Bundle bundle = getActivity().getIntent().getExtras();
                String title = null;
                String content = null;
                if(bundle!=null){
                    title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                    content = bundle.getString(JPushInterface.EXTRA_ALERT);
                }
                tv.setText("Title : " + title + "  " + "Content : " + content);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }
}
