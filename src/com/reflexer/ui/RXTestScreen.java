
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
    int[] itemY = new int[5];

    float startY;
    float endY;

    boolean layoutInit = false;

    private ArrayList<Button> buttons = new ArrayList<Button>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_screen);

        parentLayout = (LinearLayout)findViewById(R.id.parent_layout);

        getDisplayDimensions();

        distanceBetweenItems = (heightDisplay - 2 * buttonHeight) / (onScreenItemCount + 1);

        createItems(itemCount);
        setFirstItemsOnScreen();
        setInitialYCoords();
        initItems();
        layoutInit = true;

        parentLayout.setOnTouchListener(parentLayoutTouchListener);

    }

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
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                startY = event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                moveItems(event.getY() - startY);
                startY = event.getY();
            }
            return true;
        }
    };

    private void moveItems(float distance) {

        for (int i = 0; i < onScreenItemCount; i++) {
            itemY[i] += distance;
        }

        if ((itemY[0] < 5) && (distance < 0)) {
            Log.d("DARIO", "distance < 0");
            // ovdje srediti da mjenja na sljedeci item uvijek
            itemOnScreen[0] = itemCount - 1;
            itemOnScreen[1] = 0;
            itemOnScreen[2] = 1;
            itemOnScreen[3] = 2;
            itemOnScreen[4] = 3;

            itemY[0] = itemY[1];
            itemY[1] = itemY[2];
            itemY[2] = itemY[3];
            itemY[3] = itemY[4];
            itemY[4] = heightDisplay;

        } else if ((itemY[4] > (heightDisplay - 300)) && (distance > 0)) {
            Log.d("DARIO", "distance > 0");
            itemOnScreen[0] = itemCount - 3;
            itemOnScreen[1] = itemCount - 2;
            itemOnScreen[2] = itemCount - 1;
            itemOnScreen[3] = 0;
            itemOnScreen[4] = 1;

            itemY[4] = itemY[3];
            itemY[3] = itemY[2];
            itemY[2] = itemY[1];
            itemY[1] = itemY[0];
            itemY[0] = distanceBetweenItems;
        }
        Log.d("DARIO", "itemY4=" + itemY[4]);
        Log.d("DARIO", "height display=" + heightDisplay);

        initItems();

    }

    private void setFirstItemsOnScreen() {
        // hardkodirano za 5 itema za pocetak, poslije se moze ispravit
        itemOnScreen[0] = itemCount - 2;
        itemOnScreen[1] = itemCount - 1;
        itemOnScreen[2] = 0;
        itemOnScreen[3] = 1;
        itemOnScreen[4] = 2;

    }

    private void setInitialYCoords() {
        for (int i = 0; i < onScreenItemCount; i++) {
            itemY[i] = (int)(distanceBetweenItems * (i + 1));
        }

        // itemY[2] = heightDisplay / 2;
        // itemY[1] = itemY[2] - heightDisplay / 12;
        // itemY[0] = itemY[1] - heightDisplay / 12;
        // itemY[3] = itemY[2] + heightDisplay / 12;
        // itemY[4] = itemY[3] + heightDisplay / 12;
    }

    private void initItems() {
        for (int i = 0; i < onScreenItemCount; i++) {
            Button buttonToDraw = buttons.get(itemOnScreen[i]);
            drawItem(buttonToDraw, itemY[i]);
            // Log.d("DARIO", "itemY=" + itemY[i]);

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

        params.setMargins(leftMargin, (height / 4) + (int)(buttonHeight * percentage), 0, 0);

        // SKALIRANJE ITEMA, ALPHA, TEXT COLOR I SIZE
        params.width = (int)(buttonWidth * percentage * 2);
        params.height = (int)(buttonHeight * percentage * 2);
        button.getBackground().setAlpha((int)(percentage * 255));
        button.setTextColor(Color.argb((int)(percentage * 255), 0, 0, 0));
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12 * percentage);
        button.setLayoutParams(params);

        if (!layoutInit) {
            parentLayout.addView(button);
        } else {
            parentLayout.invalidate();
        }

    }

    private void getDisplayDimensions() {
        Display display = getWindowManager().getDefaultDisplay();
        widthDisplay = display.getWidth();
        heightDisplay = display.getHeight();

    }
}
