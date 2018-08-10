package com.shuorigf.bluetooth.streetlamp.ui.view;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by clx on 2017/12/24.
 */

public class MyXFormatter  implements IAxisValueFormatter {

    private String[] mValues;

    public MyXFormatter(String[] values) {
        this.mValues = values;
    }
    private static final String TAG = "MyXFormatter";
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        if((int) value < 0 || (int) value >= mValues.length){
            return "";
        }else{
            return mValues[(int) value];
        }
    }
}
