package com.shuorigf.bluetooth.streetlamp.data;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * Created by clx on 2017/10/6.
 */

public class IconText {

    @NonNull
    @StringRes
    public Integer title;

    @NonNull
    @DrawableRes
    public Integer icon;

    public IconText(@NonNull @StringRes Integer title, @NonNull @DrawableRes Integer icon) {
        this.title = title;
        this.icon = icon;
    }

}
