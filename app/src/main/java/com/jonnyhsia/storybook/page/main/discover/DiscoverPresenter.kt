package com.jonnyhsia.storybook.page.main.discover

import android.os.Handler
import com.jonnyhsia.storybook.biz.entity.GridBanner
import me.drakeet.multitype.Items

class DiscoverPresenter(private val view: DiscoverContract.View) : DiscoverContract.Presenter {

    /**
     * banner 的数据集合
     */
    private var bannerItems = Items()

    /**
     * 话题的数据集合
     */
    private var tagItems = Items()

    /**
     * 特色专栏数据
     */
    private var specialItemsList: List<Items> = ArrayList()

    override fun clickBanner(pos: Int) {
        val banner = bannerItems[pos] as? GridBanner ?: return
        // TODO 跳转到 Banner 详情页
        view.jump("${banner.bannerId}")
    }

    override fun start() {
        requestForBanners()
    }

    /**
     * 请求 Banner 数据
     */
    private fun requestForBanners() {
        // 模拟请求 3s 数据
        Handler().postDelayed({
            bannerItems.clear()
            bannerItems.addAll(GridBanner.generateList())
            view.showLoadBannerComplete(bannerItems)
        }, 3000)
    }

    override fun resume() {
    }

    override fun destroy() {
    }
}