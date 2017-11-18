package com.jonnyhsia.storybook.page.main.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.page.base.BaseFragment

/**
 * Created by JonnyHsia on 17/10/29.
 * Activities Fragment
 */
class ActivitiesFragment : BaseFragment<ActivitiesContract.Presenter>(), ActivitiesContract.View {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.fragment_activities, container, false)


}