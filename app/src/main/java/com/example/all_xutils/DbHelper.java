package com.example.all_xutils;

import android.content.Context;

import com.lidroid.xutils.DbUtils;

/**
 * Created by admin on 2015/4/15.
 */
public class DbHelper {
    private static DbUtils utils;
    public static DbUtils getUtils() {
        return utils;
    }
    public static void init(Context context){
        utils=DbUtils.create(context,"green");//指定数据库名字
        utils.configDebug(true);//控制台可以显示打印,以后关掉
        utils.configAllowTransaction(true);
    }
}
