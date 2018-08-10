package com.shuorigf.bluetooth.streetlamp.ui.fragment.workmode;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.util.Constants;
import com.shuorigf.bluetooth.streetlamp.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by clx on 2017/12/20.
 */

public class SetDataDialogFragment extends DialogFragment {


    @BindView(R.id.tv_set_data_dialog_title)
    TextView mTitleTv;
    @BindView(R.id.edt_set_data_dialog_content)
    EditText mContentEdt;
    @BindView(R.id.tv_set_data_dialog_unit)
    TextView mUnitTv;

    public static SetDataDialogFragment newInstance(int title, String value, String unit) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.SET_DATA_TITLE, title);
        bundle.putString(Constants.SET_DATA_VALUE, value);
        bundle.putString(Constants.SET_DATA_UNIT, unit);
        SetDataDialogFragment fragment = new SetDataDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_data_dialog, container, false);
        ButterKnife.bind(this, view);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
        return view;
    }

    private void init() {
        int strRes = getArguments().getInt(Constants.SET_DATA_TITLE);
//        if (strRes == R.string.light_control_delay) {
//            mContentEdt.setInputType(InputType.TYPE_CLASS_NUMBER);
//        }
        mTitleTv.setText(strRes);
        mUnitTv.setText(getArguments().getString(Constants.SET_DATA_UNIT));
        mContentEdt.setText(getArguments().getString(Constants.SET_DATA_VALUE));
    }


    @OnClick({R.id.iv_set_data_dialog_close, R.id.btn_set_data_dialog_cancel, R.id.btn_set_data_dialog_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_set_data_dialog_close:
                dismiss();
                break;
            case R.id.btn_set_data_dialog_cancel:
                dismiss();
                break;
            case R.id.btn_set_data_dialog_save:
                save();
                break;
        }
    }

    private void save() {
        String contentStr = mContentEdt.getText().toString().trim();
        if (TextUtils.isEmpty(contentStr) || contentStr.equals(".")){
            ToastUtil.showShortToast(getContext(), R.string.hint_input_value);
            return;
        }
        if (mOnSetDataListener != null) {
            mOnSetDataListener.onSetData(contentStr);
        }
        dismiss();
    }


    private OnSetDataListener mOnSetDataListener;

    public interface OnSetDataListener {
        void onSetData(String data);
    }

    public void setOnSetDataListener(OnSetDataListener onSetDataListener) {
        this.mOnSetDataListener = onSetDataListener;
    }
}
