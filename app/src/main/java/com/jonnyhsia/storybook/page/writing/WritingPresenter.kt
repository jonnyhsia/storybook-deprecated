package com.jonnyhsia.storybook.page.writing

import com.jonnyhsia.storybook.helper.logd
import com.jonnyhsia.storybook.biz.Injection
import com.jonnyhsia.storybook.biz.story.StoryRepository
import io.reactivex.disposables.CompositeDisposable

class WritingPresenter(private val view: WritingContract.View,
                       private var storyId: Long,
                       private var storyTitle: String,
                       private var storyContent: String,
                       private var isNewStory: Boolean) : WritingContract.Presenter {

    private val storyRepository: StoryRepository = Injection.getStoryRepository()

    private val disposable = CompositeDisposable()

    override fun start() {
        view.initUIData(storyTitle, storyContent)
    }

    override fun resume() {
    }

    override fun saveStory(title: String, content: String) {
        storyRepository.saveDraft(title, content)
        view.showSaveSuccess()
    }

    override fun publishStory(title: String, content: String) {
        view.showPublishing()
        /*storyRepository.publishStory(storyId, title, content,
                onSubscribe = {
                    disposable.add(disposable)
                },
                onSuccess = {
                    view.showPublishSuccess()
                    if (storyId != it) {
                        logd("New Story ID: $it($storyId)", "Publish")
                        storyId = it
                    }
                    if (isNewStory) {
                        storyRepository.clearDraft()
                    }
                    view.goBack()
                },
                onFailed = {
                    view.showPublishFailed(it.message.toString())
                })*/
    }

    override fun destroy() {
        disposable.clear()
    }
}