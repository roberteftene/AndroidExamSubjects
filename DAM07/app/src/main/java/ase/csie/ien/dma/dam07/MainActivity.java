package ase.csie.ien.dma.dam07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);

        DownloadContent.handler = new Handler()
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d(TAG, "----------image received from thread------------");
                Bundle data = msg.getData();
                Bitmap image = data.getParcelable("image");
                imageView.setImageBitmap(image);
            }
        };
    }

    public void downloadImage(View view)
    {
        Log.d(TAG, "----------downloadImage method------------");
        DownloadContent imageTask = new DownloadContent("http://pdm.ase.ro/images/tehnologii.png");
        Thread downloadThread = new Thread(imageTask);
        downloadThread.start();
        Log.d(TAG, "----------download thread started------------");
    }
}