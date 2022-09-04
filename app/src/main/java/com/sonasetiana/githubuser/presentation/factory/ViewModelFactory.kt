package com.sonasetiana.githubuser.presentation.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sonasetiana.githubuser.data.local.dataStore.dataStore
import com.sonasetiana.githubuser.domain.DomainModule
import com.sonasetiana.githubuser.presentation.modules.detail.DetailViewModel
import com.sonasetiana.githubuser.presentation.modules.favorite.FavoriteViewModel
import com.sonasetiana.githubuser.presentation.modules.home.HomeViewModel
import com.sonasetiana.githubuser.presentation.modules.setting.SettingPreferences
import com.sonasetiana.githubuser.presentation.modules.setting.SettingViewModel

class ViewModelFactory constructor(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val useCase = DomainModule.provideHomeUseCase(application)
            HomeViewModel(useCase = useCase) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            val useCase = DomainModule.provideDetailUseCase(application)
            DetailViewModel(useCase = useCase) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            val useCase = DomainModule.provideFavoriteUseCase(application)
            FavoriteViewModel(useCase = useCase) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            val preferences = SettingPreferences.getInstance(application.dataStore)
            SettingViewModel(pref = preferences) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}