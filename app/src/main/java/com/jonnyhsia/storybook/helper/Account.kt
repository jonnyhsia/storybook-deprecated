package com.jonnyhsia.storybook.helper

import android.annotation.SuppressLint
import com.jonnyhsia.storybook.app.App
import com.jonnyhsia.storybook.utils.Preference

/**
 * Created by JonnyHsia on 17/10/30.
 * 已登录的账号
 */
@SuppressLint("StaticFieldLeak")
object Account {

    var username by Preference(App.INSTANCE, Preference.USERNAME, "")
}