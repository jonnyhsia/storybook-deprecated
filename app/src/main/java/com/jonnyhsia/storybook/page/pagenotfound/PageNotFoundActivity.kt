package com.jonnyhsia.storybook.page.pagenotfound

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.helper.StatusBarColorUtils

/**
 * 当路由找不到目标 Activity 时跳转到这个页面
 */
class PageNotFoundActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        StatusBarColorUtils.setStatusBarDarkIcon(this, false)

        val fragment = PageNotFoundFragment().apply {
            bindPresenter(PageNotFoundPresenter(this))
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }

}