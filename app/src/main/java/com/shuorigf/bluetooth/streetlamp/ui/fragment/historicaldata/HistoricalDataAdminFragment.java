package com.shuorigf.bluetooth.streetlamp.ui.fragment.historicaldata;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.adapter.CommonFragmentPagerAdapter;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * Created by clx on 2017/11/8.
 */

public class HistoricalDataAdminFragment extends BaseFragment {

    @BindView(R.id.historical_data_admin_tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.historical_data_admin_viewpager)
    ViewPager mViewPager;
    @BindArray(R.array.historical_data_admin_title)
    TypedArray mTabTitles;


    public static HistoricalDataAdminFragment newInstance() {
        Bundle args = new Bundle();
        HistoricalDataAdminFragment fragment = new HistoricalDataAdminFragment();
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
        return R.layout.fragment_historical_data_admin;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {
        initVP();
    }

    /**
     * init data
     */
    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

        }
    }

    private void initVP() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HistoricalDataAdminBasicFragment.newInstance());
        fragments.add(HistoricalDataAdminDataFragment.newInstance());

        mViewPager.setAdapter(new CommonFragmentPagerAdapter(getChildFragmentManager(), fragments) {
            @Override
            public CharSequence getPageTitle(int position) {
                return mTabTitles.getString(position);
            }
        });
        mViewPager.setOffscreenPageLimit(fragments.size());
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * init event
     */
    @Override
    protected void initEvent() {

    }

}
