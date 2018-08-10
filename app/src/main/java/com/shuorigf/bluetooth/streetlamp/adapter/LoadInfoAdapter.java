package com.shuorigf.bluetooth.streetlamp.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.data.LoadInfo;

import java.util.List;

/**
 * Created by clx on 2017/12/13.
 */

public class LoadInfoAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    private LoadInfo mLoadInfo;

    public LoadInfoAdapter(List<Integer> data, LoadInfo loadInfo) {
        super(R.layout.rv_item_homepage_type, data);
        mLoadInfo = loadInfo;
        if (mLoadInfo == null) {
            mLoadInfo = new LoadInfo();
        }
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, Integer integer) {
        TextView title = baseViewHolder.getView(R.id.tv_title);
        title.setText(integer);
        title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rounded_rectangle_blue_10dp, 0, 0, 0);
        switch (integer) {
            case R.string.load_brightness:
                baseViewHolder.setText(R.id.tv_content, mLoadInfo.brightness + "%");
                break;
            case R.string.voltage:
                baseViewHolder.setText(R.id.tv_content, mLoadInfo.voltage + "V");
                break;
            case R.string.load_power:
                baseViewHolder.setText(R.id.tv_content, mLoadInfo.power + "W");
                break;
            case R.string.current:
                baseViewHolder.setText(R.id.tv_content, mLoadInfo.current + "A");
                break;

        }

    }
}
