package com.shuorigf.bluetooth.streetlamp.ble;

import android.annotation.TargetApi;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;

import com.shuorigf.bluetooth.streetlamp.util.LogTools;

import java.util.List;

@TargetApi(21)
public class LeScannerForLollipop extends LeScanner {
	private final static String TAG = LeScannerForLollipop.class
			.getSimpleName();

	private BluetoothLeScanner mScanner;

	public LeScannerForLollipop(Context context) {
		super(context);
	}

	@TargetApi(21)
	@Override
	protected void startLeScan() {
		 BluetoothLeScanner scanner = getScanner();

		if (scanner == null) {
			return;
		}
		if (!getBluetoothAdapter().isEnabled()) {
			LogTools.e(TAG, "Bluetooth disable");
			return;
		}
		ScanSettings settings = new ScanSettings.Builder().setScanMode(
				ScanSettings.SCAN_MODE_LOW_LATENCY).build();;
	
		try {
			scanner.startScan(null, settings, mLeScanCallback);
		} catch (Exception e) {
			LogTools.e(TAG, "Internal Android exception in startScan()");
		}
	}

	@Override
	protected void stopLeScan() {
		final BluetoothLeScanner scanner = getScanner();
		if (scanner == null) {
			return;
		}
		try {
			scanner.stopScan(mLeScanCallback);
		} catch (Exception e) {
			LogTools.e(TAG, "Internal Android exception in stopScan()");
		}

	}

	private BluetoothLeScanner getScanner() {
		if (mScanner == null) {
			if (getBluetoothAdapter() != null) {
				mScanner = getBluetoothAdapter().getBluetoothLeScanner();
			}
			if (mScanner == null) {
				LogTools.e(TAG, "Unable to obtain a scanner.");
			}
		}
		return mScanner;
	}

	private ScanCallback mLeScanCallback = new ScanCallback() {

		@Override
		public void onScanResult(int callbackType, ScanResult scanResult) {
			super.onScanResult(callbackType, scanResult);
			if (mMyLeScanCallback != null) {
				mMyLeScanCallback.onLeScan(scanResult.getDevice(), scanResult
						.getRssi(), scanResult.getScanRecord().getBytes());
			}
		}

		@Override
		public void onBatchScanResults(List<ScanResult> results) {
			super.onBatchScanResults(results);
		}

		@Override
		public void onScanFailed(int errorCode) {
			super.onScanFailed(errorCode);
			LogTools.e(TAG, "onScanFailed"+errorCode);
		}

	};

}
