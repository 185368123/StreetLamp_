package com.shuorigf.bluetooth.streetlamp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.base.BaseFragment;
import com.shuorigf.bluetooth.streetlamp.util.Constants;
import com.shuorigf.bluetooth.streetlamp.util.SPUtils;
import com.shuorigf.bluetooth.streetlamp.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by clx on 2017/10/28.
 */

public class LoginFragment extends BaseFragment {


    @BindView(R.id.edt_login_account)
    EditText mAccountEdt;
    @BindView(R.id.edt_login_password)
    EditText mPasswordEdt;

    private SPUtils mSPUtils;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
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
        return R.layout.fragment_login;
    }

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    @Override
    public void init(Bundle savedInstanceState) {
        mSPUtils = SPUtils.getInstance(getContext());
    }

    /**
     * init data
     */
    @Override
    public void initData() {
        super.initData();
        mAccountEdt.setText(mSPUtils.getString(SPUtils.SP_USER_ACCOUNT));
    }

    /**
     * init event
     */
    @Override
    protected void initEvent() {

    }


    @OnClick(R.id.iv_login_login)
    public void onViewClicked() {
        login();
    }


    private void login() {
        String accountStr = mAccountEdt.getText().toString().trim();
        String passwordStr = mPasswordEdt.getText().toString().trim();
        if (TextUtils.isEmpty(accountStr)) {
            ToastUtil.showShortToast(getContext(), R.string.please_enter_the_login_account);
            return;
        }

        if (TextUtils.isEmpty(passwordStr)) {
            ToastUtil.showShortToast(getContext(), R.string.please_enter_the_login_password);
            return;
        }

        if (Constants.USER_ACCOUNT.equals(accountStr) && Constants.USER_PASSWORD.equals(passwordStr)) {
            Constants.IS_LOGIN = true;
            mSPUtils.put(SPUtils.SP_USER_ACCOUNT, accountStr);
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }else {
            ToastUtil.showShortToast(getContext(), R.string.hint_account_or_password_error);
        }




    }
}
