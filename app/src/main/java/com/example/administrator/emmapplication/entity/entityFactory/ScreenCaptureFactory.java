package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.ScreenCaptureEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

/**
 * Created by admine on 2018/5/11.
 */

public class ScreenCaptureFactory implements IEntityFactory{
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new ScreenCaptureEntity
                .Builder()
                .context(context)
                .build(ScreenCaptureEntity.class);
    }
}
