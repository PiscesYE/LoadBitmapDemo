package com.piscesye.loadbitmapdemo.control;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

import com.piscesye.loadbitmapdemo.global.MyApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 可从网络、资源、内存中加载图片
 * 服务于Grid和List
 */
public class loadBitmapHelper {

    public static final String imageSubDirNmae = "PiscesYE";
    private static final File PiscesYEFile;
    public static int imageSubDirListLength = 0;
    static {
        File pitcures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        PiscesYEFile = new File(pitcures.getPath(), imageSubDirNmae);
    }

    public static int getImagesNumber() {
        return PiscesYEFile.listFiles() == null ? 0 : PiscesYEFile.listFiles().length;
    }

    public static int getImageSubDirListLength() {
        if (imageSubDirListLength == 0) imageSubDirListLength = getImagesNumber();
        return imageSubDirListLength;
    }

    public static File getPiscesYEFile(){
        return PiscesYEFile;
    }
}
