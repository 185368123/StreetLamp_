package com.shuorigf.bluetooth.streetlamp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.base.BaseActivity;


/**
 * auther: chenlixin on 17/11/15.
 */
public class WelcomeActivity extends BaseActivity {
    private Handler handler = new Handler();


    @Override
    public int getLayoutRes() {
        return R.layout.activity_welcome;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }
}
