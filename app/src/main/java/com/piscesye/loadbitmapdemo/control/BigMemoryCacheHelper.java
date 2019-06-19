package com.piscesye.loadbitmapdemo.control;

import android.graphics.Bitmap;
import android.util.LruCache;

public class BigMemoryCacheHelper {
    private static LruCache<String, Bitmap> bitmapLruCache;
    public static final String NAME_TAG = "BigMemoryCache";

    static {
        bitmapLruCache = new LruCache<String, Bitmap>(50 * 1024) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public static void putBitmapToMemoryCache(String key, Bitmap value) {
        if (bitmapLruCache.get(NAME_TAG + key) == null) {
            bitmapLruCache.put(NAME_TAG + key, value);
        }
    }

    public static Bitmap getBitmapFromMemoryCache(String key) {
        return bitmapLruCache.get(NAME_TAG + key);
    }
}
