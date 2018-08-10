package com.shuorigf.bluetooth.streetlamp.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.shuorigf.bluetooth.streetlamp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by clx on 2017/12/19.
 */

public class MessageDialogFragment extends DialogFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.ok)
    Button ok;
    @BindView(R.id.cancel)
    Button cancel;

    private String titleText = "";

    private String messageText = "";

    private boolean isShowTitle = true;// 是否显示标题

    private boolean isSingle = false;//是否单按钮显示

    private OnPositiveClickListener onPositiveClickListener = null;

    private OnNegativeClickListener onNegativeClickListener = null;

    public static MessageDialogFragment newInstance() {
        Bundle bundle = new Bundle();
        MessageDialogFragment fragment = new MessageDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_dialog, container, false);
        ButterKnife.bind(this, view);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //初始化数据
        title.setText(titleText);
        title.setVisibility(isShowTitle ? View.VISIBLE : View.GONE);
        message.setText(messageText);
        cancel.setVisibility(isSingle ? View.GONE : View.VISIBLE);
        return view;
    }

    @OnClick({R.id.ok, R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok:
                if (onPositiveClickListener != null) {
                    onPositiveClickListener.onPositiveClick(this, ok);
                }
                break;
            case R.id.cancel:
                if (onNegativeClickListener != null) {
                    onNegativeClickListener.onNegativeClick(this, cancel);
                }
                break;
        }
    }

    public MessageDialogFragment setTitle(String title) {
        titleText = title;
        if (this.title != null) {
            this.title.setText(title);
        }
        return this;
    }


    public MessageDialogFragment setMessage(String message) {
        messageText = message;
        if (this.message != null) {
            this.message.setText(message);
        }
        return this;
    }


    public MessageDialogFragment setShowTitle(boolean isShowTitle) {
        this.isShowTitle = isShowTitle;
        return this;
    }


    public MessageDialogFragment setSingle(boolean isSingle) {
        this.isSingle = isSingle;
        if (cancel != null) {
            cancel.setVisibility(isSingle ? View.GONE : View.VISIBLE);
        }
        return this;
    }

    public MessageDialogFragment setOnPositiveClickListener(OnPositiveClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
        return this;
    }

    public MessageDialogFragment setOnNegativeClickListener(OnNegativeClickListener onNegativeClickListener) {
        this.onNegativeClickListener = onNegativeClickListener;
        return this;
    }

    public interface OnPositiveClickListener {

        void onPositiveClick(MessageDialogFragment fragment, View ok);

    }

    public interface OnNegativeClickListener {

        void onNegativeClick(MessageDialogFragment fragment, View cancel);

    }

}
