package com.jonnyhsia.storybook.page.writing

import android.os.Bundle
import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.page.base.DayNightActivity
import com.jonnyhsia.storybook.utils.Preference

class WritingActivity : DayNightActivity() {

    private var fragment: WritingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        val storyId = getParamOrDefault(EXTRA_STORY_ID, -1L)
        val storyTitle: String
        val storyContent: String
        val isNewStory: Boolean

        // 判断是 创作? 还是 编辑?
        if (storyId == -1L) {
            // 尝试去加载草稿
            val prefTitle by Preference(this, Preference.DRAFT_TITLE, "")
            val prefContent by Preference(this, Preference.DRAFT_CONTENT, "")
            storyTitle = prefTitle
            storyContent = prefContent
            isNewStory = true
        } else {
            storyTitle = getParamOrDefault(EXTRA_STORY_TITLE, "")
            storyContent = getParamOrDefault(EXTRA_STORY_TITLE, "")
            isNewStory = false
        }

        fragment = WritingFragment().apply {
            bindPresenter(WritingPresenter(this, storyId, storyTitle, storyContent, isNewStory))
        }

        // TODO ???
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        fragment?.saveStory()
    }

    companion object {
        val EXTRA_STORY_ID = "story_id"
        val EXTRA_STORY_TITLE = "story_title"
        val EXTRA_STORY_CONTENT = "story_content"
        val EXTRA_STORY_TIME = "story_time"
    }
}