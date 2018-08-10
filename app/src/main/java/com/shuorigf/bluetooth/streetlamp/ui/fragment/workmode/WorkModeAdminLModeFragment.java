package com.shuorigf.bluetooth.streetlamp.ui.fragment.workmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.adapter.FirstSecondAdapter;
import com.shuorigf.bluetooth.streetlamp.adapter.itemdecoration.GridOffsetsItemDecoration;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;
import com.shuorigf.bluetooth.streetlamp.ble.LeScanner;
import com.shuorigf.bluetooth.streetlamp.ble.ModbusData;
import com.shuorigf.bluetooth.streetlamp.ble.ShourigfData;
import com.shuorigf.bluetooth.streetlamp.data.FirstSecondInfo;
import com.shuorigf.bluetooth.streetlamp.data.ReadData;
import com.shuorigf.bluetooth.streetlamp.util.Constants;
import com.shuorigf.bluetooth.streetlamp.util.ConvertUtils;
import com.shuorigf.bluetooth.streetlamp.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * Created by clx on 2017/12/16.
 */

public class WorkModeAdminLModeFragment extends BaseFragment {

    @BindView(R.id.rv_work_mode_admin_type_first)
    RecyclerView mFirstRv;
    @BindView(R.id.rv_work_mode_admin_type_second)
    RecyclerView mSecondRv;
    @BindView(R.id.rv_work_mode_admin_type_three)
    RecyclerView mThreeRv;
    @BindView(R.id.rv_work_mode_admin_type_four)
    RecyclerView mFourRv;
    @BindArray(R.array.l_first_title)
    TypedArray mFirstTitle;
    @BindArray(R.array.l_second_title)
    TypedArray mSecondTitle;
    @BindArray(R.array.l_three_title)
    TypedArray mThreeTitle;
    @BindArray(R.array.l_four_title)
    TypedArray mFourTitle;
    @BindArray(R.array.color_temperature_title)
    TypedArray mColorTemperatureTitle;

    private FirstSecondInfo mFirstSecondInfo = new FirstSecondInfo();
    private FirstSecondAdapter mFirstAdapter;
    private FirstSecondAdapter mSecondAdapter;
    private FirstSecondAdapter mThreeAdapter;
    private FirstSecondAdapter mFourAdapter;

    private List<Integer> mPowerList = new ArrayList<>();


    public static WorkModeAdminLModeFragment newInstance() {
        Bundle args = new Bundle();
        WorkModeAdminLModeFragment fragment = new WorkModeAdminLModeFragment();
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
        return R.layout.fragment_work_mode_admin_type;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {
        initPvData();
        initFirst();
        initSecond();
        initThree();
        initFour();
        registerReceiver();
    }

    private void initPvData() {
        for(int i = 0 ; i <= 10 ; i ++) {
            mPowerList.add(i * 10);
        }
    }


    private void initReadData() {
        if ( mLeScanner!= null) {
            clearSettingData();
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_L_MODE_FIRST_SECOND,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.LModeInfo.REG_ADDR, ShourigfData.LModeInfo.READ_WORD)));
        }
    }

    private void clearSettingData() {
        mLeScanner.clearSettingList(Constants.TYPE_SET_L_FIRST_TIME);
        mLeScanner.clearSettingList(Constants.TYPE_SET_L_FIRST_POWER);
        mLeScanner.clearSettingList(Constants.TYPE_SET_L_FIRST_COLOR_TEMPERATURE);
        mLeScanner.clearSettingList(Constants.TYPE_SET_L_SECOND_TIME);
        mLeScanner.clearSettingList(Constants.TYPE_SET_L_SECOND_POWER);
        mLeScanner.clearSettingList(Constants.TYPE_SET_L_SECOND_COLOR_TEMPERATURE);
        mLeScanner.clearSettingList(Constants.TYPE_SET_L_THREE_TIME);
        mLeScanner.clearSettingList(Constants.TYPE_SET_L_THREE_POWER);
        mLeScanner.clearSettingList(Constants.TYPE_SET_L_THREE_COLOR_TEMPERATURE);
        mLeScanner.clearSettingList(Constants.TYPE_SET_L_FOUR_TIME);
        mLeScanner.clearSettingList(Constants.TYPE_SET_L_FOUR_POWER);
        mLeScanner.clearSettingList(Constants.TYPE_SET_L_FOUR_COLOR_TEMPERATURE);
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
            }else if (LeScanner.ACTION_DATA_AVAILABLE.equals(action)) {
                receiveData(intent.getIntExtra(LeScanner.EXTRA_TYPE, 0), intent.getByteArrayExtra(LeScanner.EXTRA_DATA));
            }
        }
    };

    private void receiveData(int type, byte[] data) {
        switch (type) {
            case Constants.TYPE_L_MODE_FIRST_SECOND:
                ShourigfData.LModeInfo lModeInfo = new ShourigfData.LModeInfo(data);
                mFirstSecondInfo.mFirstTime = lModeInfo.mFirstTime;
                mFirstSecondInfo.mFirstPower = lModeInfo.mFirstPower;
                mFirstSecondInfo.mFirstColorTemperature = lModeInfo.mFirstColorTemperature;
                mFirstSecondInfo.mSecondTime = lModeInfo.mSecondTime;
                mFirstSecondInfo.mSecondPower = lModeInfo.mSecondPower;
                mFirstSecondInfo.mSecondColorTemperature = lModeInfo.mSecondColorTemperature;
                mFirstSecondInfo.mThreeTime = lModeInfo.mThreeTime;
                mFirstSecondInfo.mThreePower = lModeInfo.mThreePower;
                mFirstSecondInfo.mThreeColorTemperature = lModeInfo.mThreeColorTemperature;
                mFirstSecondInfo.mFourTime = lModeInfo.mFourTime;
                mFirstSecondInfo.mFourPower = lModeInfo.mFourPower;
                mFirstSecondInfo.mFourColorTemperature = lModeInfo.mFourColorTemperature;
                break;

        }
        if (mFirstAdapter != null ) {
            mFirstAdapter.notifyDataSetChanged();
        }
        if (mSecondAdapter != null ) {
            mSecondAdapter.notifyDataSetChanged();
        }
        if (mThreeAdapter != null ) {
            mThreeAdapter.notifyDataSetChanged();
        }
        if (mFourAdapter != null ) {
            mFourAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mGattReceiver);
    }

    /**
     * init data
     */
    @Override
    public void initData() {
        super.initData();
        initReadData();
    }

    private void initFirst() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i<mFirstTitle.length(); i++) {
            list.add(mFirstTitle.getResourceId(i, 0));
        }
        mFirstAdapter = new FirstSecondAdapter(list, mFirstSecondInfo);
        GridOffsetsItemDecoration gridOffsetsItemDecoration = new GridOffsetsItemDecoration(GridOffsetsItemDecoration.GRID_OFFSETS_VERTICAL);
        gridOffsetsItemDecoration.setVerticalItemOffsets(ConvertUtils.dp2px(getContext(), 10));
        mFirstRv.addItemDecoration(gridOffsetsItemDecoration);
        mFirstRv.setNestedScrollingEnabled(false);
        mFirstRv.setItemViewCacheSize(list.size());
        mFirstRv.setAdapter(mFirstAdapter);
    }

    private void initSecond() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i<mSecondTitle.length(); i++) {
            list.add(mSecondTitle.getResourceId(i, 0));
        }
        mSecondAdapter = new FirstSecondAdapter(list, mFirstSecondInfo);
        GridOffsetsItemDecoration gridOffsetsItemDecoration = new GridOffsetsItemDecoration(GridOffsetsItemDecoration.GRID_OFFSETS_VERTICAL);
        gridOffsetsItemDecoration.setVerticalItemOffsets(ConvertUtils.dp2px(getContext(), 10));
        mSecondRv.addItemDecoration(gridOffsetsItemDecoration);
        mSecondRv.setNestedScrollingEnabled(false);
        mSecondRv.setItemViewCacheSize(list.size());
        mSecondRv.setAdapter(mSecondAdapter);
    }

    private void initThree() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i<mThreeTitle.length(); i++) {
            list.add(mThreeTitle.getResourceId(i, 0));
        }
        mThreeAdapter = new FirstSecondAdapter(list, mFirstSecondInfo);
        GridOffsetsItemDecoration gridOffsetsItemDecoration = new GridOffsetsItemDecoration(GridOffsetsItemDecoration.GRID_OFFSETS_VERTICAL);
        gridOffsetsItemDecoration.setVerticalItemOffsets(ConvertUtils.dp2px(getContext(), 10));
        mThreeRv.addItemDecoration(gridOffsetsItemDecoration);
        mThreeRv.setNestedScrollingEnabled(false);
        mThreeRv.setItemViewCacheSize(list.size());
        mThreeRv.setAdapter(mThreeAdapter);
    }

    private void initFour() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i<mFourTitle.length(); i++) {
            list.add(mFourTitle.getResourceId(i, 0));
        }
        mFourAdapter = new FirstSecondAdapter(list, mFirstSecondInfo);
        GridOffsetsItemDecoration gridOffsetsItemDecoration = new GridOffsetsItemDecoration(GridOffsetsItemDecoration.GRID_OFFSETS_VERTICAL);
        gridOffsetsItemDecoration.setVerticalItemOffsets(ConvertUtils.dp2px(getContext(), 10));
        mFourRv.addItemDecoration(gridOffsetsItemDecoration);
        mFourRv.setNestedScrollingEnabled(false);
        mFourRv.setItemViewCacheSize(list.size());
        mFourRv.setAdapter(mFourAdapter);
    }


    /**
     * init event
     */
    @Override
    protected void initEvent() {
        mFirstAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!Constants.IS_LOGIN) {
                    ToastUtil.showShortToast(getContext(), R.string.hint_login_first);
                    return;
                }
                switch (mFirstTitle.getResourceId(position, 0)) {
                    case R.string.first_time_colon:
                        showTimePv(R.string.first_time_colon);
                        break;
                    case R.string.first_power_colon:
                        showPowerPv(R.string.first_power_colon);
                        break;
                    case R.string.first_color_temperature_colon:
                        showColorTemperaturePv(R.string.first_color_temperature_colon);
                        break;

                }
            }
        });

        mSecondAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!Constants.IS_LOGIN) {
                    ToastUtil.showShortToast(getContext(), R.string.hint_login_first);
                    return;
                }
                switch (mSecondTitle.getResourceId(position, 0)) {
                    case R.string.second_time_colon:
                        showTimePv(R.string.second_time_colon);
                        break;
                    case R.string.second_power_colon:
                        showPowerPv(R.string.second_power_colon);
                        break;
                    case R.string.second_color_temperature_colon:
                        showColorTemperaturePv(R.string.second_color_temperature_colon);
                        break;

                }
            }
        });

        mThreeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!Constants.IS_LOGIN) {
                    ToastUtil.showShortToast(getContext(), R.string.hint_login_first);
                    return;
                }
                switch (mThreeTitle.getResourceId(position, 0)) {
                    case R.string.three_time_colon:
                        showTimePv(R.string.three_time_colon);
                        break;
                    case R.string.three_power_colon:
                        showPowerPv(R.string.three_power_colon);
                        break;
                    case R.string.three_color_temperature_colon:
                        showColorTemperaturePv(R.string.three_color_temperature_colon);
                        break;

                }
            }
        });

        mFourAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!Constants.IS_LOGIN) {
                    ToastUtil.showShortToast(getContext(), R.string.hint_login_first);
                    return;
                }
                switch (mFourTitle.getResourceId(position, 0)) {
                    case R.string.four_time_colon:
                        showTimePv(R.string.four_time_colon);
                        break;
                    case R.string.four_power_colon:
                        showPowerPv(R.string.four_power_colon);
                        break;
                    case R.string.four_color_temperature_colon:
                        showColorTemperaturePv(R.string.four_color_temperature_colon);
                        break;

                }
            }
        });
    }


    private void showColorTemperaturePv(final int res) {
        OptionsPickerView mColorTemperaturePv = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                int data = -1;
                switch (mColorTemperatureTitle.getResourceId(options1, 0)) {
                    case R.string.automatic:
                        data = ShourigfData.STATUS_COLOR_TEMPERATURE_AUTOMATIC;
                        break;
                    case R.string.cool_white:
                        data = ShourigfData.STATUS_COLOR_TEMPERATURE_COOL_WHITE;
                        break;
                    case R.string.natural_white:
                        data = ShourigfData.STATUS_COLOR_TEMPERATURE_NATURAL_WHITE;
                        break;
                    case R.string.warm_white:
                        data = ShourigfData.STATUS_COLOR_TEMPERATURE_WARM_WHITE;
                        break;
                }

                if (data != -1) {
                    switch (res) {
                        case R.string.first_color_temperature_colon:
                            mFirstSecondInfo.mFirstColorTemperature = data;
                            if (mFirstAdapter != null) {
                                mFirstAdapter.notifyDataSetChanged();
                            }
                            if (mLeScanner != null) {
                                mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_L_FIRST_COLOR_TEMPERATURE,
                                        ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_L_FIRST_COLOR_TEMPERATURE_ADDR, data)));
                            }
                            break;
                        case R.string.second_color_temperature_colon:
                            mFirstSecondInfo.mSecondColorTemperature = data;
                            if (mSecondAdapter != null) {
                                mSecondAdapter.notifyDataSetChanged();
                            }
                            if (mLeScanner != null) {
                                mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_L_SECOND_COLOR_TEMPERATURE,
                                        ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_L_SECOND_COLOR_TEMPERATURE_ADDR, data)));
                            }
                            break;
                        case R.string.three_color_temperature_colon:
                            mFirstSecondInfo.mThreeColorTemperature = data;
                            if (mThreeAdapter != null) {
                                mThreeAdapter.notifyDataSetChanged();
                            }
                            if (mLeScanner != null) {
                                mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_L_THREE_COLOR_TEMPERATURE,
                                        ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_L_THREE_COLOR_TEMPERATURE_ADDR, data)));
                            }
                            break;
                        case R.string.four_color_temperature_colon:
                            mFirstSecondInfo.mFourColorTemperature = data;
                            if (mFourAdapter != null) {
                                mFourAdapter.notifyDataSetChanged();
                            }
                            if (mLeScanner != null) {
                                mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_L_FOUR_COLOR_TEMPERATURE,
                                        ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_L_FOUR_COLOR_TEMPERATURE_ADDR, data)));
                            }
                            break;
                    }

                }

            }
        })
                .setSubCalSize(15)//确定和取消文字大小
                .setSubmitColor(ContextCompat.getColor(getContext(), R.color.white))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(getContext(), R.color.white))//取消按钮文字颜色
                .setTitleColor(ContextCompat.getColor(getContext(), R.color.black))
                .setTitleText(getString(res))
                .setTitleBgColor(ContextCompat.getColor(getContext(), R.color.textBlue))//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setCyclic(false, false, false)//循环与否
                .build();
        List<String> mColorLightSourceList = new ArrayList<>();
        for (int i = 0; i < mColorTemperatureTitle.length(); i++) {
            mColorLightSourceList.add(mColorTemperatureTitle.getString(i));
        }

        mColorTemperaturePv.setPicker(mColorLightSourceList);//添加数据源
        mColorTemperaturePv.show();

    }


    private void showTimePv(final int res) {
        OptionsPickerView mTimePv = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                int value = options1 * 60 + options2;
                    switch (res) {
                        case R.string.first_time_colon:
                            mFirstSecondInfo.mFirstTime = value;
                            if (mFirstAdapter != null) {
                                mFirstAdapter.notifyDataSetChanged();
                            }
                            if (mLeScanner != null) {
                                mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_L_FIRST_TIME,
                                        ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_L_FIRST_TIME_ADDR, value)));
                            }
                            break;
                        case R.string.second_time_colon:
                            mFirstSecondInfo.mSecondTime = value;
                            if (mSecondAdapter != null) {
                                mSecondAdapter.notifyDataSetChanged();
                            }
                            if (mLeScanner != null) {
                                mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_L_SECOND_TIME,
                                        ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_L_SECOND_TIME_ADDR, value)));
                            }
                            break;
                        case R.string.three_time_colon:
                            mFirstSecondInfo.mThreeTime = value;
                            if (mThreeAdapter != null) {
                                mThreeAdapter.notifyDataSetChanged();
                            }
                            if (mLeScanner != null) {
                                mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_L_THREE_TIME,
                                        ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_L_THREE_TIME_ADDR, value)));
                            }
                            break;
                        case R.string.four_time_colon:
                            mFirstSecondInfo.mFourTime = value;
                            if (mFourAdapter != null) {
                                mFourAdapter.notifyDataSetChanged();
                            }
                            if (mLeScanner != null) {
                                mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_L_FOUR_TIME,
                                        ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_L_FOUR_TIME_ADDR, value)));
                            }
                            break;
                    }



            }
        })
                .setSubCalSize(15)//确定和取消文字大小
                .setSubmitColor(ContextCompat.getColor(getContext(), R.color.white))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(getContext(), R.color.white))//取消按钮文字颜色
                .setTitleColor(ContextCompat.getColor(getContext(), R.color.black))
                .setTitleText(getString(res))
                .setTitleBgColor(ContextCompat.getColor(getContext(), R.color.textBlue))//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setCyclic(false, false, false)//循环与否
                .build();

        List<String> mHTimeList = new ArrayList<>();
        List<List<String>> mMinTimeList = new ArrayList<>();
        for(int i = 0 ; i < 15 ; i ++) {
            mHTimeList.add(i + "h");
        }
        for (int i = 0; i < mHTimeList.size(); i++) {
            List<String> temp = new ArrayList<>();
            for(int j = 0 ; j < 60 ; j ++) {
                temp.add(j+"min");
            }
            mMinTimeList.add(temp);
        }

        mTimePv.setPicker(mHTimeList, mMinTimeList);//添加数据源
        mTimePv.show();

    }


    private void showPowerPv(final int res) {
        OptionsPickerView mPowerPv = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                int value = mPowerList.get(options1);
                switch (res) {
                    case R.string.first_power_colon:
                        mFirstSecondInfo.mFirstPower = value;
                        if (mFirstAdapter != null) {
                            mFirstAdapter.notifyDataSetChanged();
                        }
                        if (mLeScanner != null) {
                            mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_L_FIRST_POWER,
                                    ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_L_FIRST_POWER_ADDR, value)));
                        }
                        break;
                    case R.string.second_power_colon:
                        mFirstSecondInfo.mSecondPower = value;
                        if (mSecondAdapter != null) {
                            mSecondAdapter.notifyDataSetChanged();
                        }
                        if (mLeScanner != null) {
                            mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_L_SECOND_POWER,
                                    ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_L_SECOND_POWER_ADDR, value)));
                        }
                        break;
                    case R.string.three_power_colon:
                        mFirstSecondInfo.mThreePower = value;
                        if (mThreeAdapter != null) {
                            mThreeAdapter.notifyDataSetChanged();
                        }
                        if (mLeScanner != null) {
                            mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_L_THREE_POWER,
                                    ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_L_THREE_POWER_ADDR, value)));
                        }
                        break;
                    case R.string.four_power_colon:
                        mFirstSecondInfo.mFourPower = value;
                        if (mFourAdapter != null) {
                            mFourAdapter.notifyDataSetChanged();
                        }
                        if (mLeScanner != null) {
                            mLeScanner.addSettingList(new ReadData(Constants.TYPE_SET_L_FOUR_POWER,
                                    ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_L_FOUR_POWER_ADDR, value)));
                        }
                        break;
                }



            }
        })
                .setSubCalSize(15)//确定和取消文字大小
                .setSubmitColor(ContextCompat.getColor(getContext(), R.color.white))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(getContext(), R.color.white))//取消按钮文字颜色
                .setTitleColor(ContextCompat.getColor(getContext(), R.color.black))
                .setTitleText(getString(res))
                .setTitleBgColor(ContextCompat.getColor(getContext(), R.color.textBlue))//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setCyclic(false, false, false)//循环与否
                .build();

        List<String> mList = new ArrayList<>();
        for(int i = 0 ; i < mPowerList.size() ; i ++) {
            mList.add(mPowerList.get(i) + "%");
        }


        mPowerPv.setPicker(mList);//添加数据源
        mPowerPv.show();

    }

}
