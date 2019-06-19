package com.piscesye.loadbitmapdemo.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

import com.piscesye.loadbitmapdemo.control.BigMemoryCacheHelper;
import com.piscesye.loadbitmapdemo.control.MemoryCacheHepler;
import com.piscesye.loadbitmapdemo.control.loadBitmapHelper;

import java.io.File;

import static com.piscesye.loadbitmapdemo.control.loadBitmapHelper.imageSubDirNmae;

public class LoadBitmapFromStorageTask extends AsyncTask<Integer, Void, Bitmap> {
    private ImageView showImage;
    private int inSampleSize = 2;

    public LoadBitmapFromStorageTask(ImageView imageView) {
        showImage = imageView;
    }

    public LoadBitmapFromStorageTask(ImageView imageView, int sampleSize) {
        inSampleSize = sampleSize;
        showImage = imageView;
    }

    @Override
    protected Bitmap doInBackground(Integer... integers) {
        if (integers[0] < loadBitmapHelper.getImageSubDirListLength()) {
            File pitcures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageSubDir = new File(pitcures.getPath(), imageSubDirNmae);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = inSampleSize;
            Bitmap returnBitmap = BitmapFactory.decodeFile(imageSubDir.listFiles()[integers[0]].getPath(), options);
            if (inSampleSize == 1){
                BigMemoryCacheHelper.putBitmapToMemoryCache(String.valueOf(integers[0]), returnBitmap);
            }else{
                MemoryCacheHepler.putBitmapToMemoryCache(String.valueOf(integers[0]), returnBitmap);
            }
            return returnBitmap;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        showImage.setImageBitmap(bitmap);
    }
}
