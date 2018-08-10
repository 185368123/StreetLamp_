package com.shuorigf.bluetooth.streetlamp.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.data.SolarPanelInfo;

import java.util.List;

/**
 * Created by clx on 2017/12/13.
 */

public class SolarPanelInfoAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    private SolarPanelInfo mSolarPanelInfo;

    public SolarPanelInfoAdapter(List<Integer> data, SolarPanelInfo solarPanelInfo) {
        super(R.layout.rv_item_homepage_type, data);
        mSolarPanelInfo = solarPanelInfo;
        if (mSolarPanelInfo == null) {
            mSolarPanelInfo = new SolarPanelInfo();
        }
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, Integer integer) {;
        TextView title = baseViewHolder.getView(R.id.tv_title);
        title.setText(integer);
        title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rounded_rectangle_blue_10dp, 0, 0, 0);
        switch (integer) {
            case R.string.charging_voltage:
                baseViewHolder.setText(R.id.tv_content, mSolarPanelInfo.voltage + "V");
                break;
            case R.string.charging_power:
                baseViewHolder.setText(R.id.tv_content, mSolarPanelInfo.power + "W");
                break;
            case R.string.charging_current:
                baseViewHolder.setText(R.id.tv_content, mSolarPanelInfo.current + "A");
                break;

        }

    }
}
