package com.example.administrator.emmapplication.activity.Fragement;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.emmapplication.R;
import com.example.administrator.emmapplication.activity.MainActivity;

public class FragmentWorking extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";

    public FragmentWorking() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        try {
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.planets_array)[i];
            rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            getActivity().setTitle(planet);
            init(rootView);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }

    private void init(View rootView) {
        Button test = rootView.findViewById(R.id.btnTest);
        test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        });
    }
}
