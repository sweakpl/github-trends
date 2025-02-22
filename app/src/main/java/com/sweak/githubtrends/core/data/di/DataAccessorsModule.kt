package com.sweak.githubtrends.core.data.di

import com.sweak.githubtrends.core.data.github.GitHubRepositoryImpl
import com.sweak.githubtrends.core.data.user.UserDataRepositoryImpl
import com.sweak.githubtrends.core.domain.github.GitHubRepository
import com.sweak.githubtrends.core.domain.user.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataAccessorsModule {

    @Binds
    fun bindGitHubRepository(gitHubRepository: GitHubRepositoryImpl): GitHubRepository

    @Binds
    fun bindUserDataRepository(userDataRepository: UserDataRepositoryImpl) : UserDataRepository
}