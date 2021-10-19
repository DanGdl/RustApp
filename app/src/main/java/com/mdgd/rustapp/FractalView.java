package com.mdgd.rustapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class FractalView extends View {

    private static final int MAX_ITERATIONS = 50;
    private static final double RADIUS = 0.7885;

    private final Paint p = new Paint();
    private int angle = 0; // 0 - 360

    public FractalView(Context context) {
        this(context, null);
    }

    public FractalView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FractalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FractalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        p.setStyle(Paint.Style.FILL);
    }

    public void updateAngle() {
        angle++;
        angle %= 360;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int w = getWidth();
        final int h = getHeight();
        final double midX = w / 2.0;
        final double midY = h / 2.0;

        double a = Math.toRadians(angle);
        final double CX = RADIUS * Math.cos(a);
        final double CY = RADIUS * Math.sin(a);

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                final int r = (int) (x * 255F / w);
                final int b = (int) (y * 255F / h);

                double zx = (2.0 * x - w) / midX;
                double zy = (2.0 * y - h) / midY;
                int i = 0;
                while (i < MAX_ITERATIONS && (zx * zx + zy * zy) <= 4.0) {
                    double tmpZx = zx * zx - zy * zy + CX;
                    zy = 2 * zx * zy + CY;
                    zx = tmpZx;
                    i++;
                }
                final int g = 255 * i / MAX_ITERATIONS;
                p.setColor(Color.rgb(r, g, b));
                canvas.drawPoint(x, y, p);
            }
        }
    }
}
