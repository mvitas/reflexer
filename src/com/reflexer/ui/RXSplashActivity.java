
package com.reflexer.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.reflexer.R;

public class RXSplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {
                Intent intent = new Intent(RXSplashActivity.this, RXMenuActivity.class);
                RXSplashActivity.this.startActivity(intent);
                finish();
            }

        }, 1000);

    }

}
