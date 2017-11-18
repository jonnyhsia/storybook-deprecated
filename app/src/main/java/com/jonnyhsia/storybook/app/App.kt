package com.jonnyhsia.storybook.app

import android.app.Application
import android.content.pm.ApplicationInfo
import android.graphics.Typeface
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatDelegate
import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.helper.checkNotEmpty
import com.jonnyhsia.storybook.utils.Preference
import kotlin.properties.Delegates

/**
 * Created by JonnyHsia on 17/8/3.
 * Application
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        initialize()
    }

    @Suppress("UNUSED_VALUE")
    private fun initialize() {
        // TODO 写完登注册后记得改掉
        var username by Preference(this, Preference.USERNAME, "")
        if (username.checkNotEmpty()) {
            username = "supotato"
        }

        // 设置日夜间模式
        val isNightMode by Preference(this, Preference.IS_NIGHT_MODE, false)
        AppCompatDelegate.setDefaultNightMode(
                if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)

        DEBUG = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
    }

    companion object {

        /**
         * 是否处于调试模式
         */
        @JvmStatic
        var DEBUG = true

        /**
         * 全局上下文
         */
        @JvmStatic
        var INSTANCE: App by Delegates.notNull()
            private set

        /**
         * 字体
         */
        @JvmStatic
        val TYPEFACE: Typeface? by lazy {
            ResourcesCompat.getFont(INSTANCE, R.font.noto_sans)
        }
    }

}