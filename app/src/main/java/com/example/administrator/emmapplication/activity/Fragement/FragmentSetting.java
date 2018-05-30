package com.example.administrator.emmapplication.activity.Fragement;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.emmapplication.R;
import com.example.administrator.emmapplication.activity.LoginActivity;
import com.example.administrator.emmapplication.entity.LimitStrategyEntity;
import com.example.administrator.emmapplication.utils.GsonUtils;
import com.example.administrator.emmapplication.utils.SharedfPreferencesUtils;
import com.example.administrator.emmapplication.utils.UpdateStrategyChangeUtils;
import com.example.administrator.emmapplication.utils.retrofits.BaseRetData;
import com.example.administrator.emmapplication.utils.retrofits.LoginOutBaseRetrofitUtils;
import com.example.administrator.emmapplication.utils.retrofits.BaseRetrofitUtils;

import static android.security.KeyStore.getApplicationContext;

public class FragmentSetting extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";

    public FragmentSetting() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragement_set, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.planets_array)[i];
            setInit(rootView);
            getActivity().setTitle(planet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }

    private void setInit(View view){
        view.findViewById(R.id.btnLoginOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //TODO something --- loginOut
                try {
                    String token= SharedfPreferencesUtils.getInstance(view.getContext()).getToken();
                    new LoginOutBaseRetrofitUtils().loginOut(token, new BaseRetrofitUtils.ApiCallback() {
                        @Override
                        public void onSuccess(Object ret) {
                            SharedfPreferencesUtils.getInstance(view.getContext()).setIsNotLoginFirstUse();
                            Intent intent = new Intent(view.getContext(), LoginActivity.class);
                            view.getContext().startActivity(intent);
//                            JPushUtils.getInstance().stopJPush();
//                            MenuActivity.this.finish();
                            ((Activity)view.getContext()).finish();
                        }

                        @Override
                        public void onError(Object ret) {
                            String msg = ((BaseRetData)ret).msg;
                            System.out.println(msg);
                        }

                        @Override
                        public void onFailure() {
                            System.out.println("连接失败！");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        view.findViewById(R.id.btnHelp).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    LimitStrategyEntity limitStrategy = (LimitStrategyEntity)
                            GsonUtils.getJsonToEntity(getApplicationContext(),
                                    UpdateStrategyChangeUtils.fileName,LimitStrategyEntity.class);
                    System.out.println(limitStrategy);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
