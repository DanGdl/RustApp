package com.mdgd.rustapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class FractalView extends View {

    private final Paint p = new Paint();
    private final ComplexNumber complex = new ComplexNumber(-0.4, 0.6);
    private final ComplexNumber c = new ComplexNumber();

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int w = getWidth();
        final int h = getHeight();

        final float sX = 3F / w;
        final float sY = 4F / h;

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int r = (int) (x * 255F / w);
                int b = (int) (y * 255F / h);

                c.set(x * sX - 1.5, y * sY - 2F);
                int i = 0;
                while (i < 255 && c.norm() <= 2.0) {
                    c.multiply(c);
                    c.add(complex);
                    i += 1;
                }

                p.setColor(Color.rgb(r, i, b));
                canvas.drawPoint(x, y, p);
            }
        }
    }
}
