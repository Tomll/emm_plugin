package com.example.administrator.emmapplication.execute;

import android.app.Activity;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Base64;

import com.example.administrator.emmapplication.activity.ActivationActivity;
import com.example.administrator.emmapplication.activity.MenuActivity;
import com.example.administrator.emmapplication.utils.Camera2TakePhotoUtils;
import com.example.administrator.emmapplication.utils.LocationUtils;
import com.example.administrator.emmapplication.utils.LockPolicyUtils;
import com.example.administrator.emmapplication.utils.MemoryUtils;
import com.example.administrator.emmapplication.utils.PlayAudioUtils;
import com.example.administrator.emmapplication.utils.ResetFactorySettingsUtils;
import com.example.administrator.emmapplication.utils.ScreenCaptureUtils;
import com.example.administrator.emmapplication.utils.ScreenUtils;
import com.example.administrator.emmapplication.utils.enums.CMD;
import com.example.administrator.emmapplication.utils.retrofits.CmdStateBackBaseRetrofitUtils;
import com.example.administrator.emmapplication.utils.retrofits.LocationBaseRetrofitUtils;
import com.example.administrator.emmapplication.utils.retrofits.UploadPictureRetrofitUtils;

import static android.security.KeyStore.getApplicationContext;
import static com.example.administrator.emmapplication.utils.enums.CMD.Elmnt_Dvc;
import static com.example.administrator.emmapplication.utils.enums.CMD.Ers_Dvc;
import static com.example.administrator.emmapplication.utils.enums.CMD.PHOTO;
import static com.example.administrator.emmapplication.utils.enums.CMD.SCREEN;

import org.json.JSONException;
import org.json.JSONObject;

public class Command extends ExecuteOption {
    public Command(JSONObject data) {
        super(data);
    }

    @Override
    public void action() {
        switch (CMD.getById(action)){
            case RING:
                startRing();
                break;
            case LOC://设备定位
                location();
                break;
            case LOCK://设备锁定
                LockPolicyUtils.getInstance().lock(getApplicationContext());
                break;
            case Ers_Dvc://设备擦出
                wipeDevice(Ers_Dvc.getIndex());
                break;
            case Elmnt_Dvc://设备淘汰
                wipeDevice(Elmnt_Dvc.getIndex());
                break;
            case MISS://标记丢失
                markLost();
                break;
            case BACK://标记找回
                //tagBack();
                break;
            case SCREEN://截屏
                screenShoot();
                break;
            case PHOTO://拍照
                takePhoto();
                break;
        }
    }

    private void location() {
        Context mContext=getApplicationContext();
        final LocationUtils locationUtils = LocationUtils.getInstance(mContext);
        locationUtils.initLocation(mContext);
        //开启定位
        locationUtils.startLocation();
        locationUtils.setLocationResltListener(new LocationUtils.LocationResultListener() {
            @Override
            public void locationSuccess(String locationInfo) {
                System.out.println("定位信息"+locationInfo);
                //new LocationBaseRetrofitUtils().loc(locationInfo);
            }
            @Override
            public void locationFailed(String failedInfo) {
            }
        });
        //5分钟后自动关闭定位
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                locationUtils.stopLocation();
            }
        },30000);
    }

    //标记丢失
    private void markLost() {
        try {
            String pwd=data.getString("password");
            LockPolicyUtils.getInstance().lock(getApplicationContext(),pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void markBack(){}

    private void wipeDevice(int stateType) {
        try {
            int erase_type=data.getInt("erase_data_type");
            if(1==erase_type){
                //TODO execute default CMD or STRTGY
            }else if(2==erase_type){
                ResetFactorySettingsUtils.resetFactorySettings(getApplicationContext());
            }
            new CmdStateBackBaseRetrofitUtils().stateBack(stateType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void screenShoot() {
        Activity activity = (MenuActivity) MemoryUtils.getContext();
        ScreenCaptureUtils.startScreenCap(activity, ScreenUtils.getScreenWidth(activity),ScreenUtils.getScreenHeight(activity));
        new UploadPictureRetrofitUtils().upload(SCREEN.getIndex(),"");
    }

    private void takePhoto() {
        Activity context = (Activity)MemoryUtils.getContext();
        Camera2TakePhotoUtils camera2 = new Camera2TakePhotoUtils(context);
        camera2.takePicture();
        camera2.setLocationResltListener(new Camera2TakePhotoUtils.PhotoResultListener() {
            @Override
            public void takePhotoSuccess(byte[] photo) {
                String picture=Base64.encodeToString(photo, Base64.DEFAULT);
                System.out.println("BYTE："+ picture);
                new UploadPictureRetrofitUtils().upload(PHOTO.getIndex(),picture);
            }
        });
    }

    private void startRing(){
        PlayAudioUtils.getInstance().play(getApplicationContext(),getSystemDefultRingtoneUri(getApplicationContext()));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                PlayAudioUtils.getInstance().stop(getApplicationContext(),getSystemDefultRingtoneUri(getApplicationContext()));
            }
        },10000);
    }
    private Uri getSystemDefultRingtoneUri(Context context) {
        return RingtoneManager.getActualDefaultRingtoneUri(context,
                RingtoneManager.TYPE_RINGTONE);
    }
}
