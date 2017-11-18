package com.jonnyhsia.storybook.page.main

import android.os.Bundle
import android.support.v4.app.Fragment
import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.app.AppError
import com.jonnyhsia.storybook.app.AppException
import com.jonnyhsia.storybook.helper.addFragmentSafely
import com.jonnyhsia.storybook.page.base.DayNightActivity
import com.jonnyhsia.storybook.page.main.activities.ActivitiesFragment
import com.jonnyhsia.storybook.page.main.activities.ActivitiesPresenter
import com.jonnyhsia.storybook.page.main.discover.DiscoverFragment
import com.jonnyhsia.storybook.page.main.discover.DiscoverPresenter
import com.jonnyhsia.storybook.page.main.self.SelfFragment
import com.jonnyhsia.storybook.page.main.self.SelfPresenter
import com.jonnyhsia.storybook.page.main.timeline.TimelineFragment
import com.jonnyhsia.storybook.page.main.timeline.TimelinePresenter
import com.jonnyhsia.storybook.router.Router
import com.jonnyhsia.uilib.widget.BottomNavigation
import kotlinx.android.synthetic.main.activity_main.bottomNavigation
import java.util.Arrays

class MainActivity : DayNightActivity() {
    private val fragmentTags = arrayOf("timeline", "discover", "", "activities", "self")

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoTitle)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var indexState = 0
        if (savedInstanceState != null) {
            indexState = savedInstanceState.getInt(STATE_INDEX, 0)
        }

        setupBottomNavigation(indexState)
        addFragmentSafely(R.id.container, fragmentTags[indexState], {
            fragmentOfPosition(indexState)
        })
    }

    /**
     * 创建与设置 Bottom Navigation
     */
    private fun setupBottomNavigation(index: Int = 0) {
        val navItems = Arrays.asList(
                BottomNavigation.BottomNavItem("故事", R.mipmap.ic_timeline),
                BottomNavigation.BottomNavItem("合辑", R.mipmap.ic_stories),
                BottomNavigation.BottomNavItem("光影", R.mipmap.ic_cal),
                BottomNavigation.BottomNavItem("我的", R.mipmap.ic_me))

        bottomNavigation.setNavItems(navItems)
            .addPrimarySelectListener {
                Router.jump(this, Router.URI_CREATE_STORY)
            }
            .addItemSelectListener { oldPos, pos, _ ->
                navigate(oldPos, pos)
            }
            .addItemReselectListener { pos, _ ->
                (fragmentOfPosition(pos) as? OnScroll2Top)?.scroll2Top()
            }
            .apply {
                activeIndex = index
            }
    }

    /**
     * 导航到各个位置的 Fragment
     */
    private fun navigate(from: Int, to: Int) {
        val fromFragment = fragmentOfPosition(from)
        val toFragment = fragmentOfPosition(to) ?: return

        val xaction = supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.popup_enter, R.anim.popup_exit)
            .hide(fromFragment)

        if (toFragment.isAdded) {
            xaction.show(toFragment)
        } else {
            xaction.add(R.id.container, toFragment, fragmentTags[to])
        }

        xaction.commit()
    }

    /**
     * 返回各个位置上的 Fragment
     */
    private fun fragmentOfPosition(pos: Int): Fragment {
        val tag = fragmentTags.getOrNull(pos) ?: throw AppError.INCORRECT_FRAGMENT_POS.exception
        return supportFragmentManager.findFragmentByTag(tag) ?: generateFragmentByPos(pos)
    }

    /**
     * 根据 pos 创建不同的 Fragment 实例
     */
    private fun generateFragmentByPos(pos: Int): Fragment {
        return when (pos) {
            0 -> {
                TimelineFragment().also { it.bindPresenter(TimelinePresenter(it)) }
            }
            1 -> {
                DiscoverFragment().also { it.bindPresenter(DiscoverPresenter(it)) }
            }
            3 -> {
                ActivitiesFragment().also { it.bindPresenter(ActivitiesPresenter(it)) }
            }
            4 -> {
                SelfFragment().also { it.bindPresenter(SelfPresenter(it)) }
            }
            else -> throw AppException("The position of home fragment position is invalid!")
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(STATE_INDEX, bottomNavigation.activeIndex)
    }

    companion object {
        private val STATE_INDEX = "state_index"
    }
}
