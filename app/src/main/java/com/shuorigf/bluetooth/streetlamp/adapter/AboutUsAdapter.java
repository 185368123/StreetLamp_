package com.shuorigf.bluetooth.streetlamp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.data.IconText;

import java.util.List;


/**
 * Created by clx on 2017/10/30.
 */

public class AboutUsAdapter extends BaseQuickAdapter<IconText, BaseViewHolder> {

    public AboutUsAdapter(List<IconText> data) {
        super(R.layout.rv_item_about_us_content, data);
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, IconText iconText) {;
        baseViewHolder.setImageResource(R.id.iv_icon, iconText.icon);
        baseViewHolder.setText(R.id.tv_title, iconText.title);
        switch (iconText.title) {
            case R.string.company_name:
                baseViewHolder.setText(R.id.tv_content, R.string.company_name_content);
                break;
            case R.string.company_address:
                baseViewHolder.setText(R.id.tv_content, R.string.company_address_content);
                break;
            case R.string.customer_hotline:
                baseViewHolder.setText(R.id.tv_content, R.string.customer_hotline_content);
                break;
            case R.string.company_official_website:
                baseViewHolder.setText(R.id.tv_content, R.string.company_official_website_content);
                break;
            case R.string.contact_mailbox:
                baseViewHolder.setText(R.id.tv_content, R.string.contact_mailbox_content);
                break;
            case R.string.engineering_case:
                baseViewHolder.setText(R.id.tv_content, "");
                break;
        }
    }
}
