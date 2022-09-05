package com.sonasetiana.githubuser.presentation.modules.home

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sonasetiana.githubuser.base.BaseViewModel
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.data.remote.network.NetworkResults
import com.sonasetiana.githubuser.domain.home.HomeUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val useCase: HomeUseCase
) : BaseViewModel() {

    private var keyword: String = ""

    private val allData = MutableLiveData<NetworkResults<List<UserData>>>()

    private val searchingData = MutableLiveData<NetworkResults<List<UserData>>>()

    private val debound = Runnable {
        if (keyword.isNotEmpty()) {
            requestSearch()
        } else {
            requestAllUsers()
        }
    }

    private var handler : Handler? = null

    init {
        handler = Handler(Looper.getMainLooper())
    }

    fun searching(keyword: String) {
        handler?.removeCallbacks(debound)
        this.keyword = keyword
        handler?.postDelayed(debound, 500)
    }

    fun getAllUsers() : LiveData<NetworkResults<List<UserData>>> {
        return allData
    }

    fun requestAllUsers() {
        viewModelScope.launch {
            useCase.getAllUsers().collect {
                allData.value = it
            }
        }
    }

    fun requestSearch() {
        viewModelScope.launch {
            useCase.searchUser(keyword).collect {
                searchingData.value = it
            }
        }
    }

    fun successSearchUser() : LiveData<NetworkResults<List<UserData>>> {
        return searchingData
    }

    override fun onCleared() {
        super.onCleared()
        handler = null
    }
}