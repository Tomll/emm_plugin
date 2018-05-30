package com.example.administrator.emmapplication.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.emmapplication.R;
import com.example.administrator.emmapplication.utils.HandlerActivityUtils;
import com.example.administrator.emmapplication.utils.retrofits.LoginBaseRetrofitUtils;
import com.example.administrator.emmapplication.utils.retrofits.BaseRetrofitUtils;
import com.example.administrator.emmapplication.utils.SharedfPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {
    EditText account;
    EditText password;
    TextView missPwd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initControl();
        initAccount();
        HandlerActivityUtils.getInstance().loginActivity(LoginActivity.this);
    }

    private void initControl(){
        account=findViewById(R.id.editText7);
        password=findViewById(R.id.editText6);
        missPwd=findViewById(R.id.btnMissPwd);
        missPwd.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                findPwd();
            }
        });
    }

    private void initAccount(){
        String userName = SharedfPreferencesUtils.getInstance(this).getAccount();
        account.setText(userName);
    }

    public void login(View view){
        String userName = account.getText().toString();
        String pwd = password.getText().toString();
        String tenant = SharedfPreferencesUtils.getInstance(this).getTenant();
        new LoginBaseRetrofitUtils().login(tenant,userName,pwd,new BaseRetrofitUtils.ApiCallback(){
            @Override
            public void onSuccess(Object ret) {
                try {
                    String data=((LoginBaseRetrofitUtils.LoginRetData)ret).data.toString();
                    System.out.println(data);
                    JSONObject jsonData = new JSONObject(data);
                    String token = jsonData.getString("token");
                    SharedfPreferencesUtils.getInstance(LoginActivity.this).setToken(token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SharedfPreferencesUtils.getInstance(LoginActivity.this).setIsLoginFirstUse();
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
//                JPushUtils.getInstance().resumeJPush();
                finish();
            }

            @Override
            public void onError(Object ret) {
                int status=((LoginBaseRetrofitUtils.LoginRetData)ret).status;
                String msg=((LoginBaseRetrofitUtils.LoginRetData)ret).msg;
                System.out.println("HttpError"+status+msg);
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                System.out.println("连接失败");
                Toast.makeText(getApplicationContext(),"通信失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void findPwd(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(R.string.string_find_password);
        //好的，退出弹窗
        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}
