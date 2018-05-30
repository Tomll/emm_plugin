package com.example.administrator.emmapplication.utils.mobileInfos;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class MobileInfosUtils {

    private CpuInfos cpu;
    private MacInfos mac;
    private StorageInfos storage;
    private DeviceInfos device;
    private OtherInfos other;
    private SIMCardInfos sim;

    public MobileInfosUtils(Context context){
        cpu = new CpuInfos();
        other = new OtherInfos(context);
        mac = new MacInfos(context);
        storage = new StorageInfos(context);
        device = new DeviceInfos(context);
        sim=new SIMCardInfos(context);
    }

    public String getAllInfos2(){
        String deviceBrand="【厂商】"+device.getDeviceBrand()+"\r\n";
        String systemModel="【型号】"+device.getSystemModel()+"\r\n";
        String systemVersion="【版本】"+device.getSystemVersion()+"\r\n";
        String RAM="【RAM】"+storage.getRAM()+"\r\n";
        String ROM="【ROM】"+storage.getTotalRomSpace()+"/"+storage.getRemainRomSpace()+"\r\n";
        String blueToothMac="【蓝牙】"+mac.blueToothMac()+"\r\n";
        String wifiMac="【WIFI】"+mac.wifiMac()+"\r\n";
        String battery="【电量】"+device.battery()+"\r\n";
        String bootTime="【开机时间】"+other.bootTime()+"\r\n";
        String isRoot="【是否ROOT】"+other.isRoot()+"\r\n";
        String id="【序列号】"+DeviceInfos.getSerialNumber()+"\r\n";
        String cpuInfos="【CPU】"+cpu.cpuInfo()+" / "+cpu.getMaxCpuFreq()+" / "+cpu.getNumberOfCPUCores()+"\r\n";
        String camera="【相机】"+other.cameraPixels()+"\r\n";
        String sd="【SD卡】"+storage.getTotalSDSpace()+"/"+storage.getRemainSDSpace()+"/"+storage.getSDCid()+"\r\n";
        return id+deviceBrand+systemModel+systemVersion+RAM+ROM+blueToothMac+wifiMac+battery+bootTime+isRoot+cpuInfos+camera+sd;
    }


    public JSONObject getAllInfos(){
        String cpuInfos=cpu.cpuInfo()+"/"+cpu.getMaxCpuFreq()+"/"+cpu.getNumberOfCPUCores();
        String IMEI=sim.getMachineImei(0)+"/"+sim.getMachineImei(1);
        String IMSI=sim.getSubscriberId(0)+"/"+sim.getSubscriberId(1);
        JSONObject device_info = new JSONObject();
        try {
            device_info.put("device_plat","Google Android");
            device_info.put("device_type",other.device_type());
            device_info.put("serial_num",DeviceInfos.getSerialNumber());
            device_info.put("version_num",device.getSystemVersion());
            device_info.put("manufacturer",device.getDeviceBrand());
            device_info.put("model",device.getSystemModel());
            device_info.put("cpu",cpuInfos);
            device_info.put("ram",storage.getRAM());
            device_info.put("rom_capacity",storage.getTotalRomSpace());
            device_info.put("rom_available_capacity",storage.getRemainRomSpace());
            device_info.put("camera",other.cameraPixels());
            device_info.put("bluetooth_mac",mac.blueToothMac());
            device_info.put("wifi_mac",mac.wifiMac());
            device_info.put("sd_capacity",storage.getTotalSDSpace());
            device_info.put("sd_available_capacity",storage.getRemainSDSpace());
            device_info.put("sd_serial_num",storage.getSDCid());
            device_info.put("power_status",device.battery());
            device_info.put("root_flag",other.isRoot());
            device_info.put("imei",IMEI);
            device_info.put("imsi",IMSI);
            device_info.put("carrier_info",sim.simOperatorName());
            device_info.put("data_roaming",sim.netWorkRoaming());
            device_info.put("mobile",sim.lineNumber());;
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        return id+deviceBrand+systemModel+systemVersion+RAM+ROM+blueToothMac+wifiMac+battery+bootTime+isRoot+cpuInfos+camera+sd;
        return device_info;
    }
}
