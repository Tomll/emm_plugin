package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.EnableOrDisableCameraEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

public class EnableOrDisableCameraFactory implements IEntityFactory {
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new EnableOrDisableCameraEntity
                .Builder()
                .context(context)
                .build(EnableOrDisableCameraEntity.class);
    }
}
