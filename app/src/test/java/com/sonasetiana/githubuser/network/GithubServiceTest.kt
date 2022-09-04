package com.sonasetiana.githubuser.network

import com.sonasetiana.githubuser.data.model.DetailUserData
import com.sonasetiana.githubuser.data.model.SearchUserGitHubResponse
import com.sonasetiana.githubuser.data.remote.network.BaseInterceptor
import com.sonasetiana.githubuser.data.remote.services.GitHubService
import com.sonasetiana.githubuser.network.ZoneDateTimeProvider.loadTimeZone
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class GithubServiceTest {

    private lateinit var service : GitHubService

    private lateinit var mockWebServer : MockWebServer

    @Before
    fun before() {
        ZoneDateTimeProvider.loadTimeZone()
        mockWebServer = MockWebServer()
        val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(BaseInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(GitHubService::class.java)
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetAllUsers() {
        enqueueResponse(fileName = "github_user.json")
        runTest {
            val request = async { service.getAllUsers() }
            val response = request.await()
            val items = response.body() as ArrayList
            Assert.assertEquals(30, items.size)
            val item = items.first()
            Assert.assertEquals(1, item.id)
            Assert.assertEquals("mojombo", item.login)
        }
    }

    @Test
    fun testSearchUser() {
        enqueueResponse(fileName = "github_search_user.json")
        runTest {
            val request = async { service.searchUser("mojombo") }
            val response = request.await()
            val body = response.body() as SearchUserGitHubResponse
            val items = body.items
            Assert.assertEquals(1, body.total_count)
            Assert.assertEquals(false, body.incomplete_results)
            Assert.assertEquals(1, items.size)
            val item = items.first()
            Assert.assertEquals(1, item.id)
            Assert.assertEquals("mojombo", item.login)

        }
    }

    @Test
    fun testGetDetailUser() {
        enqueueResponse(fileName = "github_detail_user.json")
        runTest {
            val request = async { service.getDetailUser("mojombo") }
            val response = request.await()
            val body = response.body() as DetailUserData

            Assert.assertEquals(1, body.id)
            Assert.assertEquals("mojombo", body.login)
            Assert.assertEquals("Tom Preston-Werner", body.name)

        }
    }

    @Test
    fun testGetFollowing() {
        enqueueResponse(fileName = "github_user_following.json")
        runTest {
            val request = async { service.getFollowing("mojombo") }
            val response = request.await()
            val items = response.body() as ArrayList
            Assert.assertEquals(11, items.size)
            val item = items.first()
            Assert.assertEquals(2, item.id)
            Assert.assertEquals("defunkt", item.login)
        }
    }

    @Test
    fun testGetFollower() {
        enqueueResponse(fileName = "github_user_follower.json")
        runTest {
            val request = async { service.getFollowing("mojombo") }
            val response = request.await()
            val items = response.body() as ArrayList
            Assert.assertEquals(30, items.size)
            val item = items.first()
            Assert.assertEquals(2, item.id)
            Assert.assertEquals("defunkt", item.login)
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for((key, values) in headers) {
            mockResponse.addHeader(key, values)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)))
    }
}