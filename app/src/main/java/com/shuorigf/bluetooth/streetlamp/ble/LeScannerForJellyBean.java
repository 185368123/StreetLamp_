package com.shuorigf.bluetooth.streetlamp.ble;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.shuorigf.bluetooth.streetlamp.util.LogTools;

public class LeScannerForJellyBean extends LeScanner {
	private final static String TAG = LeScannerForJellyBean.class
			.getSimpleName();

	public LeScannerForJellyBean(Context context) {
		super(context);
	}

	@Override
	protected void startLeScan() {
		if (getBluetoothAdapter() == null) {
			return;
		}
		if (!getBluetoothAdapter().isEnabled()) {
			LogTools.e(TAG, "Bluetooth disable");
			return;
		}
		
		try {
			getBluetoothAdapter().startLeScan(mLeScanCallback);
		} catch (Exception e) {
			LogTools.e(TAG,
					"Internal Android exception in startLeScan()");
		}

	}

	@Override
	protected void stopLeScan() {
		if (getBluetoothAdapter() == null) {
			return;
		}
		try {
			getBluetoothAdapter().stopLeScan(mLeScanCallback);
		} catch (Exception e) {
			LogTools.e(TAG, "Internal Android exception in stopLeScan()");
		}
	}

	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

		@Override
		public void onLeScan(final BluetoothDevice device, int rssi,
				final byte[] scanRecord) {
				if (mMyLeScanCallback != null) {
					mMyLeScanCallback.onLeScan(device, rssi, scanRecord);
				}
		}
	};

}