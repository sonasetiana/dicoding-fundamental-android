package com.sonasetiana.githubuser.presentation.detail

import androidx.lifecycle.LiveData
import com.sonasetiana.githubuser.base.BaseViewModelContact
import com.sonasetiana.githubuser.base.SingleLiveEvent
import com.sonasetiana.githubuser.data.model.DetailUserData
import com.sonasetiana.githubuser.data.model.UserData

interface DetailViewModelContract : BaseViewModelContact {

    fun getDetailUser(username: String)

    fun getFollower(username: String)

    fun getFollowing(username: String)

    fun loadingGetFollower() : LiveData<Boolean>

    fun loadingGetFollowing() : LiveData<Boolean>

    fun successGetDetailUser() : LiveData<DetailUserData>

    fun successGetFollower() : LiveData<ArrayList<UserData>>

    fun successGetFollowing() : LiveData<ArrayList<UserData>>

    fun errorGetDetailUser() : SingleLiveEvent<String>

    fun errorGetFollower() : SingleLiveEvent<String>

    fun errorGetFollowing() : SingleLiveEvent<String>
}