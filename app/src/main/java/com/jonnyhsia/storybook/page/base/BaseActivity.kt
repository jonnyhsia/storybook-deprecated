package com.jonnyhsia.storybook.page.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jonnyhsia.storybook.app.AppException

/**
 * Created by JonnyHsia on 17/10/29.
 * Base Activity
 * TODO 考虑迁移到扩展方法中
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    /**
     * 获取 Activity 的参数
     */
    fun arguments(): Bundle {
        return if (intent?.extras == null) {
            Bundle()
        } else {
            intent.extras
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getParamOrDefault(key: String, default: T): T {
        val param: Any = when (default) {
            is Int -> intent.getIntExtra(key, default)
            is Double -> intent.getDoubleExtra(key, default)
            is Long -> intent.getLongExtra(key, default)
            is Float -> intent.getFloatExtra(key, default)
            is String -> intent.getStringExtra(key) ?: ""
            else -> throw AppException("Type is invalid")
        }
        return param as T
    }
}