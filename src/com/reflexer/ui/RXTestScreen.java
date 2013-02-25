
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

    int distanceBetweenItems;

    int itemCount = 10;
    int onScreenItemCount = 5;
    
    int previousLocation = 0;

    int[] itemOnScreen = new int[5];
    int[] heightButton = new int[5];

    private ArrayList<Button> buttons = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_screen);

        // item = (Button)findViewById(R.id.item);
        // item = new Button(this);
        // LinearLayout.LayoutParams params = new
        // LinearLayout.LayoutParams(buttonWidth, buttonHeight);
        // item.setLayoutParams(params);
        // parentLayout.addView(item);

        parentLayout = (LinearLayout)findViewById(R.id.parent_layout);
        parentLayout.setOnTouchListener(parentLayoutTouchListener);

        getDisplayDimensions();

        // setItemPosition(0);

        distanceBetweenItems = (heightDisplay) / (onScreenItemCount + 1);
        Log.d("DARIO", "height display=" + heightDisplay);
        Log.d("DARIO", "distance between items=" + distanceBetweenItems);

        createItems(itemCount);
        setItemsOnScreen();
        setStartingPosition();

        // item.setOnClickListener(itemOnClickListener);
    }

    // private int itemHeightCombined() {
    // int heightCombined = 0;
    // for (int i = 0; i < onScreenItemCount; i++) {
    // heightCombined += buttons.get(i).getHeight();
    // }
    //
    // return heightCombined;
    // }

    private void createItems(int n) {
        for (int i = 0; i < n; i++) {
            Button button = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonWidth, buttonHeight);
            button.setLayoutParams(params);
            button.setText("Item " + i);
            button.setOnClickListener(itemOnClickListener);
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
                for (int i = 0; i < onScreenItemCount; i++) {
                    
                }
            }
            return true;
        }
    };

    private void setItemsOnScreen() {
        // hardkodirano za 5 itema za pocetak, poslije se moze ispravit
        itemOnScreen[0] = itemCount - 2;
        itemOnScreen[1] = itemCount - 1;
        itemOnScreen[2] = 0;
        itemOnScreen[3] = 1;
        itemOnScreen[4] = 2;

    }

    private void setHeightsOfButtons() {
        for (int i = 0; i < onScreenItemCount; i++) {
            heightButton[i] = (int)(distanceBetweenItems * (i + 1));
        }
    }

    private void setStartingPosition() {

        for (int i = 0; i < onScreenItemCount; i++) {

            Button buttonToDraw = buttons.get(itemOnScreen[i]);
            drawItem(buttonToDraw, heightButton[i]);

        }

    }

    private void drawItem(Button button, int height) {

        LinearLayout.LayoutParams params = (LayoutParams)button.getLayoutParams();

        float percentage;
        if (height < (heightDisplay / 2)) {
            percentage = (float)height / (heightDisplay / 2);

        } else {
            percentage = (float)(heightDisplay - height) / (heightDisplay / 2);
        }

        int leftMargin = (widthDisplay - (int)(buttonWidth * percentage * 2)) / 2;

        Log.d("DARIO", "percentage=" + percentage);
        Log.d("DARIO", "height/2=" + height / 2);

        if (height < (heightDisplay / 2)) {
            // params.setMargins(leftMargin, (height / 2) - (int)(buttonHeight *
            // percentage * 2), 0, 0);
            params.setMargins(leftMargin, (height / 4) + (int)(buttonHeight * percentage), 0, 0);
        } else {
            // params.setMargins(leftMargin, (height / 2) - (int)(buttonHeight *
            // percentage * 2 * 2), 0, 0);
            params.setMargins(leftMargin, (height / 4) + (int)(buttonHeight * percentage), 0, 0);
        }
        Log.d("DARIO", "minus=" + (int)(buttonHeight * percentage * 2));
        Log.d("DARIO", "total=" + ((height / 2) - (int)(buttonHeight * percentage * 2)));
        Log.d("DARIO", " ");

        params.width = (int)(buttonWidth * percentage * 2);
        params.height = (int)(buttonHeight * percentage * 2);

        button.getBackground().setAlpha((int)(percentage * 255));

        button.setTextColor(Color.argb((int)(percentage * 255), 0, 0, 0));
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12 * percentage);

        button.setLayoutParams(params);

        parentLayout.addView(button);

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
