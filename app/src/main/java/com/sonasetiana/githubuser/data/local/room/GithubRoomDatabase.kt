package com.sonasetiana.githubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sonasetiana.githubuser.data.local.room.dao.FavoriteDao
import com.sonasetiana.githubuser.data.model.FavoriteData

@Database(entities = [FavoriteData::class], version = 1)
abstract class GithubRoomDatabase : RoomDatabase(){
    abstract fun favoriteDao() : FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE : GithubRoomDatabase? = null

        fun getDatabase(context: Context) : GithubRoomDatabase {
            if (INSTANCE == null) {
                synchronized(GithubRoomDatabase::class.java) {
                    INSTANCE = Room
                        .databaseBuilder(
                            context.applicationContext,
                            GithubRoomDatabase::class.java,
                            "gidhub.db"
                        ).build()
                }
            }
            return INSTANCE as GithubRoomDatabase
        }
    }
}