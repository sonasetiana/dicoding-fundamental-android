package com.sonasetiana.githubuser.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUserData(
    @SerializedName("id")
    var id : Int,
    @SerializedName("login")
    var login: String,
    @SerializedName("avatar_url")
    var avatarUrl: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("company")
    var company: String,
    @SerializedName("blog")
    var blog: String,
    @SerializedName("location")
    var location: String,
    @SerializedName("public_repos")
    var publicRepos: Int,
    @SerializedName("followers")
    var followers: Int,
    @SerializedName("following")
    var following: Int,
    @SerializedName("created_at")
    var created_at: String,
    @SerializedName("updated_at")
    var updated_at: String
): Parcelable

@Parcelize
data class DetailUserResults(var data : ArrayList<DetailUserData>) : Parcelable
