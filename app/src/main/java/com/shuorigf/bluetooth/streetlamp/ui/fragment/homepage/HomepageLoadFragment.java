package com.shuorigf.bluetooth.streetlamp.ui.fragment.homepage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.adapter.LoadInfoAdapter;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;
import com.shuorigf.bluetooth.streetlamp.ble.LeScanner;
import com.shuorigf.bluetooth.streetlamp.ble.ModbusData;
import com.shuorigf.bluetooth.streetlamp.ble.ShourigfData;
import com.shuorigf.bluetooth.streetlamp.data.LoadInfo;
import com.shuorigf.bluetooth.streetlamp.data.ReadData;
import com.shuorigf.bluetooth.streetlamp.util.Constants;
import com.shuorigf.bluetooth.streetlamp.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * Created by clx on 2017/12/13.
 */

public class HomepageLoadFragment extends BaseFragment {
    @BindView(R.id.rv_homepage_type_content)
    RecyclerView mContentRv;
    @BindView(R.id.tv_homepage_type_status)
    TextView mStatusTv;
    @BindView(R.id.homepage_type_radio_group)
    RadioGroup radioGroup;
    @BindArray(R.array.homepage_load_title)
    TypedArray mTitle;

    private LoadInfo mLoadInfo = new LoadInfo();
    private LoadInfoAdapter mLoadInfoAdapter;


    private int mLastCheckId;

    public static HomepageLoadFragment newInstance() {
        Bundle args = new Bundle();
        HomepageLoadFragment fragment = new HomepageLoadFragment();
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
        return R.layout.fragment_homepage_load;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i<mTitle.length(); i++) {
            list.add(mTitle.getResourceId(i, 0));
        }
        mLoadInfoAdapter = new LoadInfoAdapter(list, mLoadInfo);
        mContentRv.setNestedScrollingEnabled(false);
        mContentRv.setItemViewCacheSize(list.size());
        mContentRv.setAdapter(mLoadInfoAdapter);
        registerReceiver();
        mLastCheckId = radioGroup.getCheckedRadioButtonId();
    }

    private void initReadData() {
        if (mLeScanner!= null) {
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_LOAD,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.LoadInfo.REG_ADDR, ShourigfData.LoadInfo.READ_WORD)));
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_LOAD_STATUS,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.FaultAndWarnInfo.REG_ADDR, ShourigfData.FaultAndWarnInfo.READ_WORD)));
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_LAMP_BRIGHTNESS,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.BatteryState.REG_ADDR, ShourigfData.BatteryState.READ_WORD)));
        }
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
            case Constants.TYPE_LOAD:
                ShourigfData.LoadInfo loadInfo = new ShourigfData.LoadInfo(data);
                mLoadInfo.voltage = loadInfo.mVoltage;
                mLoadInfo.current = loadInfo.mCurrent;
                mLoadInfo.power = loadInfo.mLoadPower;
                if (mLoadInfoAdapter != null) {
                    mLoadInfoAdapter.notifyDataSetChanged();
                }
                break;
            case Constants.TYPE_LOAD_STATUS:
                ShourigfData.FaultAndWarnInfo faultAndWarnInfo = new ShourigfData.FaultAndWarnInfo(data);
                if (faultAndWarnInfo.mList.size() >= 15) {
                    if (faultAndWarnInfo.mList.get(3)) {
                        mStatusTv.setText(R.string.abnormal);
                        mStatusTv.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.textRed));
                    }else {
                        mStatusTv.setText(R.string.normal);
                        mStatusTv.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.textGreen));
                    }
                }
                break;
            case Constants.TYPE_LAMP_BRIGHTNESS:
                ShourigfData.BatteryState batteryState = new ShourigfData.BatteryState(data);
                mLoadInfo.brightness = batteryState.mLampBrightness;
                if (mLoadInfoAdapter != null) {
                    mLoadInfoAdapter.notifyDataSetChanged();
                }
                if(batteryState.mLampBrightness == 0) {
                    radioGroup.check(R.id.rb_homepage_type_zero_percent);
                }else if(batteryState.mLampBrightness <= 30) {
                    radioGroup.check(R.id.rb_homepage_type_thirty_percent);
                }else if(batteryState.mLampBrightness <= 50) {
                    radioGroup.check(R.id.rb_homepage_type_fifty_percent);
                }else if(batteryState.mLampBrightness <= 70) {
                    radioGroup.check(R.id.rb_homepage_type_seventy_percent);
                }else if(batteryState.mLampBrightness <= 100) {
                    radioGroup.check(R.id.rb_homepage_type_one_hundred_percent);
                }
                mLastCheckId = radioGroup.getCheckedRadioButtonId();
                break;
            case Constants.TYPE_SET_LAMP_BRIGHTNESS:
                if (ModbusData.writeSigRegSuccess(data)) {
                    ToastUtil.showShortToast(getContext(), R.string.test_success);
                    mLastCheckId = radioGroup.getCheckedRadioButtonId();
                    switch (mLastCheckId) {
                        case R.id.rb_homepage_type_zero_percent:
                            mLoadInfo.brightness = 0;
                            break;
                        case R.id.rb_homepage_type_thirty_percent:
                            mLoadInfo.brightness = 30;
                            break;
                        case R.id.rb_homepage_type_fifty_percent:
                            mLoadInfo.brightness = 50;
                            break;
                        case R.id.rb_homepage_type_seventy_percent:
                            mLoadInfo.brightness = 70;
                            break;
                        case R.id.rb_homepage_type_one_hundred_percent:
                            mLoadInfo.brightness = 100;
                            break;
                    }

                    if (mLoadInfoAdapter != null) {
                        mLoadInfoAdapter.notifyDataSetChanged();
                    }
                }else {
                    ToastUtil.showShortToast(getContext(), R.string.test_failed);
                    radioGroup.check(mLastCheckId);
                }
                break;
        }

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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                View viewById = radioGroup.findViewById(checkedId);
                if (!viewById.isPressed()){
                    return;
                }
                int data = -1;
                switch (checkedId) {
                    case R.id.rb_homepage_type_zero_percent:
                        data = 0;
                        break;
                    case R.id.rb_homepage_type_thirty_percent:
                        data = 30;
                        break;
                    case R.id.rb_homepage_type_fifty_percent:
                        data = 50;
                        break;
                    case R.id.rb_homepage_type_seventy_percent:
                        data = 70;
                        break;
                    case R.id.rb_homepage_type_one_hundred_percent:
                        data = 100;
                        break;
                }
                if (data != -1) {
                    if (mLeScanner != null) {
                        boolean b =  mLeScanner.addLastList(new ReadData(Constants.TYPE_SET_LAMP_BRIGHTNESS,
                                ModbusData.buildWriteRegCmd(mLeScanner.getDeviceId(), ShourigfData.REG_LAMP_BRIGHTNESS_ADDR, data)));
                        if (!b) {
                            ToastUtil.showShortToast(getContext(), R.string.test_failed);
                            radioGroup.check(mLastCheckId);
                        }
                    }
                }

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mGattReceiver);
    }
}
