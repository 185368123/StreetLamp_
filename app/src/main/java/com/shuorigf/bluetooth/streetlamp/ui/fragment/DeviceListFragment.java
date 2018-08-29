package com.shuorigf.bluetooth.streetlamp.ui.fragment;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.adapter.DeviceListAdapter;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;
import com.shuorigf.bluetooth.streetlamp.base.MessageDialogFragment;
import com.shuorigf.bluetooth.streetlamp.ble.LeScanner;
import com.shuorigf.bluetooth.streetlamp.ble.MyLeScanCallback;
import com.shuorigf.bluetooth.streetlamp.data.BleDeviceInfo;
import com.shuorigf.bluetooth.streetlamp.ui.activity.DeviceInfoActivity;
import com.shuorigf.bluetooth.streetlamp.util.Constants;
import com.shuorigf.bluetooth.streetlamp.util.GsonUtil;
import com.shuorigf.bluetooth.streetlamp.util.LogTools;
import com.shuorigf.bluetooth.streetlamp.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by clx on 2017/11/21.
 */

public class DeviceListFragment extends BaseFragment {
    private final static String TAG = DeviceListFragment.class.getSimpleName();
    @BindView(R.id.rv_device_list_history_connect)
    RecyclerView mHistoryConnectRv;
    @BindView(R.id.rv_device_list_search_device)
    RecyclerView mSearchRv;
    @BindView(R.id.tv_refresh)
    TextView mRefreshTv;
    @BindView(R.id.pgb_refresh)
    ProgressBar mRefreshPgb;


    private SPUtils mSPUtils;

    private DeviceListAdapter mHistoryConnectAdapter;
    private DeviceListAdapter mSearchAdapter;
    private ArrayList<BleDeviceInfo> mHistoryConnectList;
    private List<BleDeviceInfo> mSearchList = new ArrayList<>();


    public static DeviceListFragment newInstance() {
        Bundle args = new Bundle();
        DeviceListFragment fragment = new DeviceListFragment();
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
        return R.layout.fragment_device_list;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {
        mSPUtils = SPUtils.getInstance(getContext());
        mHistoryConnectRv.setNestedScrollingEnabled(false);
        mSearchRv.setNestedScrollingEnabled(false);

        mSearchAdapter = new DeviceListAdapter(mSearchList);
        mSearchRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mSearchRv.setAdapter(mSearchAdapter);

        String historyConnectStr = mSPUtils.getString(SPUtils.SP_HISTORY_CONNECT, "[]");
        mHistoryConnectList = GsonUtil.fromJson(historyConnectStr, new TypeToken<ArrayList<BleDeviceInfo>>() {
        });
        if (mHistoryConnectList == null) {
            mHistoryConnectList = new ArrayList<>();
        }
        mHistoryConnectAdapter = new DeviceListAdapter(mHistoryConnectList);
        mHistoryConnectRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mHistoryConnectRv.setAdapter(mHistoryConnectAdapter);
        registerReceiver();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LeScanner.ACTION_START_SCAN);
        intentFilter.addAction(LeScanner.ACTION_STOP_SCAN);
        intentFilter.addAction(LeScanner.ACTION_FINISH_SCAN);
        intentFilter.addAction(LeScanner.ACTION_GATT_CONNECTED);
        intentFilter.addAction(LeScanner.ACTION_GATT_CONNECTING);
        intentFilter.addAction(LeScanner.ACTION_GATT_DISCONNECTED);
        getContext().registerReceiver(mGattReceiver, intentFilter);
    }

    /**
     * init data
     */
    @Override
    public void initData() {
        super.initData();
    }

    /**
     * init event
     */
    @Override
    protected void initEvent() {
        if (mLeScanner != null) {
            mLeScanner.setMyLeScanCallback(new MyLeScanCallback() {
                @Override
                public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                    boolean exist = false;
                    if (device != null && !TextUtils.isEmpty(device.getName())) {
                        for (BleDeviceInfo bleDeviceInfo : mSearchList) {
                            if (bleDeviceInfo.mDeviceName.equals(device.getName())
                                    && bleDeviceInfo.mMacAddress.equals(device.getAddress())) {
                                exist = true;
                                break;
                            }
                        }
                        if (!exist) {
                            mSearchList.add(new BleDeviceInfo(device.getName(), device.getAddress()));
                            mSearchAdapter.notifyDataSetChanged();
                        }
                    }
                }

            });
        }

        mSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (mLeScanner != null) {
                    mLeScanner.scanLeDevice(false);
                }
                BleDeviceInfo bleDeviceInfo = (BleDeviceInfo) baseQuickAdapter.getItem(i);
                for (BleDeviceInfo info : mHistoryConnectList) {
                    if (TextUtils.equals(info.mMacAddress, bleDeviceInfo.mMacAddress)) {
                        mHistoryConnectList.remove(info);
                        break;
                    }
                }
                mHistoryConnectList.add(bleDeviceInfo);
                mSPUtils.put(SPUtils.SP_HISTORY_CONNECT, GsonUtil.toJson(mHistoryConnectList));
                if (mHistoryConnectAdapter != null) {
                    mHistoryConnectAdapter.notifyDataSetChanged();
                }
                Intent intent = new Intent(getContext(), DeviceInfoActivity.class);
                intent.putExtra(Constants.MAC, bleDeviceInfo.mMacAddress);
                startActivity(intent);
                getActivity().finish();
            }
        });

        mHistoryConnectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (mLeScanner != null) {
                    mLeScanner.scanLeDevice(false);
                }
                BleDeviceInfo bleDeviceInfo = (BleDeviceInfo) baseQuickAdapter.getItem(i);
                for (BleDeviceInfo info : mHistoryConnectList) {
                    if (TextUtils.equals(info.mMacAddress, bleDeviceInfo.mMacAddress)) {
                        mHistoryConnectList.remove(info);
                        break;
                    }
                }
                mHistoryConnectList.add(bleDeviceInfo);
                mSPUtils.put(SPUtils.SP_HISTORY_CONNECT, GsonUtil.toJson(mHistoryConnectList));
                if (mHistoryConnectAdapter != null) {
                    mHistoryConnectAdapter.notifyDataSetChanged();
                }
                Intent intent = new Intent(getContext(), DeviceInfoActivity.class);
                intent.putExtra(Constants.MAC, bleDeviceInfo.mMacAddress);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }


    @AfterPermissionGranted(Constants.REQUEST_CODE_LOCATION_PERM)
    public void refresh() {

        if (EasyPermissions.hasPermissions(getContext(), Constants.LOCATION_PERM)) {
            // Have permissions, do the thing!
            startScan();
        } else {
            // Ask for both permissions
            LogTools.e(TAG, "requestPermissions");
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.hint_rationale_location),
                    Constants.REQUEST_CODE_LOCATION_PERM,
                    Constants.LOCATION_PERM);
        }
    }


    private void startScan() {
        if (mLeScanner != null) {
            if (!mLeScanner.isScanning()) {
                mSearchList.clear();
                if (mSearchAdapter != null) {
                    mSearchAdapter.notifyDataSetChanged();
                }
                mLeScanner.scanLeDevice(true);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mGattReceiver);
        if (mLeScanner != null) {
            mLeScanner.scanLeDevice(false);
        }
    }


    private final BroadcastReceiver mGattReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (LeScanner.ACTION_START_SCAN.equals(action)) {
                mRefreshTv.setVisibility(View.GONE);
                mRefreshPgb.setVisibility(View.VISIBLE);
            } else if (LeScanner.ACTION_STOP_SCAN.equals(action)) {
                mRefreshTv.setVisibility(View.VISIBLE);
                mRefreshPgb.setVisibility(View.GONE);
            } else if (LeScanner.ACTION_FINISH_SCAN.equals(action)) {
                mRefreshTv.setVisibility(View.VISIBLE);
                mRefreshPgb.setVisibility(View.GONE);
                if (mSearchList.size() == 0) {
                    showDialog();
                }
            } else {
                if (mSearchAdapter != null) {
                    mSearchAdapter.notifyDataSetChanged();
                }
                if (mHistoryConnectAdapter != null) {
                    mHistoryConnectAdapter.notifyDataSetChanged();
                }
            }

        }
    };

    private void showDialog() {
        MessageDialogFragment messageDialogFragment = MessageDialogFragment.newInstance();
        messageDialogFragment.setShowTitle(false)
                .setMessage(getString(R.string.hint_no_search_ble_device))
                .setSingle(true)
                .setOnPositiveClickListener(new MessageDialogFragment.OnPositiveClickListener() {
                    @Override
                    public void onPositiveClick(MessageDialogFragment fragment, View ok) {
                        fragment.dismiss();
                    }
                })
                .show(getChildFragmentManager(), "");
    }

    @OnClick({R.id.tv_clear, R.id.tv_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clear:
                clear();
                break;
            case R.id.tv_refresh:
                refresh();
                break;
        }
    }

    private void clear() {
        mHistoryConnectList.clear();
        mSPUtils.put(SPUtils.SP_HISTORY_CONNECT, "[]");
        if (mHistoryConnectAdapter != null) {
            mHistoryConnectAdapter.notifyDataSetChanged();
        }
    }
}
