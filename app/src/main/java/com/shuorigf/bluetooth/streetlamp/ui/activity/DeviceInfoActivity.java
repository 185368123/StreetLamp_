package com.shuorigf.bluetooth.streetlamp.ui.activity;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
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
import com.shuorigf.bluetooth.streetlamp.adapter.DeviceInformationAdapter;
import com.shuorigf.bluetooth.streetlamp.adapter.DeviceListAdapter;
import com.shuorigf.bluetooth.streetlamp.base.BaseActivity;
import com.shuorigf.bluetooth.streetlamp.base.MessageDialogFragment;
import com.shuorigf.bluetooth.streetlamp.ble.LeScanner;
import com.shuorigf.bluetooth.streetlamp.ble.ModbusData;
import com.shuorigf.bluetooth.streetlamp.ble.MyLeScanCallback;
import com.shuorigf.bluetooth.streetlamp.ble.ShourigfData;
import com.shuorigf.bluetooth.streetlamp.data.BleDeviceInfo;
import com.shuorigf.bluetooth.streetlamp.data.DeviceInfo;
import com.shuorigf.bluetooth.streetlamp.data.ReadData;
import com.shuorigf.bluetooth.streetlamp.util.Constants;
import com.shuorigf.bluetooth.streetlamp.util.GsonUtil;
import com.shuorigf.bluetooth.streetlamp.util.SPUtils;
import com.shuorigf.bluetooth.streetlamp.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by clx on 2017/12/6.
 */

public class DeviceInfoActivity extends BaseActivity {

    @BindView(R.id.tv_device_info_state)
    TextView mStateTv;
    @BindView(R.id.rv_device_info_content)
    RecyclerView mContentRv;
    @BindView(R.id.rv_device_info_other_device)
    RecyclerView mSearchRv;
    @BindView(R.id.tv_refresh)
    TextView mRefreshTv;
    @BindView(R.id.pgb_refresh)
    ProgressBar mRefreshPgb;

    @BindArray(R.array.device_information_title)
    TypedArray mTitle;

    private SPUtils mSPUtils;
    private DeviceInfo mDeviceInfo = new DeviceInfo();
    private List<BleDeviceInfo> mSearchList = new ArrayList<>();
    private ArrayList<BleDeviceInfo> mHistoryConnectList;
    private DeviceListAdapter mSearchAdapter;
    private DeviceInformationAdapter mDeviceInformationAdapter;

    private String mac;


    /**
     * get layout resources id
     *
     * @return layoutRes
     */
    @Override
    public int getLayoutRes() {
        return R.layout.activity_device_info;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {
        mSPUtils = SPUtils.getInstance(this);
        mContentRv.setNestedScrollingEnabled(false);
        mSearchRv.setNestedScrollingEnabled(false);
        String historyConnectStr = mSPUtils.getString(SPUtils.SP_HISTORY_CONNECT, "[]");
        mHistoryConnectList = GsonUtil.fromJson(historyConnectStr, new TypeToken<ArrayList<BleDeviceInfo>>() {
        });
        if (mHistoryConnectList == null) {
            mHistoryConnectList = new ArrayList<>();
        }
        getIntent().putExtra(Constants.DEVICE_INFO, mDeviceInfo);
        mSearchAdapter = new DeviceListAdapter(mSearchList);
        mSearchRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mSearchRv.setAdapter(mSearchAdapter);

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < mTitle.length(); i++) {
            list.add(mTitle.getResourceId(i, 0));
        }
        mDeviceInformationAdapter = new DeviceInformationAdapter(list);
        mContentRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mContentRv.setItemViewCacheSize(list.size());
        mContentRv.setAdapter(mDeviceInformationAdapter);
        switchState();
        mac = getIntent().getStringExtra(Constants.MAC);
        if (!TextUtils.isEmpty(mac)) {
            if (mLeScanner != null) {
                mLeScanner.connect(mac);
            }
        }
        registerReceiver();
        initEvent();
        initReadData();
    }

    private void initReadData() {
        if (mLeScanner != null) {
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_DEVICE_PRODUCT,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.DeviceProductType.REG_ADDR, ShourigfData.DeviceProductType.READ_WORD)));
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_DEVICE_VERSION,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.DeviceVersionInfo.REG_ADDR, ShourigfData.DeviceVersionInfo.READ_WORD)));
        }
    }


    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LeScanner.ACTION_START_SCAN);
        intentFilter.addAction(LeScanner.ACTION_STOP_SCAN);
        intentFilter.addAction(LeScanner.ACTION_FINISH_SCAN);
        intentFilter.addAction(LeScanner.ACTION_GATT_CONNECTED);
        intentFilter.addAction(LeScanner.ACTION_GATT_CONNECTING);
        intentFilter.addAction(LeScanner.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(LeScanner.ACTION_GET_ID);
        intentFilter.addAction(LeScanner.ACTION_DATA_AVAILABLE);
        registerReceiver(mGattReceiver, intentFilter);
    }

    private void initEvent() {
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
                if (mLeScanner != null) {
                    mac = bleDeviceInfo.mMacAddress;
                    mLeScanner.connect(mac);
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mGattReceiver);
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
                    showNoSearchDialog();
                }
            } else if (LeScanner.ACTION_GET_ID.equals(action)) {
                initReadData();
            } else if (LeScanner.ACTION_DATA_AVAILABLE.equals(action)) {
                receiveData(intent.getIntExtra(LeScanner.EXTRA_TYPE, 0), intent.getByteArrayExtra(LeScanner.EXTRA_DATA));
            } else {
                switchState();
                if (mSearchAdapter != null) {
                    mSearchAdapter.notifyDataSetChanged();
                }
            }

        }
    };

    private void receiveData(int type, byte[] data) {
        switch (type) {
            case Constants.TYPE_DEVICE_PRODUCT:
                ShourigfData.DeviceProductType deviceProductType = new ShourigfData.DeviceProductType(data);
                mDeviceInfo.deviceType = deviceProductType.mProductTypeStr;
                break;
            case Constants.TYPE_DEVICE_VERSION:
                ShourigfData.DeviceVersionInfo deviceVersionInfo = new ShourigfData.DeviceVersionInfo(data);
                mDeviceInfo.hardwareVersion = deviceVersionInfo.mHardwareVersion;
                mDeviceInfo.firmwareVersion = deviceVersionInfo.mSoftVersion;
                break;
            case Constants.TYPE_RESTORE_FACTORY_SETTINGS:
                if (ModbusData.resetFactorySettingsSuccess(data)) {
                    ToastUtil.showShortToast(this, R.string.restore_factory_settings_success);
                } else {
                    ToastUtil.showShortToast(this, R.string.restore_factory_settings_failed);
                }
                break;
        }
        if (mDeviceInformationAdapter != null) {
            mDeviceInformationAdapter.notifyDataSetChanged();
        }
    }


    private void switchState() {
        switch (mLeScanner.getState()) {
            case LeScanner.STATE_DISCONNECTED:
                mStateTv.setText(R.string.connect_device);
                break;
            case LeScanner.STATE_CONNECTING:
                mStateTv.setText(R.string.connecting_device);
                break;
            case LeScanner.STATE_CONNECTED:
                mStateTv.setText(R.string.disconnect_device);
                break;
        }
    }


    @OnClick({R.id.tv_device_info_state, R.id.tv_refresh, R.id.btn_device_info_restore})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_device_info_state:
                if (mLeScanner != null) {
                    if (mLeScanner.getState() != LeScanner.STATE_DISCONNECTED) {
                        mLeScanner.close();
                        Intent intent = new Intent(this, DeviceListActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (mLeScanner.getState() == LeScanner.STATE_DISCONNECTED) {
                        if (!TextUtils.isEmpty(mac)) {
                            if (mLeScanner != null) {
                                mLeScanner.connect(mac);
                            }
                        }
                    }
                }
                break;
            case R.id.tv_refresh:
                refresh();
                break;
            case R.id.btn_device_info_restore:
                showRestoreDialog();
                break;
        }
    }

    @AfterPermissionGranted(Constants.REQUEST_CODE_LOCATION_PERM)
    public void refresh() {

        if (EasyPermissions.hasPermissions(this, Constants.LOCATION_PERM)) {
            // Have permissions, do the thing!
            startScan();
        } else {
            // Ask for both permissions
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

    private void showNoSearchDialog() {
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
                .show(getSupportFragmentManager(), "");
    }


    private void showRestoreDialog() {
        MessageDialogFragment messageDialogFragment = MessageDialogFragment.newInstance();
        messageDialogFragment.setShowTitle(false)
                .setMessage(getString(R.string.hint_restore_factory_settings))
                .setSingle(false)
                .setOnPositiveClickListener(new MessageDialogFragment.OnPositiveClickListener() {
                    @Override
                    public void onPositiveClick(MessageDialogFragment fragment, View ok) {
                        restoreFactorySettings();
                        fragment.dismiss();
                    }
                })
                .setOnNegativeClickListener(new MessageDialogFragment.OnNegativeClickListener() {
                    @Override
                    public void onNegativeClick(MessageDialogFragment fragment, View cancel) {
                        fragment.dismiss();
                    }
                })
                .show(getSupportFragmentManager(), "");
    }

    private void restoreFactorySettings() {
        if (mLeScanner != null) {
            boolean b = mLeScanner.addFirstList(new ReadData(Constants.TYPE_RESTORE_FACTORY_SETTINGS, ModbusData.buildRestoreFactoryCmd(mLeScanner.getDeviceId())));
            if (!b) {
                ToastUtil.showShortToast(this, R.string.restore_factory_settings_failed);
            }
        }
    }
}
