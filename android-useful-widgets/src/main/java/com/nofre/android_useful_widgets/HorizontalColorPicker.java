package com.nofre.android_useful_widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class HorizontalColorPicker extends View {

    private static final int[] DEFAULT_COLORS = {
            0xFFFFFFFF, // White
            0xFF888888, // Gray
            0xFF000000, // Black
            0xFFFFFF00, // Yellow
            0xFFFFA500, // Orange
            0xFFFF0000, // Red
            0xFF00FF00, // Green
            0xFF0000FF, // Blue
            0xFFE855E8, // Violet
    };

    protected Paint paint;

    protected float radius;
    protected float borderWidth;
    protected int borderColorUnselected;
    protected int borderColorSelected;
    protected float margin;

    protected ArrayList<Integer> colors = new ArrayList<>();
    protected int selected = 0;
    protected ColorPickerListener listener;

    public HorizontalColorPicker(Context context) {
        super(context);
        init(context, null);
    }

    public HorizontalColorPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HorizontalColorPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ColorPicker, 0, 0);
        try {
            float density  = getResources().getDisplayMetrics().density;
            radius = a.getDimension(R.styleable.ColorPicker_radius, 12*density);
            borderWidth = a.getDimension(R.styleable.ColorPicker_borderWidth, 4*density);
            borderColorUnselected = a.getColor(R.styleable.ColorPicker_borderColorUnselected, 0xFF888888);
            borderColorSelected = a.getColor(R.styleable.ColorPicker_borderColorSelected, 0xFF000000);
            margin = a.getDimension(R.styleable.ColorPicker_margin, 4*density);
        }
        finally {
            a.recycle();
        }

        setDefaultColors();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (((radius+borderWidth)*2+margin)*colors.size() + margin), (int) (radius+borderWidth+margin)*2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i = 0; i < colors.size(); ++i) {
            //Border
            paint.setColor(i == selected ? borderColorSelected : borderColorUnselected);
            canvas.drawCircle(margin + (((radius+borderWidth)*2)+margin)*i + radius + borderWidth, canvas.getHeight() / 2, radius + borderWidth, paint);

            paint.setColor(colors.get(i));
            canvas.drawCircle(margin + (((radius+borderWidth)*2)+margin)*i + radius + borderWidth, canvas.getHeight() / 2, radius, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();

        for (int i = 0; i < colors.size(); ++i) {
            float min = margin + ((radius + borderWidth)*2 + margin)*i;
            float max = margin + ((radius + borderWidth)*2 + margin)*(i+1);

            if (x > min && x < max) {
                selected = i;
                invalidate();

                if (listener != null) listener.onColorSelected(colors.get(i));

                return true;
            }
        }

        return false;
    }

    public void setDefaultColors() {
        colors.clear();
        for (int color : DEFAULT_COLORS) colors.add(color);
    }

    /**
     * Add a color to the picker.
     * @param newColor The color value to be added.
     * @return Returns false if the color was already in the picker, otherwise returns true.
     */
    public boolean addColor(int newColor) {
        for (int color : colors) {
            if (color == newColor) return false;
        }
        colors.add(newColor);
        return true;
    }

    /**
     * Remove a color from the picker
     * @param color The color value to be removed.
     * @return Returns false if the picker has not this color, otherwise returns true.
     */
    public boolean removeColor(int color) {
        for (int i = 0; i < colors.size(); ++i) {
            if (colors.get(i) == color) {
                colors.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Set the listener that will be called when a color is selected.
     * @param listener
     */
    public void setListener(ColorPickerListener listener) {
        this.listener = listener;
    }
}
