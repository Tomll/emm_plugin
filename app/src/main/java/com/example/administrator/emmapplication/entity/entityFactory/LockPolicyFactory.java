package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.LockEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

public class LockPolicyFactory implements IEntityFactory {
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new LockEntity.Builder().context(context).build(LockEntity.class);
    }
}
