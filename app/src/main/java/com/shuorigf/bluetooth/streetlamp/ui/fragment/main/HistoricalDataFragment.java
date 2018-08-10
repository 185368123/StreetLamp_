package com.shuorigf.bluetooth.streetlamp.ui.fragment.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;
import com.shuorigf.bluetooth.streetlamp.ui.activity.LoginActivity;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.historicaldata.HistoricalDataAdminBasicFragment;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.historicaldata.HistoricalDataAdminDataFragment;
import com.shuorigf.bluetooth.streetlamp.util.Constants;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by clx on 2017/10/30.
 */

public class HistoricalDataFragment extends BaseFragment {

    @BindView(R.id.tv_historical_data_logout_administrator)
    TextView mLogoutAdministratorTv;
    @BindView(R.id.tv_historical_data_tourist_model)
    TextView mTouristModelTv;
    @BindView(R.id.historical_data_tablayout)
    TabLayout mTabLayout;
    @BindArray(R.array.historical_data_admin_title)
    TypedArray mTabTitles;

    private HistoricalDataAdminBasicFragment mHistoricalDataAdminBasicFragment;
    private HistoricalDataAdminDataFragment mHistoricalDataAdminDataFragment;


    public static HistoricalDataFragment newInstance() {
        Bundle args = new Bundle();
        HistoricalDataFragment fragment = new HistoricalDataFragment();
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
        return R.layout.fragment_historical_data;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {
        initTabs();
    }

    private void initTabs() {
        for (int i = 0; i < mTabTitles.length(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.historical_data_top_tab, null, false);
            TextView tab = view.findViewById(R.id.tv_historical_data_top_tab);
            String tabTitle = mTabTitles.getString(i);
            tab.setText(tabTitle);
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(view));
        }
    }


    /**
     * init data
     */
    @Override
    public void initData() {
        super.initData();
        switchTab(R.string.basic_data);
    }

    /**
     * init event
     */
    @Override
    protected void initEvent() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchTab(mTabTitles.getResourceId(tab.getPosition(), 0));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @OnClick({R.id.tv_historical_data_logout_administrator, R.id.tv_historical_data_tourist_model})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_historical_data_logout_administrator:
                Constants.IS_LOGIN = false;
                changeState();
                break;
            case R.id.tv_historical_data_tourist_model:
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_LOGIN);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_LOGIN:
                if (resultCode == Activity.RESULT_OK){
                    changeState();
                }
                break;
        }
    }


    public void changeState() {
        if (Constants.IS_LOGIN) {
            mLogoutAdministratorTv.setVisibility(View.VISIBLE);
            mTouristModelTv.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.VISIBLE);
        }else {
            mTouristModelTv.setVisibility(View.VISIBLE);
            mLogoutAdministratorTv.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.GONE);
            switchTab(R.string.basic_data);
        }
    }


    public void switchTab(@StringRes int stringResId){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);


        switch (stringResId) {
            case R.string.basic_data:
                if (mHistoricalDataAdminBasicFragment == null) {
                    mHistoricalDataAdminBasicFragment = HistoricalDataAdminBasicFragment.newInstance();
                    transaction.add(R.id.historical_data_container, mHistoricalDataAdminBasicFragment);
                } else {
                    transaction.show(mHistoricalDataAdminBasicFragment);
                }
                break;

            case R.string.administrator_data:
                if (mHistoricalDataAdminDataFragment == null) {
                    mHistoricalDataAdminDataFragment = HistoricalDataAdminDataFragment.newInstance();
                    transaction.add(R.id.historical_data_container, mHistoricalDataAdminDataFragment);
                } else {
                    transaction.show(mHistoricalDataAdminDataFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();

        for (int i = 0; i < mTabTitles.length(); i++) {
            if (mTabTitles.getResourceId(i, 0) == stringResId) {
                mTabLayout.getTabAt(i).select();
            }
        }

    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mHistoricalDataAdminBasicFragment != null) {
            transaction.hide(mHistoricalDataAdminBasicFragment);
        }

        if (mHistoricalDataAdminDataFragment != null) {
            transaction.hide(mHistoricalDataAdminDataFragment);
        }

    }
}
