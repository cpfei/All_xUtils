package com.example.all_xutils;

import android.content.Context;
import android.os.Environment;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by admin on 2015/4/15.
 */
public class BitmapHelper {
    private static BitmapUtils utils;

    public static BitmapUtils getUtils() {
        return utils;
    }

    public static void init(Context context){
        //参数：1    2.磁盘路径  3.占内存总数的百分比，4.磁盘缓存的大小
        utils=new BitmapUtils(context, Environment.getDownloadCacheDirectory()+"/greenhotle"
        ,1/8.0f,10*1024*1024);
    }



}
