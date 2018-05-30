package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.ADBEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

/**
 * Created by admine on 2018/5/17.
 */

public class ADBFactory implements IEntityFactory{
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new ADBEntity
                .Builder()
                .context(context)
                .build(ADBEntity.class);
    }
}
