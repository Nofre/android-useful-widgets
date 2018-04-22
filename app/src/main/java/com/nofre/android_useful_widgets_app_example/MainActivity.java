package com.nofre.android_useful_widgets_app_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nofre.android_useful_widgets.ColorPickerListener;
import com.nofre.android_useful_widgets.GridColorPicker;
import com.nofre.android_useful_widgets.HorizontalColorPicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HorizontalColorPicker horizontalColorPicker = findViewById(R.id.horizontal_color_picker);
        horizontalColorPicker.setListener(new ColorPickerListener() {
            @Override
            public void onColorSelected(int color) {
                findViewById(R.id.color1).setBackgroundColor(color);
            }
        });

        GridColorPicker gridColorPicker = findViewById(R.id.grid_color_picker);
        gridColorPicker.setListener(new ColorPickerListener() {
            @Override
            public void onColorSelected(int color) {
                findViewById(R.id.color2).setBackgroundColor(color);
            }
        });
    }
}
