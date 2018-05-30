package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.WechatQQKeywordFilterEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

public class WechatQQKeywordFilterFactory implements IEntityFactory{
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new WechatQQKeywordFilterEntity
                .Builder()
                .context(context)
                .build(WechatQQKeywordFilterEntity.class);
    }
}
