package com.shuorigf.bluetooth.streetlamp.ui.fragment.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.adapter.CommonFragmentPagerAdapter;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;
import com.shuorigf.bluetooth.streetlamp.ble.LeScanner;
import com.shuorigf.bluetooth.streetlamp.ble.ModbusData;
import com.shuorigf.bluetooth.streetlamp.ble.ShourigfData;
import com.shuorigf.bluetooth.streetlamp.data.ReadData;
import com.shuorigf.bluetooth.streetlamp.ui.activity.DeviceInfoActivity;
import com.shuorigf.bluetooth.streetlamp.ui.activity.DeviceListActivity;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.homepage.HomepageBatteryFragment;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.homepage.HomepageLoadFragment;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.homepage.HomepageSolarPanelFragment;
import com.shuorigf.bluetooth.streetlamp.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by clx on 2017/10/30.
 */

public class HomePageFragment extends BaseFragment {

    @BindView(R.id.homepage_tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.homepage_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tv_start_testing)
    TextView mStartTestingTv;
    @BindView(R.id.tv_testing_info)
    TextView mTestingInfoTv;
    @BindView(R.id.tv_fault_info)
    TextView mFaultInfoTv;
    @BindArray(R.array.homepage_title)
    TypedArray mTabTitles;
    @BindArray(R.array.homepage_icon)
    TypedArray mTabIcons;
    @BindArray(R.array.fault_info_title)
    TypedArray mFaultInfoTitles;

    private List<Fragment> fragments;



    public static HomePageFragment newInstance() {
        Bundle args = new Bundle();
        HomePageFragment fragment = new HomePageFragment();
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
        return R.layout.fragment_homepage;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {
        initVP();
        registerReceiver();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LeScanner.ACTION_DATA_AVAILABLE);
        getContext().registerReceiver(mGattReceiver, intentFilter);
    }

    private final BroadcastReceiver mGattReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (LeScanner.ACTION_DATA_AVAILABLE.equals(action)) {
                receiveData(intent.getIntExtra(LeScanner.EXTRA_TYPE, 0), intent.getByteArrayExtra(LeScanner.EXTRA_DATA));
            }
        }
    };

    private void receiveData(int type, byte[] data) {
        switch (type) {
            case Constants.TYPE_DEVICE_TESTING:
                ShourigfData.FaultAndWarnInfo faultAndWarnInfo = new ShourigfData.FaultAndWarnInfo(data);
                if (faultAndWarnInfo.mList.size() == 0) {
                    mTestingInfoTv.setText(R.string.click_testing);
                    mStartTestingTv.setText(R.string.start_testing);
                }else {
                    int num = 0;
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < faultAndWarnInfo.mList.size(); i++) {
                        if (faultAndWarnInfo.mList.get(i)) {
                            num++;
                            sb.append(mFaultInfoTitles.getString(i));
                            sb.append("\n");
                        }
                    }
                    mFaultInfoTv.setText(sb.toString());
                    if (num > 0) {
                        mTestingInfoTv.setText(R.string.testing_abnormal);
                        mStartTestingTv.setText(String.format(getString(R.string.format_abnormal_device), num));
                    }else {
                        mTestingInfoTv.setText(R.string.testing_normal);
                        mStartTestingTv.setText(R.string.normal);
                    }
                }
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
    }


    private void initVP() {
        fragments = new ArrayList<>();
        fragments.add(HomepageSolarPanelFragment.newInstance());
        fragments.add(HomepageBatteryFragment.newInstance());
        fragments.add(HomepageLoadFragment.newInstance());

        mViewPager.setAdapter(new CommonFragmentPagerAdapter(getChildFragmentManager(), fragments));
        mViewPager.setOffscreenPageLimit(fragments.size());
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.homepage_tab, null, false);
            TextView tab = view.findViewById(R.id.tv_homepage_tab);
            tab.setText(mTabTitles.getText(i));
            tab.setCompoundDrawablesWithIntrinsicBounds(mTabIcons.getDrawable(i), null, null, null);
            if (mTabLayout.getTabAt(i) != null)
                 mTabLayout.getTabAt(i).setCustomView(view);
        }
    }

    /**
     * init event
     */
    @Override
    protected void initEvent() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (fragments.size() > position) {
                    ((BaseFragment) fragments.get(position)).initData();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @OnClick({R.id.iv_homepage_device_information, R.id.llyt_start_testing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_homepage_device_information:
                if (mLeScanner.getState() == LeScanner.STATE_CONNECTED) {
                    Intent intent = new Intent(getContext(), DeviceInfoActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(), DeviceListActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.llyt_start_testing:
                testing();
                break;
        }
    }

    private void testing(){
        mFaultInfoTv.setText(null);
        mTestingInfoTv.setText(R.string.being_testing_and_waiting);
        mStartTestingTv.setText(R.string.being_testing);
        if (mLeScanner != null) {
            boolean b =  mLeScanner.addFirstList(new ReadData(Constants.TYPE_DEVICE_TESTING,
                    ModbusData.buildReadRegsCmd(mLeScanner.getDeviceId(), ShourigfData.FaultAndWarnInfo.REG_ADDR, ShourigfData.FaultAndWarnInfo.READ_WORD)));
            if (!b) {
                mTestingInfoTv.setText(R.string.click_testing);
                mStartTestingTv.setText(R.string.start_testing);
            }
        }
    }


//    public void refreshData() {
//        for (Fragment fragment : fragments) {
//            if (fragment instanceof BaseFragment) {
//                ((BaseFragment) fragment).initData();
//            }
//        }
//    }
}
