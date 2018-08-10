package com.shuorigf.bluetooth.streetlamp.ui.fragment.workmode;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by clx on 2017/12/20.
 */

public class SwitchButtonDialogFragment extends DialogFragment {


    @BindView(R.id.tv_switch_button_dialog_title)
    TextView mTitleTv;
    @BindView(R.id.tv_switch_button_dialog_content)
    TextView mContentTv;
    @BindView(R.id.chk_switch_button)
    CheckBox mSwitchButtonChk;

    public static SwitchButtonDialogFragment newInstance(int title, boolean value) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.SWITCH_BUTTON_TITLE, title);
        bundle.putBoolean(Constants.SWITCH_BUTTON_VALUE, value);
        SwitchButtonDialogFragment fragment = new SwitchButtonDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_switch_button_dialog, container, false);
        ButterKnife.bind(this, view);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
        return view;
    }

    private void init() {
        mTitleTv.setText(getArguments().getInt(Constants.SWITCH_BUTTON_TITLE));
        mContentTv.setText(getArguments().getInt(Constants.SWITCH_BUTTON_TITLE));
        mSwitchButtonChk.setChecked(getArguments().getBoolean(Constants.SWITCH_BUTTON_VALUE));
    }


    @OnClick({R.id.iv_switch_button_dialog_close, R.id.btn_switch_button_dialog_cancel, R.id.btn_switch_button_dialog_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_switch_button_dialog_close:
                dismiss();
                break;
            case R.id.btn_switch_button_dialog_cancel:
                dismiss();
                break;
            case R.id.btn_switch_button_dialog_save:
                if (mOnSwitchButtonListener != null) {
                    mOnSwitchButtonListener.onSwitchButton(mSwitchButtonChk.isChecked());
                }
                dismiss();
                break;
        }
    }


    private OnSwitchButtonListener mOnSwitchButtonListener;

    public interface OnSwitchButtonListener {
        void onSwitchButton(boolean value);
    }

    public void setOnSetDataListener(OnSwitchButtonListener onSwitchButtonListener) {
        this.mOnSwitchButtonListener = onSwitchButtonListener;
    }
}
