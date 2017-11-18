package com.jonnyhsia.storybook.helper

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * Created by JonnyHsia on 17/10/31.
 * UI 帮助类
 */
object UIKits {

    /**
     * dp 转换为 px
     */
    fun dp2px(dp: Float, context: Context): Float {
        return dp2px(dp, context.resources.displayMetrics)
    }

    private fun dp2px(dp: Float, metrics: DisplayMetrics): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics)
    }

    fun px2sp(context: Context, pxValue: Float): Float {
        return pxValue / (context.resources.displayMetrics.scaledDensity)
    }

    fun tintDrawable(drawable: Drawable, colors: Int): Drawable {
        val wrappedDrawable = DrawableCompat.wrap(drawable).mutate()
        DrawableCompat.setTint(wrappedDrawable, colors)
        return wrappedDrawable
    }
}