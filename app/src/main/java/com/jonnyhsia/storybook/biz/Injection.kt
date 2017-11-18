package com.jonnyhsia.storybook.biz

import com.jonnyhsia.storybook.biz.story.StoryRepository

/**
 * Created by JonnyHsia on 17/10/29.
 * Injection
 */
object Injection {

    fun getStoryRepository() = StoryRepository.instance()

    // fun generateUserRepository() = UserRepository

    // fun generateAppRepository() = AppRepository
}