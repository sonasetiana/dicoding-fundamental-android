package com.sonasetiana.githubuser.presentation.modules.main

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.sonasetiana.githubuser.R
import com.sonasetiana.githubuser.presentation.modules.favorite.FavoriteAdapter
import com.sonasetiana.githubuser.presentation.modules.home.HomeAdapter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testLoading() {
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerViewLoaded() {
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
        Thread.sleep(1000)
    }

    @Test
    fun testRecyclerViewItemClicked() {
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_users)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HomeAdapter.Holder>(
                0,
                click()
            )
        )
        onView(withId(R.id.detail_progress_bar)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.content_view)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).perform(click())
        Thread.sleep(1000)
    }

    @Test
    fun testSearching() {
        onView(withId(R.id.search)).perform(click())
        val keyword = "mojombo"
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText(keyword), closeSoftKeyboard())
        Thread.sleep(500)
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
        Thread.sleep(1000)
    }

    @Test
    fun testClickButtonFavorite() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        onView(withText("Favorit")).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.rv_favorite)).check(matches(isDisplayed()))
        Thread.sleep(500)
        onView(withId(R.id.rv_favorite)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FavoriteAdapter.Holder>(
                0,
                click()
            )
        )
        onView(withId(R.id.detail_progress_bar)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.content_view)).check(matches(isDisplayed()))
        Thread.sleep(1000)
    }

    @Test
    fun testClickButtonSetting() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        onView(withText("Pengaturan")).perform(click())
        onView(withId(R.id.switch_theme)).perform(click())
        Thread.sleep(1000)
    }
}