package com.shuorigf.bluetooth.streetlamp.ui.fragment.historicaldata;

import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;
import com.shuorigf.bluetooth.streetlamp.ble.ShourigfData;
import com.shuorigf.bluetooth.streetlamp.data.LineChartData;
import com.shuorigf.bluetooth.streetlamp.ui.view.MyMarkerView;
import com.shuorigf.bluetooth.streetlamp.ui.view.MyXFormatter;
import com.shuorigf.bluetooth.streetlamp.util.Constants;
import com.shuorigf.bluetooth.streetlamp.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * Created by clx on 2017/11/7.
 */

public class HistoricalDataChartFragment extends BaseFragment {
    @BindView(R.id.tv_historical_data_chart_title_left)
    TextView mTitleLeftTv;
    @BindView(R.id.tv_historical_data_chart_title_right)
    TextView mTitleRightTv;
    @BindView(R.id.historical_data_chart_line_chart)
    LineChart mLineChart;
    @BindArray(R.array.historical_data_day)
    TypedArray mDays;
    String[] days;
    private int type;

    public static HistoricalDataChartFragment newInstance(int res) {
        Bundle args = new Bundle();
        args.putInt(Constants.TYPE_HISTORICAL_DATA_CONTENT, res);
        HistoricalDataChartFragment fragment = new HistoricalDataChartFragment();
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
        return R.layout.fragment_historical_data_chart;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {
        List<String>  list=new ArrayList<>();
        for (int i = 0; i < mDays.length(); i++) {
            list.add(mDays.getString(i));
        }
        days=(String[]) list.toArray(new String[8]);
        type = getArguments().getInt(Constants.TYPE_HISTORICAL_DATA_CONTENT);
        initLineChart();
    }

    /**
     * init data
     */
    @Override
    public void initData() {
        super.initData();
//        initReadData();
    }

    private void initLineChart() {
        // enable touch gestures
        mLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLineChart.setDragEnabled(false);
        mLineChart.setScaleEnabled(false);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setNoDataText("");
        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);
        mLineChart.setPinchZoom(false);
        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(getContext(), R.layout.custom_marker_view);
        mv.setChartView(mLineChart); // For bounds control
        mLineChart.setMarker(mv); // Set the marker to the chart

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.textBlue));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(45f);

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.textBlue));
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawAxisLine(false);
        //leftAxis.setAxisMaximum(200f);
        //leftAxis.setAxisMinimum(-50f);
        //leftAxis.setYOffset(20f);
        //leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mLineChart.getAxisRight().setEnabled(false);
        Legend l = mLineChart.getLegend();
        l.setEnabled(false);



    }

//    private void initReadData() {
//        if ( mLeScanner!= null) {
//            mLeScanner.addFirstList(new ReadData(Constants.TYPE_HISTORICAL_CHART_DATA,
//                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.HistoricalChartDataInfo.REG_ADDR, ShourigfData.HistoricalChartDataInfo.READ_WORD)));
//        }
//    }

    /**
     * init event
     */
    @Override
    protected void initEvent() {

    }
    public void setHistoricalData(ShourigfData.HistoricalDataInfo historicalDataInfo) {
        if (historicalDataInfo == null) {
            return;
        }
        switch (type) {
            case R.string.battery_voltage:
                mTitleLeftTv.setText(String.format(getString(R.string.format_maximum_voltage), historicalDataInfo.mDayBatteryMaxVoltage + ""));
                mTitleRightTv.setText(String.format(getString(R.string.format_minimum_voltage), historicalDataInfo.mDayBatteryMinVoltage + ""));
                break;
            case R.string.temperature:
                mTitleLeftTv.setText(String.format(getString(R.string.format_maximum_temperature), historicalDataInfo.mDayMaxTemperature + ""));
                mTitleRightTv.setText(String.format(getString(R.string.format_minimum_temperature), historicalDataInfo.mDayMinTemperature + ""));
                break;
            case R.string.day_generating_capacity:
                mTitleLeftTv.setText(String.format(getString(R.string.format_day_generating_capacity), historicalDataInfo.mAccumulativeGeneratingCapacity + ""));
                mTitleRightTv.setVisibility(View.GONE);
                break;
            case R.string.day_power_consumption:
                mTitleLeftTv.setText(String.format(getString(R.string.format_day_power_consumption), historicalDataInfo.mAccumulativePowerConsumption + ""));
                mTitleRightTv.setVisibility(View.GONE);
                break;
            case R.string.day_charge_apm_hour:
                mTitleLeftTv.setText(String.format(getString(R.string.format_day_charge_apm_hour), historicalDataInfo.mDayChargeAmpHour + ""));
                mTitleRightTv.setVisibility(View.GONE);
                break;
            case R.string.day_discharge_apm_hour:
                mTitleLeftTv.setText(String.format(getString(R.string.format_day_discharge_apm_hour), historicalDataInfo.mDayDischargeAmpHour + ""));
                mTitleRightTv.setVisibility(View.GONE);
                break;
        }
    }

    public void setHistoricalChartData(List<ShourigfData.HistoricalChartDataInfo> historicalChartDataInfos) {
        if (historicalChartDataInfos == null) {
            return;
        }
        if (historicalChartDataInfos.size() > 6) {
            mLineChart.getXAxis().setValueFormatter(new MyXFormatter(days));
        }else if (historicalChartDataInfos.size() > 5){
            mLineChart.getXAxis().setValueFormatter(new MyXFormatter(Constants.Y_MONTH));
        }else{
            mLineChart.getXAxis().setValueFormatter(new MyXFormatter(Constants.Y_YEAR));
        }
        LineChartData lineChartData = new LineChartData();
        List<Float> yVal = new ArrayList<>();
        List<Float> yVal2 = new ArrayList<>();
        switch (type) {
            case R.string.battery_voltage:
                for (int i = 0; i < historicalChartDataInfos.size(); i++) {
                    ShourigfData.HistoricalChartDataInfo historicalChartDataInfo =  historicalChartDataInfos.get(i);
                    yVal.add(historicalChartDataInfo.mDayBatteryMaxVoltage);
                    yVal2.add(historicalChartDataInfo.mDayBatteryMinVoltage);
                }
                lineChartData.addLine(yVal2, ContextCompat.getColor(getContext(), R.color.textRed));
                break;
            case R.string.temperature:
                for (int i = 0; i < historicalChartDataInfos.size(); i++) {
                    ShourigfData.HistoricalChartDataInfo historicalChartDataInfo =  historicalChartDataInfos.get(i);
                    float f = historicalChartDataInfo.mDayMaxTemperature;
                    float f2 = historicalChartDataInfo.mDayMinTemperature;
                    yVal.add(f);
                    yVal2.add(f2);
                }
                lineChartData.addLine(yVal2, ContextCompat.getColor(getContext(), R.color.textRed));
                break;
            case R.string.day_generating_capacity:
                for (int i = 0; i < historicalChartDataInfos.size(); i++) {
                    ShourigfData.HistoricalChartDataInfo historicalChartDataInfo =  historicalChartDataInfos.get(i);
                    float f = historicalChartDataInfo.mAccumulativeGeneratingCapacity;
                    yVal.add(f);
                }
                break;
            case R.string.day_power_consumption:
                for (int i = 0; i < historicalChartDataInfos.size(); i++) {
                    ShourigfData.HistoricalChartDataInfo historicalChartDataInfo =  historicalChartDataInfos.get(i);
                    float f = historicalChartDataInfo.mAccumulativePowerConsumption;
                    yVal.add(f);
                }
                break;
            case R.string.day_charge_apm_hour:
                for (int i = 0; i < historicalChartDataInfos.size(); i++) {
                    ShourigfData.HistoricalChartDataInfo historicalChartDataInfo =  historicalChartDataInfos.get(i);
                    float f = historicalChartDataInfo.mDayChargeAmpHour;
                    yVal.add(f);
                }
                break;
            case R.string.day_discharge_apm_hour:
                for (int i = 0; i < historicalChartDataInfos.size(); i++) {
                    ShourigfData.HistoricalChartDataInfo historicalChartDataInfo =  historicalChartDataInfos.get(i);
                    float f = historicalChartDataInfo.mDayDischargeAmpHour;
                    yVal.add(f);
                }
                break;
        }
        lineChartData.addLine(yVal, ContextCompat.getColor(getContext(), R.color.textBlue));
        lineChartData.drawLine(mLineChart);
    }

}
