package com.sonasetiana.githubuser.presentation.modules.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sonasetiana.githubuser.base.BaseViewModel
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPreferences) : BaseViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}