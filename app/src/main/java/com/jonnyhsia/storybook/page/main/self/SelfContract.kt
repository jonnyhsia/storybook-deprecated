package com.jonnyhsia.storybook.page.main.self

import com.jonnyhsia.storybook.page.base.BasePresenter
import com.jonnyhsia.storybook.page.base.BaseView

/**
 * Created by JonnyHsia on 17/11/7.
 * SelfContract
 */
class SelfContract {

    interface Presenter : BasePresenter {

    }

    interface View : BaseView<Presenter> {

    }
}