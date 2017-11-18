package com.jonnyhsia.storybook.page.main.discover

import com.jonnyhsia.storybook.page.base.BasePresenter
import com.jonnyhsia.storybook.page.base.BaseView
import me.drakeet.multitype.Items

/**
 * Created by JonnyHsia on 17/11/7.
 * Discover Contract
 */
class DiscoverContract {

    interface Presenter : BasePresenter {
        fun clickBanner(pos: Int)

    }

    interface View : BaseView<Presenter> {

        // TODO 强制刷新页面
        fun showForceLoading()

        // TODO 参数待补全
        fun showLoadBannerComplete(items: Items)

        // TODO 参数待补全
        fun showLoadSubscriptionComplete(items: Items)
    }
}