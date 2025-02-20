package com.sweak.githubtrends.core.data.di

import com.sweak.githubtrends.core.data.GitHubRepositoryImpl
import com.sweak.githubtrends.core.domain.GitHubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataAccessorsModule {

    @Binds
    fun bindGitHubRepository(gitHubRepository: GitHubRepositoryImpl): GitHubRepository
}