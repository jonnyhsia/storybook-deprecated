package com.jonnyhsia.storybook.biz.entity

import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by JonnyHsia on 17/11/8.
 * 网格 Banner 实体类
 */
data class GridBanner(val bannerId: Long,
                      val bannerTitle: String,
                      val bannerImgUrl: String,
                      val bannerItems: List<GridBannerItem>) {

    data class GridBannerItem(val storyId: Long,
                              val title: String,
                              val description: String,
                              val imgUrl: String)

    companion object {

        private fun generate(): GridBanner {
            val bannerItems = Arrays.asList(
                    GridBannerItem(storyId = 1, title = "", description = "", imgUrl = "http://ou4f31a1x.bkt.clouddn.com/17-8-4/38058011.jpg"),
                    GridBannerItem(storyId = 2, title = "", description = "", imgUrl = "http://ou4f31a1x.bkt.clouddn.com/17-8-4/38058011.jpg"),
                    GridBannerItem(storyId = 3, title = "", description = "", imgUrl = "http://ou4f31a1x.bkt.clouddn.com/17-8-4/38058011.jpg"),
                    GridBannerItem(storyId = 4, title = "", description = "", imgUrl = "http://ou4f31a1x.bkt.clouddn.com/17-8-4/38058011.jpg"),
                    GridBannerItem(storyId = 5, title = "", description = "", imgUrl = "http://ou4f31a1x.bkt.clouddn.com/17-8-4/38058011.jpg"),
                    GridBannerItem(storyId = 6, title = "", description = "", imgUrl = "http://ou4f31a1x.bkt.clouddn.com/17-8-4/38058011.jpg"),
                    GridBannerItem(storyId = 7, title = "", description = "", imgUrl = "http://ou4f31a1x.bkt.clouddn.com/17-8-4/38058011.jpg"))
            return GridBanner(bannerId = 1, bannerTitle = "", bannerImgUrl = "http://ou4f31a1x.bkt.clouddn.com/17-8-4/38058011.jpg", bannerItems = bannerItems)
        }

        fun generateList(size: Int = 3): List<GridBanner> {
            val list = ArrayList<GridBanner>(size)
            for (i in 0 until 3) {
                list.add(generate())
            }
            return list
        }
    }
}