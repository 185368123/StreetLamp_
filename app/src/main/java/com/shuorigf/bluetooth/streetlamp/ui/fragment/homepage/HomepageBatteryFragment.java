package com.shuorigf.bluetooth.streetlamp.ui.fragment.homepage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.adapter.BatteryInfoAdapter;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;
import com.shuorigf.bluetooth.streetlamp.ble.LeScanner;
import com.shuorigf.bluetooth.streetlamp.ble.ModbusData;
import com.shuorigf.bluetooth.streetlamp.ble.ShourigfData;
import com.shuorigf.bluetooth.streetlamp.data.BatteryInfo;
import com.shuorigf.bluetooth.streetlamp.data.ReadData;
import com.shuorigf.bluetooth.streetlamp.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * Created by clx on 2017/12/13.
 */

public class HomepageBatteryFragment extends BaseFragment {
    @BindView(R.id.rv_homepage_type_content)
    RecyclerView mContentRv;
    @BindView(R.id.tv_homepage_type_status)
    TextView mStatusTv;
    @BindArray(R.array.homepage_battery_title)
    TypedArray mTitle;

    private BatteryInfo mBatteryInfo = new BatteryInfo();
    private BatteryInfoAdapter mBatteryInfoAdapter;

    public static HomepageBatteryFragment newInstance() {
        Bundle args = new Bundle();
        HomepageBatteryFragment fragment = new HomepageBatteryFragment();
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
        return R.layout.fragment_homepage_type;
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
        mBatteryInfoAdapter = new BatteryInfoAdapter(list, mBatteryInfo);
        mContentRv.setNestedScrollingEnabled(false);
        mContentRv.setItemViewCacheSize(list.size());
        mContentRv.setAdapter(mBatteryInfoAdapter);
        registerReceiver();
    }

    private void initReadData() {
        if (mLeScanner!= null) {
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_BATTERY,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.BatteryInfo.REG_ADDR, ShourigfData.BatteryInfo.READ_WORD)));
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_BATTERY_STATUS,
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
            case Constants.TYPE_BATTERY:
                ShourigfData.BatteryInfo batteryInfo = new ShourigfData.BatteryInfo(data);
                mBatteryInfo.voltage = batteryInfo.mVoltage;
                mBatteryInfo.current = batteryInfo.mCurrent;
                mBatteryInfo.electricity = batteryInfo.mElectricity;
                mBatteryInfo.temperature = batteryInfo.mBatteryTemperature;
                if (mBatteryInfoAdapter != null ) {
                    mBatteryInfoAdapter.notifyDataSetChanged();
                }
                break;
            case Constants.TYPE_BATTERY_STATUS:
                ShourigfData.BatteryState batteryState = new ShourigfData.BatteryState(data);
                switch (batteryState.mBatteryState) {
                    case ShourigfData.STATUS_BATTERY_ABNORMAL:
                        mStatusTv.setText(R.string.abnormal);
                        mStatusTv.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.textRed));
                        break;
                    case ShourigfData.STATUS_BATTERY_NORMAL:
                        mStatusTv.setText(R.string.normal);
                        mStatusTv.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.textGreen));
                        break;
                    case ShourigfData.STATUS_BATTERY_DISCHARGING:
                        mStatusTv.setText(R.string.discharging);
                        mStatusTv.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.textGreen));
                        break;
                    default:
                        mStatusTv.setText(R.string.charging);
                        mStatusTv.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.textGreen));
                        break;
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

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mGattReceiver);
    }
}
