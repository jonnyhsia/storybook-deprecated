package com.jonnyhsia.storybook.biz.story

import com.jonnyhsia.storybook.biz.OnEmpty
import com.jonnyhsia.storybook.biz.OnError
import com.jonnyhsia.storybook.biz.OnSubscribe
import com.jonnyhsia.storybook.biz.entity.Story

typealias OnGetUserTimelineSuccess = (timeline: List<Story>) -> Unit
typealias OnGetStorySuccess = (story: Story) -> Unit
typealias OnDeleteStorySuccess = (Int) -> Unit
typealias OnPublishStorySuccess = (storyId: Long) -> Unit

interface StoryDataSource {

    /**
     * 获取用户时间线数据
     *
     * @param username 用户名
     * @param offset   页码
     * @param limit    每页篇数
     */
    fun getUserTimeline(username: String,
                        offset: Int,
                        limit: Int,
                        onSubscribe: OnSubscribe,
                        onSuccess: OnGetUserTimelineSuccess,
                        onEmpty: OnEmpty,
                        onError: OnError)

    /**
     * 获取故事详情
     *
     * @param storyId 故事的 ID
     */
    fun getStory(storyId: Long,
                 onSubscribe: OnSubscribe,
                 onSuccess: OnGetStorySuccess,
                 onError: OnError)

    /**
     * 请求删除故事, 并同步删除本地
     *
     * @param username  删除的故事的用户 TODO 待实现
     * @param story     删除的故事
     */
    fun deleteStory(story: Story,
                    onSubscribe: OnSubscribe,
                    onSuccess: OnDeleteStorySuccess,
                    onFailed: OnError)

    /**
     * 发布或更新故事
     *
     * @param title   故事标题
     * @param content 故事内容
     */
    fun publishStory(title: String,
                     content: String,
                     onSubscribe: OnSubscribe,
                     onSuccess: OnPublishStorySuccess,
                     onFailed: OnError)

    /**
     * 发布或更新故事
     *
     * @param storyId 故事 ID
     * @param title   故事标题
     * @param content 故事内容
     */
    fun updateStory(storyId: Long,
                    title: String,
                    content: String,
                    onSubscribe: OnSubscribe,
                    onSuccess: OnPublishStorySuccess,
                    onFailed: OnError)

    /**
     * 本地保存草稿
     *
     * @param title   故事标题
     * @param content 故事内容
     */
    fun saveDraft(title: String,
                  content: String)
}