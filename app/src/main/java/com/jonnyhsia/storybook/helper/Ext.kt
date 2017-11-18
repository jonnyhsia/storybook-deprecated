package com.jonnyhsia.storybook.helper

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.IdRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.app.App
import com.jonnyhsia.storybook.router.Router
import com.jonnyhsia.storybook.ui.SnackbarCompat


fun Activity.jump(uriString: String) {
    Router.jump(this, uriString)
}

/**
 * 设置布局全屏
 */
fun Activity.setFullVision(view: View) {
    view.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
}

/**
 * 设置背景颜色
 */
fun Activity.setWindowBackgroundColor(@ColorInt color: Int) {
    window?.setBackgroundDrawable(ColorDrawable(color))
}

/**
 * 替换 Fragment
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

/**
 * 添加 Fragment
 *
 * @param constructor 构造器
 * @param tag         标记
 */
inline fun AppCompatActivity.addFragmentSafely(containerId: Int = R.id.container,
                                               tag: String = "FRAG",
                                               constructor: () -> Fragment) {
    val fragment = supportFragmentManager.findFragmentByTag(tag) ?: constructor()
    supportFragmentManager.transact {
        add(containerId, fragment, tag)
    }
}

inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun Fragment.toast(msg: String?, duration: Int = Toast.LENGTH_SHORT) {
    ToastUtil.showToast(activity, msg, duration)
}

fun Context.toast(msg: String?, duration: Int = Toast.LENGTH_SHORT) {
    ToastUtil.showToast(this, msg, duration)
}

fun View.snack(msg: String?, duration: Int = Snackbar.LENGTH_SHORT)
        = SnackbarCompat.make(this, msg, duration)

fun Snackbar.getColor(color: Int) = view.resources.getColor(color)

fun <T : View> RecyclerView.ViewHolder.find(@IdRes id: Int): T? = itemView.findViewById<T>(id)

@Suppress("DEPRECATION")
fun TextView.setTintDrawable(resources: Resources,
                             drawableRes: Int,
                             colorRes: Int,
                             gravity: Int = Gravity.END) {
    setTintDrawable(resources.getDrawable(drawableRes), resources.getColor(colorRes), gravity)
}

fun TextView.setTintDrawable(drawable: Drawable, tint: Int, gravity: Int = Gravity.END) {
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    drawable.setTint(tint)
    when (gravity) {
        Gravity.END -> setCompoundDrawables(null, null, drawable, null)
    }
}

fun Any.logd(msg: String, tag: String = javaClass.simpleName) {
    if (App.DEBUG) {
        Log.d(tag, msg)
    }
}

fun Any.loge(e: Throwable, msg: String? = null, tag: String = javaClass.simpleName) {
    if (App.DEBUG) {
        Log.e(tag, msg ?: e.message, e)
    }
}

fun String?.checkEmpty(): Boolean = this.isNullOrEmpty()

fun String?.checkNotEmpty() = !checkEmpty()

fun Any?.checkNull() = (null == this)

fun Any?.checkNotNull() = (null != this)