package com.example.administrator.emmapplication.entity;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;

import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.utils.PlayAudioUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlarmEntity extends RecycleViewImpl {

    private static ExecutorService singleExecutor = Executors.newSingleThreadExecutor();

    public AlarmEntity(Builder builder){
        super(builder);
    }

    @Override
    public Object getData() {
        return "播放或停止铃声";
    }

    @Override
    public void clickOn() {
        singleExecutor.execute(new Runnable() {
            @Override
            public void run() {
                PlayAudioUtils.getInstance().play(mContext,getSystemDefultRingtoneUri(mContext));
            }
        });
    }

    @Override
    public void clickOff() {
        singleExecutor.execute(new Runnable() {
            @Override
            public void run() {
                PlayAudioUtils.getInstance().stop(mContext,getSystemDefultRingtoneUri(mContext));
            }
        });
    }

    private  Uri getSystemDefultRingtoneUri(Context context) {
        return RingtoneManager.getActualDefaultRingtoneUri(context,
                RingtoneManager.TYPE_RINGTONE);
    }


}
