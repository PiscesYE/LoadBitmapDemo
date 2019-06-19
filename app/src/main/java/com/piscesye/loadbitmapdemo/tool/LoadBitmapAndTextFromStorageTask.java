package com.piscesye.loadbitmapdemo.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import com.piscesye.loadbitmapdemo.control.MemoryCacheHepler;
import com.piscesye.loadbitmapdemo.control.loadBitmapHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.piscesye.loadbitmapdemo.control.loadBitmapHelper.imageSubDirNmae;

public class LoadBitmapAndTextFromStorageTask extends AsyncTask<Integer, Void, Bitmap> {
    private ImageView showImage;
    private TextView name, message;
    private String nameString, messageString;
    private int inSampleSize = 2;

    public LoadBitmapAndTextFromStorageTask(ImageView imageView, TextView name, TextView message) {
        this.name = name;
        this.message = message;
        showImage = imageView;
    }

    @Override
    protected Bitmap doInBackground(Integer... integers) {
        if (integers[0] < loadBitmapHelper.getImageSubDirListLength()) {
            File imageSubDir = loadBitmapHelper.getPiscesYEFile();
            File thatFile = imageSubDir.listFiles()[integers[0]];
            nameString = thatFile.getName();
            messageString = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(thatFile.lastModified()));
            long fileSize = thatFile.length() / 1024;
            if (fileSize > 1024) {
                messageString += " " + String.valueOf(fileSize / 1024) + "MB";
            } else {
                messageString += " " + String.valueOf(fileSize) + "KB";
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = inSampleSize;
            Bitmap returnBitmap = BitmapFactory.decodeFile(thatFile.getPath(), options);
            MemoryCacheHepler.putBitmapToMemoryCache(String.valueOf(integers[0]), returnBitmap);
            return returnBitmap;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        name.setText(nameString);
        message.setText(messageString);
        showImage.setImageBitmap(bitmap);
    }
}
