package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.AlarmEntity;
import com.example.administrator.emmapplication.entity.UnKnownSourceEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

/**
 * Created by admine on 2018/4/18.
 */

public class UnKnownSourceFactory implements IEntityFactory {
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new UnKnownSourceEntity
                .Builder()
                .context(context)
                .build(UnKnownSourceEntity.class);
    }
}
