package com.jonnyhsia.storybook.page.pagenotfound

import com.jonnyhsia.storybook.page.base.BasePresenter
import com.jonnyhsia.storybook.page.base.BaseView

/**
 * Created by JonnyHsia on 17/11/5.
 * PageNotFoundContract
 */
class PageNotFoundContract {

    interface Presenter : BasePresenter {

        /**
         * 点击反馈按钮
         */
        fun clickFeedback(feedback: String)
    }

    interface View : BaseView<Presenter> {

    }
}