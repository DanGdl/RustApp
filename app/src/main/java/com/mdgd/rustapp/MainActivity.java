package com.mdgd.rustapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("rust");
    }

    private native void renderFractal(Bitmap bitmap);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = findViewById(R.id.imageView);
        final Bitmap bitmap = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888);
        renderFractal(bitmap);
        imageView.setImageBitmap(bitmap);
    }
}