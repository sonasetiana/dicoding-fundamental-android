package com.sonasetiana.githubuser.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sonasetiana.githubuser.base.BaseViewModel
import com.sonasetiana.githubuser.base.SingleLiveEvent
import com.sonasetiana.githubuser.data.GitHubRepository
import com.sonasetiana.githubuser.data.model.SearchUserGitHubResponse
import com.sonasetiana.githubuser.data.model.UserData
import kotlinx.coroutines.*

class MainViewModel constructor(
    private val repository: GitHubRepository
) : BaseViewModel(), MainViewModelContract {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorRequest.postValue("Exception handled: ${throwable.localizedMessage}")
    }

    private val successGetAllUsers = MutableLiveData<ArrayList<UserData>>()

    private val successSearchUser = MutableLiveData<ArrayList<UserData>>()

    private val errorRequest = SingleLiveEvent<String>()

    override fun getAllUsers() {
        setIsLoading(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getAllUsers()
            withContext(Dispatchers.Main) {
                setIsLoading(false)
                if (response.isSuccessful) {
                    response.body()?.let {
                        successGetAllUsers.value = ArrayList(it)
                    }
                } else  {
                    errorRequest.value = "Error : ${response.message()} "
                }
            }
        }
    }

    override fun searchUser(keyword: String) {
        setIsLoading(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.searchUser(keyword = keyword)
            withContext(Dispatchers.Main) {
                setIsLoading(false)
                if (response.isSuccessful) {
                    (response.body() as SearchUserGitHubResponse)?.let {
                        successSearchUser.value = it.items
                    }
                } else  {
                    errorRequest.value = "Error : ${response.message()} "
                }
            }
        }
    }

    override fun successGetAllUsers(): LiveData<ArrayList<UserData>> = successGetAllUsers

    override fun successSearchUser(): LiveData<ArrayList<UserData>> = successSearchUser

    override fun errorRequest(): SingleLiveEvent<String> = errorRequest
}