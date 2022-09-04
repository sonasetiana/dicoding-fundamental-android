package com.sonasetiana.githubuser.data.local.room.dao

import androidx.room.*
import com.sonasetiana.githubuser.data.model.FavoriteData
import kotlinx.coroutines.flow.Flow

interface FavoriteDao {
    @Query("SELECT * from favorite ORDER BY id DESC")
    fun getFavorites() : Flow<List<FavoriteData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(values: FavoriteData)

    @Update
    fun update(values: FavoriteData)

    @Delete
    fun delete(values: FavoriteData)
}