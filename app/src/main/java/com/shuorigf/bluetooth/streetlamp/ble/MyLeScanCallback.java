package com.shuorigf.bluetooth.streetlamp.ble;

import android.bluetooth.BluetoothDevice;

public interface MyLeScanCallback {
     void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord);
}
