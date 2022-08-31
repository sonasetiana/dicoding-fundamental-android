package com.sonasetiana.githubuser.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    @SerializedName("id")
    var id : Int,
    @SerializedName("login")
    var login: String,
    @SerializedName("avatar_url")
    var avatarUrl: String
) : Parcelable

@Parcelize
data class UserResults(var data : ArrayList<UserData>) : Parcelable

@Parcelize
data class SearchUserGitHubResponse(var items: ArrayList<UserData>): Parcelable
