package com.jonnyhsia.storybook.helper

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver

/**
 * simple and powerful Keyboard show/hidden listener
 * [android.R.id.content] and [ViewTreeObserver.OnGlobalLayoutListener]
 */
class KeyboardChangeListener(activity: Activity?) : ViewTreeObserver.OnGlobalLayoutListener {
    private var contentView: View? = null
    private var originHeight: Int = 0
    private var preHeight: Int = 0

    var onKeyboardChange: ((isShow: Boolean, keyboardHeight: Float) -> Unit)? = null

    init {
        if (activity == null) {
            Log.i(TAG, "activity is null")
        } else {
            contentView = findContentView(activity)
            addContentTreeObserver()
        }
    }

    private fun findContentView(contextObj: Activity): View {
        return contextObj.findViewById(android.R.id.content)
    }

    private fun addContentTreeObserver() {
        contentView?.viewTreeObserver?.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        val currHeight = contentView?.height ?: return
        if (currHeight == 0) {
            Log.i(TAG, "currHeight is 0")
            return
        }
        var hasChange = false
        if (preHeight == 0) {
            preHeight = currHeight
            originHeight = currHeight
        } else {
            if (preHeight != currHeight) {
                hasChange = true
                preHeight = currHeight
            } else {
                hasChange = false
            }
        }
        if (hasChange) {
            val isShow: Boolean
            var keyboardHeight = 0f
            if (originHeight == currHeight) {
                //hidden
                isShow = false
            } else {
                //show
                keyboardHeight = (originHeight - currHeight).toFloat()
                isShow = true
            }

            if (onKeyboardChange != null) {
                onKeyboardChange?.invoke(isShow, keyboardHeight)
            }
        }
    }

    fun destroy() {
        contentView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
    }

    companion object {
        private val TAG = "ListenerHandler"
    }
}