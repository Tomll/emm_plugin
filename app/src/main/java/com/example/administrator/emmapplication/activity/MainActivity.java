package com.example.administrator.emmapplication.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.emmapplication.R;
import com.example.administrator.emmapplication.device.DeviceFactory;
import com.example.administrator.emmapplication.device.deviceBrand.BdHuaWei;
import com.example.administrator.emmapplication.device.deviceBrand.BdNokio;
import com.example.administrator.emmapplication.device.deviceBrand.BdXiaoMi;
import com.example.administrator.emmapplication.device.deviceBrand.BdXiaoMiMax1Decorator;
import com.example.administrator.emmapplication.device.deviceBrand.BdXiaoMiMax2Decorator;
import com.example.administrator.emmapplication.device.deviceBrand.DeviceBrandFactory;
import com.example.administrator.emmapplication.device.deviceNormal.DeviceNormalFactory;
import com.example.administrator.emmapplication.device.deviceNormal.NDefault;
import com.example.administrator.emmapplication.device.deviceVersion.DeviceVersionFactory;
import com.example.administrator.emmapplication.device.deviceVersion.VsFiveZero;
import com.example.administrator.emmapplication.device.deviceVersion.VsFourZero;
import com.example.administrator.emmapplication.device.deviceVersion.VsSixZero;
import com.example.administrator.emmapplication.entity.ScreenCaptureEntity;
import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.entity.entityFactory.ADBFactory;
import com.example.administrator.emmapplication.entity.entityFactory.AlarmFactory;
import com.example.administrator.emmapplication.entity.entityFactory.EnableOrDisableCameraFactory;
import com.example.administrator.emmapplication.entity.entityFactory.DeviceFacory;
import com.example.administrator.emmapplication.entity.entityFactory.EnableOrDisableAppFactory;
import com.example.administrator.emmapplication.entity.entityFactory.EnableOrDisableScreenCaptureFactory;
import com.example.administrator.emmapplication.entity.entityFactory.EntityCreator;
import com.example.administrator.emmapplication.entity.entityFactory.GPSFactory;
import com.example.administrator.emmapplication.entity.entityFactory.LocationFactory;
import com.example.administrator.emmapplication.entity.entityFactory.LockPolicyFactory;
import com.example.administrator.emmapplication.entity.entityFactory.MobileInfoFactory;
import com.example.administrator.emmapplication.entity.entityFactory.PwdConfigFactory;
import com.example.administrator.emmapplication.entity.entityFactory.RecorderFactory;
import com.example.administrator.emmapplication.entity.entityFactory.SDCardFactory;
import com.example.administrator.emmapplication.entity.entityFactory.SIMInfoFactory;
import com.example.administrator.emmapplication.entity.entityFactory.SafeModeFactory;
import com.example.administrator.emmapplication.entity.entityFactory.ScreenCaptureFactory;
import com.example.administrator.emmapplication.entity.entityFactory.UnKnownSourceFactory;
import com.example.administrator.emmapplication.entity.entityFactory.UsbManagerFactory;
import com.example.administrator.emmapplication.entity.entityFactory.WechatQQAuditLogFactory;
import com.example.administrator.emmapplication.entity.entityFactory.WechatQQKeywordFilterFactory;
import com.example.administrator.emmapplication.entity.entityFactory.WechatQQLimitFactory;
import com.example.administrator.emmapplication.entity.entityFactory.WifiAccessPointFactory;
import com.example.administrator.emmapplication.utils.DevicePolicyUtils;
import com.example.administrator.emmapplication.utils.ScreenUtils;
import com.example.administrator.emmapplication.entity.entityFactory.TakePhotoFactory;
import com.example.administrator.emmapplication.entity.entityFactory.WifiEnableFactory;
import com.example.administrator.emmapplication.utils.MemoryUtils;
import com.example.administrator.emmapplication.utils.accessibility.AccessibilityUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private Context context;
    private RecyclerView recyclerView;
    private List<RecycleViewImpl> recycleViewList;
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();
    MyLocationReceiver myLocationReceiver = new MyLocationReceiver();
    private static final int REQUEST_CODE_ACTIVE_COMPONENT = 1210;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fndViewById();
        initData();
        setData();
//        grandPermission();
//        createBrandOrVersion();
        createDeractor();
        policyIntent();
        getWifiPreMission();
        checkP();
        checkAccess();
    }

    private void checkAccess() {
        try {
            //com.example.applications/com.example.administrator.emmapplication.service.MyAccessibilityService
            final String service = "com.example.applications/com.example.administrator.emmapplication.service.MyAccessibilityService";
            boolean isStartAccessibility= AccessibilityUtils.getInstance().isAccessibilitySettingsOn(this,service);
            if(!isStartAccessibility){
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * WIFI设置请求码
     */
    private final int REQUEST_CODE_ASK_WRITE_SETTINGS = 0X1;

    /**
     * 请求权限
     */
    private void getWifiPreMission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, REQUEST_CODE_ASK_WRITE_SETTINGS);
            }
        }
    }

    private void checkP(){
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
        MemoryUtils.setContext(MainActivity.this);
    }


    private void createBrandOrVersion(){
        new DeviceFactory().build(DeviceBrandFactory.class, BdHuaWei.class);
        new DeviceFactory().build(DeviceBrandFactory.class, BdNokio.class);
        new DeviceFactory().build(DeviceNormalFactory.class, NDefault.class);
        new DeviceFactory().build(DeviceVersionFactory.class, VsFiveZero.class);
        new DeviceFactory().build(DeviceVersionFactory.class, VsFourZero.class);
        new DeviceFactory().build(DeviceVersionFactory.class, VsSixZero.class);
    }

    private void createDeractor(){
        BdXiaoMi brandXiaoMi = new BdXiaoMi();

        BdXiaoMiMax1Decorator bdXiaoMiMax1Decorator = new BdXiaoMiMax1Decorator();
        bdXiaoMiMax1Decorator.setBrand(brandXiaoMi);
        bdXiaoMiMax1Decorator.excuteBrand();

        BdXiaoMiMax2Decorator bdXiaoMiMax2Decorator = new BdXiaoMiMax2Decorator();
        bdXiaoMiMax2Decorator.setBrand(brandXiaoMi);
        bdXiaoMiMax2Decorator.excuteBrand();
    }

    private void setData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        ToolsBaseAdapter toolsBaseAdapter = new ToolsBaseAdapter();
        recyclerView.setAdapter(toolsBaseAdapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(40));
        registReceiver();
    }

    private void registReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.location.test");
        registerReceiver(myLocationReceiver, intentFilter);
    }

    private void unRegistReceiver() {
        unregisterReceiver(myLocationReceiver);
    }


    private void grandPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 判断是否有WRITE_SETTINGS权限if(!Settings.System.canWrite(this)) {
            // 申请WRITE_SETTINGS权限
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1);
        }
    }

    private void initData() {
        context = getApplicationContext();
        recycleViewList = new ArrayList<>();
        recycleViewList.add(EntityCreator.createEntity(UsbManagerFactory.class,this));
        recycleViewList.add(EntityCreator.createEntity(WechatQQLimitFactory.class,this));
        recycleViewList.add(EntityCreator.createEntity(WechatQQAuditLogFactory.class,this));
        recycleViewList.add(EntityCreator.createEntity(WechatQQKeywordFilterFactory.class,this));
        recycleViewList.add(EntityCreator.createEntity(ADBFactory.class,this));
        recycleViewList.add(EntityCreator.createEntity(EnableOrDisableScreenCaptureFactory.class,this));
        recycleViewList.add(EntityCreator.createEntity(WifiEnableFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(MobileInfoFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(SIMInfoFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(TakePhotoFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(LockPolicyFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(ScreenCaptureFactory.class,this));
        recycleViewList.add(EntityCreator.createEntity(EnableOrDisableCameraFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(PwdConfigFactory.class,context));
//        recycleViewList.add(EntityCreator.createEntity(ResetFactorySettingsFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(EnableOrDisableAppFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(SafeModeFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(RecorderFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(UnKnownSourceFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(WifiAccessPointFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(SDCardFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(DeviceFacory.class,context));
        recycleViewList.add(EntityCreator.createEntity(GPSFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(LocationFactory.class,context));
        recycleViewList.add(EntityCreator.createEntity(AlarmFactory.class,context));

    }

    private void fndViewById() {
        recyclerView = findViewById(R.id.recycle_view);
    }

    public class ToolsBaseAdapter extends RecyclerView.Adapter<MyHolder> {
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {
            final RecycleViewImpl iRecycleViewEntity = recycleViewList.get(position);
            if (iRecycleViewEntity.getData() != null) {
                holder.textView.setVisibility(View.VISIBLE);
                if (RecycleViewImpl.STRING == iRecycleViewEntity.data_type)
                    holder.textView.setText((String) iRecycleViewEntity.getData());
                if (holder.checkBox.isChecked()) {
                    holder.checkBox.setText(iRecycleViewEntity.cbTextOff);
                } else {
                    holder.checkBox.setText(iRecycleViewEntity.cbTextOn);
                }
            } else {
                holder.textView.setVisibility(View.INVISIBLE);
                holder.textView.setText("");
            }

            if (iRecycleViewEntity.getDataChangeListener() != null) {
                iRecycleViewEntity.getDataChangeListener().dataChanged();
            }

            final CheckBox checkBox = holder.checkBox;
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mCheckStates.put(position, true);
                        iRecycleViewEntity.clickOn();
                        checkBox.setText(iRecycleViewEntity.cbTextOff);
                    } else {
                        mCheckStates.delete(position);
                        iRecycleViewEntity.clickOff();
                        checkBox.setText(iRecycleViewEntity.cbTextOn);

                    }
                    checkBox.setChecked(mCheckStates.get(position, false));
//                    if(isChecked){
//                    checkBox.setText(iRecycleViewEntity.getTextOn());
//                    checkBox.setText(iRecycleViewEntity.getTextOff());
                }
            });

        }

        @Override
        public int getItemCount() {
            return recycleViewList.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView textView;

        MyHolder(View itemView) {
            super(itemView);
            checkBox =  itemView.findViewById(R.id.check_box);
            textView =  itemView.findViewById(R.id.tv_content);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegistReceiver();
    }

    private class MyLocationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println((String) recycleViewList.get(0).getData());
            recyclerView.notifyAll();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 判断是否有WRITE_SETTINGS权限
                if (Settings.System.canWrite(this)) {
                    Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == REQUEST_CODE_ACTIVE_COMPONENT) {
            // 激活组件的响应
            if (resultCode == Activity.RESULT_CANCELED) {
                //如果点击取消激活则关闭页面
                finish();
            }
        }

//        if (requestCode == REQUEST_CODE_ASK_WRITE_SETTINGS ) {
//            if (!Settings.System.canWrite(getApplicationContext())) {
//                //如果还是没有权限，就弹框提醒用户
//                Toast.makeText(getApplicationContext(),"系统设置",Toast.LENGTH_SHORT).show();
//            }
//        }

        ScreenCaptureEntity.capOnActivityResult(this,requestCode,resultCode,data, ScreenUtils.getScreenWidth(this),ScreenUtils.getScreenHeight(this),(int)ScreenUtils.getScreenDensity(this));
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if(parent.getChildPosition(view) != 0)
                outRect.top = space;
        }
    }

    private void policyIntent(){
        DevicePolicyUtils devicePolicyUtils = new DevicePolicyUtils(this);
        if(!devicePolicyUtils.isAdminActive()){
            // 打开管理器的激活窗口
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            // 指定需要激活的组件
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,devicePolicyUtils.getComponentName());
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "(激活窗口中的描述信息)");
            startActivityForResult(intent, REQUEST_CODE_ACTIVE_COMPONENT);
        }
    }




}


//https://blog.csdn.net/qq_37293612/article/details/54915250