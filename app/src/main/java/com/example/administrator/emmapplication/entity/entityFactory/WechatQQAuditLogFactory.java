package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.WechatQQAuditLogEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

public class WechatQQAuditLogFactory implements IEntityFactory {
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new WechatQQAuditLogEntity
                .Builder()
                .context(context)
                .build(WechatQQAuditLogEntity.class);
    }
}
