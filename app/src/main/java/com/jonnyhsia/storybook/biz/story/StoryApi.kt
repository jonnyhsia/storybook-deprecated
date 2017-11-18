package com.jonnyhsia.storybook.biz.story

import com.jonnyhsia.storybook.biz.entity.Response
import com.jonnyhsia.storybook.biz.entity.Story
import io.reactivex.Single
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by JonnyHsia on 17/10/29.
 *
 * 故事接口
 */
interface StoryApi {

    /**
     * 发布故事
     *
     * @param storyMap 故事信息 Map
     */
    @POST("publish")
    @FormUrlEncoded
    fun publishStory(@FieldMap storyMap: Map<String, String>)
            : Single<Response<Long>>

    /**
     * 获取用户时间线
     *
     * @param username 用户名
     * @param offset   页数
     * @param limit    每页故事篇数
     */
    @GET("{username}/timeline")
    fun getUserTimeline(@Path("username") username: String,
                        @Query("offset") offset: Int = 0,
                        @Query("limit") limit: Int = 20)
            : Single<Response<List<Story>>>

    /**
     * 获取故事详情
     *
     * @param storyId 故事 ID
     */
    @GET("{story_id}")
    fun getStory(@Path("story_id") storyId: Long)
            : Single<Response<Story>>

    /**
     * 删除故事
     * TODO 后续添加 Token 验证身份
     *
     * @param storyId 故事 ID
     */
    @POST("{story_id}/delete")
    fun deleteStory(@Path("story_id") storyId: Long,
                    @Query("username") username: String)
            : Single<Response<Int>>
}