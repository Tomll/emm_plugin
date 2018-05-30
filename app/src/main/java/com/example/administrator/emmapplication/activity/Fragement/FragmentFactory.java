package com.example.administrator.emmapplication.activity.Fragement;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;

import com.example.administrator.emmapplication.R;
import com.example.administrator.emmapplication.activity.MenuActivity;


public class FragmentFactory {
    private Fragment fragment;
    private static final String ARG_PLANET_NUMBER = "planet_number";

    public FragmentFactory(int position){
        switch (position){
            case 0:
                fragment = new FragmentWorking();
                break;
            case 1:
                fragment = new FragmentNotes();
                break;
            case 2:
                fragment = new FragmentSetting();
                break;
        }
    }

    public void init(Activity activity,int position){
        Bundle args = new Bundle();
        args.putInt(ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);
        FragmentManager fragmentManager = activity.getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
}
