package com.sonasetiana.githubuser.domain

import android.app.Application
import com.sonasetiana.githubuser.data.DataModule
import com.sonasetiana.githubuser.domain.detail.DetailInteractor
import com.sonasetiana.githubuser.domain.detail.DetailUseCase
import com.sonasetiana.githubuser.domain.favorite.FavoriteInteractor
import com.sonasetiana.githubuser.domain.favorite.FavoriteUseCase
import com.sonasetiana.githubuser.domain.home.HomeInteractor
import com.sonasetiana.githubuser.domain.home.HomeUseCase

object DomainModule {

    fun provideHomeUseCase(application: Application) : HomeUseCase {
        val repository = DataModule.provideRepository(application)
        return HomeInteractor(repository)
    }

    fun provideDetailUseCase(application: Application) : DetailUseCase {
        val repository = DataModule.provideRepository(application)
        return DetailInteractor(repository)
    }

    fun provideFavoriteUseCase(application: Application) : FavoriteUseCase {
        val repository = DataModule.provideRepository(application)
        return FavoriteInteractor(repository)
    }

}