
package com.reflexer.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.reflexer.R;

public class RXMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        Button button = (Button)findViewById(R.id.test_screen);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RXMenuActivity.this, RXTestScreen.class);
                startActivity(intent);
            }
        });
    }
}
