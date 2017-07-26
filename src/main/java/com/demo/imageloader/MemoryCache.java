package com.demo.imageloader;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by dong on 2017/7/24.
 */

public class MemoryCache implements Cache{

    private static final String TAG = "MemoryCache";

    private LruCache<String,Bitmap> memoryCache;

    public MemoryCache() {
        long currentMemory = Runtime.getRuntime().maxMemory();
        Log.d(TAG, "MemoryCache: " + currentMemory);
        long cacheSize = currentMemory / 8;
        memoryCache = new LruCache<String,Bitmap>((int) cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return memoryCache.get(url);
    }

    public void setBitmap(String url, Bitmap bitmap) {
        memoryCache.put(url, bitmap);
    }
}
