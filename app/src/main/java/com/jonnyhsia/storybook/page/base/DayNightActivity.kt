package com.jonnyhsia.storybook.page.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import com.jonnyhsia.storybook.helper.StatusBarColorUtils
import com.jonnyhsia.storybook.utils.Preference

/**
 * Created by JonnyHsia on 17/10/30.
 * 支持日夜间显示的 Activity
 */
@SuppressLint("Registered")
open class DayNightActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isNightMode by Preference(this, Preference.IS_NIGHT_MODE, false)
        StatusBarColorUtils.setStatusBarDarkIcon(this, !isNightMode)
    }
}