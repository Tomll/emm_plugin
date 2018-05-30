package com.example.administrator.emmapplication.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SDCardUtils {

    private static Method getPathsMethod;

    public static void mountSDCard(Context context,boolean enabled,String path){
        try {
            StorageManager storageService = (StorageManager) context.getSystemService(
                    Context.STORAGE_SERVICE);
            Method privateStringMethod;
            Class storageManagerClass = Class.forName("android.os.storage.StorageManager");
            if (enabled) {
//                privateStringMethod = storageManagerClass.getDeclaredMethod("mount", String.class);
                privateStringMethod = StorageManager.class.getDeclaredMethod("enableUsbMassStorage", null);
            } else {
//                privateStringMethod = storageManagerClass.getDeclaredMethod("unmount", String.class);
                privateStringMethod = StorageManager.class.getDeclaredMethod("disableUsbMassStorage", null);
            }
            privateStringMethod.setAccessible(true);
            privateStringMethod.invoke(storageService, path);

            int loopKillCount = 0;
            boolean loopCondition;
            if (enabled) {
                loopCondition = isSDPresent();
            } else {
                loopCondition = !isSDPresent();
            }
            while (loopCondition) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loopKillCount++;
                if (loopKillCount > 15) {
                    LogUtils.d("SD should have been dismounted by now");
                    break;
                }
            }
            if (enabled) {
                loopCondition = isSDPresent();
            } else {
                loopCondition = !isSDPresent();
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.d("occur exception is ......"+e.toString());
        }
    }

    private static boolean isSDPresent() {
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        LogUtils.d("sd card is present..........................");
        return (isSDPresent);
    }

    public static void setUSBMassstorage(Context context,boolean enabled){
        try {
            StorageManager storageService = (StorageManager) context.getSystemService(
                    Context.STORAGE_SERVICE);
            Method privateStringMethod;
            Class storageManagerClass = Class.forName("android.os.storage.StorageManager");
            if (enabled) {
                privateStringMethod = storageManagerClass.getDeclaredMethod("enableUsbMassStorage", null);
//                privateStringMethod = StorageManager.class.getDeclaredMethod("enableUsbMassStorage", null);
            } else {
                privateStringMethod = storageManagerClass.getDeclaredMethod("disableUsbMassStorage", null);
//                privateStringMethod = StorageManager.class.getDeclaredMethod("disableUsbMassStorage", null);
            }
            privateStringMethod.setAccessible(true);
            privateStringMethod.invoke(storageService, null);

            int loopKillCount = 0;
            boolean loopCondition;
            if (enabled) {
                loopCondition = isSDPresent();
            } else {
                loopCondition = !isSDPresent();
            }
            while (loopCondition) {
//            mSolo.sleep(500);
                Thread.sleep(500);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loopKillCount++;
                if (loopKillCount > 15) {
                    LogUtils.d("SD should have been dismounted by now");
                    break;
                }
                if (enabled) {
                    loopCondition = isSDPresent();
                } else {
                    loopCondition = !isSDPresent();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.d("occur exception is ......"+e.toString());
        }
    }

    /**
     * 获取ROM总大小
     *
     * @param context
     * @return
     */
    public static String getMaxRomSpace(Context context) {
        StatFs fs = new StatFs(getInnerSDCardPath(context));
        String romSpace;
        long blockCount = fs.getBlockCount();
        long bloackSize = fs.getBlockSize();
        long totalSpace = bloackSize * blockCount;
        romSpace = Formatter.formatFileSize(context, totalSpace);
        return romSpace;
    }

    /**
     * 获取SD卡总大小
     *
     * @param context
     * @return
     */
    public static String getMaxSDSpace(Context context) {
        String extPath = getExtSDCardPath(context);
        if (TextUtils.isEmpty(extPath)) {
            Toast.makeText(context, "无SD卡", Toast.LENGTH_SHORT).show();
            return null;
        }
        File path = new File(extPath);
        StatFs fs = new StatFs(path.getPath());
        String sdSpace;
        if (Build.VERSION.SDK_INT >= 18) {
            long blockCount = fs.getBlockCountLong();
            long bloackSize = fs.getBlockSizeLong();
            long totalSpace = bloackSize * blockCount;
            sdSpace = Formatter.formatFileSize(context, totalSpace);
        } else {
            long blockCount = fs.getBlockCount();
            long bloackSize = fs.getBlockSize();
            long totalSpace = bloackSize * blockCount;
            sdSpace = Formatter.formatFileSize(context, totalSpace);
        }
        return sdSpace;
    }


    /**
     * 获取rom可用大
     * 小
     *
     * @param context
     * @return
     */
    public static String getAvailableRomSpace(Context context) {
        File path = Environment.getDataDirectory();
        StatFs fs = new StatFs(path.getPath());
        String Space = null;
        long blockCount = fs.getAvailableBlocks();
        long bloackSize = fs.getBlockSize();
        long totalSpace = bloackSize * blockCount;
        Space = Formatter.formatFileSize(context, totalSpace);
        return Space;
    }


    /**
     * 获取SD卡可用大小
     *
     * @param context
     * @return
     */
    public static String getAvailableSdSpace(Context context) {
        String extPath = getExtSDCardPath(context);
        if (TextUtils.isEmpty(extPath)) {
            Toast.makeText(context, "无SD卡", Toast.LENGTH_SHORT).show();
            return null;
        }
        File path = new File(extPath);
        StatFs fs = new StatFs(path.getPath());
        String Space;

        long blockCount = fs.getAvailableBlocks();
        long bloackSize = fs.getBlockSize();
        long totalSpace = bloackSize * blockCount;
        Space = Formatter.formatFileSize(context, totalSpace);

        return Space;
    }

    /**
     * 获取外置SD卡路径
     *
     * @return 应该就一条记录或空
     */
    public static String getExtSDCardPath(Context context) {
        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            getPathsMethod = sm.getClass().getMethod("getVolumePaths", null);
            String[] path = (String[]) getPathsMethod.invoke(sm, null);
            if (path.length > 0)
                return "";
            return path[1];
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取内置内存路径
     *
     * @return 应该就一条记录或空
     */
    public static String getInnerSDCardPath(Context context) {
        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            getPathsMethod = sm.getClass().getMethod("getVolumePaths", null);
            String[] path = (String[]) getPathsMethod.invoke(sm, null);
            return path[0];
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* Checks if external storage is available for read and write */

    /**
     * 检查外部存储是否可读写
     *
     * @return
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    /* Checks if external storage is available to at least read */

    /**
     * 检测是否有外部存储设备
     *
     * @return
     */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    /**
     * 如果有sd卡，优先存储在sd卡，如果没有sd卡，则存储在内存中
     *
     * @param context 上下文对象
     * @param dir     存储目录
     * @return
     */
    public static String getFilePath(Context context, String dir) {
        String directoryPath = "";
        //判断SD卡是否可用
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            directoryPath = context.getExternalFilesDir(dir).getAbsolutePath();
        } else {
            //没内存卡就存机身内存
            directoryPath = context.getFilesDir() + File.separator + dir;
        }
        File file = new File(directoryPath);
        if (file.exists()) {//判断文件目录是否存在
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.d("filePath====>" + directoryPath);
        return directoryPath;
    }

    /**
     * 获取外部存储卡的非本地目录
     *
     * @param context 上下文对象
     * @param dir     存储目录
     * @return
     */
    public static String getOterSDCardStoragePath(Context context, String dir) {
        String directoryPath = "";
        //判断SD卡是否可用
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            directoryPath = Environment.getExternalStorageDirectory()+ File.separator + dir;
        }
        LogUtils.d("OterSDCardStoragePath====>" + directoryPath);
        File file = new File(directoryPath);
        if (file.exists()) {//判断文件目录是否存在
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directoryPath;
    }


    /**
     * 外置存储卡
     *
     * @param context 上下文对象
     * @param dir     存储目录
     * @return
     */
    public static String getOuterSDCardFilePath(Context context, String dir) {
        String directoryPath = "";
        //判断SD卡是否可用
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            directoryPath = context.getExternalFilesDir(dir).getAbsolutePath();
        }
        File file = new File(directoryPath);
        if (file.exists()) {//判断文件目录是否存在
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.d("OuterSDCardFilePath ====>" + directoryPath);
        return directoryPath;
    }


    public static String getOuterSDCardCacheFilePath(Context context, String dir) {
        String directoryPath = "";
        //判断SD卡是否可用
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            directoryPath = context.getExternalCacheDir().getAbsolutePath();
        }
        File file = new File(directoryPath);
        if (file.exists()) {//判断文件目录是否存在
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.d("SDCardFilePath====>" + directoryPath);
        return directoryPath;
    }


    /**
     * @param context 上下文对象
     * @param dir     存储目录
     * @return
     */
    public static String getInnerSDCardPath(Context context, String dir) {
        String directoryPath = context.getFilesDir() + File.separator + dir;
        File file = new File(directoryPath);
        if (file.exists()) {//判断文件目录是否存在
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.d("InnerSDCardPath====>" + directoryPath);
        return directoryPath;
    }
}
