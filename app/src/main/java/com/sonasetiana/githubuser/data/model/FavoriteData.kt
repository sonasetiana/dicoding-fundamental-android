package com.sonasetiana.githubuser.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite")
@Parcelize
data class FavoriteData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "user_id")
    var userId: Int,
    @ColumnInfo(name = "login")
    var login: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String,
    @ColumnInfo(name = "created_at")
    var createdAt: String,
) : Parcelable
