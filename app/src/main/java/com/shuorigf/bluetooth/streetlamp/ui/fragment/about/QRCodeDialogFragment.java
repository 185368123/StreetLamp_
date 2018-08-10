package com.shuorigf.bluetooth.streetlamp.ui.fragment.about;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.shuorigf.bluetooth.streetlamp.R;

import butterknife.ButterKnife;

/**
 * Created by clx on 2017/12/19.
 */

public class QRCodeDialogFragment extends DialogFragment {


    public static QRCodeDialogFragment newInstance() {
        Bundle bundle = new Bundle();
        QRCodeDialogFragment fragment = new QRCodeDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code_dialog, container, false);
        ButterKnife.bind(this, view);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

}
