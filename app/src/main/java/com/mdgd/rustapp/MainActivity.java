package com.mdgd.rustapp;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("rust");
    }

    private native void renderFractal(Bitmap bitmap);

    private FractalView image;
    private final Runnable r = new Runnable() {
        @Override
        public void run() {
            image.updateAngle();
            image.invalidate();
            image.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = findViewById(R.id.imageView);
        // final ImageView image = findViewById(R.id.imageView);
        // final Bitmap bitmap = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888);
        // renderFractal(bitmap);
        // imageView.setImageBitmap(bitmap);
        image.postDelayed(r, 500);
    }
}