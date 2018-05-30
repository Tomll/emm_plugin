package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.UsbManagerEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

public class UsbManagerFactory implements IEntityFactory{
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new UsbManagerEntity
                .Builder()
                .context(context)
                .build(UsbManagerEntity.class);
    }
}
