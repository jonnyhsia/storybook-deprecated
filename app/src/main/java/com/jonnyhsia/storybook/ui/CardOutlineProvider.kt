package com.jonnyhsia.storybook.ui

import android.graphics.Outline
import android.graphics.Rect
import android.view.View
import android.view.ViewOutlineProvider

/**
 * Created by JonnyHsia on 17/9/13.
 */
class CardOutlineProvider(var pxValue: Float = 0f,
                          var alpha: Float = 1.0f) : ViewOutlineProvider() {

    override fun getOutline(view: View?, outline: Outline?) {
        if (view != null && outline != null) {
            val rect = Rect(0, 0, view.width, view.height)
            outline.setRect(rect)
            outline.alpha = alpha
        }
    }
}