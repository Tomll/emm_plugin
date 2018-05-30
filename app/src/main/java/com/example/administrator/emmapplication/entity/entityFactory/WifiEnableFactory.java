package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.WifiEnableEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

public class WifiEnableFactory implements IEntityFactory {
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new WifiEnableEntity.Builder().context(context).build(WifiEnableEntity.class);
    }
}
