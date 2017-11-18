package com.jonnyhsia.storybook.router

/**
 * Created by JonnyHsia on 17/11/4.
 * 跳转目标 Mapping
 *
 * @param schema        跳转的协议
 * @param target        跳转的目标 (Web 页面统一 WebViewActivity)
 * @param paramKeys     参数的键名集合
 * @param requestCode   请求的 Code
 * @param mustLogin     是否需要登录
 */
@Suppress("MemberVisibilityCanPrivate")
data class Mapping(val schema: String = "native",
                   val target: Class<*>,
                   val paramKeys: List<String>? = null,
                   val requestCode: Int? = null,
                   val mustLogin: Boolean = false)