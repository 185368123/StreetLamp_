package com.shuorigf.bluetooth.streetlamp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.data.DeviceInfo;
import com.shuorigf.bluetooth.streetlamp.util.ActivityManager;
import com.shuorigf.bluetooth.streetlamp.util.Constants;

import java.util.List;

/**
 * Created by clx on 2017/10/31.
 */

public class DeviceInformationAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    private DeviceInfo mDeviceInfo;

    public DeviceInformationAdapter(List<Integer> data) {
        super(R.layout.rv_item_simple_content_m, data);
        mDeviceInfo = ActivityManager.getInstance().topOfStackActivity().getIntent()
                .getParcelableExtra(Constants.DEVICE_INFO);
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, Integer integer) {;
        baseViewHolder.setText(R.id.tv_title, integer);
        switch (integer) {
            case R.string.device_type:
                baseViewHolder.setText(R.id.tv_content, mDeviceInfo.deviceType);
                break;
            case R.string.hardware_version:
                baseViewHolder.setText(R.id.tv_content, mDeviceInfo.hardwareVersion);
                break;
            case R.string.firmware_version:
                baseViewHolder.setText(R.id.tv_content, mDeviceInfo.firmwareVersion);
                break;

        }

    }
}
