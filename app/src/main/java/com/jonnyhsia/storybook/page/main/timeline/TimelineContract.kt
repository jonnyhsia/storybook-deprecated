package com.jonnyhsia.storybook.page.main.timeline

import android.support.annotation.StringRes
import com.jonnyhsia.storybook.biz.entity.Story
import com.jonnyhsia.storybook.page.base.BasePresenter
import com.jonnyhsia.storybook.page.base.BaseView
import me.drakeet.multitype.Items

/**
 * Created by JonnyHsia on 17/10/29.
 * 时间线的合同类
 */
class TimelineContract {

    interface Presenter : BasePresenter {
        /**
         * 请求时间线数据
         */
        fun requestTimeline()

        /**
         * 准备删除故事
         * @param pos
         */
        fun prepareToDeleteStory(pos: Int)

        /**
         * 取消删除故事
         */
        fun cancelDeleteStory(pos: Int)

        /**
         * 删除故事
         */
        fun deleteStory()

        fun readStory(pos: Int)

        fun clickSearchBar()

        fun clickAvatar()

        fun loadMoreStories()

        fun refreshTimeline()

        fun clickCreateStory()
    }

    interface View : BaseView<Presenter> {

        fun showSubTitle(subTitle: String)

        fun showEmptyState()

        fun showLoading()

        fun showLoadingError(@StringRes errorMsg: Int)

        fun showLoadingSuccess(timelineData: List<Story>)

        fun showCachedData(cachedTimelineData: List<Story>)

        fun showRemoveStory(items: Items, pos: Int)

        fun restoreStory(items: Items, pos: Int)

        fun showLoadedComplete()
    }
}