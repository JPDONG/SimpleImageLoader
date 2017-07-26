package com.demo.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by dong on 2017/7/24.
 */

public interface Cache {
    Bitmap getBitmap(String url);
}
