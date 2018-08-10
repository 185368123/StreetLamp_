package com.shuorigf.bluetooth.streetlamp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by clx on 2017/12/7.
 */

public class DeviceInfo implements Parcelable {
    public String deviceType;
    public String hardwareVersion;
    public String firmwareVersion;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.deviceType);
        dest.writeString(this.hardwareVersion);
        dest.writeString(this.firmwareVersion);
    }

    public DeviceInfo() {
    }

    protected DeviceInfo(Parcel in) {
        this.deviceType = in.readString();
        this.hardwareVersion = in.readString();
        this.firmwareVersion = in.readString();
    }

    public static final Creator<DeviceInfo> CREATOR = new Creator<DeviceInfo>() {
        @Override
        public DeviceInfo createFromParcel(Parcel source) {
            return new DeviceInfo(source);
        }

        @Override
        public DeviceInfo[] newArray(int size) {
            return new DeviceInfo[size];
        }
    };
}
