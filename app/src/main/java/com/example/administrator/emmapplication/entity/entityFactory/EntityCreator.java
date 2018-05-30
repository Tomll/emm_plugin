package com.example.administrator.emmapplication.entity.entityFactory;

import android.content.Context;
import com.example.administrator.emmapplication.entity.abstractEntity.RecycleViewImpl;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by admine on 2018/4/19.
 */

public class EntityCreator  {
    public static <T extends RecycleViewImpl> T createEntity(Class cls,Context context){
        try {
            //获取factory的构造函数
            Constructor<?> constructor = cls.getConstructor();
            //创建factory的实例
            IEntityFactory iEntityFactory = (IEntityFactory) constructor.newInstance();
            //获取factory中创建对象的方法
            Method getEntityFunc = cls.getMethod("getEntity",Context.class);
            //返回所需要的对象
            return (T) getEntityFunc.invoke(iEntityFactory,context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
