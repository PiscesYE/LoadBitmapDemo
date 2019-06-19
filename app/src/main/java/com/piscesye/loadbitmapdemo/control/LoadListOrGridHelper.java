package com.piscesye.loadbitmapdemo.control;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.piscesye.loadbitmapdemo.global.MyApplication;

public class LoadListOrGridHelper {
    public static final String TAG = "文件信息存储测试：";
    public static final String SF_NAME = "com.piscesye.loadbitmapdemo.jarvis_laod";
    private static SharedPreferences loadSF = MyApplication
            .getGlobalContext().getSharedPreferences(SF_NAME, Context.MODE_PRIVATE);

    public static int loadListOrGrid(String key) {
        int result = loadSF.getInt(key, 4);
        //Log.d(TAG, "状态为："+result);
        return result;
    }

    public static void setListOrGrid(String key, int value) {
        if (value == loadListOrGrid(key)) {
            //Log.d(TAG, "结果一致！");
            return;
        } else {
            SharedPreferences.Editor editor = loadSF.edit();
            editor.putInt(key, value);
            editor.commit();
            //Log.d(TAG, "保存成功！");
        }
    }
}
