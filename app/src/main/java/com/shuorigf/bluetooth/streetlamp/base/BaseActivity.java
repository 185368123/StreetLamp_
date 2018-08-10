package com.shuorigf.bluetooth.streetlamp.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.shuorigf.bluetooth.streetlamp.R;
import com.shuorigf.bluetooth.streetlamp.ble.LeScanner;
import com.shuorigf.bluetooth.streetlamp.util.FragmentUtils;

import java.util.List;

import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by clx on 17/9/26.
 */

public abstract class BaseActivity extends AppCompatActivity implements IContainer, EasyPermissions.PermissionCallbacks {

    protected LeScanner mLeScanner;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        toolbar = findViewById(getToolbarId());
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);
        mLeScanner = LeScanner.getInstance(this);
        init(savedInstanceState);
        initData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (FragmentUtils.popBackStack(getSupportFragmentManager())) {
            return true;
        }
        finish();
        return super.onSupportNavigateUp();
    }

    @IdRes
    protected int getToolbarId() {
        return R.id.toolbar;
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

}
