package com.jonnyhsia.storybook.http

import android.util.Log
import com.jonnyhsia.storybook.biz.AppApi
import com.jonnyhsia.storybook.biz.story.StoryApi
import com.jonnyhsia.storybook.biz.profile.ProfileApi
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by JonnyHsia on 17/5/26.
 * Retrofit 提供器
 */
object RetrofitFactory {

    private val SERVER = ""
    private val URL_USER = "$SERVER/story/user/"
    private val URL_STORY = "$SERVER/story/story/"
    private val URL_APP = "$SERVER/story/app/"

    fun story() = StoryHolder.storyInstance

    fun app() = AppHolder.appInstance

    fun user() = UserHolder.userInstance

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.d("HttpLog", it)
        }).setLevel(HttpLoggingInterceptor.Level.BODY))
        .readTimeout(8, TimeUnit.SECONDS)
        .writeTimeout(8, TimeUnit.SECONDS)
        .build()

    private fun createRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    object StoryHolder {
        val storyInstance: StoryApi = createRetrofit(URL_STORY).create(StoryApi::class.java)
    }

    object AppHolder {
        val appInstance: AppApi = createRetrofit(URL_APP).create(AppApi::class.java)
    }

    object UserHolder {
        val userInstance: ProfileApi = createRetrofit(URL_USER).create(ProfileApi::class.java)
    }
}