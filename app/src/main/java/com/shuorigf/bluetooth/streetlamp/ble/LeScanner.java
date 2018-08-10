package com.shuorigf.bluetooth.streetlamp.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.shuorigf.bluetooth.streetlamp.data.ReadData;
import com.shuorigf.bluetooth.streetlamp.util.Constants;
import com.shuorigf.bluetooth.streetlamp.util.ConvertUtils;
import com.shuorigf.bluetooth.streetlamp.util.LogTools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


public abstract class LeScanner {
    private final static String TAG = LeScanner.class.getSimpleName();

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;

    private boolean mScanning;
    private int mConnectionState = STATE_DISCONNECTED;
    private String mMac;
    private int mDeviceId = -1;


    private static final long SCAN_PERIOD = 10000;
    private static final long TIME_OVER = 10000;
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECTED = 2;

    public final static String ACTION_START_SCAN =
            "com.shuorigf.ble.ACTION_START_SCAN";
    public final static String ACTION_STOP_SCAN =
            "com.shuorigf.ble.ACTION_STOP_SCAN";
    public final static String ACTION_FINISH_SCAN =
            "com.shuorigf.ble.ACTION_FINISH_SCAN";
    public final static String ACTION_GATT_CONNECTED =
            "com.shuorigf.ble.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_CONNECTING =
            "com.shuorigf.ble.ACTION_GATT_CONNECTING";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.shuorigf.ble.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.shuorigf.ble.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_GET_ID =
            "com.shuorigf.ble.ACTION_GET_ID";
    public final static String ACTION_DATA_AVAILABLE =
            "com.shuorigf.ble.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.shuorigf.ble.EXTRA_DATA";
    public final static String EXTRA_TYPE =
            "com.shuorigf.ble.EXTRA_TYPE";

    private Context mContext;
    protected MyLeScanCallback mMyLeScanCallback;
    private BluetoothGattCharacteristic mWriteCharacteristic;
    private BluetoothGattCharacteristic mReadCharacteristic;

    private LinkedList<ReadData> mDataList = new LinkedList<>();
    private List<ReadData> mSettingDataList = new ArrayList<>();
    private boolean isSend = false;
    private int mType;
    private byte[] mData;

    private final Timer timer = new Timer();
    private TimerTask task;

    private Thread connectedThread;
    private String lastMac;
    private boolean connectedLastMacFlag = false;
    private int num;

    public boolean addLastList(ReadData readData) {
        if (mDeviceId != -1 && mConnectionState == STATE_CONNECTED) {
            mDataList.addLast(readData);
            return true;
        } else {
            return false;
        }
    }

    public boolean addFirstList(ReadData readData) {
        if (mDeviceId != -1 && mConnectionState == STATE_CONNECTED) {
            mDataList.addFirst(readData);
            return true;
        } else {
            return false;
        }
    }

    public void addSettingList(ReadData readData) {
        for (int i = 0; i < mSettingDataList.size(); i++) {
            if (readData.type == mSettingDataList.get(i).type) {
                mSettingDataList.remove(i);
                break;
            }
        }
        mSettingDataList.add(readData);
    }

    public void clearSettingList() {
        mSettingDataList.clear();
    }

    public synchronized void clearSettingList(int type) {
        for (int i = 0; i < mSettingDataList.size(); i++) {
            if (type == mSettingDataList.get(i).type) {
                mSettingDataList.remove(i);
                break;
            }
        }
    }

    public boolean setSettingList() {
        if (mDeviceId != -1 && mConnectionState == STATE_CONNECTED) {
            for (int i = 0; i < mSettingDataList.size(); i++) {
                addLastList(mSettingDataList.get(i));
            }
            mSettingDataList.clear();
            return true;
        } else {
            return false;
        }

    }

//    private synchronized ReadData getFirstList() {
//        if (mDataList.size() > 0) {
//            return  mDataList.removeFirst();
//        }else {
//            return null;
//        }
//    }

    private void sendData() {
        if (mDataList.size() > 0 && !isSend) {
            mData = null;
            isSend = true;
            mHandler.removeCallbacks(timeOutRunnable);
            ReadData readData = mDataList.removeFirst();
            mType = readData.type;
            setCharacteristicValue(readData.data);
            mHandler.postDelayed(timeOutRunnable, Constants.SEND_DATA_TIME_OVER);
        }
    }

    Runnable timeOutRunnable = new Runnable() {
        @Override
        public void run() {
            LogTools.e(TAG, "send data time out");
            isSend = false;
        }

    };

    private void receiveData(byte[] data) {
        if (data != null && data.length > 0) {
//            if (mDeviceId == -1) {
//                mDeviceId = ShourigfData.Bytes2Int(data, 0, 1);
//                Log.e(TAG, "mDeviceId: "+ mDeviceId);
//                sendBroadcast(ACTION_GET_ID);
//                return;
//            }
            if (mData == null) {
                mData = data;
            } else {
                byte[] temp = new byte[data.length + mData.length];
                System.arraycopy(mData, 0, temp, 0, mData.length);
                System.arraycopy(data, 0, temp, mData.length, data.length);
                mData = temp;
            }
            if (ModbusData.dataCrcCorrect(mData)) {
                Intent intent = new Intent(ACTION_DATA_AVAILABLE);
                intent.putExtra(EXTRA_TYPE, mType);
                intent.putExtra(EXTRA_DATA, mData);
                mContext.sendBroadcast(intent);
                mHandler.removeCallbacks(timeOutRunnable);
                isSend = false;
            }
        }

    }

    public void setMyLeScanCallback(MyLeScanCallback myLeScanCallback) {
        this.mMyLeScanCallback = myLeScanCallback;
    }

    private static UUID READ_SERVER_UUID = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
    private static UUID WRITE_SERVER_UUID = UUID.fromString("0000ffd0-0000-1000-8000-00805f9b34fb");
    private static UUID READ_DATA_UUID = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");
    private static UUID WRITE_DATA_UUID = UUID.fromString("0000ffd1-0000-1000-8000-00805f9b34fb");

    protected LeScanner(Context context) {
        this.mContext = context.getApplicationContext();
        initialize();
        initTimerTask();
    }


    private volatile static LeScanner mInstance;

    public static LeScanner getInstance(Context context) {
        if (android.os.Build.VERSION.SDK_INT < 18) {
            LogTools.e(TAG, "Not supported prior to API 18.");
            return null;
        }
        if (mInstance == null) {//第一次检查
            synchronized (LeScanner.class) {//加锁
                if (mInstance == null) {//第二次次检查
                    if (android.os.Build.VERSION.SDK_INT < 21) {
                        mInstance = new LeScannerForJellyBean(context);
                    } else {
                        mInstance = new LeScannerForLollipop(context);
                    }
                }
            }
        }
        return mInstance;
    }

    private void initTimerTask() {
        cancelTimerTask();
        task = new TimerTask() {
            @Override
            public void run() {
                sendData();
            }
        };
        timer.schedule(task, 2000, 300);
    }

    private void cancelTimerTask() {
        if (task != null) {
            task.cancel();
            timer.cancel();
        }
    }

    public boolean initialize() {
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                LogTools.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            LogTools.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }
        return true;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    public boolean isScanning() {
        return mScanning;
    }

    public void scanLeDevice(boolean enable) {
        mHandler.removeCallbacks(stopScanRunnable);
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(stopScanRunnable, SCAN_PERIOD); //在这里可以自己进行时间的设置，比如搜索10秒
            mScanning = true;
            sendBroadcast(ACTION_START_SCAN);
            startScan(); //开始搜索
        } else {
            mScanning = false;
            sendBroadcast(ACTION_STOP_SCAN);
            stopScan();//停止搜索
        }
    }

    Runnable stopScanRunnable = new Runnable() {
        @Override
        public void run() {
            LogTools.e(TAG, "scan time out");
            mScanning = false;
            sendBroadcast(ACTION_FINISH_SCAN);
            stopScan();//停止搜索
        }

    };

    Runnable disconnectRunnable = new Runnable() {
        @Override
        public void run() {
            LogTools.e(TAG, "disconnect time out");
            close();
        }

    };

//    private class getDeviceIdThread implements Runnable{
//
//        public void run() {
//            while(mDeviceId<0 && mConnectionState == STATE_CONNECTED)
//            {
//                byte []bs = ModbusData.buildReadRegsCmd(0xff, 0x001A,0x1);
//                setCharacteristicValue(bs);
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//
//    };

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param mac The device address of the destination device.
     * @return Return true if the connection is initiated successfully. The
     * connection result is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public boolean connect(String mac) {
//        if (mConnectionState != STATE_DISCONNECTED && TextUtils.equals(mac, mMac)) {
//            LogTools.e(TAG, "mac is connected or connecting");
//            return false;
//        }
        if (mConnectionState==STATE_CONNECTED){
            close();
        }
        mHandler.removeCallbacks(disconnectRunnable);
        if (mBluetoothAdapter == null) {
            LogTools.e(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            LogTools.e(TAG, "BluetoothAdapter no enabled");
            return false;
        }

        if (mac == null) {
            LogTools.e(TAG, "mac is null");
            return false;
        }

        final BluetoothDevice device = mBluetoothAdapter
                .getRemoteDevice(mac);
        if (device == null) {
            LogTools.e(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        LogTools.e(TAG, "mac address:" + mac);
        mConnectionState = STATE_CONNECTING;
        mMac = mac;
        sendBroadcast(ACTION_GATT_CONNECTING);
        LogTools.e(TAG, "Connecting to GATT server.");
        mBluetoothGatt = device.connectGatt(mContext, false, mBluetoothGattCallback);
        mHandler.postDelayed(disconnectRunnable, TIME_OVER);
        return true;
    }

    private final BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                            int newState) {
            mHandler.removeCallbacks(disconnectRunnable);
//            if (status != BluetoothGatt.GATT_SUCCESS) {
//                LogTools.e(TAG, "onConnectionStateChange status != BluetoothGatt.GATT_SUCCESS");
//                close();
//                return;
//            }
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                LogTools.e(TAG, "Connected to GATT server.");
                mConnectionState = STATE_CONNECTED;
                connectedLastMacFlag=false;
                lastMac=mMac;
                sendBroadcast(ACTION_GATT_CONNECTED);
                if (gatt != null) {
                    LogTools.e(TAG, "start discover Services");
                    gatt.discoverServices();
                }

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                LogTools.e(TAG, "Disconnected from GATT server.");
                connectedLastMacFlag=true;
                if (connectedThread == null) {
                    num=0;
                    connectedThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (connectedLastMacFlag) {
                                try {
                                    Thread.sleep(1000 * 10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (lastMac != null&&mConnectionState==STATE_DISCONNECTED) {
                                    num++;
                                    LogTools.e("地址："+lastMac,"正在进行第"+num+"次重连！");
                                    connect(lastMac);
                                }
                            }
                            connectedThread=null;
                        }
                    });
                    connectedThread.start();
                }
                close();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                LogTools.e(TAG, "Services Discovered");
                sendBroadcast(ACTION_GATT_SERVICES_DISCOVERED);
                getWriteReadCharacteristic();
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mDeviceId = 0xFF;
                sendBroadcast(ACTION_GET_ID);
//                new Thread(new getDeviceIdThread()).start();
            } else {
                LogTools.e(TAG, "onServicesDiscovered" + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                LogTools.e(TAG, " readCharacteristic success=:" + ConvertUtils.bytes2HexString(characteristic.getValue()));

            } else {
                LogTools.e(TAG, "on unknown characteristic read status: "
                        + status);
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt,
                                          BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                LogTools.e(TAG, " writeCharacteristic success=:" + ConvertUtils.bytes2HexString(characteristic.getValue()));
            } else {
                LogTools.e(TAG, "on unknown characteristic write status: "
                        + status);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            LogTools.e(TAG, " onCharacteristicChanged=:" + ConvertUtils.bytes2HexString(characteristic.getValue()));
            if (READ_DATA_UUID.equals(characteristic.getUuid())) {
                receiveData(characteristic.getValue());
            }

        }
    };


    private void sendBroadcast(String action) {
        Intent intent = new Intent(action);
        mContext.sendBroadcast(intent);
    }


    /**
     * Disconnects an existing connection or cancel a pending connection. The
     * disconnection result is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            LogTools.e(TAG, "BluetoothAdapter not initialized");
            return;
        }
        LogTools.e(TAG, "Bluetooth disconnect");
        mBluetoothGatt.disconnect();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure
     * resources are released properly.
     */
    public void close() {
        mConnectionState = STATE_DISCONNECTED;
        sendBroadcast(ACTION_GATT_DISCONNECTED);
        clear();
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            LogTools.e(TAG, "BluetoothAdapter not initialized");
            return;
        }
        LogTools.e(TAG, "Bluetooth close");
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    private void clear() {
        mDeviceId = -1;
        mDataList.clear();
        mSettingDataList.clear();
        mHandler.removeCallbacks(timeOutRunnable);
        isSend = false;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read
     * result is reported asynchronously through the
     * {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            LogTools.e(TAG, "BluetoothAdapter not initialized");
            return;
        }
        LogTools.e(TAG, "mBluetoothGatt readCharacteristic");
        mBluetoothGatt.readCharacteristic(characteristic);

    }

    public void writeCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            LogTools.e(TAG, "BluetoothAdapter not initialized");
            return;
        }
        LogTools.e(TAG, "mBluetoothGatt writeCharacteristic :=" + ConvertUtils.bytes2HexString(characteristic.getValue()));
        mBluetoothGatt.writeCharacteristic(characteristic);
    }

    private void getWriteReadCharacteristic() {
        mWriteCharacteristic = getWriteCharacteristic();
        mReadCharacteristic = getReadCharacteristic();
        setCharacteristicNotification(mReadCharacteristic, true);
    }

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled        If true, enable notification. False otherwise.
     */
    public void setCharacteristicNotification(
            BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            LogTools.e(TAG, "BluetoothAdapter not initialized");
            return;
        }
        if (characteristic == null) {
            return;
        }
        LogTools.e(TAG, "setCharacteristicNotification" + characteristic.getUuid().toString());
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
        if (descriptor != null) {
            LogTools.e(TAG, "descriptor setValue enabled" + descriptor.getUuid().toString());
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }

//        List<BluetoothGattDescriptor> descriptors = characteristic.getDescriptors();
//        if (descriptors != null) {
//            for(BluetoothGattDescriptor dp:descriptors){
//                LogTools.e(TAG, "descriptor setValue enabled" + dp.getUuid().toString());
//                dp.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//                mBluetoothGatt.writeDescriptor(dp);
//            }
//        }
    }


    /**
     * Retrieves a list of supported GATT services on the connected device. This
     * should be invoked only after {@code BluetoothGatt#discoverServices()}
     * completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null)
            return null;
        return mBluetoothGatt.getServices();
    }


    private BluetoothGattCharacteristic getWriteCharacteristic() {
        BluetoothGattService service = null;
        try {
            service = mBluetoothGatt.getService(WRITE_SERVER_UUID);
        } catch (Exception e) {
            LogTools.e(TAG, "get service Exception !");
        }

        if (service == null) {
            LogTools.e(TAG, "service not found !");
            return null;
        }

        BluetoothGattCharacteristic characteristic = service
                .getCharacteristic(WRITE_DATA_UUID);
        if (characteristic == null) {
            LogTools.e(TAG, "characteristic not found!");
            return null;
        }
        return characteristic;
    }

    public BluetoothGattCharacteristic getReadCharacteristic() {
        BluetoothGattService service = null;
        try {
            service = mBluetoothGatt.getService(READ_SERVER_UUID);
        } catch (Exception e) {
            LogTools.e(TAG, "get service Exception !");
        }

        if (service == null) {
            LogTools.e(TAG, "service not found !");
            return null;
        }

        BluetoothGattCharacteristic characteristic = service
                .getCharacteristic(READ_DATA_UUID);
        if (characteristic == null) {
            LogTools.e(TAG, "characteristic not found!");
            return null;
        }
        return characteristic;
    }

    /**
     * write data to BLE device
     *
     * @param value
     * @return
     */
    public boolean setCharacteristicValue(byte[] value) {
        if (value == null) {
            LogTools.e(TAG, "empty action!");
            return false;
        }
//        Log.e(TAG, "value len: " + value.length + ", in string is: "
//                + ConvertUtils.bytes2HexString(value));

        if (mWriteCharacteristic == null) {
            return false;
        }
        mWriteCharacteristic.setValue(value);
//        mWriteCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        writeCharacteristic(mWriteCharacteristic);
        return true;
    }


    private void startScan() {
        LogTools.e(TAG, "startScan");
        startLeScan();
    }

    private void stopScan() {
        LogTools.e(TAG, "stopScan");
        stopLeScan();
    }

    protected abstract void startLeScan();

    protected abstract void stopLeScan();


    public String getMac() {
        return mMac == null ? "" : mMac;
    }

    public int getState() {
        return mConnectionState;
    }

    public int getDeviceId() {
        return mDeviceId;
    }
}
