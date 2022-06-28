package com.mdgd.rustapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("rust");
    }

    private native void renderFractal(Bitmap bitmap, int angle);

    private boolean isRunning = false;

    private int angle = 180;
    private Bitmap bitmap;
    // private FractalView image;
    private ImageView image;
    private final Runnable r = new Runnable() {
        @Override
        public void run() {
            final long start = System.currentTimeMillis();
            renderFractal(bitmap, angle);
            image.setImageBitmap(bitmap);
            Log.d("LOGG", "Time " + (System.currentTimeMillis() - start));
            angle++;
            angle %= 360;
            // image.updateAngle();
            // image.invalidate();
            if (isRunning) {
                image.postDelayed(this, 100);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.imageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isRunning = true;
        image.post(() -> {
            bitmap = Bitmap.createBitmap(image.getMeasuredWidth(), image.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            r.run();
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }
}
