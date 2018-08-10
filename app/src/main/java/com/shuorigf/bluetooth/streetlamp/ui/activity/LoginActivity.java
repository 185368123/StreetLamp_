package com.shuorigf.bluetooth.streetlamp.ui.activity;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.LoginFragment;


/**
 * Created by clx on 2017/10/28.
 */

public class LoginActivity extends ToolbarContainerActivity {

    @NonNull
    @Override
    protected String getToolbarTitle() {
        return getString(R.string.login);
    }

    @NonNull
    @Override
    public Fragment newFragmentInstance() {
        return LoginFragment.newInstance();
    }
}
