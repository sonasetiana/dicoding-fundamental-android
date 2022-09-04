package com.sonasetiana.githubuser.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.sonasetiana.githubuser.data.local.room.GithubRoomDatabase
import com.sonasetiana.githubuser.data.local.room.dao.FavoriteDao
import com.sonasetiana.githubuser.data.model.FavoriteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
@SmallTest
class GithubRoomTest {

    private lateinit var dao : FavoriteDao

    private lateinit var database : GithubRoomDatabase

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            GithubRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.favoriteDao()
    }

    @After
    fun after() {
        database.close()
    }

    @Test
    fun testGetFavoritesIsEmpty() = runBlocking {
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            dao.getFavorites().collect {
                assertThat(it).isEmpty()
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun testFindFavorites() = runBlocking {
        val favorite = FavoriteData(
            id = 1,
            userId = 1,
            login = "mojombo",
            name = "Tom Preston-Werner",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            createdAt = "2007-10-20T05:24:19Z"
        )
        dao.insert(favorite)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            dao.findFavorites(userId = 1).collect {
                val item = it.first()
                assertThat(item.login).isEqualTo("mojombo")
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun testInsertFavorites() = runBlocking {
        val favorite = FavoriteData(
            id = 1,
            userId = 1,
            login = "mojombo",
            name = "Tom Preston-Werner",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            createdAt = "2007-10-20T05:24:19Z"
        )
        dao.insert(favorite)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            dao.getFavorites().collect {
                assertThat(it).isNotEmpty()
                assertThat(it).contains(favorite)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun testUpdateFavorites() = runBlocking {
        val favorite = FavoriteData(
            id = 1,
            userId = 1,
            login = "mojombo",
            name = "Tom Preston-Werner",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            createdAt = "2007-10-20T05:24:19Z"
        )
        dao.insert(favorite)
        favorite.login = "menjomblo"

        dao.update(favorite)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            dao.getFavorites().collect {
                assertThat(it).isNotEmpty()
                assertThat(it).contains(favorite)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun testDeleteFavorites() = runBlocking {
        val favorite = FavoriteData(
            id = 1,
            userId = 1,
            login = "menjomblo",
            name = "Tom Preston-Werner",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            createdAt = "2007-10-20T05:24:19Z"
        )
        dao.insert(favorite)
        dao.delete(favorite)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            dao.getFavorites().collect {
                assertThat(it).isEmpty()
                assertThat(it).doesNotContain(favorite)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }



}