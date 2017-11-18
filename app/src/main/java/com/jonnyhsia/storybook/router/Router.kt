package com.jonnyhsia.storybook.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.ArrayMap
import com.jonnyhsia.storybook.app.AppException
import com.jonnyhsia.storybook.helper.Account
import com.jonnyhsia.storybook.helper.checkNotEmpty
import com.jonnyhsia.storybook.helper.logd
import com.jonnyhsia.storybook.page.auth.AuthActivity
import com.jonnyhsia.storybook.page.writing.WritingActivity
import com.jonnyhsia.storybook.page.main.MainActivity
import com.jonnyhsia.storybook.page.storydetail.StoryDetailActivity
import com.jonnyhsia.storybook.page.pagenotfound.PageNotFoundActivity

/**
 * Created by JonnyHsia on 17/11/4.
 * 页面跳转路由
 */
@Suppress("MemberVisibilityCanPrivate")
object Router {

    /**
     * 暴露给外部调用的跳转方法
     */
    fun jump(context: Context, uriString: String) {
        // 解析 Uri 字符串并根据协议跳转到不同类型的 Activity
        val uri = Uri.parse(uriString) ?: return
        when (uri.scheme) {
            "native" -> startActivityWithNativeUri(context, uri)
            "http", "https" -> startActivityWithUrl(context, uri)
            else -> throw AppException("Uri scheme is invalid!")
        }
    }

    /**
     * 通过 Native Uri 跳转 Activity
     */
    private fun startActivityWithNativeUri(context: Context, uri: Uri) {
        // 匹配跳转的 Mapping
        val mapping = map.getOrDefault(
                uri.host, Mapping(target = PageNotFoundActivity::class.java))
        // DEBUG
        with(uri) { logd("Scheme: $scheme\nHost: $host\nParams: $queryParameterNames", "Router") }
        logd("$mapping", "Router")
        // 判断页面是否需要登录以及用户是否已经登录
        if (mapping.mustLogin && Account.username.isEmpty()) {
            startActivityWithNativeUri(context, Uri.parse(URI_AUTH))
            return
        }
        // 匹配参数键值
        val arguments = Bundle()
        mapping.paramKeys?.let {
            for (key in it) {
                val value = uri.getQueryParameter(key)
                if (value.checkNotEmpty()) {
                    arguments.putString(key, value)
                }
            }
        }

        execStartActivity(context, mapping.target, arguments, mapping.requestCode)
    }

    /**
     * 通过 HTTP(S) URL 跳转 Activity
     */
    private fun startActivityWithUrl(context: Context, uri: Uri) {
        // TODO 跳转到 WebViewActivity
    }

    /**
     * 执行 Activity 跳转
     *
     * @param target      跳转的目标
     * @param args        跳转携带的参数
     * @param requestCode 跳转请求码
     */
    private fun execStartActivity(context: Context, target: Class<*>, args: Bundle, requestCode: Int?) {
        val activity = context as? Activity ?: throw AppException("Context is unsuitable for start the activity!")
        val intent = Intent(context, target)
        intent.putExtras(args)

        if (requestCode == null) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 页面的匹配 Map
    ///////////////////////////////////////////////////////////////////////////

    private val map: Map<String, Mapping> = ArrayMap<String, Mapping>().apply {
        put("main", Mapping(target = MainActivity::class.java))
        put("auth", Mapping(target = AuthActivity::class.java))
        put("writing", Mapping(target = WritingActivity::class.java, mustLogin = false, paramKeys = listOf(WritingActivity.EXTRA_STORY_ID, WritingActivity.EXTRA_STORY_TITLE, WritingActivity.EXTRA_STORY_CONTENT, WritingActivity.EXTRA_STORY_TIME)))
        put("story_detail", Mapping(target = StoryDetailActivity::class.java, mustLogin = true))
    }

    ///////////////////////////////////////////////////////////////////////////
    // URI 常量
    ///////////////////////////////////////////////////////////////////////////

    // 主页
    val URI_MAIN = "native://main"
    // 登录注册页
    val URI_AUTH = "native://auth"
    // 故事创作, 编辑页
    val URI_CREATE_STORY = "native://writing"
    // 故事详情页
    val URI_STORY_DETAIL = "native://story_detail"

}
