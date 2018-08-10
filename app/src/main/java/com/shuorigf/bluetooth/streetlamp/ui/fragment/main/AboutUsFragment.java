package com.shuorigf.bluetooth.streetlamp.ui.fragment.main;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.adapter.AboutUsAdapter;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;
import com.shuorigf.bluetooth.streetlamp.data.IconText;
import com.shuorigf.bluetooth.streetlamp.ui.fragment.about.QRCodeDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by clx on 2017/10/30.
 */

public class AboutUsFragment extends BaseFragment {

    @BindView(R.id.rv_about_us_content)
    RecyclerView mContentRv;
    @BindArray(R.array.about_us_title)
    TypedArray mTitle;
    @BindArray(R.array.about_us_icon)
    TypedArray mIcon;

    private AboutUsAdapter mAboutUsAdapter;


    public static AboutUsFragment newInstance() {
        Bundle args = new Bundle();
        AboutUsFragment fragment = new AboutUsFragment();
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
        return R.layout.fragment_about_us;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {
        List<IconText> list = new ArrayList<>();
        for (int i = 0; i < mTitle.length(); i++) {
            list.add(new IconText(mTitle.getResourceId(i, 0), mIcon.getResourceId(i, 0)));
        }
        mAboutUsAdapter = new AboutUsAdapter(list);
        mContentRv.setNestedScrollingEnabled(false);
        mContentRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mContentRv.setItemViewCacheSize(list.size());
        mContentRv.setAdapter(mAboutUsAdapter);
    }

    /**
     * init data
     */
    @Override
    public void initData() {
        super.initData();
    }

    /**
     * init event
     */
    @Override
    protected void initEvent() {
        mAboutUsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Uri uri = null;
                switch (mTitle.getResourceId(i, 0)) {
                    case R.string.company_official_website:
                        uri = Uri.parse(getString(R.string.company_official_website_content));
                        break;
                    case R.string.engineering_case:
                        uri = Uri.parse(getString(R.string.engineering_case_website_content));
                        break;
                }
                if (uri != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }

            }
        });
    }


    @OnClick(R.id.iv_about_us_qr_code)
    public void onViewClicked() {
        showQRCodeDialog();
    }

    private void showQRCodeDialog() {
        QRCodeDialogFragment qrCodeDialogFragment = QRCodeDialogFragment.newInstance();
        qrCodeDialogFragment.show(getChildFragmentManager(), "");
    }
}
