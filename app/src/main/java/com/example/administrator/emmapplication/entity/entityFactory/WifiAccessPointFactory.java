package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;
import com.example.administrator.emmapplication.entity.WifiAccessPointEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;

/**
 * Created by admine on 2018/4/18.
 */

public class WifiAccessPointFactory implements IEntityFactory {
    @Override
    public IRecycleViewEntity getEntity(Context context) {
        return new WifiAccessPointEntity
                .Builder()
                .context(context)
                .build(WifiAccessPointEntity.class);
    }
}
