package com.example.administrator.emmapplication.entity;

import android.widget.Toast;

import com.example.administrator.emmapplication.utils.mobileInfos.SIMCardInfos;

public class SIMInfoEntity extends RecorderEntity{
    public SIMInfoEntity(Builder builder) {
        super(builder);
    }

    @Override
    public Object getData() {
        return "手机SIM卡信息";
    }

    @Override
    public void clickOn() {
        super.clickOn();
        SIMCardInfos tel = new SIMCardInfos(mContext);
        String IMEI="【IMEI】"+tel.getMachineImei(0)+"/"+tel.getMachineImei(1)+"\r\n";
        String IMSI="【IMSI】"+tel.getSubscriberId(0)+"/"+tel.getSubscriberId(1)+"\r\n";
        String SIMNAME="【运营商】"+tel.simOperatorName()+"\r\n";
        String LINENUMBER="【手机号】"+tel.lineNumber()+"\r\n";
        String NETWORK="【是否漫游】"+tel.netWorkRoaming()+"\r\n";
        Toast.makeText(mContext,IMEI+IMSI+SIMNAME+LINENUMBER+NETWORK,Toast.LENGTH_LONG).show();
    }

    @Override
    public void clickOff() {
        super.clickOff();
    }
}
