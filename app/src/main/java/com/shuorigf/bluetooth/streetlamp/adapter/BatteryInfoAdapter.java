package com.shuorigf.bluetooth.streetlamp.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.data.BatteryInfo;

import java.util.List;

/**
 * Created by clx on 2017/12/13.
 */

public class BatteryInfoAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    private BatteryInfo mBatteryInfo;

    public BatteryInfoAdapter(List<Integer> data, BatteryInfo batteryInfo) {
        super(R.layout.rv_item_homepage_type, data);
        mBatteryInfo = batteryInfo;
        if (mBatteryInfo == null) {
            mBatteryInfo = new BatteryInfo();
        }
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, Integer integer) {;
        TextView title = baseViewHolder.getView(R.id.tv_title);
        title.setText(integer);
        title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rounded_rectangle_blue_10dp, 0, 0, 0);
        switch (integer) {
            case R.string.voltage:
                baseViewHolder.setText(R.id.tv_content, mBatteryInfo.voltage + "V");
                break;
            case R.string.current:
                baseViewHolder.setText(R.id.tv_content, mBatteryInfo.current + "A");
                break;
            case R.string.electricity:
                baseViewHolder.setText(R.id.tv_content, mBatteryInfo.electricity + "%");
                break;
            case R.string.temperature:
                baseViewHolder.setText(R.id.tv_content, mBatteryInfo.temperature + "℃");
                break;

        }

    }
}
