package com.shuorigf.bluetooth.streetlamp.ui.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * Created by clx on 2017/11/1.
 */

public class HomepageTypeFragment extends BaseFragment {
    @BindView(R.id.rv_homepage_type_content)
    RecyclerView mContentRv;
    @BindArray(R.array.homepage_solar_panel_title)
    TypedArray mSolarPanelTitle;


    public static HomepageTypeFragment newInstance() {
        Bundle args = new Bundle();
        HomepageTypeFragment fragment = new HomepageTypeFragment();
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
        return R.layout.fragment_homepage_type;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {

    }

    /**
     * init data
     */
    @Override
    public void initData() {
       super.initData();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i<mSolarPanelTitle.length(); i++) {
            list.add(mSolarPanelTitle.getResourceId(i, 0));
        }
    }

    /**
     * init event
     */
    @Override
    protected void initEvent() {

    }

}
