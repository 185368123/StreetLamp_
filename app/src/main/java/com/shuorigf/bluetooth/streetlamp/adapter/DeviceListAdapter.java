package com.shuorigf.bluetooth.streetlamp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.ble.LeScanner;
import com.shuorigf.bluetooth.streetlamp.data.BleDeviceInfo;

import java.util.List;

/**
 * Created by clx on 2017/10/31.
 */

public class DeviceListAdapter extends BaseQuickAdapter<BleDeviceInfo, BaseViewHolder> {

    public DeviceListAdapter(List<BleDeviceInfo> data) {
        super(R.layout.rv_item_simple_content_m, data);
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, BleDeviceInfo bleDeviceInfo) {
        baseViewHolder.setText(R.id.tv_title, bleDeviceInfo.mDeviceName);
        if (LeScanner.getInstance(mContext).getMac().equals(bleDeviceInfo.mMacAddress)) {
            switch(LeScanner.getInstance(mContext).getState()) {
                case LeScanner.STATE_DISCONNECTED:
                    baseViewHolder.setText(R.id.tv_content, R.string.disconnected);
                    break;
                case LeScanner.STATE_CONNECTING:
                    baseViewHolder.setText(R.id.tv_content, R.string.connecting);
                    break;
                case LeScanner.STATE_CONNECTED:
                    baseViewHolder.setText(R.id.tv_content, R.string.connected);
                    break;
            }
        }else {
            baseViewHolder.setText(R.id.tv_content, R.string.disconnected);
        }

    }
}
