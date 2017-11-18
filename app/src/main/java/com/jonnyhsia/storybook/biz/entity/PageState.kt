package com.jonnyhsia.storybook.biz.entity

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

data class PageState(@StringRes var title: Int,
                     @StringRes var description: Int,
                     @DrawableRes var imgRes: Int)