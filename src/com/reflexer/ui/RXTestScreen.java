
package com.reflexer.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.reflexer.R;

import java.util.ArrayList;

public class RXTestScreen extends Activity {

    Button item;
    LinearLayout parentLayout;

    int widthDisplay;
    int heightDisplay;

    // TODO: ovo je u pixelima hardkodirano, srediti da se vuce iz xml layouta
    int buttonWidth = 100;
    int buttonHeight = 60;

    int itemCount = 10;
    int onScreenItemCount = 5;

    private ArrayList<Button> buttons = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_screen);

        // item = (Button)findViewById(R.id.item);
        item = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonWidth, buttonHeight);
        item.setLayoutParams(params);
        parentLayout = (LinearLayout)findViewById(R.id.parent_layout);
        parentLayout.addView(item);

        parentLayout.setOnTouchListener(parentLayoutTouchListener);

        getDisplayDimensions();

        // setItemPosition(0);
        createItems(itemCount);
        setStartingPosition(0);

        item.setOnClickListener(itemOnClickListener);
    }

    private void createItems(int n) {
        for (int i = 0; i < n; i++) {
            Button button = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonWidth, buttonHeight);
            button.setLayoutParams(params);
            buttons.add(button);
        }
    }

    OnClickListener itemOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(RXTestScreen.this, RXTestScreen.class);
            startActivity(intent);
            finish();
        }
    };

    OnTouchListener parentLayoutTouchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                setItemPosition((int)(event.getY()));
            }
            return true;
        }
    };

    private void setStartingPosition(int startItem) {

        for (int i = 0; i < itemCount; i++) {

        }

    }

    private void setItemPosition(int height) {

        LinearLayout.LayoutParams params = (LayoutParams)item.getLayoutParams();

        float percentage;
        if (height < (heightDisplay / 2)) {
            percentage = (float)height / (heightDisplay / 2);
        } else {
            percentage = (float)(heightDisplay - height) / (heightDisplay / 2);
        }

        int leftMargin = (widthDisplay - (int)(buttonWidth * percentage * 2)) / 2;
        params.setMargins(leftMargin, height - (int)(buttonWidth * percentage), 0, 0);

        params.width = (int)(buttonWidth * percentage * 2);
        params.height = (int)(buttonHeight * percentage * 2);
        item.getBackground().setAlpha((int)(percentage * 255));

        item.setTextColor(Color.argb((int)(percentage * 255), 0, 0, 0));
        item.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12 * percentage * 2);

        item.setLayoutParams(params);
        item.invalidate();

    }

    private void getDisplayDimensions() {
        Display display = getWindowManager().getDefaultDisplay();
        widthDisplay = display.getWidth();
        heightDisplay = display.getHeight();

    }
}
