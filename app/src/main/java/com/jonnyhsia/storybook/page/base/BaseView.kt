package com.jonnyhsia.storybook.page.base

import android.support.annotation.StringRes
import android.view.View

/**
 * Created by JonnyHsia on 17/10/15.
 * View 层的基础接口
 */
interface BaseView<in T> {

    fun goBack()

    fun bindPresenter(presenter: T)

    fun showMessage(@StringRes msgRes: Int,
                    level: Int = BaseFragment.LEVEL_TOAST,
                    view: View? = null)

    fun showMessage(msg: String,
                    level: Int = BaseFragment.LEVEL_TOAST,
                    view: View? = null)

    fun jump(uriString: String)
}