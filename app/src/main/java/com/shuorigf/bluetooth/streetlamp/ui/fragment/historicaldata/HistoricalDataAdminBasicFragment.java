package com.shuorigf.bluetooth.streetlamp.ui.fragment.historicaldata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.adapter.CommonFragmentPagerAdapter;
import com.shuorigf.bluetooth.streetlamp.adapter.HistoricalDataNonAdminAdapter;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;
import com.shuorigf.bluetooth.streetlamp.ble.LeScanner;
import com.shuorigf.bluetooth.streetlamp.ble.ModbusData;
import com.shuorigf.bluetooth.streetlamp.ble.ShourigfData;
import com.shuorigf.bluetooth.streetlamp.data.HistoricalDataInfo;
import com.shuorigf.bluetooth.streetlamp.data.ReadData;
import com.shuorigf.bluetooth.streetlamp.util.Constants;
import com.shuorigf.bluetooth.streetlamp.util.ConvertUtils;
import com.shuorigf.bluetooth.streetlamp.util.TimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by clx on 2017/11/7.
 */

public class HistoricalDataAdminBasicFragment extends BaseFragment {
    @BindView(R.id.rv_historical_data_non_admin_content)
    RecyclerView mContentRv;
    @BindView(R.id.historical_data_non_admin_tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.historical_data_non_admin_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tv_historical_data_non_admin_time_select)
    TextView mTimeSelectTv;
    @BindArray(R.array.historical_data_non_admin_chart_title)
    TypedArray mTabTitles;
    @BindArray(R.array.historical_data_non_admin_content_title)
    TypedArray mContentTitles;

    private HistoricalDataInfo mHistoricalDataInfo = new HistoricalDataInfo();
    private HistoricalDataNonAdminAdapter mHistoricalDataNonAdminAdapter;
    List<Fragment> fragments = new ArrayList<>();

    private List<String> options1Items = new ArrayList<>();
    private List<List<String>> options2Items = new ArrayList<>();
    private List<List<List<String>>> options3Items = new ArrayList<>();

    private OptionsPickerView mOptionsPickerView;
    private DateFormat mYMFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
    private DateFormat mYMDFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private List<ShourigfData.HistoricalChartDataInfo> mHistoricalChartDataInfos = new ArrayList<>();
    private int mDateType;

    public static HistoricalDataAdminBasicFragment newInstance() {
        Bundle args = new Bundle();
        HistoricalDataAdminBasicFragment fragment = new HistoricalDataAdminBasicFragment();
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
        return R.layout.fragment_historical_data_non_admin;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {
        mTimeSelectTv.setText(TimeUtils.getNowString(mYMFormat));
        mDateType = Constants.DATE_TYPE_MONTH;
        initVP();
        initContent();
        registerReceiver();
    }


    private void initReadData() {
        if (mLeScanner != null) {
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_BATTERY,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.BatteryInfo.REG_ADDR, ShourigfData.BatteryInfo.READ_WORD)));
            mLeScanner.addFirstList(new ReadData(Constants.TYPE_HISTORICAL_DATA,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.HistoricalDataInfo.REG_ADDR, ShourigfData.HistoricalDataInfo.READ_WORD)));
            mHistoricalChartDataInfos.clear();
            readChartData();
        }
    }

    private void readChartData() {
        if (mDateType == Constants.DATE_TYPE_ALL) {
            readAllChartData();
        }else if (mDateType == Constants.DATE_TYPE_YEAR) {
            readYearChartData();
        }else if (mDateType == Constants.DATE_TYPE_MONTH){
            readMonthChartData();
        }
    }

    private void readAllChartData() {
        for (int i = 0; i < Constants.Y_YEAR.length; i++) {
            String str = Constants.Y_YEAR[i] + "-" + "01" + "-"+ "01";
            Log.e("111", "readAllChartData: " + str);
            int time = TimeUtils.getNowDaySpan(str,mYMDFormat);
            if(time >= 0) {
                if (mLeScanner != null) {
                    if (i == Constants.Y_YEAR.length - 1) {
                        mLeScanner.addLastList(new ReadData(Constants.TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC_END,
                                ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.HistoricalChartDataInfo.REG_ADDR + time, ShourigfData.HistoricalChartDataInfo.READ_WORD)));
                    }else {
                        mLeScanner.addLastList(new ReadData(Constants.TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC,
                                ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.HistoricalChartDataInfo.REG_ADDR + time, ShourigfData.HistoricalChartDataInfo.READ_WORD)));
                    }

                }
            }else {
                if (mLeScanner != null) {
                    if (i == Constants.Y_YEAR.length - 1) {
                        mLeScanner.addLastList(new ReadData(Constants.TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC_END,
                                ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.HistoricalChartDataInfo.REG_ADDR, ShourigfData.HistoricalChartDataInfo.READ_WRONG)));
                    }else {
                        mLeScanner.addLastList(new ReadData(Constants.TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC,
                                ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.HistoricalChartDataInfo.REG_ADDR, ShourigfData.HistoricalChartDataInfo.READ_WRONG)));
                    }

                }
            }
        }
    }

    private void readYearChartData() {
        String curDate = mTimeSelectTv.getText().toString();
        if (TextUtils.isEmpty(curDate)) {
            return;
        }
        for (int i = 0; i < Constants.Y_MONTH.length; i++) {
            String str = curDate + "-" + Constants.Y_MONTH[i] + "-" + "01";
            Log.e("111", "readYearChartData: " + str);
            int time = TimeUtils.getNowDaySpan(str,mYMDFormat);
            if(time >= 0) {
                if (mLeScanner != null) {
                    if (i == Constants.Y_MONTH.length - 1) {
                        mLeScanner.addLastList(new ReadData(Constants.TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC_END,
                                ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.HistoricalChartDataInfo.REG_ADDR + time, ShourigfData.HistoricalChartDataInfo.READ_WORD)));
                    }else {
                        mLeScanner.addLastList(new ReadData(Constants.TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC,
                                ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.HistoricalChartDataInfo.REG_ADDR + time, ShourigfData.HistoricalChartDataInfo.READ_WORD)));
                    }

                }
            }else {
                if (mLeScanner != null) {
                    if (i == Constants.Y_MONTH.length - 1) {
                        mLeScanner.addLastList(new ReadData(Constants.TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC_END,
                                ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.HistoricalChartDataInfo.REG_ADDR, ShourigfData.HistoricalChartDataInfo.READ_WRONG)));
                    }else {
                        mLeScanner.addLastList(new ReadData(Constants.TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC,
                                ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.HistoricalChartDataInfo.REG_ADDR, ShourigfData.HistoricalChartDataInfo.READ_WRONG)));
                    }

                }
            }
        }
    }

    private void readMonthChartData() {
      /*  String curDate = mTimeSelectTv.getText().toString();
        if (TextUtils.isEmpty(curDate)) {
            return;
        }*/
        String [] DAY=TimeUtils.getOld7Day();
        for (int i = 0; i < DAY.length; i++) {
           /* String str = curDate + "-" + Constants.Y_DAY[i];
            int time = TimeUtils.getNowDaySpan(str,mYMDFormat);*/
           int time=7-i;
            if(time >= 0) {
                if (mLeScanner != null) {
                    if (i == DAY.length - 1) {
                        mLeScanner.addLastList(new ReadData(Constants.TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC_END,
                                ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.HistoricalChartDataInfo.REG_ADDR + time, ShourigfData.HistoricalChartDataInfo.READ_WORD)));
                    }else {
                        mLeScanner.addLastList(new ReadData(Constants.TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC,
                                ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.HistoricalChartDataInfo.REG_ADDR + time, ShourigfData.HistoricalChartDataInfo.READ_WORD)));
                    }

                }
            }else {
                if (mLeScanner != null) {
                    if (i == DAY.length - 1) {
                        mLeScanner.addLastList(new ReadData(Constants.TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC_END,
                                ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.HistoricalChartDataInfo.REG_ADDR, ShourigfData.HistoricalChartDataInfo.READ_WRONG)));
                    }else {
                        mLeScanner.addLastList(new ReadData(Constants.TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC,
                                ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.HistoricalChartDataInfo.REG_ADDR, ShourigfData.HistoricalChartDataInfo.READ_WRONG)));
                    }

                }
            }
        }
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LeScanner.ACTION_GET_ID);
        intentFilter.addAction(LeScanner.ACTION_DATA_AVAILABLE);
        getContext().registerReceiver(mGattReceiver, intentFilter);
    }

    private final BroadcastReceiver mGattReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (LeScanner.ACTION_GET_ID.equals(action)) {
                initReadData();
            } else if (LeScanner.ACTION_DATA_AVAILABLE.equals(action)) {
                receiveData(intent.getIntExtra(LeScanner.EXTRA_TYPE, 0), intent.getByteArrayExtra(LeScanner.EXTRA_DATA));
            }
        }
    };

    private void receiveData(int type, byte[] data) {
        switch (type) {
            case Constants.TYPE_HISTORICAL_DATA:
                ShourigfData.HistoricalDataInfo historicalDataInfo = new ShourigfData.HistoricalDataInfo(data);
                mHistoricalDataInfo.mAllRunDays = historicalDataInfo.mAllRunDays;
                mHistoricalDataInfo.mBatteryChargeFullTimes = historicalDataInfo.mBatteryChargeFullTimes;
                mHistoricalDataInfo.mBatteryAllDischargeTimes = historicalDataInfo.mBatteryAllDischargeTimes;
                if (mHistoricalDataNonAdminAdapter != null) {
                    mHistoricalDataNonAdminAdapter.notifyDataSetChanged();
                }
                for (Fragment fragment : fragments) {
                    if (fragment instanceof HistoricalDataChartFragment) {
                        ((HistoricalDataChartFragment) fragment).setHistoricalData(historicalDataInfo);
                    }
                }
                break;
            case Constants.TYPE_BATTERY:
                ShourigfData.BatteryInfo batteryInfo = new ShourigfData.BatteryInfo(data);
                mHistoricalDataInfo.mCurrentTemperatur = batteryInfo.mDeviceTemperature;
                if (mHistoricalDataNonAdminAdapter != null) {
                    mHistoricalDataNonAdminAdapter.notifyDataSetChanged();
                }
                break;
            case Constants.TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC:
                ShourigfData.HistoricalChartDataInfo historicalChartDataInfo = new ShourigfData.HistoricalChartDataInfo(data);
                mHistoricalChartDataInfos.add(historicalChartDataInfo);

                break;
            case Constants.TYPE_HISTORICAL_CHART_DATA_ADMIN_BASIC_END:
                ShourigfData.HistoricalChartDataInfo historicalChartDataInfoEnd = new ShourigfData.HistoricalChartDataInfo(data);
                mHistoricalChartDataInfos.add(historicalChartDataInfoEnd);
                    for (Fragment fragment : fragments) {
                        if (fragment instanceof HistoricalDataChartFragment) {
                            ((HistoricalDataChartFragment) fragment).setHistoricalChartData(mHistoricalChartDataInfos);
                        }
                    }
                mHistoricalChartDataInfos.clear();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mGattReceiver);
    }

    /**
     * init data
     */
    @Override
    public void initData() {
        super.initData();
        initReadData();
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            initReadData();
//        }
//    }

    private void initVP() {
        for (int i = 0; i < mTabTitles.length(); i++) {
            fragments.add(HistoricalDataChartFragment.newInstance(mTabTitles.getResourceId(i, 0)));
        }

        mViewPager.setAdapter(new CommonFragmentPagerAdapter(getChildFragmentManager(), fragments) {
            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitles.getString(position);
            }
        });
        mViewPager.setOffscreenPageLimit(fragments.size());
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initContent() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < mContentTitles.length(); i++) {
            list.add(mContentTitles.getResourceId(i, 0));
        }
        mHistoricalDataNonAdminAdapter = new HistoricalDataNonAdminAdapter(list, mHistoricalDataInfo);
        mContentRv.setItemViewCacheSize(list.size());
        mContentRv.setAdapter(mHistoricalDataNonAdminAdapter);
    }

    /**
     * init event
     */
    @Override
    protected void initEvent() {

    }


    @OnClick(R.id.tv_historical_data_non_admin_time_select)
    public void onViewClicked() {
        showPickView();
    }

    private void showPickView() {
        if (mOptionsPickerView == null) {
            mOptionsPickerView = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if (options1Items.get(options1).equals(getString(R.string.all))) {
                        mDateType = Constants.DATE_TYPE_ALL;
                        mTimeSelectTv.setText(options1Items.get(options1));
                    }else if(options3Items.get(options1).get(options2).get(options3).equals(getString(R.string.all))){
                        mDateType = Constants.DATE_TYPE_YEAR;
                        mTimeSelectTv.setText(options2Items.get(options1).get(options2));
                    }else {
                        mDateType = Constants.DATE_TYPE_MONTH;
                        mTimeSelectTv.setText(options2Items.get(options1).get(options2)+ "-" +options3Items.get(options1).get(options2).get(options3));
                    }
                    readChartData();
                }
            })
                    .setTitleText(getString(R.string.please_select_check_time))
                    .setSubCalSize(16)//确定和取消文字大小
                    .setTitleSize(16)//确定和取消文字大小
                    .setTitleColor(ContextCompat.getColor(getContext(), R.color.textBlack))
                    .setSubmitColor(ContextCompat.getColor(getContext(), R.color.white))//确定按钮文字颜色
                    .setCancelColor(ContextCompat.getColor(getContext(), R.color.white))//取消按钮文字颜色
                    .setTitleBgColor(ContextCompat.getColor(getContext(), R.color.textBlue))//标题背景颜色 Night mode
                    .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                    .setContentTextSize(16)//滚轮文字大小
                    .setCyclic(false, false, false)//循环与否
                    .build();
            options1Items.add(getString(R.string.year));
            options1Items.add(getString(R.string.all));
            for (int i = 0; i < options1Items.size(); i++) {
                List<String> yearList = new ArrayList<>();
                List<List<String>> monthList = new ArrayList<>();
                if (i == 0) {
                    for (int year = 1990; year <= 2090; year++) {
                        yearList.add(year + "");
                        List<String> list = new ArrayList<>();
                        list.add(getString(R.string.all));
                        for (int month = 1; month <= 12; month++) {
                            list.add(month + "");
                        }
                        monthList.add(list);
                    }
                }else {
                    yearList.add("");
                    List<String> list = new ArrayList<>();
                    list.add("");
                    monthList.add(list);
                }
                options2Items.add(yearList);
                options3Items.add(monthList);
            }

            mOptionsPickerView.setPicker(options1Items, options2Items, options3Items);//三级选择器
        }

        mOptionsPickerView.show();

    }
}
