package com.jonnyhsia.storybook.biz

import com.jonnyhsia.storybook.app.AppError
import io.reactivex.disposables.Disposable

/**
 * Created by JonnyHsia on 17/11/11.
 * 通用回调方法别名定义
 */

// 通用的无入参成功回调
typealias OnSuccess = () -> Unit

// 通用的返回数据为空回调
typealias OnEmpty = () -> Unit

// 通用的失败回调
typealias OnError = (error: Throwable) -> Unit

// 订阅
typealias OnSubscribe = (d: Disposable) -> Unit