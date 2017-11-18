package com.jonnyhsia.storybook.biz.profile

import com.jonnyhsia.storybook.entity.User
import com.jonnyhsia.storybook.biz.entity.Response
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by JonnyHsia on 17/10/29.
 *
 * 用户接口
 */
interface ProfileApi {

    /**
     * 注册账号
     *
     * @param user 用户注册信息 Map
     */
    @POST("user/register")
    @FormUrlEncoded
    fun register(@FieldMap user: Map<String, String>)
            : Observable<Response<User>>

    /**
     * 登录账号
     *
     * @param username 用户名
     * @param password 密码
     */
    @POST("login")
    @FormUrlEncoded
    fun login(@Field("username") username: String,
              @Field("password") password: String)
            : Observable<Response<User>>
}