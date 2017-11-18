package com.jonnyhsia.storybook.page.writing

import com.jonnyhsia.storybook.page.base.BasePresenter
import com.jonnyhsia.storybook.page.base.BaseView

/**
 * Created by JonnyHsia on 17/11/5.
 * 故事创建页的契约类
 */
class WritingContract {

    interface Presenter : BasePresenter {

        /**
         * 保存故事, 后续添加 meta data
         *
         * @param title   标题
         * @param content 内容
         */
        fun saveStory(title: String, content: String)

        /**
         * 发布故事
         *
         * @param title   标题
         * @param content 内容
         */
        fun publishStory(title: String, content: String)
    }

    interface View : BaseView<Presenter> {

        /**
         * 初始化界面数据
         *
         * @param time 若从草稿加载, 则还有草稿保存时间
         */
        fun initUIData(title: String, content: String)

        /**
         * 显示保存故事成功
         */
        fun showSaveSuccess()

        /**
         * 显示发布故事成功
         */
        fun showPublishSuccess()

        /**
         * 显示发布故事失败
         */
        fun showPublishFailed(errorMsg: String)

        /**
         * 正在发布故事
         */
        fun showPublishing()
    }
}