package com.demo.imageloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.iv_image);
        ImageLoader imageLoader = new ImageLoader(this);
        imageLoader.displayImage(imageView,"http://img06.tooopen.com/images/20160921/tooopen_sy_179583447187.jpg");
    }
}
