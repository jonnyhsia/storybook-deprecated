package com.jonnyhsia.storybook.page.storydetail

import android.os.Bundle

import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.page.base.DayNightActivity

class StoryDetailActivity : DayNightActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_detail)
    }
}
