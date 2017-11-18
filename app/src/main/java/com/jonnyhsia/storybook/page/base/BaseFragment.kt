package com.jonnyhsia.storybook.page.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.View
import com.jonnyhsia.storybook.helper.toast
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.jonnyhsia.storybook.helper.checkNotNull
import com.jonnyhsia.storybook.helper.jump
import com.jonnyhsia.storybook.helper.snack
import kotlin.properties.Delegates


/**
 * Created by JonnyHsia on 17/10/29.
 * Base Fragment
 */
open class BaseFragment<T : BasePresenter> : Fragment() {

    var presenter: T by Delegates.notNull()

    fun bindPresenter(presenter: T) {
        this.presenter = presenter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.start()
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    /**
     * 布局是否已经被销毁
     * @return View 依然活跃
     */
    fun isActive() = view.checkNotNull()

    /**
     * 跳转 Activity
     */
    fun jump(uriString: String) {
        activity?.jump(uriString)
    }

    /**
     * 返回
     */
    fun goBack() {
        activity?.onBackPressed()
    }

    /**
     * 显示信息
     */
    fun showMessage(@StringRes msgRes: Int, level: Int = LEVEL_TOAST, view: View? = null) {
        showMessage(getString(msgRes), level, view)
    }

    fun showMessage(msg: String, level: Int = LEVEL_TOAST, view: View? = null) {
        when (level) {
            LEVEL_TOAST_IMPORTANT -> toast(msg, Toast.LENGTH_LONG)
            LEVEL_SNACK -> view?.snack(msg)
            else -> toast(msg)
        }
    }

    companion object {
        const val LEVEL_TOAST = 0
        const val LEVEL_TOAST_IMPORTANT = 1
        const val LEVEL_SNACK = 2
    }

    /**
     * 隐藏软键盘
     */
    fun hideSoftKeyboard() {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
}