package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.AlarmEntity;
import com.example.administrator.emmapplication.entity.DeviceInfoEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

/**
 * Created by admine on 2018/4/18.
 */

public class DeviceFacory implements IEntityFactory {
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new DeviceInfoEntity
                .Builder()
                .context(context)
                .build(DeviceInfoEntity.class);
    }
}
