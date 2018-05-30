package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.WechatQQLimitEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

public class WechatQQLimitFactory implements IEntityFactory {
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new WechatQQLimitEntity
                .Builder()
                .context(context)
                .build(WechatQQLimitEntity.class);
    }
}
