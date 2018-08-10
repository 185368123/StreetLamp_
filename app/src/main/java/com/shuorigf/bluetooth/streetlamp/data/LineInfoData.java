package com.shuorigf.bluetooth.streetlamp.data;

import java.util.List;

/**
 * auther: clx on 17/10/26.
 */

public class LineInfoData {
    public List<Float> mYVal;
    public int mLineColor;
    public LineInfoData(List<Float> yVal, int lineColor) {
        this.mYVal = yVal;
        this.mLineColor = lineColor;
    }
}
