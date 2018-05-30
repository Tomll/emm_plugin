package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.SDCardEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

/**
 * Created by admine on 2018/4/18.
 */

public class SDCardFactory implements IEntityFactory {

    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new SDCardEntity
                .Builder()
                .context(context)
                .build(SDCardEntity.class);
    }
}
