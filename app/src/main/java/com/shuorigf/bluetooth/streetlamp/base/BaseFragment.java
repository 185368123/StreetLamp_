package com.shuorigf.bluetooth.streetlamp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shuorigf.bluetooth.streetlamp.ble.LeScanner;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by clx on 17/9/26.
 */

public abstract class BaseFragment extends Fragment implements IContainer, EasyPermissions.PermissionCallbacks {

    private boolean isFirstLoad = true;
    private Unbinder unbinder;

    protected LeScanner mLeScanner;

    protected View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) root = inflater.inflate(getLayoutRes(), container, false);
        unbinder = ButterKnife.bind(this, root);
        mLeScanner = LeScanner.getInstance(getContext());
        init(savedInstanceState);
        initEvent();
        return root;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (root != null && isFirstLoad && isVisibleToUser) {
            initData();
            isFirstLoad = false;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isFirstLoad && getUserVisibleHint()) {
            initData();
            isFirstLoad = false;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //noinspection TryWithIdenticalCatches
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //权限管理
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void initData() {

    }

    protected abstract void initEvent();

}
