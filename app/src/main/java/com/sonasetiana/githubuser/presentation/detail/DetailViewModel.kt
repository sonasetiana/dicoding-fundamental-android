package com.sonasetiana.githubuser.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sonasetiana.githubuser.base.BaseViewModel
import com.sonasetiana.githubuser.base.SingleLiveEvent
import com.sonasetiana.githubuser.data.GitHubRepository
import com.sonasetiana.githubuser.data.model.DetailUserData
import com.sonasetiana.githubuser.data.model.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel constructor(
    private val repository: GitHubRepository
): BaseViewModel(), DetailViewModelContract {
    
    private val loadingGetFollower = MutableLiveData<Boolean>()
    
    private val loadingGetFollowing = MutableLiveData<Boolean>()
    
    private val successGetDetailUser = MutableLiveData<DetailUserData>()
    
    private val successGetFollower = MutableLiveData<ArrayList<UserData>>()
    
    private val successGetFollowing = MutableLiveData<ArrayList<UserData>>()

    private val errorGetDetailUser = SingleLiveEvent<String>()
    
    private val errorGetFollower = SingleLiveEvent<String>()
    
    private val errorGetFollowing = SingleLiveEvent<String>()
    
    
    override fun getDetailUser(username: String) {
        setIsLoading(true)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getDetailUser(username = username)
            withContext(Dispatchers.Main) {
                setIsLoading(false)
                if (response.isSuccessful) {
                    response.body()?.let {
                        successGetDetailUser.value = it
                    }
                } else  {
                    errorGetDetailUser.value = "Error : ${response.message()} "
                }
            }
        }
    }

    override fun getFollower(username: String) {
        loadingGetFollower.value = true
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getFollower(username = username)
            withContext(Dispatchers.Main) {
                loadingGetFollower.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("DETAILFRAGMENT", "getFollower: ${it.size}")
                        successGetFollower.value = ArrayList(it)
                    }
                } else  {
                    errorGetFollower.value = "Error : ${response.message()} "
                }
            }
        }
    }

    override fun getFollowing(username: String) {
        loadingGetFollowing.value = true
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getFollowing(username = username)
            withContext(Dispatchers.Main) {
                loadingGetFollowing.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("DETAILFRAGMENT", "getFollowing: ${it.size}")
                        successGetFollowing.value = ArrayList(it)
                    }
                } else  {
                    errorGetFollowing.value = "Error : ${response.message()} "
                }
            }
        }
    }

    override fun loadingGetFollower(): LiveData<Boolean> = loadingGetFollower

    override fun loadingGetFollowing(): LiveData<Boolean> = loadingGetFollowing

    override fun successGetDetailUser(): LiveData<DetailUserData> = successGetDetailUser

    override fun successGetFollower(): LiveData<ArrayList<UserData>> = successGetFollower

    override fun successGetFollowing(): LiveData<ArrayList<UserData>> = successGetFollowing

    override fun errorGetDetailUser(): SingleLiveEvent<String> = errorGetDetailUser

    override fun errorGetFollower(): SingleLiveEvent<String> = errorGetFollower

    override fun errorGetFollowing(): SingleLiveEvent<String> = errorGetFollowing

}