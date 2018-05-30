package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;
import com.example.administrator.emmapplication.entity.SafeModeEntity;

/**
 * Created by admine on 2018/4/24.
 */

public class SafeModeFactory implements IEntityFactory{
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new SafeModeEntity
                .Builder()
                .context(context)
                .build(SafeModeEntity.class);
    }
}
