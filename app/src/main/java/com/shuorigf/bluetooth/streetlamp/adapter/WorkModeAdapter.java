package com.shuorigf.bluetooth.streetlamp.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.ble.ShourigfData;
import com.shuorigf.bluetooth.streetlamp.data.IconText;
import com.shuorigf.bluetooth.streetlamp.data.WorkModeContentInfo;

import java.util.List;

/**
 * Created by clx on 2017/11/6.
 */

public class WorkModeAdapter extends BaseQuickAdapter<IconText, BaseViewHolder> {
    private WorkModeContentInfo mWorkModeContentInfo;
    private boolean mIsEdit;

    public WorkModeAdapter(List<IconText> data, WorkModeContentInfo workModeContentInfo, boolean isEdit) {
        super(R.layout.rv_item_work_mode, data);
        this.mIsEdit = isEdit;
        mWorkModeContentInfo = workModeContentInfo;
        if (mWorkModeContentInfo == null) {
            mWorkModeContentInfo = new WorkModeContentInfo();
        }
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, IconText iconText) {;
        TextView title = baseViewHolder.getView(R.id.tv_title);
        TextView content = baseViewHolder.getView(R.id.tv_content);
        title.setText(iconText.title);
        title.setCompoundDrawablesWithIntrinsicBounds(iconText.icon, 0, 0, 0);
        if (mIsEdit) {
            content.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.go_to, 0);
        }

        switch (iconText.title) {
            case R.string.light_control_voltage:
                content.setText(mWorkModeContentInfo.mLightControlVoltage + "V");
                break;
            case R.string.light_control_delay:
                content.setText(mWorkModeContentInfo.mLightControlDelay +"min");
                break;
            case R.string.load_current:
                content.setText(mWorkModeContentInfo.mLoadCurrent + "mA");
                break;
            case R.string.color_light_source:
                int colorLightSourceRes = R.string.flicker;
                switch (mWorkModeContentInfo.mColorLightSource) {
                    case ShourigfData.STATUS_COLOR_LIGHT_SOURCE_FLICKER:
                        colorLightSourceRes = R.string.flicker;
                        break;
                    case ShourigfData.STATUS_COLOR_LIGHT_SOURCE_RED:
                        colorLightSourceRes = R.string.red;
                        break;
                    case ShourigfData.STATUS_COLOR_LIGHT_SOURCE_GREEN:
                        colorLightSourceRes = R.string.green;
                        break;
                    case ShourigfData.STATUS_COLOR_LIGHT_SOURCE_BLUE:
                        colorLightSourceRes = R.string.blue;
                        break;
                    case ShourigfData.STATUS_COLOR_LIGHT_SOURCE_RED_GREEN:
                        colorLightSourceRes = R.string.red_green;
                        break;
                    case ShourigfData.STATUS_COLOR_LIGHT_SOURCE_RED_BLUE:
                        colorLightSourceRes = R.string.red_blue;
                        break;
                    case ShourigfData.STATUS_COLOR_LIGHT_SOURCE_GREEN_BLUE:
                        colorLightSourceRes = R.string.green_blue;
                        break;
                    case ShourigfData.STATUS_COLOR_LIGHT_SOURCE_PUT_OUT:
                        colorLightSourceRes = R.string.put_out;
                        break;
                }
                content.setText(colorLightSourceRes);
                break;
            case R.string.smart_power:
                int smartPowerRes = R.string.close;
                switch (mWorkModeContentInfo.mSmartPower) {
                    case ShourigfData.STATUS_CLOSE:
                        smartPowerRes = R.string.close;
                        break;
                    case ShourigfData.STATUS_AUTOMATIC:
                        smartPowerRes = R.string.automatic;
                        break;
                    case ShourigfData.STATUS_ONE_LEVEL:
                        smartPowerRes = R.string.one_level;
                        break;
                    case ShourigfData.STATUS_TWO_LEVEL:
                        smartPowerRes = R.string.two_level;
                        break;
                    case ShourigfData.STATUS_THREE_LEVEL:
                        smartPowerRes = R.string.three_level;
                        break;
                    case ShourigfData.STATUS_FOUR_LEVEL:
                        smartPowerRes = R.string.four_level;
                        break;
                    case ShourigfData.STATUS_FIVE_LEVEL:
                        smartPowerRes = R.string.five_level;
                        break;
                    case ShourigfData.STATUS_SIX_LEVEL:
                        smartPowerRes = R.string.six_level;
                        break;
                    case ShourigfData.STATUS_SEVEN_LEVEL:
                        smartPowerRes = R.string.seven_level;
                        break;

                }
                content.setText(smartPowerRes);
                break;
            case R.string.temperature_protection:
                int temperatureProtectionRes = R.string.close;
                switch (mWorkModeContentInfo.mTemperatureProtection) {
                    case ShourigfData.STATUS_SWITCH_CLOSE:
                        temperatureProtectionRes = R.string.close;
                        break;
                    case ShourigfData.STATUS_SWITCH_OPEN:
                        temperatureProtectionRes = R.string.open;
                        break;
                }
                content.setText(temperatureProtectionRes);
                break;
            case R.string.induction_distance:
                int inductionDistanceRes = R.string.one_level;
                switch (mWorkModeContentInfo.mInductionDistance) {
                    case ShourigfData.STATUS_ONE_LEVEL:
                        inductionDistanceRes = R.string.one_level;
                        break;
                    case ShourigfData.STATUS_TWO_LEVEL:
                        inductionDistanceRes = R.string.two_level;
                        break;
                    case ShourigfData.STATUS_THREE_LEVEL:
                        inductionDistanceRes = R.string.three_level;
                        break;
                    case ShourigfData.STATUS_FOUR_LEVEL:
                        inductionDistanceRes = R.string.four_level;
                        break;
                    case ShourigfData.STATUS_FIVE_LEVEL:
                        inductionDistanceRes = R.string.five_level;
                        break;
                    case ShourigfData.STATUS_SIX_LEVEL:
                        inductionDistanceRes = R.string.six_level;
                        break;
                    case ShourigfData.STATUS_SEVEN_LEVEL:
                        inductionDistanceRes = R.string.seven_level;
                        break;

                }
                content.setText(inductionDistanceRes);
                break;
            case R.string.music_switch:
                int musicSwitchRes = R.string.close;
                switch (mWorkModeContentInfo.mMusicSwitch) {
                    case ShourigfData.STATUS_SWITCH_CLOSE:
                        musicSwitchRes = R.string.close;
                        break;
                    case ShourigfData.STATUS_SWITCH_OPEN:
                        musicSwitchRes = R.string.open;
                        break;
                }
                content.setText(musicSwitchRes);
                break;
            case R.string.over_charge_voltage:
                content.setText(mWorkModeContentInfo.mOverchargeVoltage + "V");
                break;
            case R.string.over_charge_return:
                content.setText(mWorkModeContentInfo.mOverchargeReturn + "V");
                break;
            case R.string.over_discharge_return:
                content.setText(mWorkModeContentInfo.mOverDischargeReturn + "V");
                break;
            case R.string.over_discharge_voltage:
                content.setText(mWorkModeContentInfo.mOverDischargeVoltage + "V");
                break;
        }
    }
}