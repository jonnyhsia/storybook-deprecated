package com.jonnyhsia.storybook.biz.story

import com.jonnyhsia.storybook.app.AppError
import com.jonnyhsia.storybook.biz.OnEmpty
import com.jonnyhsia.storybook.biz.OnError
import com.jonnyhsia.storybook.biz.OnSubscribe
import com.jonnyhsia.storybook.biz.entity.Story

/**
 * Created by JonnyHsia on 17/11/18.
 * StoryLocalDataSource
 */
class StoryLocalDataSource : StoryDataSource {

    override fun saveDraft(title: String, content: String) {
        // TODO
    }

    override fun getUserTimeline(username: String,
                                 offset: Int,
                                 limit: Int,
                                 onSubscribe: OnSubscribe,
                                 onSuccess: OnGetUserTimelineSuccess,
                                 onEmpty: OnEmpty,
                                 onError: OnError) {
        throw AppError.INCORRECT_LOCAL_REQUEST.exception
    }

    override fun getStory(storyId: Long,
                          onSubscribe: OnSubscribe,
                          onSuccess: OnGetStorySuccess,
                          onError: OnError) {
        throw AppError.INCORRECT_LOCAL_REQUEST.exception
    }

    override fun deleteStory(story: Story,
                             onSubscribe: OnSubscribe,
                             onSuccess: OnDeleteStorySuccess,
                             onFailed: OnError) {
        throw AppError.INCORRECT_LOCAL_REQUEST.exception
    }

    override fun publishStory(title: String,
                              content: String,
                              onSubscribe: OnSubscribe,
                              onSuccess: OnPublishStorySuccess,
                              onFailed: OnError) {
        throw AppError.INCORRECT_LOCAL_REQUEST.exception
    }

    override fun updateStory(storyId: Long,
                             title: String,
                             content: String,
                             onSubscribe: OnSubscribe,
                             onSuccess: OnPublishStorySuccess,
                             onFailed: OnError) {
        throw AppError.INCORRECT_LOCAL_REQUEST.exception
    }

}