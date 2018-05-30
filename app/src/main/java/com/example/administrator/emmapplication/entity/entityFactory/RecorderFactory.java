package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.RecorderEntity;
import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

/**
 * Created by admine on 2018/4/19.
 */

public class RecorderFactory implements IEntityFactory{

    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new RecorderEntity
                .Builder()
                .context(context)
                .build(RecorderEntity.class);
    }
}
