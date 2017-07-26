package com.demo.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

/**
 * Created by dong on 2017/7/24.
 */

public class NetCache implements Cache{

    private FileCache fileCache;
    private MemoryCache memoryCache;

    public NetCache(FileCache fileCache, MemoryCache memoryCache) {
        this.fileCache = fileCache;
        this.memoryCache = memoryCache;
    }

    public void getBitmap(ImageView imageView, String url) {
        new DownloadTask().executeOnExecutor(Executors.newFixedThreadPool(5), imageView, url);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return null;
    }

    class DownloadTask extends AsyncTask<Object,Void,Bitmap> {

        private ImageView imageView;
        private String url;

        @Override
        protected Bitmap doInBackground(Object... params) {
            url = (String) params[1];
            imageView = (ImageView) params[0];
            return downloadBitmap(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                fileCache.setBitmap(url, bitmap);
                memoryCache.setBitmap(url, bitmap);
            }
        }
    }

    private Bitmap downloadBitmap(String url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream(), null, options);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
                connection = null;
            }
        }
        return null;
    }
}
