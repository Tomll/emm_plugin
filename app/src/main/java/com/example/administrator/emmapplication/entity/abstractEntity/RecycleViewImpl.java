package com.example.administrator.emmapplication.entity.abstractEntity;

import android.content.Context;

import com.example.administrator.emmapplication.entity.AlarmEntity;
import com.example.administrator.emmapplication.entity.interfaceEntity.IRecycleViewEntity;
import com.example.administrator.emmapplication.utils.LogUtils;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/4/9.
 * 子类必须实现父类带参的构造函数，否则会报错
 */

public abstract class RecycleViewImpl implements IRecycleViewEntity, Serializable {

    public Context mContext;

    public static final int DEFAULT = 0;
    public static final int STRING = 1;

    public int data_type = STRING;
    public String mTextOn;
    public String mTextOff;
    public String cbTextOn;
    public String cbTextOff;

    private DataChangeListener dataChangeListener;

    public RecycleViewImpl(Builder builder) {
        this.mContext = builder.context;
        this.mTextOn = builder.textOn;
        this.mTextOff = builder.textOff;
        this.cbTextOn = builder.cbTextOn;
        this.cbTextOff = builder.cbTextOff;
    }

    public interface DataChangeListener {
        String dataChanged();
    }

    public void setDataChangedListener(DataChangeListener dataChangedListener) {
        this.dataChangeListener = dataChangedListener;
    }

    public DataChangeListener getDataChangeListener() {
        return dataChangeListener;
    }

    @Override
    public abstract Object getData();

    @Override
    public abstract void clickOn();

    @Override
    public abstract void clickOff();

    public void init(){

    }


    public static class Builder {
        private Context context;
        private String textOn;
        private String textOff;
        private String cbTextOn;
        private String cbTextOff;
        private int dataType;

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder textOn(String textOn) {
            this.textOn = textOn;
            return this;
        }

        public Builder textOff(String textOff) {
            this.textOff = textOff;
            return this;
        }

        public Builder cbTextOn(String cbTextOn) {
            this.cbTextOn = cbTextOn;
            return this;
        }

        public Builder cbTextOff(String cbTextOff) {
            this.cbTextOff = cbTextOff;
            return this;
        }

        public Builder dataType(int dataType) {
            this.dataType = dataType;
            return this;
        }

        public RecycleViewImpl build(Class cls) {
            try {
                Constructor<?> constructor = cls.getConstructor(Builder.class);
                RecycleViewImpl entity = (RecycleViewImpl) constructor.newInstance(this);
                //调用对象的init（）方法。父类是一个空实现，如果子类不复写的话就相当于不执行任何内容
                //如果子类复写了该方法，则会执行子类中的方法
                Method initFunc = cls.getMethod("init");
                initFunc.invoke(entity);
                return entity;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
