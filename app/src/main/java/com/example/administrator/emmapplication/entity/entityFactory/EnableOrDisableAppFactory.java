package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.EnableOrDisableAppEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

/**
 * Created by admine on 2018/4/25.
 */

public class EnableOrDisableAppFactory implements IEntityFactory {

    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new EnableOrDisableAppEntity
                .Builder()
                .context(context)
                .build(EnableOrDisableAppEntity.class);
    }
}
