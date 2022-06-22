package com.android.mynewsapp.ui.headline

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.android.mynewsapp.R
import com.android.mynewsapp.data.network.*
import com.android.mynewsapp.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class)
class HeadlinesActivityTest{

    @get: Rule
    val activityRule = ActivityScenarioRule(HeadlinesActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    private val listItemInTest = 4
    private val testItem = FakeData.newsList[4]


    @Test
    fun test_isListVisible_onAppLaunch(){
        onView(withId(R.id.rv_headline_list)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectListItem_isDetailActivityVisible() {
        onView(withId(R.id.rv_headline_list))
            .perform(actionOnItemAtPosition<HeadlineAdapter.ViewHolder>(listItemInTest, click()))

        onView(withId(R.id.tv_description)).check(matches(withText(testItem.description)))
    }

    @Test
    fun test_backNavigation_toListActivity() {
        onView(withId(R.id.rv_headline_list))
            .perform(actionOnItemAtPosition<HeadlineAdapter.ViewHolder>(listItemInTest, click()))

        onView(withId(R.id.tv_description)).check(matches(withText(testItem.description)))

        pressBack()

        onView(withId(R.id.rv_headline_list)).check(matches(isDisplayed()))
    }

    @Test
    fun test_DetailActivity_validateList() {

        onView(withId(R.id.rv_headline_list))
            .perform(actionOnItemAtPosition<HeadlineAdapter.ViewHolder>(listItemInTest, click()))

        onView(withId(R.id.tv_headline)).check(matches(withText(testItem.title)))

        onView(withId(R.id.tv_description))
            .check(matches(withText(testItem.description)))
    }
}