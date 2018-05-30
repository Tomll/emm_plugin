package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.TakePhotoEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

public class TakePhotoFactory implements IEntityFactory {
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new TakePhotoEntity.Builder().context(context).build(TakePhotoEntity.class);
    }
}
