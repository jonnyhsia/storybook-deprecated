package com.jonnyhsia.storybook.page.pagenotfound

import com.jonnyhsia.storybook.helper.checkNotEmpty

class PageNotFoundPresenter(private val view: PageNotFoundContract.View)
    : PageNotFoundContract.Presenter {

    override fun start() {
    }

    override fun resume() {
    }

    override fun destroy() {
    }

    override fun clickFeedback(feedback: String) {
        // TODO 将反馈信息发送到服务器
        if (feedback.checkNotEmpty()) {
            view.showMessage("Thanks for your feedback :)")
        }
    }
}