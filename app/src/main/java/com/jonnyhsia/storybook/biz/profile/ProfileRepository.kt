package com.jonnyhsia.storybook.biz.profile

import com.jonnyhsia.storybook.biz.BaseRepository

/**
 * Created by JonnyHsia on 17/11/13.
 */
class ProfileRepository(private val profileLocalDataSource: ProfileLocalDataSource,
                        private val profileRemoteDataSource: ProfileRemoteDataSource)
    : BaseRepository(), ProfileDataSource {


}