package com.sonasetiana.githubuser.presentation.modules.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sonasetiana.githubuser.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    private val textChange = MutableLiveData<String>()

    fun setTextOnChange(keyword: String) {
        textChange.value = keyword
    }

    fun onTextChange(): LiveData<String> = textChange

}