package com.nofre.android_useful_widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class GridColorPicker extends HorizontalColorPicker {

    private int rows;
    private int columns;

    public GridColorPicker(Context context) {
        super(context);
        init(context, null);
    }

    public GridColorPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GridColorPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ColorPicker, 0, 0);
        try {
            rows = a.getInt(R.styleable.ColorPicker_rows, 4);
            columns = a.getInt(R.styleable.ColorPicker_columns, 4);
        }
        finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (((radius+borderWidth)*2+margin)*columns + margin), (int) (radius+borderWidth+margin)*2*rows);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                int colorIndex = i*columns + j;
                if (colorIndex < colors.size()) {
                    //Border
                    paint.setColor(colorIndex == selected ? borderColorSelected : borderColorUnselected);
                    canvas.drawCircle(
                            margin + (((radius+borderWidth)*2)+margin)*j + radius + borderWidth,
                            margin + (((radius+borderWidth)*2)+margin)*i + radius + borderWidth,
                            radius + borderWidth,
                            paint);

                    paint.setColor(colors.get(colorIndex));
                    canvas.drawCircle(
                            margin + (((radius+borderWidth)*2)+margin)*j + radius + borderWidth,
                            margin + (((radius+borderWidth)*2)+margin)*i + radius + borderWidth,
                            radius,
                            paint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                int colorIndex = i * columns + j;
                if (colorIndex < colors.size()) {
                    float minX = margin + ((radius + borderWidth)*2 + margin)*j;
                    float maxX = margin + ((radius + borderWidth)*2 + margin)*(j+1);
                    float minY = (radius + borderWidth + margin)*2*i;
                    float maxY = (radius + borderWidth + margin)*2*(i+1);

                    if (x > minX && x < maxX && y > minY && y < maxY) {
                        selected = colorIndex;
                        invalidate();

                        if (listener != null) listener.onColorSelected(colors.get(colorIndex));

                        return true;
                    }

                }
            }
        }

        return false;
    }
}
