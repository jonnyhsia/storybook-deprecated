package com.jonnyhsia.storybook.biz.story

import com.jonnyhsia.storybook.app.AppError
import com.jonnyhsia.storybook.biz.BaseRepository
import com.jonnyhsia.storybook.biz.OnEmpty
import com.jonnyhsia.storybook.biz.OnError
import com.jonnyhsia.storybook.biz.OnSubscribe
import com.jonnyhsia.storybook.biz.entity.Story
import com.jonnyhsia.storybook.http.RetrofitFactory
import com.jonnyhsia.storybook.http.RxHttpHandler
import com.jonnyhsia.storybook.http.RxHttpSchedulers

/**
 * Created by JonnyHsia on 17/11/13.
 * 故事接口实现
 */
class StoryRemoteDataSource : BaseRepository(), StoryDataSource {
    private val storyApi = RetrofitFactory.story()

    // 获取用户时间线
    override fun getUserTimeline(username: String,
                                 offset: Int,
                                 limit: Int,
                                 onSubscribe: OnSubscribe,
                                 onSuccess: OnGetUserTimelineSuccess,
                                 onEmpty: OnEmpty,
                                 onError: OnError) {
        storyApi.getUserTimeline(username, offset, limit)
            .compose(RxHttpSchedulers.composeSingle())
            .compose(RxHttpHandler.handleSingle())
            .doOnSubscribe(onSubscribe)
            .subscribe({ timeline ->
                if (timeline.isEmpty()) {
                    onEmpty()
                    return@subscribe
                }
                onSuccess(timeline)
            }, onError)
    }

    // 获取故事详情
    override fun getStory(storyId: Long,
                          onSubscribe: OnSubscribe,
                          onSuccess: OnGetStorySuccess,
                          onError: OnError) {
        storyApi.getStory(storyId)
            .compose(RxHttpSchedulers.composeSingle())
            .compose(RxHttpHandler.handleSingle())
            .doOnSubscribe(onSubscribe)
            .subscribe(onSuccess, onError)
    }

    // 删除指定 ID 的故事
    override fun deleteStory(story: Story,
                             onSubscribe: OnSubscribe,
                             onSuccess: OnDeleteStorySuccess,
                             onFailed: OnError) {
        // TODO
        val username = ""
        storyApi.deleteStory(story.storyId, username)
            .compose(RxHttpSchedulers.composeSingle())
            .compose(RxHttpHandler.handleSingle())
            .doOnSubscribe(onSubscribe)
            .doOnSuccess(onSuccess)
            .doOnError(onFailed)
    }

    // 发布新的故事
    override fun publishStory(title: String,
                              content: String,
                              onSubscribe: OnSubscribe,
                              onSuccess: OnPublishStorySuccess,
                              onFailed: OnError) {
        val author = ""
        val params = mapOf(
                "title" to title,
                "content" to content,
                "imgs" to "",
                "author" to author)
        storyApi.publishStory(params)
            .compose(RxHttpSchedulers.composeSingle())
            .compose(RxHttpHandler.handleSingle())
            .doOnSubscribe(onSubscribe)
            .subscribe(onSuccess, onFailed)
    }

    // 更新指定 ID 的故事
    override fun updateStory(storyId: Long,
                             title: String,
                             content: String,
                             onSubscribe: OnSubscribe,
                             onSuccess: OnPublishStorySuccess,
                             onFailed: OnError) {
        val author = ""
        val params = mapOf(
                "story_id" to storyId.toString(),
                "title" to title,
                "content" to content,
                "imgs" to "",
                "author" to author)
        storyApi.publishStory(params)
            .compose(RxHttpSchedulers.composeSingle())
            .compose(RxHttpHandler.handleSingle())
            .doOnSubscribe(onSubscribe)
            .subscribe(onSuccess, onFailed)
    }

    // 保存草稿
    override fun saveDraft(title: String, content: String) {
        throw AppError.INCORRECT_REMOTE_REQUEST.exception
    }
}