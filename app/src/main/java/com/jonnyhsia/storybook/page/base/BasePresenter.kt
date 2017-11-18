package com.jonnyhsia.storybook.page.base

/**
 * Created by JonnyHsia on 17/10/15.
 * Presenter 层的基础接口
 */
interface BasePresenter {

    /**
     * 在 View 的 onActivityCreated() 中调用
     */
    fun start()

    /**
     * 在 View 的 onResume() 中调用
     */
    fun resume()

    /**
     * 在 View 的 onDestroyView() 中调用
     */
    fun destroy()
}