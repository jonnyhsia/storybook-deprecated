package com.jonnyhsia.storybook.helper

import android.content.Context
import android.widget.Toast

/**
 * Created by JonnyHsia on 17/10/29.
 * Toast Util
 */
object ToastUtil {
    private var toast: Toast? = null

    fun showToast(context: Context?, message: String?, duration: Int = Toast.LENGTH_SHORT) {
        if (context == null || message == null || message.isEmpty()) {
            return
        }

        toast?.cancel()
        toast = Toast.makeText(context, message, duration)
        toast?.show()
    }
}