package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.device.IDeviceFactory;
import com.example.administrator.emmapplication.entity.ResetFactorySettingsEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

/**
 * Created by admine on 2018/5/3.
 */

public class ResetFactorySettingsFactory implements IEntityFactory {

    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new ResetFactorySettingsEntity
                .Builder()
                .context(context)
                .build(ResetFactorySettingsEntity.class);
    }
}
