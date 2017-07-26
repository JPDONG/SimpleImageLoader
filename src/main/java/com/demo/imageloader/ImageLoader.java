package com.demo.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by dong on 2017/7/24.
 */

public class ImageLoader {

    private static final String TAG = "ImageLoader";

    private NetCache mNetCache;
    private FileCache mFileCache;
    private MemoryCache mMemoryCache;
    private Context mContext;

    public ImageLoader(Context mContext) {
        this.mContext = mContext;
        mFileCache = new FileCache(mContext);
        mMemoryCache = new MemoryCache();
        mNetCache = new NetCache(mFileCache, mMemoryCache);
    }

    public void displayImage(ImageView imageView, String url) {
        Bitmap memoryBitmap = mMemoryCache.getBitmap(url);
        if (memoryBitmap != null) {
            imageView.setImageBitmap(memoryBitmap);
            Log.d(TAG, "displayImage: memory");
            return;
        }
        Bitmap fileBitmap = mFileCache.getBitmap(url);
        if (fileBitmap != null) {
            imageView.setImageBitmap(fileBitmap);
            mMemoryCache.setBitmap(url, fileBitmap);
            Log.d(TAG, "displayImage: file");
            return;
        }
        mNetCache.getBitmap(imageView, url);
        Log.d(TAG, "displayImage: net");
    }
}
