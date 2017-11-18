package com.jonnyhsia.storybook.page.main.self

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.page.base.BaseFragment

/**
 * Created by JonnyHsia on 17/10/29.
 *
 * 个人中心 Fragment
 */
class SelfFragment : BaseFragment<SelfContract.Presenter>(), SelfContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_self, container, false)
    }
}