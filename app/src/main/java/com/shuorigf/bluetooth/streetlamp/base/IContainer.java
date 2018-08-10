package com.shuorigf.bluetooth.streetlamp.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

/**
 * Created by clx on 2017/9/26.
 */

public interface IContainer {

    /**
     * get layout resources id
     *
     * @return layoutRes
     */
    @LayoutRes
    int getLayoutRes();

    /**
     * init view
     *
     * @param savedInstanceState saved instance state
     */
    void init(Bundle savedInstanceState);

    /**
     * init data
     */
    void initData();

}
