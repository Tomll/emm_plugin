package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;

import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

/**
 * Created by admine on 2018/4/18.
 */

public interface IEntityFactory {
    IRecycleViewEntity getEntity(Context context);
}
