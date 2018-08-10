package com.shuorigf.bluetooth.streetlamp.util;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by chenyp on 16/7/26.
 */
public final class FragmentUtils {

    private FragmentUtils() {

    }

    /**
     * 显示Fragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        Fragment实例
     */
    public static void showFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                              Fragment fragment) {
        if (!fragment.isAdded()) {
            addFragmentToActivity(containerId, fragmentManager, fragment);
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    /**
     * 隐藏Fragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        Fragment实例
     */
    public static void hideFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                              Fragment fragment) {
        if (!fragment.isAdded()) {
            addFragmentToActivity(containerId, fragmentManager, fragment);
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.hide(fragment);
        ft.commitAllowingStateLoss();
    }

    /**
     * addFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     */
    public static void addFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                             Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(containerId, fragment);
        ft.commit();
    }

    /**
     * addFragmentToBackStack
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     * @param tag             标记
     */
    public static void addFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                             Fragment fragment, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(containerId, fragment, tag);
        ft.addToBackStack(tag);
        ft.commit();
    }

    /**
     * replaceFragment
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例
     */
    public static void replaceFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                                 Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(containerId, fragment);
        ft.commit();
    }

    /**
     * replaceFragmentToBackStack
     *
     * @param containerId     容器ID
     * @param fragmentManager Fragment管理器
     * @param fragment        fragment实例标记
     * @param tag             标记
     */
    public static void replaceFragmentToActivity(@IdRes int containerId, FragmentManager fragmentManager,
                                                 Fragment fragment, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(containerId, fragment, tag);
        ft.addToBackStack(tag);
        ft.commit();
    }

    /**
     * 退出当前FragmentBackStack的一个实例
     *
     * @param fragmentManager
     * @return true : success  false: fail
     */
    public static boolean popBackStack(FragmentManager fragmentManager) {
        boolean isBackSuccess = false;
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            isBackSuccess = true;
        }
        return isBackSuccess;
    }

    /**
     * 退出当前FragmentBackStack一个指tag的实例
     *
     * @param tag             标记
     * @param fragmentManager Fragment管理器
     * @return true : success  false: fail
     */
    public static boolean popBackStack(String tag, FragmentManager fragmentManager) {
        boolean isBackSuccess = false;
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            isBackSuccess = true;
        }
        return isBackSuccess;
    }

    /**
     * 退出当前所有FragmentBackStack
     *
     * @param fragmentManager Fragment管理器
     */
    public static void clearBackStack(FragmentManager fragmentManager) {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
