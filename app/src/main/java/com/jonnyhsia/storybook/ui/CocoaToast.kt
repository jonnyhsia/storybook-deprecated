package com.jonnyhsia.storybook.ui

import android.support.annotation.IntDef
import android.widget.Toast

/**
 * Created by JonnyHsia on 17/11/6.
 * TODO Cocoa Toast 待实现
 * 暂用 [android.support.design.widget.Snackbar]
 * 或 [android.widget.Toast] 替代
 */
class CocoaToast private constructor() {

    @Duration
    private var duration = Toast.LENGTH_SHORT

    private var type = Type.MIXED

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(Toast.LENGTH_LONG.toLong(), Toast.LENGTH_SHORT.toLong())
    annotation class Duration

    /**
     * 显示 Cocoa Toast
     */
    fun show() {

    }

    class Builder {
        private val toast = CocoaToast()

        fun setDuration(@Duration duration: Int) {
            toast.duration = duration
        }

        fun setType(type: Type): Builder {
            toast.type = type
            return this
        }

        fun build(): CocoaToast {
            return toast
        }
    }

    /**
     * Cocoa toast 的类型
     */
    enum class Type {
        TEXT,
        IMAGE,
        MIXED
    }
}