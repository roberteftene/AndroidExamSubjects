package com.app.deplasariangajati;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    Intent intent;
    ImageView imageView;
    String imgUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        intent = getIntent();
        imgUrl = intent.getStringExtra("imgUrl");

        imageView = findViewById(R.id.angajatImg);
        DownloadContent.handler = new Handler()
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle data = msg.getData();
                Bitmap image = data.getParcelable("image");
                imageView.setImageBitmap(image);
            }
        };

        DownloadContent downloadContent = new DownloadContent(imgUrl);
        Thread downloadThread = new Thread(downloadContent);
        downloadThread.start();
    }
}