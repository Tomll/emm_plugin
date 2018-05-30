package com.example.administrator.emmapplication.execute;

import com.example.administrator.emmapplication.entity.LimitStrategyEntity;

import org.json.JSONException;
import org.json.JSONObject;

public class RestrictionStrategy {
    private JSONObject newRestriction ;
    private LimitStrategyEntity.RestrictionBean oldRestriction ;

    public static RestrictionStrategy getInstance(LimitStrategyEntity.RestrictionBean oldRestriction, JSONObject newRestriction){
        return new RestrictionStrategy(oldRestriction,newRestriction);
    }
    private RestrictionStrategy(LimitStrategyEntity.RestrictionBean oldRestriction, JSONObject newRestriction){
        this.newRestriction=newRestriction;
        this.oldRestriction=oldRestriction;
    }

    public void executeLimitStrategy(){
        try {
            //(新策略值,旧策略值)；只有新的策略值有改动才会执行新策略
            allowCamera(newRestriction.getInt("allowCamera"),oldRestriction.getAllowCamera());
            allowSdCard(newRestriction.getInt("allowSdCard"),oldRestriction.getAllowSdCard());
            allowWifi(newRestriction.getInt("allowWifi"),oldRestriction.getAllowWifi());
            allowBluetooth(newRestriction.getInt("allowBluetooth"),oldRestriction.getAllowBluetooth());
            allowCellular(newRestriction.getInt("allowCellular"),oldRestriction.getAllowCellular());
            allowMobileHotspot(newRestriction.getInt("allowMobileHotspot"),oldRestriction.getAllowMobileHotspot());
            allow_gps(newRestriction.getInt("allow_gps"),oldRestriction.getAllow_gps());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param val1 新策略值
     * @param val2 旧策略值
     */
    private void allowCamera(int val1,int val2){
        if(val1!=val2){}
    }
    private void allowSdCard(int val1,int val2){}
    private void allowWifi(int val1,int val2){}
    private void allowBluetooth(int val1,int val2){}
    private void allowCellular(int val1,int val2){}
    private void allowMobileHotspot(int val1,int val2){}
    private void allow_gps(int val1,int val2){}
}
