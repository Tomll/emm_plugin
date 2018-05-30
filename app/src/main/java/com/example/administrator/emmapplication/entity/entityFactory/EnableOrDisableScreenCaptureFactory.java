package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;
import com.example.administrator.emmapplication.entity.EnableOrDisableScreenCaptureEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

/**
 * Created by admine on 2018/5/17.
 */

public class EnableOrDisableScreenCaptureFactory implements IEntityFactory {
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new EnableOrDisableScreenCaptureEntity
                .Builder()
                .context(context)
                .build(EnableOrDisableScreenCaptureEntity.class);
    }
}
