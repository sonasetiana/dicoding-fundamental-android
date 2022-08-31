package com.sonasetiana.githubuser.presentation.main

import androidx.lifecycle.LiveData
import com.sonasetiana.githubuser.base.BaseViewModelContact
import com.sonasetiana.githubuser.base.SingleLiveEvent
import com.sonasetiana.githubuser.data.model.UserData

interface MainViewModelContract : BaseViewModelContact{
    fun getAllUsers()

    fun searchUser(keyword: String)

    fun successGetAllUsers() : LiveData<ArrayList<UserData>>

    fun successSearchUser() : LiveData<ArrayList<UserData>>

    fun errorRequest() : SingleLiveEvent<String>

}