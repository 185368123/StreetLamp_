package com.shuorigf.bluetooth.streetlamp.ui.fragment.workmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.adapter.FirstSecondAdapter;
import com.shuorigf.bluetooth.streetlamp.adapter.WorkModeAdapter;
import com.shuorigf.bluetooth.streetlamp.adapter.itemdecoration.GridOffsetsItemDecoration;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;
import com.shuorigf.bluetooth.streetlamp.ble.LeScanner;
import com.shuorigf.bluetooth.streetlamp.ble.ModbusData;
import com.shuorigf.bluetooth.streetlamp.ble.ShourigfData;
import com.shuorigf.bluetooth.streetlamp.data.FirstSecondInfo;
import com.shuorigf.bluetooth.streetlamp.data.IconText;
import com.shuorigf.bluetooth.streetlamp.data.ReadData;
import com.shuorigf.bluetooth.streetlamp.data.WorkModeContentInfo;
import com.shuorigf.bluetooth.streetlamp.util.Constants;
import com.shuorigf.bluetooth.streetlamp.util.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * Created by clx on 2017/11/6.
 */

public class WorkModeNonAdminFragment extends BaseFragment {

    @BindView(R.id.rv_work_mode_non_admin_content)
    RecyclerView mContentRv;
    @BindView(R.id.rv_work_mode_non_admin_first_second)
    RecyclerView mFirstSecondRv;
    @BindView(R.id.rv_work_mode_non_admin_three_four)
    RecyclerView mThreeFourRv;
    @BindArray(R.array.l_first_title)
    TypedArray mFirstSecondTitle;
    @BindArray(R.array.l_three_title)
    TypedArray mThreeFourTitle;
    @BindArray(R.array.work_mode_title)
    TypedArray mTitle;
    @BindArray(R.array.work_mode_icon)
    TypedArray mIcon;

    private FirstSecondInfo mFirstSecondInfo = new FirstSecondInfo();
    private WorkModeContentInfo mWorkModeContentInfo = new WorkModeContentInfo();
    private FirstSecondAdapter mFirstSecondAdapter;
    private FirstSecondAdapter mThreeFourAdapter;
    private WorkModeAdapter mWorkModeAdapter;

    public static WorkModeNonAdminFragment newInstance() {
        Bundle args = new Bundle();
        WorkModeNonAdminFragment fragment = new WorkModeNonAdminFragment();
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
        return R.layout.fragment_work_node_non_admin;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {
        initFirstSecond();
        initThreeFour();
        initContent();
        registerReceiver();
    }

    private void initReadData() {
        if (mLeScanner!= null) {
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_WORK_MODE_CONTENT,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.WorkModeContentInfo.REG_ADDR, ShourigfData.WorkModeContentInfo.READ_WORD)));
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_OVER,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.OverInfo.REG_ADDR, ShourigfData.OverInfo.READ_WORD)));
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_L_MODE_FIRST_SECOND,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.LModeInfo.REG_ADDR, ShourigfData.LModeInfo.READ_WORD)));

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
                if (mFirstSecondAdapter != null ) {
                    mFirstSecondAdapter.notifyDataSetChanged();
                }
                if (mThreeFourAdapter != null ) {
                    mThreeFourAdapter.notifyDataSetChanged();
                }
                break;
            case Constants.TYPE_OVER:
                ShourigfData.OverInfo overInfo = new ShourigfData.OverInfo(data);
                mWorkModeContentInfo.mOverchargeVoltage = overInfo.mOverchargeVoltage;
                mWorkModeContentInfo.mOverchargeReturn = overInfo.mOverchargeReturn;
                mWorkModeContentInfo.mOverDischargeReturn = overInfo.mOverDischargeReturn;
                mWorkModeContentInfo.mOverDischargeVoltage = overInfo.mOverDischargeVoltage;
                if (mWorkModeAdapter != null ) {
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
                if (mWorkModeAdapter != null ) {
                    mWorkModeAdapter.notifyDataSetChanged();
                }
                break;
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initReadData();
        }
    }

    private void initFirstSecond() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i<mFirstSecondTitle.length(); i++) {
            list.add(mFirstSecondTitle.getResourceId(i, 0));
        }
        mFirstSecondAdapter = new FirstSecondAdapter(list, mFirstSecondInfo);
        GridOffsetsItemDecoration gridOffsetsItemDecoration = new GridOffsetsItemDecoration(GridOffsetsItemDecoration.GRID_OFFSETS_VERTICAL);
        gridOffsetsItemDecoration.setVerticalItemOffsets(ConvertUtils.dp2px(getContext(), 10));
        mFirstSecondRv.addItemDecoration(gridOffsetsItemDecoration);
        mFirstSecondRv.setNestedScrollingEnabled(false);
        mFirstSecondRv.setItemViewCacheSize(list.size());
        mFirstSecondRv.setAdapter(mFirstSecondAdapter);
    }

    private void initThreeFour() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i<mThreeFourTitle.length(); i++) {
            list.add(mThreeFourTitle.getResourceId(i, 0));
        }
        mThreeFourAdapter = new FirstSecondAdapter(list, mFirstSecondInfo);
        GridOffsetsItemDecoration gridOffsetsItemDecoration = new GridOffsetsItemDecoration(GridOffsetsItemDecoration.GRID_OFFSETS_VERTICAL);
        gridOffsetsItemDecoration.setVerticalItemOffsets(ConvertUtils.dp2px(getContext(), 10));
        mThreeFourRv.addItemDecoration(gridOffsetsItemDecoration);
        mThreeFourRv.setNestedScrollingEnabled(false);
        mThreeFourRv.setItemViewCacheSize(list.size());
        mThreeFourRv.setAdapter(mThreeFourAdapter);
    }

    private void initContent() {
        List<IconText> list = new ArrayList<>();
        for (int i = 0; i<mTitle.length(); i++) {
            list.add(new IconText(mTitle.getResourceId(i, 0), mIcon.getResourceId(i, 0)));
        }
        mWorkModeAdapter = new WorkModeAdapter(list, mWorkModeContentInfo, false);
        mContentRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mContentRv.setNestedScrollingEnabled(false);
        mContentRv.setItemViewCacheSize(list.size());
        mContentRv.setAdapter(mWorkModeAdapter);
    }

    /**
     * init event
     */
    @Override
    protected void initEvent() {

    }

}
