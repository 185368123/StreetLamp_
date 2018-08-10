package com.shuorigf.bluetooth.streetlamp.data;

/**
 * Created by clx on 2017/12/11.
 */

public class ReadData {
    public int type;
    public byte[] data;

    public ReadData(int type, byte[] data) {
        this.type = type;
        this.data = data;
    }
}
