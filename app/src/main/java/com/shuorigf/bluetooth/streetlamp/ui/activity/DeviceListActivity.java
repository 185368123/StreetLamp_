package com.shuorigf.bluetooth.streetlamp.ui.activity;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.DeviceListFragment;


/**
 * Created by clx on 2017/10/31.
 */

public class DeviceListActivity extends ToolbarContainerActivity {

    @NonNull
    @Override
    protected String getToolbarTitle() {
        return getString(R.string.device_information);
    }

    @NonNull
    @Override
    public Fragment newFragmentInstance() {
        return DeviceListFragment.newInstance();
    }
}
