package com.jonnyhsia.storybook.page.writing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jonnyhsia.storybook.R
import com.jonnyhsia.storybook.helper.checkEmpty
import com.jonnyhsia.storybook.helper.snack
import com.jonnyhsia.storybook.page.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_create_story.coordinatorLayout
import kotlinx.android.synthetic.main.fragment_create_story.inputStoryContent
import kotlinx.android.synthetic.main.fragment_create_story.inputStoryTitle
import kotlinx.android.synthetic.main.fragment_create_story.tvPublishStory
import kotlinx.android.synthetic.main.fragment_create_story.tvSaveStory

/**
 * 创建故事 Fragment
 */
class WritingFragment : BaseFragment<WritingContract.Presenter>(), WritingContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_create_story, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvSaveStory?.setOnClickListener { saveStory() }
        tvPublishStory?.setOnClickListener { publishStory() }
    }

    override fun initUIData(title: String, content: String) {
        inputStoryTitle.setText(title)
        inputStoryContent.setText(content)
    }

    /**
     * 保存故事
     */
    internal fun saveStory() {
        val storyTitle = inputStoryTitle?.text.toString()
        val storyContent = inputStoryContent?.text.toString()
        // 若内容全空则不保存故事
        if (storyTitle.isNotEmpty() && storyContent.isNotEmpty()) {
            return
        }

        presenter.saveStory(storyTitle, storyContent)
    }

    /**
     * 发布故事
     */
    private fun publishStory() {
        val storyTitle = inputStoryTitle?.text.toString()
        val storyContent = inputStoryContent?.text.toString()

        // 若内容全空则不保存故事
        if (storyContent.checkEmpty()) {
            coordinatorLayout.snack("故事内容不能为空")
            return
        }

        presenter.publishStory(storyTitle, storyContent)
    }

    override fun showSaveSuccess() {
        coordinatorLayout.snack("Save story successfully.")
                .setAction("结束", {
                    // 隐藏软键盘, 避免首页显示的错误
                    hideSoftKeyboard()
                    activity.finish()
                })
                .show()
    }

    override fun showPublishing() {
        tvPublishStory.isEnabled = false
        coordinatorLayout.snack("Publishing...").show()
    }

    override fun showPublishSuccess() {
        tvPublishStory.isEnabled = true
        hideSoftKeyboard()
    }

    override fun showPublishFailed(errorMsg: String) {
        tvPublishStory.isEnabled = true
        coordinatorLayout.snack("Publish story failed.").show()
    }
}