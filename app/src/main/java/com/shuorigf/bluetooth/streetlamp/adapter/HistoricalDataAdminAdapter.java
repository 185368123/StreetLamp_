package com.shuorigf.bluetooth.streetlamp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.data.BatteryTemperatureInfo;

import java.util.List;

/**
 * Created by clx on 2017/11/8.
 */

public class HistoricalDataAdminAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    private BatteryTemperatureInfo mBatteryTemperatureInfo;

    public HistoricalDataAdminAdapter(List<Integer> data, BatteryTemperatureInfo batteryTemperatureInfo) {
        super(R.layout.rv_item_historical_data_admin, data);
        this.mBatteryTemperatureInfo = batteryTemperatureInfo;
        if (this.mBatteryTemperatureInfo == null) {
            this.mBatteryTemperatureInfo = new BatteryTemperatureInfo();
        }
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, Integer integer) {
        baseViewHolder.setText(R.id.tv_title, integer);
        switch (integer) {
            case R.string.battery_temperature:
                baseViewHolder.setText(R.id.tv_content, mBatteryTemperatureInfo.mBatteryTemperature + "℃");
                break;
            case R.string.protective_plate_temperature:
                baseViewHolder.setText(R.id.tv_content, mBatteryTemperatureInfo.mProtectivePlateTemperature + "℃");
                break;
            case R.string.control_board_temperature:
                baseViewHolder.setText(R.id.tv_content, mBatteryTemperatureInfo.mControlBoardTemperature + "℃");
                break;
        }
    }
}
