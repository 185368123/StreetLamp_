package com.shuorigf.bluetooth.streetlamp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.data.HistoricalDataInfo;

import java.util.List;

/**
 * Created by clx on 2017/11/7.
 */

public class HistoricalDataNonAdminAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private HistoricalDataInfo mHistoricalDataInfo;

    public HistoricalDataNonAdminAdapter(List<Integer> data, HistoricalDataInfo historicalDataInfo) {
        super(R.layout.rv_item_historical_data_non_admin, data);
        this.mHistoricalDataInfo = historicalDataInfo;
        if (mHistoricalDataInfo == null) {
            mHistoricalDataInfo = new HistoricalDataInfo();
        }
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, Integer integer) {
        baseViewHolder.setText(R.id.tv_title, integer);
        switch (integer) {
            case R.string.running_days:
                baseViewHolder.setText(R.id.tv_content, mHistoricalDataInfo.mAllRunDays + mContext.getString(R.string.days));
                break;
            case R.string.filling_time:
                baseViewHolder.setText(R.id.tv_content, mHistoricalDataInfo.mBatteryChargeFullTimes + mContext.getString(R.string.times));
                break;
            case R.string.over_discharge_times:
                baseViewHolder.setText(R.id.tv_content, mHistoricalDataInfo.mBatteryAllDischargeTimes + mContext.getString(R.string.times));
                break;
            case R.string.current_temperature:
                baseViewHolder.setText(R.id.tv_content, mHistoricalDataInfo.mCurrentTemperatur + "℃");
                break;

        }
    }
}
