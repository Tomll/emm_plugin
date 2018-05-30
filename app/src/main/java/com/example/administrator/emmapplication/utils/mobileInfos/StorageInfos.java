package com.example.administrator.emmapplication.utils.mobileInfos;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.format.Formatter;
import android.util.Log;

import com.example.administrator.emmapplication.utils.LogUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

import static android.app.ActivityThread.TAG;

public class StorageInfos {
    private Context mContext;

    public StorageInfos(Context context){
        this.mContext=context;
    }

    /**
     * 获取手机RAM容量
     *
     * @return 手机RAM容量
     */
    public long getRAM(){
        try {
            final String mem_path = "/proc/meminfo";// 系统内存信息文件，第一行为内存大小
            Reader reader = new FileReader(mem_path);
            BufferedReader bufferedReader = new BufferedReader(reader, 8192);
            long totalRAMSize = Long.parseLong(bufferedReader.readLine().split("\\s+")[1]) * 1024L;//这里*1024是转换为单位B（字节）
//            return Formatter.formatFileSize(mContext.getApplicationContext(),totalRAMSize);
            return totalRAMSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 获取手机ROM总容量
     *
     * @return 手机ROM总容量
     */
    public long getTotalRomSpace() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockCount = stat.getBlockCountLong();
        long blockSize = stat.getBlockSizeLong();
//        return Formatter.formatFileSize(mContext.getApplicationContext(), blockCount*blockSize);
        return (blockCount*blockSize);
    }

    /**
     * 获取手机ROM可用容量
     *
     * @return 手机ROM可用容量
     */
    public long getRemainRomSpace() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
//        return Formatter.formatFileSize(mContext.getApplicationContext(), blockSize*availableBlocks);
        return (blockSize*availableBlocks);
    }

    /**
     * @param is_removale true 外置
     *                    false 内置
     *                    获取外置/内置内存卡的地址
     */
    private static String getStoragePath(Context mContext, boolean is_removale) {
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getTotalSDSpace() {
        if (getStoragePath(mContext, true) != null) {
            StatFs sf = new StatFs(getStoragePath(mContext, true));
            long  blockSize = sf.getBlockSizeLong();
            long  blockCount = sf.getBlockCountLong();
//            return Formatter.formatFileSize(mContext,blockSize*blockCount);
            return (blockSize*blockCount);
        }
        return 0;
    }

    public long getRemainSDSpace(){
        if (getStoragePath(mContext, true) != null) {
            StatFs sf = new StatFs(getStoragePath(mContext, true));
            long  blockSize = sf.getBlockSizeLong();
            long  availCount = sf.getAvailableBlocksLong();
//            return Formatter.formatFileSize(mContext,blockSize*availCount);
            return (blockSize*availCount);
        }
        return 0;
    }

    public String getSDCid(){
        StringBuilder stringB = new StringBuilder();
        if (getStoragePath(mContext, true) != null) {
            try {
                File input = new File("/sys/class/mmc_host/mmc1");
                String cid_directory = null;
                int i = 0;
                File[] sid = input.listFiles();

                for (i = 0; i < sid.length; i++) {
                    if (sid[i].toString().contains("mmc1:")) {
                        cid_directory = sid[i].toString();
                        String SID = (String) sid[i].toString().subSequence(
                                cid_directory.length() - 4,
                                cid_directory.length());
                        Log.e(TAG, " SID of MMC = " + SID);
                        stringB.append("==SID=="+SID);
                        break;
                    }
                }
                BufferedReader CID = new BufferedReader(new FileReader(
                        cid_directory + "/cid"));
                String sd_cid = CID.readLine();
                Log.e(TAG, "CID of the MMC = " + sd_cid);
                stringB.append("==sd_cid=="+sd_cid);

            } catch (Exception e) {
                Log.e(TAG, "Can not read SD-card cid");
            }

        } else {
            Log.e(TAG, "External Storage Not available!!");
        }

        return stringB.toString();
    }
}
