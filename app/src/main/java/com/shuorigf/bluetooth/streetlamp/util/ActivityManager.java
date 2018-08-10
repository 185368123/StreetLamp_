package com.shuorigf.bluetooth.streetlamp.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clx on 17/9/26.
 */

public final class ActivityManager {

    private static final String TAG = "ActivityManager";

    /**
     * 保存所有Activity
     */
    private volatile List<Activity> activities = new ArrayList<>();

    private static volatile ActivityManager activityManager;

    private ActivityManager() {

    }

    /**
     * 创建单例类，提供静态方法调用
     *
     * @return ActivityManager
     */
    public static ActivityManager getInstance() {
        if (activityManager == null) {
            synchronized (ActivityManager.class) {
                if (activityManager == null) {
                    activityManager = new ActivityManager();
                }
            }
        }
        return activityManager;
    }

    /**
     * 移除指定的Activity
     *
     * @param activity Activity
     */
    public void pop(Activity activity) {
        if (activity != null) {
            activity.finish();
            activities.remove(activity);
        }
    }

    /**
     * 移除指定的Activity
     *
     * @param cls Class 类名
     */
    public void popByClass(Class cls) {
        if (cls == null) {
            return;
        }
        for (Activity activity : activities) {
            if (null == activity || activity.getClass().equals(cls)) {
                continue;
            }
            activity.finish();
        }
    }

    /**
     * 移除所有Activity
     */
    public void popAll() {
        for (Activity activity : activities) {
            pop(activity);
        }
    }

    /**
     * 添加指定的Activity
     *
     * @param activity Activity
     */
    public void push(Activity activity) {
        if (size() > 6) {
            activities.get(1).finish();
        }
        activities.add(activity);
    }

    /**
     * 查找栈内的Activity
     *
     * @param name Manifests的Activity Name
     * @return activity
     */
    public Activity findByName(String name) {
        Activity activity = null;
        for (Activity act : activities) {
            if (name.equals(act.getLocalClassName())) {
                activity = act;
                break;
            }
        }
        return activity;
    }

    /**
     * 查找栈内的Activity
     *
     * @param cls 活动的Activity cls
     * @return activity
     */
    public Activity findByName(Class<? extends Activity> cls) {
        Activity activity = null;
        try {
            Activity temp = cls.newInstance();
            for (Activity act : activities) {
                if (temp.getLocalClassName()
                        .equals(act.getLocalClassName())) {
                    activity = act;
                    break;
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return activity;
    }

    /**
     * 栈顶的Activity(当前)
     *
     * @return activity
     */
    public Activity topOfStackActivity() {
        Activity activity = null;
        if (activities.size() != 0) {
            activity = activities.get(activities.size() - 1);
        }
        return activity;
    }

    /**
     * 栈底的Activity
     *
     * @return Activity Activity
     */
    public Activity bottomOfStackActivity() {
        Activity activity = null;
        if (activities.size() != 0) {
            activity = activities.get(0);
        }
        return activity;
    }

    public int size() {
        return activities.size();
    }


    public void appExit() {
        try {
            popAll();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception localException) {

        }
    }

}
