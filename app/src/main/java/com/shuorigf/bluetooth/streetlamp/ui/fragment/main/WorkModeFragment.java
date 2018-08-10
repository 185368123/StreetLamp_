package com.shuorigf.bluetooth.streetlamp.ui.fragment.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.adapter.CommonFragmentPagerAdapter;
import com.shuorigf.bluetooth.streetlamp.adapter.WorkModeAdapter;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;
import com.shuorigf.bluetooth.streetlamp.ble.LeScanner;
import com.shuorigf.bluetooth.streetlamp.ble.ModbusData;
import com.shuorigf.bluetooth.streetlamp.ble.ShourigfData;
import com.shuorigf.bluetooth.streetlamp.data.IconText;
import com.shuorigf.bluetooth.streetlamp.data.ReadData;
import com.shuorigf.bluetooth.streetlamp.data.WorkModeContentInfo;
import com.shuorigf.bluetooth.streetlamp.ui.activity.LoginActivity;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.workmode.SetDataDialogFragment;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.workmode.SwitchButtonDialogFragment;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.workmode.WorkModeAdminLModeFragment;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.workmode.WorkModeAdminMModeFragment;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.workmode.WorkModeAdminTModeFragment;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.workmode.WorkModeAdminUModeFragment;
import com.shuorigf.bluetooth.streetlamp.util.Constants;
import com.shuorigf.bluetooth.streetlamp.util.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by clx on 2017/10/30.
 */

public class WorkModeFragment extends BaseFragment {

    @BindView(R.id.tv_work_mode_logout_administrator)
    TextView mLogoutAdministratorTv;
    @BindView(R.id.tv_work_mode_tourist_model)
    TextView mTouristModelTv;
    @BindView(R.id.tv_work_mode_setting)
    TextView mSettingTv;
    @BindView(R.id.work_mode_admin_tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.work_mode_admin_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.rv_work_mode_admin_content)
    RecyclerView mContentRv;
    @BindArray(R.array.work_mode_admin_title)
    TypedArray mTabTitles;
    @BindArray(R.array.work_mode_title)
    TypedArray mTitle;
    @BindArray(R.array.work_mode_icon)
    TypedArray mIcon;

    @BindArray(R.array.color_light_source_title)
    TypedArray mColorLightSourceTitle;
    @BindArray(R.array.smart_power_title)
    TypedArray mSmartPowerTitle;
    @BindArray(R.array.induction_distance_title)
    TypedArray mInductionDistanceTitle;


    private WorkModeContentInfo mWorkModeContentInfo = new WorkModeContentInfo();
    private WorkModeAdapter mWorkModeAdapter;

    private OptionsPickerView mColorLightSourcePv;
    private OptionsPickerView mSmartPowerPv;
    private OptionsPickerView mInductionDistancePv;

    private List<Fragment> fragments = new ArrayList<>();


    public static WorkModeFragment newInstance() {
        Bundle args = new Bundle();
        WorkModeFragment fragment = new WorkModeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * get layout resources id
     *
     * @return layoutRes
     */
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_work_mode;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {
        initVP();
        initContent();
        registerReceiver();
    }

    private void initReadData() {
        if (mLeScanner != null) {
            clearSettingData();
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_WORK_MODE_CONTENT,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.WorkModeContentInfo.REG_ADDR, ShourigfData.WorkModeContentInfo.READ_WORD)));
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_OVER,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.OverInfo.REG_ADDR, ShourigfData.OverInfo.READ_WORD)));

        }
    }

    private void clearSettingData() {
        mLeScanner.clearSettingList(Constants.TYPE_SET_COLOR_LIGHT_SOURCE);
        mLeScanner.clearSettingList(Constants.TYPE_SET_SMART_POWER);
        mLeScanner.clearSettingList(Constants.TYPE_SET_INDUCTION_DISTANCE);
        mLeScanner.clearSettingList(Constants.TYPE_SET_TEMPERATURE_PROTECTION);
        mLeScanner.clearSettingList(Constants.TYPE_SET_MUSIC_SWITCH);
        mLeScanner.clearSettingList(Constants.TYPE_SET_LIGHT_CONTROL_VOLTAGE);
        mLeScanner.clearSettingList(Constants.TYPE_SET_LIGHT_CONTROL_DELAY);
        mLeScanner.clearSettingList(Constants.TYPE_SET_LOAD_CURRENT);
        mLeScanner.clearSettingList(Constants.TYPE_SET_OVER_CHARGE_VOLTAGE);
        mLeScanner.clearSettingList(Constants.TYPE_SET_OVER_CHARGE_RETURN);
        mLeScanner.clearSettingList(Constants.TYPE_SET_OVER_DISCHARGE_RETURN);
        mLeScanner.clearSettingList(Constants.TYPE_SET_OVER_DISCHARGE_VOLTAGE);
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LeScanner.ACTION_GET_ID);
        intentFilter.addAction(LeScanner.ACTION_DATA_AVAILABLE);
        getContext().registerReceiver(mGattReceiver, intentFilter);
    }

    private final BroadcastReceiver mGattReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (LeScanner.ACTION_GET_ID.equals(action)) {
                initReadData();
            } else if (LeScanner.ACTION_DATA_AVAILABLE.equals(action)) {
                receiveData(intent.getIntExtra(LeScanner.EXTRA_TYPE, 0), intent.getByteArrayExtra(LeScanner.EXTRA_DATA));
            }
        }
    };

    private void receiveData(int type, byte[] data) {
        switch (type) {
            case Constants.TYPE_OVER:
                ShourigfData.OverInfo overInfo = new ShourigfData.OverInfo(data);
                mWorkModeContentInfo.mOverchargeVoltage = overInfo.mOverchargeVoltage;
                mWorkModeContentInfo.mOverchargeReturn = overInfo.mOverchargeReturn;
                mWorkModeContentInfo.mOverDischargeReturn = overInfo.mOverDischargeReturn;
                mWorkModeContentInfo.mOverDischargeVoltage = overInfo.mOverDischargeVoltage;
                if (mWorkModeAdapter != null) {
                    mWorkModeAdapter.notifyDataSetChanged();
                }
                break;
            case Constants.TYPE_WORK_MODE_CONTENT:
                ShourigfData.WorkModeContentInfo workModeContentInfo = new ShourigfData.WorkModeContentInfo(data);
                mWorkModeContentInfo.mLightControlDelay = workModeContentInfo.mLightControlDelay;
                mWorkModeContentInfo.mLightControlVoltage = workModeContentInfo.mLightControlVoltage;
                mWorkModeContentInfo.mLoadCurrent = workModeContentInfo.mLoadCurrent;
                mWorkModeContentInfo.mColorLightSource = workModeContentInfo.mColorLightSource;
                mWorkModeContentInfo.mSmartPower = workModeContentInfo.mSmartPower;
                mWorkModeContentInfo.mTemperatureProtection = workModeContentInfo.mTemperatureProtection;
                mWorkModeContentInfo.mInductionDistance = workModeContentInfo.mInductionDistance;
                mWorkModeContentInfo.mMusicSwitch = workModeContentInfo.mMusicSwitch;
                if (mWorkModeAdapter != null) {
                    mWorkModeAdapter.notifyDataSetChanged();
                }
                break;
            case Constants.TYPE_READ_MODE:
                ShourigfData.ReadMode readMode = new ShourigfData.ReadMode(data);
                switch (readMode.mMode) {
                    case ShourigfData.MODE_L://L
                        mViewPager.setCurrentItem(2);
                        break;
                    case ShourigfData.MODE_T://T
                        mViewPager.setCurrentItem(0);
                        break;
                    case ShourigfData.MODE_M://M
                        mViewPager.setCurrentItem(1);
                        break;
                    case ShourigfData.MODE_U://U
                        mViewPager.setCurrentItem(3);
                        break;
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mGattReceiver);
    }

    private void initVP() {
        fragments.add(WorkModeAdminTModeFragment.newInstance());
        fragments.add(WorkModeAdminMModeFragment.newInstance());
        fragments.add(WorkModeAdminLModeFragment.newInstance());
        fragments.add(WorkModeAdminUModeFragment.newInstance());


        mViewPager.setAdapter(new CommonFragmentPagerAdapter(getChildFragmentManager(), fragments) {
            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitles.getString(position);
            }
        });
        mViewPager.setOffscreenPageLimit(fragments.size());
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initContent() {
        List<IconText> list = new ArrayList<>();
        for (int i = 0; i < mTitle.length(); i++) {
            list.add(new IconText(mTitle.getResourceId(i, 0), mIcon.getResourceId(i, 0)));
        }
        mWorkModeAdapter = new WorkModeAdapter(list, mWorkModeContentInfo, true);
        mContentRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mContentRv.setNestedScrollingEnabled(false);
        mContentRv.setItemViewCacheSize(list.size());
        mContentRv.setAdapter(mWorkModeAdapter);
    }

    /**
     * init data
     */
    @Override
    public void initData() {
        super.initData();
        initReadData();
    }

    /**
     * init event
     */
    @Override
    protected void initEvent() {
        mWorkModeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!Constants.IS_LOGIN) {
                    ToastUtil.showShortToast(getContext(), R.string.hint_login_first);
                    return;
                }
                switch (mTitle.getResourceId(position, 0)) {
                    case R.string.temperature_protection:
                        showTemperatureProtectionDialog();
                        break;
                    case R.string.music_switch:
                        showMusicSwitchDialog();
                        break;
                    case R.string.color_light_source:
                        showColorLightSourcePv();
                        break;
                    case R.string.smart_power:
                        showSmartPowerPv();
                        break;
                    case R.string.induction_distance:
                        showInductionDistancePv();
                        break;
                    case R.string.light_control_voltage:
                        showLightControlVoltageDialog();
                        break;
                    case R.string.light_control_delay:
                        showLightControlDelayDialog();
                        break;
                    case R.string.load_current:
                        showLoadCurrentDialog();
                        break;
                    case R.string.over_charge_voltage:
                        showOverChargeVoltageDialog();
                        break;
                    case R.string.over_charge_return:
                        showOverChargeReturnDialog();
                        break;
                    case R.string.over_discharge_return:
                        showOverDisChargeReturnDialog();
                        break;
                    case R.string.over_discharge_voltage:
                        showOverDisChargeVoltageDialog();
                        break;

                }
            }
        });
    }

    private void showColorLightSourcePv() {
        if (mColorLightSourcePv == null) {
            mColorLightSourcePv = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    int data = -1;
                    switch (mColorLightSourceTitle.getResourceId(options1, 0)) {
                        case R.string.flicker:
                            data = ShourigfData.STATUS_COLOR_LIGHT_SOURCE_FLICKER;
                            break;
                        case R.string.red:
                            data = ShourigfData.STATUS_COLOR_LIGHT_SOURCE_RED;
                            break;
                        case R.string.green:
                            data = ShourigfData.STATUS_COLOR_LIGHT_SOURCE_GREEN;
                            break;
                        case R.string.blue:
                            data = ShourigfData.STATUS_COLOR_LIGHT_SOURCE_BLUE;
                            break;
                        case R.string.red_green:
                            data = ShourigfData.STATUS_COLOR_LIGHT_SOURCE_RED_GREEN;
                            break;
                        case R.string.red_blue:
                            data = ShourigfData.STATUS_COLOR_LIGHT_SOURCE_RED_BLUE;
                            break;
                        case R.string.green_blue:
                            data = ShourigfData.STATUS_COLOR_LIGHT_SOURCE_GREEN_BLUE;
                            break;
                        case R.string.put_out:
                            data = ShourigfData.STATUS_COLOR_LIGHT_SOURCE_PUT_OUT;
                            break;
                    }

                    if (data != -1) {
                        mWorkModeContentInfo.mColorLightSource = data;
                        if (mWorkModeAdapter != null) {
                            mWorkModeAdapter.notifyDataSetChanged();
                        }
                        if (mLeScanner != null) {
                            mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_COLOR_LIGHT_SOURCE,
                                    ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_COLOR_LIGHT_SOURCE_ADDR, data)));
                        }
                    }

                }
            })
                    .setSubCalSize(15)//确定和取消文字大小
                    .setSubmitColor(ContextCompat.getColor(getContext(), R.color.white))//确定按钮文字颜色
                    .setCancelColor(ContextCompat.getColor(getContext(), R.color.white))//取消按钮文字颜色
                    .setTitleColor(ContextCompat.getColor(getContext(), R.color.black))
                    .setTitleText(getString(R.string.color_light_source))
                    .setTitleBgColor(ContextCompat.getColor(getContext(), R.color.textBlue))//标题背景颜色 Night mode
                    .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                    .setContentTextSize(18)//滚轮文字大小
                    .setCyclic(false, false, false)//循环与否
                    .build();
            List<String> mColorLightSourceList = new ArrayList<>();
            for (int i = 0; i < mColorLightSourceTitle.length(); i++) {
                mColorLightSourceList.add(mColorLightSourceTitle.getString(i));
            }

            mColorLightSourcePv.setPicker(mColorLightSourceList);//添加数据源
        }
        mColorLightSourcePv.show();

    }

    private void showSmartPowerPv() {
        if (mSmartPowerPv == null) {
            mSmartPowerPv = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    int data = -1;
                    switch (mSmartPowerTitle.getResourceId(options1, 0)) {
                        case R.string.close:
                            data = ShourigfData.STATUS_CLOSE;
                            break;
                        case R.string.one_level:
                            data = ShourigfData.STATUS_ONE_LEVEL;
                            break;
                        case R.string.two_level:
                            data = ShourigfData.STATUS_TWO_LEVEL;
                            break;
                        case R.string.three_level:
                            data = ShourigfData.STATUS_THREE_LEVEL;
                            break;
                        case R.string.four_level:
                            data = ShourigfData.STATUS_FOUR_LEVEL;
                            break;
                        case R.string.five_level:
                            data = ShourigfData.STATUS_FIVE_LEVEL;
                            break;
                        case R.string.six_level:
                            data = ShourigfData.STATUS_SEVEN_LEVEL;
                            break;
                        case R.string.seven_level:
                            data = ShourigfData.STATUS_SEVEN_LEVEL;
                            break;
                        case R.string.eight_level:
                            data = ShourigfData.STATUS_EIGHT_LEVEL;
                            break;
                        case R.string.automatic:
                            data = ShourigfData.STATUS_AUTOMATIC;
                            break;
                    }
                    if (data != -1) {
                        mWorkModeContentInfo.mSmartPower = data;
                        if (mWorkModeAdapter != null) {
                            mWorkModeAdapter.notifyDataSetChanged();
                        }
                        if (mLeScanner != null) {
                            mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_SMART_POWER,
                                    ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_SMART_POWER_ADDR, data)));
                        }
                    }

                }
            })
                    .setSubCalSize(15)//确定和取消文字大小
                    .setSubmitColor(ContextCompat.getColor(getContext(), R.color.white))//确定按钮文字颜色
                    .setCancelColor(ContextCompat.getColor(getContext(), R.color.white))//取消按钮文字颜色
                    .setTitleColor(ContextCompat.getColor(getContext(), R.color.black))
                    .setTitleText(getString(R.string.smart_power))
                    .setTitleBgColor(ContextCompat.getColor(getContext(), R.color.textBlue))//标题背景颜色 Night mode
                    .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                    .setContentTextSize(18)//滚轮文字大小
                    .setCyclic(false, false, false)//循环与否
                    .build();
            List<String> mSmartPowerList = new ArrayList<>();
            for (int i = 0; i < mSmartPowerTitle.length(); i++) {
                mSmartPowerList.add(mSmartPowerTitle.getString(i));
            }

            mSmartPowerPv.setPicker(mSmartPowerList);//添加数据源
        }
        mSmartPowerPv.show();
    }

    private void showInductionDistancePv() {
        if (mInductionDistancePv == null) {
            mInductionDistancePv = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    int data = -1;
                    switch (mInductionDistanceTitle.getResourceId(options1, 0)) {
                        case R.string.one_level:
                            data = ShourigfData.STATUS_ONE_LEVEL;
                            break;
                        case R.string.two_level:
                            data = ShourigfData.STATUS_TWO_LEVEL;
                            break;
                        case R.string.three_level:
                            data = ShourigfData.STATUS_THREE_LEVEL;
                            break;
                        case R.string.four_level:
                            data = ShourigfData.STATUS_FOUR_LEVEL;
                            break;
                        case R.string.five_level:
                            data = ShourigfData.STATUS_FIVE_LEVEL;
                            break;
                        case R.string.six_level:
                            data = ShourigfData.STATUS_SEVEN_LEVEL;
                            break;
                        case R.string.seven_level:
                            data = ShourigfData.STATUS_SEVEN_LEVEL;
                            break;
                        case R.string.eight_level:
                            data = ShourigfData.STATUS_EIGHT_LEVEL;
                            break;
                    }

                    if (data != -1) {
                        mWorkModeContentInfo.mInductionDistance = data;
                        if (mWorkModeAdapter != null) {
                            mWorkModeAdapter.notifyDataSetChanged();
                        }
                        if (mLeScanner != null) {
                            mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_INDUCTION_DISTANCE,
                                    ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_INDUCTION_DISTANCE_ADDR, data)));
                        }
                    }

                }
            })
                    .setSubCalSize(15)//确定和取消文字大小
                    .setSubmitColor(ContextCompat.getColor(getContext(), R.color.white))//确定按钮文字颜色
                    .setCancelColor(ContextCompat.getColor(getContext(), R.color.white))//取消按钮文字颜色
                    .setTitleColor(ContextCompat.getColor(getContext(), R.color.black))
                    .setTitleText(getString(R.string.induction_distance))
                    .setTitleBgColor(ContextCompat.getColor(getContext(), R.color.textBlue))//标题背景颜色 Night mode
                    .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                    .setContentTextSize(18)//滚轮文字大小
                    .setCyclic(false, false, false)//循环与否
                    .build();
            List<String> mInductionDistanceList = new ArrayList<>();
            for (int i = 0; i < mInductionDistanceTitle.length(); i++) {
                mInductionDistanceList.add(mInductionDistanceTitle.getString(i));
            }

            mInductionDistancePv.setPicker(mInductionDistanceList);//添加数据源
        }
        mInductionDistancePv.show();
    }

    private void showLightControlVoltageDialog() {
        SetDataDialogFragment setDataDialogFragment = SetDataDialogFragment.newInstance(R.string.light_control_voltage, mWorkModeContentInfo.mLightControlVoltage+"", "V");
        setDataDialogFragment.setOnSetDataListener(new SetDataDialogFragment.OnSetDataListener() {
            @Override
            public void onSetData(String data) {
                BigDecimal dataBD = new BigDecimal(data);
                if(dataBD.compareTo(new BigDecimal("0.1")) < 0 || dataBD.compareTo(new BigDecimal("60")) > 0) {
                    ToastUtil.showShortToast(getContext(), R.string.between_light_control_voltage);
                }else {
                    mWorkModeContentInfo.mLightControlVoltage = dataBD.setScale(1, BigDecimal.ROUND_DOWN).floatValue();
                    if (mWorkModeAdapter != null) {
                        mWorkModeAdapter.notifyDataSetChanged();
                    }
                    int value = dataBD.multiply(new BigDecimal("10")).intValue();
                    if (mLeScanner != null) {
                        mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_LIGHT_CONTROL_VOLTAGE,
                                ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_LIGHT_CONTROL_VOLTAGE_ADDR,value)));
                    }
                }
            }
        });
        setDataDialogFragment.show(getChildFragmentManager(), "");

    }

    private void showLightControlDelayDialog() {
        SetDataDialogFragment setDataDialogFragment = SetDataDialogFragment.newInstance(R.string.light_control_delay, mWorkModeContentInfo.mLightControlDelay+"", "min");
        setDataDialogFragment.setOnSetDataListener(new SetDataDialogFragment.OnSetDataListener() {
            @Override
            public void onSetData(String data) {
                int value = new BigDecimal(data).intValue();
                if(value < 0 || value > 60) {
                    ToastUtil.showShortToast(getContext(), R.string.between_light_control_delay);
                }else {
                    mWorkModeContentInfo.mLightControlDelay = value;
                    if (mWorkModeAdapter != null) {
                        mWorkModeAdapter.notifyDataSetChanged();
                    }

                    if (mLeScanner != null) {
                        mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_LIGHT_CONTROL_DELAY,
                                ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_LIGHT_CONTROL_DELAY_ADDR,value)));
                    }
                }
            }
        });
        setDataDialogFragment.show(getChildFragmentManager(), "");
    }

    private void showLoadCurrentDialog() {
        SetDataDialogFragment setDataDialogFragment = SetDataDialogFragment.newInstance(R.string.load_current, mWorkModeContentInfo.mLoadCurrent+"", "A");
        setDataDialogFragment.setOnSetDataListener(new SetDataDialogFragment.OnSetDataListener() {
            @Override
            public void onSetData(String data) {
                int value = new BigDecimal(data).intValue() / 10;
                mWorkModeContentInfo.mLoadCurrent = value * 10;
                if (mWorkModeAdapter != null) {
                    mWorkModeAdapter.notifyDataSetChanged();
                }

                if (mLeScanner != null) {
                    mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_LOAD_CURRENT,
                            ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_LOAD_CURRENT_ADDR,value)));
                }

            }
        });
        setDataDialogFragment.show(getChildFragmentManager(), "");
    }

    private void showOverChargeVoltageDialog() {
        SetDataDialogFragment setDataDialogFragment = SetDataDialogFragment.newInstance(R.string.over_charge_voltage, mWorkModeContentInfo.mOverchargeVoltage+"", "V");
        setDataDialogFragment.setOnSetDataListener(new SetDataDialogFragment.OnSetDataListener() {
            @Override
            public void onSetData(String data) {
                BigDecimal dataBD = new BigDecimal(data);
                if(dataBD.compareTo(new BigDecimal("0")) < 0 || dataBD.compareTo(new BigDecimal("60")) > 0) {
                    ToastUtil.showShortToast(getContext(), R.string.between_over_charge_voltage);
                }else {
                    mWorkModeContentInfo.mOverchargeVoltage = dataBD.setScale(2, BigDecimal.ROUND_DOWN).floatValue();
                    if (mWorkModeAdapter != null) {
                        mWorkModeAdapter.notifyDataSetChanged();
                    }
                    int value = dataBD.multiply(new BigDecimal("100")).intValue();
                    if (mLeScanner != null) {
                        mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_OVER_CHARGE_VOLTAGE,
                                ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_OVER_CHARGE_VOLTAGE_ADDR,value)));
                    }
                }
            }
        });
        setDataDialogFragment.show(getChildFragmentManager(), "");
    }

    private void showOverChargeReturnDialog() {
        SetDataDialogFragment setDataDialogFragment = SetDataDialogFragment.newInstance(R.string.over_charge_return, mWorkModeContentInfo.mOverchargeReturn+"", "V");
        setDataDialogFragment.setOnSetDataListener(new SetDataDialogFragment.OnSetDataListener() {
            @Override
            public void onSetData(String data) {
                BigDecimal dataBD = new BigDecimal(data);
                if(dataBD.compareTo(new BigDecimal("0")) < 0 || dataBD.compareTo(new BigDecimal("60")) > 0) {
                    ToastUtil.showShortToast(getContext(), R.string.between_over_charge_return);
                }else {
                    mWorkModeContentInfo.mOverchargeReturn = dataBD.setScale(2, BigDecimal.ROUND_DOWN).floatValue();
                    if (mWorkModeAdapter != null) {
                        mWorkModeAdapter.notifyDataSetChanged();
                    }
                    int value = dataBD.multiply(new BigDecimal("100")).intValue();
                    if (mLeScanner != null) {
                        mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_OVER_CHARGE_RETURN,
                                ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_OVER_CHARGE_RETURN_ADDR,value)));
                    }
                }
            }
        });
        setDataDialogFragment.show(getChildFragmentManager(), "");
    }

    private void showOverDisChargeReturnDialog() {
        SetDataDialogFragment setDataDialogFragment = SetDataDialogFragment.newInstance(R.string.over_discharge_return, mWorkModeContentInfo.mOverDischargeReturn+"", "V");
        setDataDialogFragment.setOnSetDataListener(new SetDataDialogFragment.OnSetDataListener() {
            @Override
            public void onSetData(String data) {
                BigDecimal dataBD = new BigDecimal(data);
                if(dataBD.compareTo(new BigDecimal("0")) < 0 || dataBD.compareTo(new BigDecimal("60")) > 0) {
                    ToastUtil.showShortToast(getContext(), R.string.between_over_discharge_return);
                }else {
                    mWorkModeContentInfo.mOverDischargeReturn = dataBD.setScale(2, BigDecimal.ROUND_DOWN).floatValue();
                    if (mWorkModeAdapter != null) {
                        mWorkModeAdapter.notifyDataSetChanged();
                    }
                    int value = dataBD.multiply(new BigDecimal("100")).intValue();
                    if (mLeScanner != null) {
                        mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_OVER_DISCHARGE_RETURN,
                                ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_OVER_DISCHARGE_RETURN_ADDR,value)));
                    }
                }
            }
        });
        setDataDialogFragment.show(getChildFragmentManager(), "");
    }

    private void showOverDisChargeVoltageDialog() {
        SetDataDialogFragment setDataDialogFragment = SetDataDialogFragment.newInstance(R.string.over_discharge_voltage, mWorkModeContentInfo.mOverDischargeVoltage+"", "V");
        setDataDialogFragment.setOnSetDataListener(new SetDataDialogFragment.OnSetDataListener() {
            @Override
            public void onSetData(String data) {
                BigDecimal dataBD = new BigDecimal(data);
                if(dataBD.compareTo(new BigDecimal("0")) < 0 || dataBD.compareTo(new BigDecimal("60")) > 0) {
                    ToastUtil.showShortToast(getContext(), R.string.between_over_discharge_voltage);
                }else {
                    mWorkModeContentInfo.mOverDischargeVoltage = dataBD.setScale(2, BigDecimal.ROUND_DOWN).floatValue();
                    if (mWorkModeAdapter != null) {
                        mWorkModeAdapter.notifyDataSetChanged();
                    }
                    int value = dataBD.multiply(new BigDecimal("100")).intValue();
                    if (mLeScanner != null) {
                        mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_OVER_DISCHARGE_VOLTAGE,
                                ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_OVER_DISCHARGE_VOLTAGE_ADDR,value)));
                    }
                }
            }
        });
        setDataDialogFragment.show(getChildFragmentManager(), "");
    }

    private void showTemperatureProtectionDialog() {
        SwitchButtonDialogFragment switchButtonDialogFragment = SwitchButtonDialogFragment.newInstance(R.string.temperature_protection,
                ShourigfData.STATUS_SWITCH_OPEN == mWorkModeContentInfo.mTemperatureProtection);
        switchButtonDialogFragment.setOnSetDataListener(new SwitchButtonDialogFragment.OnSwitchButtonListener() {
            @Override
            public void onSwitchButton(boolean value) {
                mWorkModeContentInfo.mTemperatureProtection = value ? ShourigfData.STATUS_SWITCH_OPEN : ShourigfData.STATUS_SWITCH_CLOSE;
                if (mWorkModeAdapter != null) {
                    mWorkModeAdapter.notifyDataSetChanged();
                }
                if (mLeScanner != null) {
                    mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_TEMPERATURE_PROTECTION,
                            ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_TEMPERATURE_PROTECTION_ADDR,
                                    value ? ShourigfData.STATUS_SWITCH_OPEN : ShourigfData.STATUS_SWITCH_CLOSE)));
                }
            }
        });
        switchButtonDialogFragment.show(getChildFragmentManager(), "");
    }

    private void showMusicSwitchDialog() {
        SwitchButtonDialogFragment switchButtonDialogFragment = SwitchButtonDialogFragment.newInstance(R.string.music_switch,
                ShourigfData.STATUS_SWITCH_OPEN == mWorkModeContentInfo.mMusicSwitch);
        switchButtonDialogFragment.setOnSetDataListener(new SwitchButtonDialogFragment.OnSwitchButtonListener() {
            @Override
            public void onSwitchButton(boolean value) {
                mWorkModeContentInfo.mMusicSwitch = value ? ShourigfData.STATUS_SWITCH_OPEN : ShourigfData.STATUS_SWITCH_CLOSE;
                if (mWorkModeAdapter != null) {
                    mWorkModeAdapter.notifyDataSetChanged();
                }
                if (mLeScanner != null) {
                    mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_MUSIC_SWITCH,
                            ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_MUSIC_SWITCH_ADDR,
                                    value ? ShourigfData.STATUS_SWITCH_OPEN : ShourigfData.STATUS_SWITCH_CLOSE)));
                }
            }
        });
        switchButtonDialogFragment.show(getChildFragmentManager(), "");
    }


    @OnClick({R.id.tv_work_mode_logout_administrator, R.id.tv_work_mode_tourist_model, R.id.tv_work_mode_refresh, R.id.tv_work_mode_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_work_mode_logout_administrator:
                Constants.IS_LOGIN = false;
                changeState();
                break;
            case R.id.tv_work_mode_tourist_model:
                if (!Constants.IS_LOGIN ) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, Constants.REQUEST_CODE_LOGIN);
                }
                break;
            case R.id.tv_work_mode_refresh:
                if (mLeScanner != null && mLeScanner.getDeviceId() != -1 && mLeScanner.getState() == LeScanner.STATE_CONNECTED) {
                    ToastUtil.showLongToast(getContext(), R.string.refresh_success);
                    mLeScanner.addFirstList(new ReadData(Constants.TYPE_READ_MODE,
                            ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.ReadMode.REG_ADDR, ShourigfData.ReadMode.READ_WORD)));
                    initReadData();
                    for (Fragment fragment : fragments) {
                        if (fragment instanceof BaseFragment) {
                            ((BaseFragment) fragment).initData();
                        }
                    }
                }else {
                    ToastUtil.showLongToast(getContext(), R.string.refresh_failed);
                }
                break;
            case R.id.tv_work_mode_setting:
                if (mLeScanner != null) {
                    int mode = 0;
                    switch (mViewPager.getCurrentItem()) {
                        case 0:
                            mode = ShourigfData.MODE_T;//T
                            break;
                        case 1:
                            mode = ShourigfData.MODE_M;//M
                            break;
                        case 2:
                            mode = ShourigfData.MODE_L;//L
                            break;
                        case 3:
                            mode = ShourigfData.MODE_U;//U
                            break;
                    }
                    mLeScanner.addLastList(new ReadData(Constants.TYPE_SET_MODE,
                            ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_MODE_ADDR, mode)));
                    boolean b = mLeScanner.setSettingList();
                    if (b) {
                        ToastUtil.showShortToast(getContext(), R.string.setting_success);
                    }else {
                        ToastUtil.showShortToast(getContext(), R.string.setting_failed);
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_LOGIN:
                if (resultCode == Activity.RESULT_OK){
                    changeState();
                }
                break;
        }
    }


    public void changeState() {
        if (Constants.IS_LOGIN) {
            mLogoutAdministratorTv.setVisibility(View.VISIBLE);
            mSettingTv.setVisibility(View.VISIBLE);
            mTouristModelTv.setText(R.string.click_mode_information_to_edit_and_modify);

        }else {
            mLogoutAdministratorTv.setVisibility(View.GONE);
            mSettingTv.setVisibility(View.GONE);
            mTouristModelTv.setText(R.string.work_mode_tourist_model);
        }
    }




}
