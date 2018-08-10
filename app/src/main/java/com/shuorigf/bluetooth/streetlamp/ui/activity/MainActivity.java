package com.shuorigf.bluetooth.streetlamp.ui.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.adapter.CommonFragmentPagerAdapter;
import com.shuorigf.bluetooth.streetlamp.base.BaseActivity;
import com.shuorigf.bluetooth.streetlamp.ble.LeScanner;
import com.shuorigf.bluetooth.streetlamp.data.BleDeviceInfo;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.main.AboutUsFragment;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.main.HistoricalDataFragment;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.main.HomePageFragment;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.main.WorkModeFragment;
import com.shuorigf.bluetooth.streetlamp.util.ActivityManager;
import com.shuorigf.bluetooth.streetlamp.util.GsonUtil;
import com.shuorigf.bluetooth.streetlamp.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * Created by clx on 2017/10/30.
 */
public class MainActivity extends BaseActivity {
    private static final int REQUEST_ENABLE_BT = 1;

    @BindView(R.id.main_bottom_tabs)
    TabLayout mBottomTabs;
    @BindView(R.id.main_bottom_viewpager)
    ViewPager mViewpager;
    @BindArray(R.array.main_bottom_title)
    TypedArray mTabTitles;
    @BindArray(R.array.main_bottom_icon)
    TypedArray mTabIcons;

    private List<Fragment> fragments = new ArrayList<>();

    private Handler mHandler = new Handler();


    /**
     * get layout resources id
     *
     * @return layoutRes
     */
    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {
        initVP();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                connectLast();
            }
        }, 2000);
    }


    private void initVP() {
        fragments.add(HomePageFragment.newInstance());
        fragments.add(WorkModeFragment.newInstance());
        fragments.add(HistoricalDataFragment.newInstance());
        fragments.add(AboutUsFragment.newInstance());
        mViewpager.setAdapter(new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        mViewpager.setOffscreenPageLimit(fragments.size());
        mBottomTabs.setupWithViewPager(mViewpager);
        for (int i = 0; i < mBottomTabs.getTabCount(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.main_bottom_tab, null, false);
            TextView tab = view.findViewById(R.id.tv_main_bottom_tab);
            tab.setText(mTabTitles.getText(i));
            tab.setCompoundDrawablesWithIntrinsicBounds(null, mTabIcons.getDrawable(i), null, null);
            if ( mBottomTabs.getTabAt(i) != null)
                 mBottomTabs.getTabAt(i).setCustomView(view);
        }

        mBottomTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (fragments.size() > position) {
//                    if (fragments.get(position) instanceof HomePageFragment) {
//                        ((HomePageFragment) fragments.get(position)).refreshData();
//                    }

                    if (fragments.get(position) instanceof WorkModeFragment) {
                        ((WorkModeFragment) fragments.get(position)).changeState();
                    }

                    if (fragments.get(position) instanceof HistoricalDataFragment) {
                        ((HistoricalDataFragment) fragments.get(position)).changeState();
                    }
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


    @Override
    protected void onResume() {
        super.onResume();
        if (mLeScanner != null && mLeScanner.getBluetoothAdapter() != null) {
            if(!mLeScanner.getBluetoothAdapter().isEnabled()){
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
//            else {
//                connectLast();
//            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        ActivityManager.getInstance().appExit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            connectLast();
                        }
                    }, 5000);
                }
                break;
        }
    }

    private void connectLast() {
        String historyConnectStr = SPUtils.getInstance(this).getString(SPUtils.SP_HISTORY_CONNECT, "[]");
        ArrayList<BleDeviceInfo> mHistoryConnectList = GsonUtil.fromJson(historyConnectStr, new TypeToken<ArrayList<BleDeviceInfo>>(){});
        if (mHistoryConnectList != null && mHistoryConnectList.size() > 0) {
            if(mLeScanner != null && mLeScanner.getState() == LeScanner.STATE_DISCONNECTED) {
                mLeScanner.connect(mHistoryConnectList.get(mHistoryConnectList.size() - 1).mMacAddress);
            }
        }
    }
}
