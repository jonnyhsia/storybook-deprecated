package com.jonnyhsia.storybook.biz

import com.jonnyhsia.storybook.biz.entity.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by JonnyHsia on 17/10/29.
 *
 * 应用接口
 */
interface AppApi {

    /**
     * 检查应用更新
     *
     * @param versionCode 当前应用的版本号
     * @param versionName 当前应用的版本名
     */
    @GET("check_for_updates")
    fun checkForUpdates(@Query("version_code") versionCode: Int,
                        @Query("version_name") versionName: String)
            : Single<Response<Any>>

    /**
     * 提交反馈
     *
     * @param feedbackTitle       反馈标题
     * @param feedbackDescription 反馈细节描述
     * @param tag                 反馈问题类型
     * @param username            反馈的用户 (用于反馈的跟进)
     */
    @POST("check_for_updates")
    fun sendFeedback(@Query("title") feedbackTitle: String,
                     @Query("description") feedbackDescription: String,
                     @Query("tag") tag: String,
                     @Query("username") username: String)
            : Single<Response<Any>>
}