package com.shuorigf.bluetooth.streetlamp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.ble.ShourigfData;
import com.shuorigf.bluetooth.streetlamp.data.FirstSecondInfo;

import java.util.List;

/**
 * Created by clx on 2017/11/6.
 */

public class FirstSecondAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    private FirstSecondInfo mFirstSecondInfo;

    public FirstSecondAdapter(List<Integer> data, FirstSecondInfo firstSecondInfo) {
        super(R.layout.rv_item_first_second, data);
        mFirstSecondInfo = firstSecondInfo;
        if (mFirstSecondInfo == null) {
            mFirstSecondInfo = new FirstSecondInfo();
        }
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Integer integer) {;
        baseViewHolder.setText(R.id.tv_title,integer);
        switch (integer) {
            case R.string.first_time_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mFirstTime/60 + "h " + mFirstSecondInfo.mFirstTime%60 + "min");
                break;
            case R.string.first_power_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mFirstPower + "%");
                break;
            case R.string.first_color_temperature_colon:
                if(mFirstSecondInfo.mFirstColorTemperature == ShourigfData.STATUS_COLOR_TEMPERATURE_AUTOMATIC) {
                    baseViewHolder.setText(R.id.tv_content, R.string.automatic);
                }else if(mFirstSecondInfo.mFirstColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_WARM_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.warm_white);
                }else if(mFirstSecondInfo.mFirstColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_NATURAL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.natural_white);
                }else if(mFirstSecondInfo.mFirstColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_COOL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.cool_white);
                }

                break;
            case R.string.second_time_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mSecondTime/60 + "h " + mFirstSecondInfo.mSecondTime%60 + "min");
                break;
            case R.string.second_power_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mSecondPower + "%");
                break;
            case R.string.second_color_temperature_colon:
                if(mFirstSecondInfo.mSecondColorTemperature == ShourigfData.STATUS_COLOR_TEMPERATURE_AUTOMATIC) {
                    baseViewHolder.setText(R.id.tv_content, R.string.automatic);
                }else if(mFirstSecondInfo.mSecondColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_WARM_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.warm_white);
                }else if(mFirstSecondInfo.mSecondColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_NATURAL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.natural_white);
                }else if(mFirstSecondInfo.mSecondColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_COOL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.cool_white);
                }
                break;
            case R.string.three_time_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mThreeTime/60 + "h " + mFirstSecondInfo.mThreeTime%60 + "min");
                break;
            case R.string.three_power_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mThreePower + "%");
                break;
            case R.string.three_color_temperature_colon:
                if(mFirstSecondInfo.mThreeColorTemperature == ShourigfData.STATUS_COLOR_TEMPERATURE_AUTOMATIC) {
                    baseViewHolder.setText(R.id.tv_content, R.string.automatic);
                }else if(mFirstSecondInfo.mThreeColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_WARM_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.warm_white);
                }else if(mFirstSecondInfo.mThreeColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_NATURAL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.natural_white);
                }else if(mFirstSecondInfo.mThreeColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_COOL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.cool_white);
                }
                break;
            case R.string.four_time_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mFourTime/60 + "h " + mFirstSecondInfo.mFourTime%60 + "min");
                break;
            case R.string.four_power_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mFourPower + "%");
                break;
            case R.string.four_color_temperature_colon:
                if(mFirstSecondInfo.mFourColorTemperature == ShourigfData.STATUS_COLOR_TEMPERATURE_AUTOMATIC) {
                    baseViewHolder.setText(R.id.tv_content, R.string.automatic);
                }else if(mFirstSecondInfo.mFourColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_WARM_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.warm_white);
                }else if(mFirstSecondInfo.mFourColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_NATURAL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.natural_white);
                }else if(mFirstSecondInfo.mFourColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_COOL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.cool_white);
                }
                break;
            case R.string.morning_time_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mMorningTime/60 + "h " + mFirstSecondInfo.mMorningTime%60 + "min");
                break;
            case R.string.morning_power_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mMorningPower + "%");
                break;
            case R.string.morning_color_temperature_colon:
                if(mFirstSecondInfo.mMorningColorTemperature == ShourigfData.STATUS_COLOR_TEMPERATURE_AUTOMATIC) {
                    baseViewHolder.setText(R.id.tv_content, R.string.automatic);
                }else if(mFirstSecondInfo.mMorningColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_WARM_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.warm_white);
                }else if(mFirstSecondInfo.mMorningColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_NATURAL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.natural_white);
                }else if(mFirstSecondInfo.mMorningColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_COOL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.cool_white);
                }
                break;
            case R.string.have_one_power_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mHaveOnePower + "%");
                break;
            case R.string.have_one_color_temperature_colon:
                if(mFirstSecondInfo.mHaveOneColorTemperature == ShourigfData.STATUS_COLOR_TEMPERATURE_AUTOMATIC) {
                    baseViewHolder.setText(R.id.tv_content, R.string.automatic);
                }else if(mFirstSecondInfo.mHaveOneColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_WARM_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.warm_white);
                }else if(mFirstSecondInfo.mHaveOneColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_NATURAL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.natural_white);
                }else if(mFirstSecondInfo.mHaveOneColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_COOL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.cool_white);
                }
                break;
            case R.string.induction_delay_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mInductionDelay + "s");
                break;
            case R.string.no_one_power_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mNoOnePower + "%");
                break;
            case R.string.have_two_power_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mHaveTwoPower + "%");
                break;
            case R.string.have_two_color_temperature_colon:
                if(mFirstSecondInfo.mHaveTwoColorTemperature == ShourigfData.STATUS_COLOR_TEMPERATURE_AUTOMATIC) {
                    baseViewHolder.setText(R.id.tv_content, R.string.automatic);
                }else if(mFirstSecondInfo.mHaveTwoColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_WARM_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.warm_white);
                }else if(mFirstSecondInfo.mHaveTwoColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_NATURAL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.natural_white);
                }else if(mFirstSecondInfo.mHaveTwoColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_COOL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.cool_white);
                }
                break;
            case R.string.no_two_power_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mNoTwoPower + "%");
                break;
            case R.string.no_people_color_temperature_colon:
                if(mFirstSecondInfo.mNoPeopleColorTemperature == ShourigfData.STATUS_COLOR_TEMPERATURE_AUTOMATIC) {
                    baseViewHolder.setText(R.id.tv_content, R.string.automatic);
                }else if(mFirstSecondInfo.mNoPeopleColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_WARM_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.warm_white);
                }else if(mFirstSecondInfo.mNoPeopleColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_NATURAL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.natural_white);
                }else if(mFirstSecondInfo.mNoPeopleColorTemperature <= ShourigfData.STATUS_COLOR_TEMPERATURE_COOL_WHITE) {
                    baseViewHolder.setText(R.id.tv_content, R.string.cool_white);
                }
                break;
            case R.string.no_people_power_colon:
                baseViewHolder.setText(R.id.tv_content, mFirstSecondInfo.mNoPeoplePower + "%");
                break;
        }
    }
}
