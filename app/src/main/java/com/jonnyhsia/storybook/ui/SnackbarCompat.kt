package com.jonnyhsia.storybook.ui

import android.support.design.internal.SnackbarContentLayout
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.widget.TextView
import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.helper.getColor
import com.jonnyhsia.storybook.helper.loge

/**
 * Created by JonnyHsia on 17/9/18.
 * 兼容性的 [Snackbar]
 */
object SnackbarCompat {

    private var done = false

    fun make(parent: View, text: String?, duration: Int = Snackbar.LENGTH_SHORT)
            : Snackbar {
        return Snackbar.make(parent, text.toString(), duration)
                .setActionTextColor(parent.resources.getColor(R.color.highlight))
                .apply {
                    // 设置 SnackBar 背景色与文字颜色
                    view.setBackgroundColor(getColor(R.color.windowBg))
                    val contentView = (view as? ViewGroup)?.getChildAt(0) as? SnackbarContentLayout
                    contentView?.findViewById<TextView>(android.support.design.R.id.snackbar_text)
                            ?.setTextColor(getColor(R.color.textPrimary))

                    // 解决 SnackBar 动画的问题
                    if (!done) {
                        try {
                            val accessibilityManagerField = BaseTransientBottomBar::class.java.getDeclaredField("mAccessibilityManager")
                            accessibilityManagerField.isAccessible = true
                            val accessibilityManager = accessibilityManagerField.get(this)
                            val isEnabledField = AccessibilityManager::class.java.getDeclaredField("mIsEnabled")
                            isEnabledField.isAccessible = true
                            isEnabledField.setBoolean(accessibilityManager, false)
                            accessibilityManagerField.set(this, accessibilityManager)
                            done = true
                        } catch (e: Exception) {
                            loge(e, "Reflection error: $e", "Snackbar")
                        }
                    }
                }
    }
}