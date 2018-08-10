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
import com.shuorigf.bluetooth.streetlamp.adapter.SolarPanelInfoAdapter;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;
import com.shuorigf.bluetooth.streetlamp.ble.LeScanner;
import com.shuorigf.bluetooth.streetlamp.ble.ModbusData;
import com.shuorigf.bluetooth.streetlamp.ble.ShourigfData;
import com.shuorigf.bluetooth.streetlamp.data.ReadData;
import com.shuorigf.bluetooth.streetlamp.data.SolarPanelInfo;
import com.shuorigf.bluetooth.streetlamp.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * Created by clx on 2017/12/12.
 */

public class HomepageSolarPanelFragment extends BaseFragment {
    @BindView(R.id.rv_homepage_type_content)
    RecyclerView mContentRv;
    @BindView(R.id.tv_homepage_type_status)
    TextView mStatusTv;
    @BindArray(R.array.homepage_solar_panel_title)
    TypedArray mTitle;

    private SolarPanelInfo mSolarPanelInfo = new SolarPanelInfo();
    private SolarPanelInfoAdapter mSolarPanelInfoAdapter;

    public static HomepageSolarPanelFragment newInstance() {
        Bundle args = new Bundle();
        HomepageSolarPanelFragment fragment = new HomepageSolarPanelFragment();
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
        mSolarPanelInfoAdapter = new SolarPanelInfoAdapter(list, mSolarPanelInfo);
        mContentRv.setNestedScrollingEnabled(false);
        mContentRv.setItemViewCacheSize(list.size());
        mContentRv.setAdapter(mSolarPanelInfoAdapter);
        registerReceiver();
    }

    private void initReadData() {
        if ( mLeScanner!= null) {
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_SOLAR_PANEL,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.SolarPanelInfo.REG_ADDR, ShourigfData.SolarPanelInfo.READ_WORD)));
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_SOLAR_PANEL_STATUS,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.FaultAndWarnInfo.REG_ADDR, ShourigfData.FaultAndWarnInfo.READ_WORD)));
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
            case Constants.TYPE_SOLAR_PANEL:
                ShourigfData.SolarPanelInfo solarPanelInfo = new ShourigfData.SolarPanelInfo(data);
                mSolarPanelInfo.voltage = solarPanelInfo.mVoltage;
                mSolarPanelInfo.current = solarPanelInfo.mCurrent;
                mSolarPanelInfo.power = solarPanelInfo.mChargingPower;
                if (mSolarPanelInfoAdapter != null ) {
                    mSolarPanelInfoAdapter.notifyDataSetChanged();
                }
                break;

            case Constants.TYPE_SOLAR_PANEL_STATUS:
                ShourigfData.FaultAndWarnInfo faultAndWarnInfo = new ShourigfData.FaultAndWarnInfo(data);
                if (faultAndWarnInfo.mList.size() >= 15) {
                    if (faultAndWarnInfo.mList.get(12)) {
                        mStatusTv.setText(R.string.abnormal);
                        mStatusTv.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.textRed));
                    }else {
                        mStatusTv.setText(R.string.normal);
                        mStatusTv.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.textGreen));
                    }
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
