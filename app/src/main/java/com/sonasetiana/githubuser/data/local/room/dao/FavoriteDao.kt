package com.sonasetiana.githubuser.data.local.room.dao

import androidx.room.*
import com.sonasetiana.githubuser.data.model.FavoriteData
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * from favorite ORDER BY id DESC")
    fun getFavorites() : Flow<List<FavoriteData>>

    @Query("SELECT * from favorite where user_id = :userId")
    fun findFavorites(userId: Int) : Flow<List<FavoriteData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(values: FavoriteData)

    @Update
    fun update(values: FavoriteData)

    @Delete
    fun delete(values: FavoriteData)
}