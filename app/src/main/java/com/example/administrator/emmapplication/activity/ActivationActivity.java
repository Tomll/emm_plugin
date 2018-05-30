package com.example.administrator.emmapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.emmapplication.R;
import com.example.administrator.emmapplication.utils.DevicePolicyUtils;
import com.example.administrator.emmapplication.utils.HandlerActivityUtils;
import com.example.administrator.emmapplication.utils.JPushUtils;
import com.example.administrator.emmapplication.utils.MemoryUtils;
import com.example.administrator.emmapplication.utils.UpdateStrategyChangeUtils;
import com.example.administrator.emmapplication.utils.mobileInfos.DeviceInfos;
import com.example.administrator.emmapplication.utils.retrofits.ActivateBaseRetrofitUtils;
import com.example.administrator.emmapplication.utils.PermissionsChecker;
import com.example.administrator.emmapplication.utils.retrofits.BaseRetrofitUtils;
import com.example.administrator.emmapplication.utils.SharedfPreferencesUtils;
import com.example.administrator.emmapplication.utils.mobileInfos.MobileInfosUtils;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivationActivity extends Activity {
    private static final int SCAN_REQUEST_CODE = 11111;

    private EditText serviceHost;
    private EditText servicePort;
    private EditText corporateLogo;
    private EditText userName;
    private EditText activatePwd;

    private PermissionsChecker mPermissionsChecker; // 权限检测器
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }

        UpdateStrategyChangeUtils.createChangeRecord(this);
    }

    private void startPermissionsActivity() {
        mPermissionsChecker.startPermission(PERMISSIONS);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);
        init();
        DevicePolicyUtils.startDeviceManager(this);
        HandlerActivityUtils.getInstance().activateActivity(ActivationActivity.this);
        MemoryUtils.setContext(ActivationActivity.this);
    }

    private void init(){
        serviceHost=findViewById(R.id.editText);
        servicePort=findViewById(R.id.editText2);
        corporateLogo=findViewById(R.id.editText3);
        userName=findViewById(R.id.editText4);
        activatePwd=findViewById(R.id.editText5);

        final Button activate = findViewById(R.id.btnActivation);
        activate.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                activate();
            }
        });

        mPermissionsChecker = new PermissionsChecker(this,ActivationActivity.this);

        JPushUtils.getInstance().initJPush();

        ZXingLibrary.initDisplayOpinion(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 213 && !mPermissionsChecker.hasAllPermissionsGranted(grantResults)){
            finish();
            Toast.makeText(this,"请赋予以上权限",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SCAN_REQUEST_CODE&&data!=null){
            Bundle bundle = data.getExtras();
            if(bundle!=null) {
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    sweepCodeAssignment(result);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
        if (requestCode == 1210) {
            // 激活组件的响应
            if (resultCode == Activity.RESULT_CANCELED) {
                //如果点击取消激活则关闭页面
                finish();
            }
        }
    }

    private void sweepCodeAssignment(String result){
        try {
            JSONObject json = new JSONObject(result);
            String proxy_ip = json.getString("proxy_ip");
            String proxy_port = json.getString("proxy_port");
            String tenant = json.getString("tenant");
            String account = json.getString("account");
            String pin_code = json.getString("pincode");

            serviceHost.setText(proxy_ip);
            servicePort.setText(proxy_port);
            corporateLogo.setText(tenant);
            userName.setText(account);
            activatePwd.setText(pin_code);

            //激活
            activate();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void scan(View view){
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, SCAN_REQUEST_CODE);
    }


    //region 设备激活并上传设备信息
    private void activate(){
        JSONObject json = new JSONObject();
        try {
            json.put("server",(nullToZero(serviceHost.getText())));
            json.put("port",(Integer.parseInt(nullToZero(servicePort.getText()))));
            json.put("tenant",(nullToZero(corporateLogo.getText())));
            json.put("account",(nullToZero(userName.getText())));
            json.put("activation_code",(nullToZero(activatePwd.getText())));
            json.putOpt("device_info",new MobileInfosUtils(this).getAllInfos());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(json.toString());
        scanActivation(json.toString());
    }
    private String nullToZero(Editable getText){
        return getText!=null?getText.toString():"0";
    }
    private void scanActivation(String json){
        new ActivateBaseRetrofitUtils().activate(json,new BaseRetrofitUtils.ApiCallback(){
            @Override
            public void onSuccess(Object ret) {
                String msg=((ActivateBaseRetrofitUtils.ActivateRetData)ret).msg;
                Object data=((ActivateBaseRetrofitUtils.ActivateRetData)ret).data;
                System.out.println(msg);
                String android_id=DeviceInfos.getSerialNumber();
                JPushUtils.getInstance().setAlias(android_id);
                addUserInfo();
                mainActivity();
                setToken(data);
                SharedfPreferencesUtils.getInstance(ActivationActivity.this).setIsActivateFirstUse();
            }

            @Override
            public void onError(Object ret) {
                int status=((ActivateBaseRetrofitUtils.ActivateRetData)ret).status;
                String msg=((ActivateBaseRetrofitUtils.ActivateRetData)ret).msg;
                System.out.println("HttpError"+status+msg);
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                System.out.println("连接失败");
                Toast.makeText(getApplicationContext(),"通信失败！",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setToken(Object data) {
        System.out.println(data);
        try {
            JSONObject jsonData = new JSONObject(data.toString());
            String token = jsonData.getString("token");
            SharedfPreferencesUtils.getInstance(ActivationActivity.this).setToken(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region simple function
    private void mainActivity(){
        Intent intent = new Intent(ActivationActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void addUserInfo(){
        String account = userName.getText().toString().trim();
        String tenant = corporateLogo.getText().toString().trim();
        SharedfPreferencesUtils.getInstance(this).setAccount(tenant,account);
    }


    //endregion
}
