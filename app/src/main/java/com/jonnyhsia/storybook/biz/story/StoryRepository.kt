package com.jonnyhsia.storybook.biz.story

import com.jonnyhsia.storybook.biz.BaseRepository
import com.jonnyhsia.storybook.biz.OnEmpty
import com.jonnyhsia.storybook.biz.OnError
import com.jonnyhsia.storybook.biz.OnSubscribe
import com.jonnyhsia.storybook.biz.entity.Story

/**
 * Created by JonnyHsia on 17/11/13.
 *
 */
class StoryRepository(private val remoteDataSource: StoryRemoteDataSource,
                      private val localDataSource: StoryLocalDataSource)
    : BaseRepository(), StoryDataSource {

    companion object {
        @JvmStatic
        fun instance() = Holder.instance
    }

    override fun getUserTimeline(username: String,
                                 offset: Int,
                                 limit: Int,
                                 onSubscribe: OnSubscribe,
                                 onSuccess: OnGetUserTimelineSuccess,
                                 onEmpty: OnEmpty,
                                 onError: OnError) {
        // TODO 验证用户合法性 (是否过期等)
        remoteDataSource.getUserTimeline(username, offset, limit, onSubscribe, onSuccess, onEmpty, onError)
    }

    override fun getStory(storyId: Long,
                          onSubscribe: OnSubscribe,
                          onSuccess: OnGetStorySuccess,
                          onError: OnError) {
        remoteDataSource.getStory(storyId, onSubscribe, onSuccess, onError)
    }

    override fun deleteStory(story: Story,
                             onSubscribe: OnSubscribe,
                             onSuccess: OnDeleteStorySuccess,
                             onFailed: OnError) {
        remoteDataSource.deleteStory(story, onSubscribe, onSuccess, onFailed)
    }

    override fun publishStory(title: String,
                              content: String,
                              onSubscribe: OnSubscribe,
                              onSuccess: OnPublishStorySuccess,
                              onFailed: OnError) {
        remoteDataSource.publishStory(title, content, onSubscribe, onSuccess, onFailed)
    }

    override fun updateStory(storyId: Long,
                             title: String,
                             content: String,
                             onSubscribe: OnSubscribe,
                             onSuccess: OnPublishStorySuccess,
                             onFailed: OnError) {
        remoteDataSource.updateStory(storyId, title, content, onSubscribe, onSuccess, onFailed)
    }

    override fun saveDraft(title: String, content: String) {
        localDataSource.saveDraft(title, content)
    }

    private object Holder {
        @JvmStatic
        val instance: StoryRepository
                = StoryRepository(StoryRemoteDataSource(), StoryLocalDataSource())
    }
}
