package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.MobileInfoEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

public class MobileInfoFactory implements IEntityFactory{
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new MobileInfoEntity.Builder().context(context).build(MobileInfoEntity.class);
    }
}
