package com.android.mynewsapp.ui.detailnews

import android.content.Context
import android.content.Intent
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.android.mynewsapp.R
import com.android.mynewsapp.data.network.FakeData
import com.android.mynewsapp.di.AppModule
import com.android.mynewsapp.other.Constant
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class)
class DetailNewsActivityTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val testItem = FakeData.newsList[1]
    private lateinit var targetContext : Context

    @Before
    fun setup(){
        targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, DetailNewsActivity::class.java)
        intent.putExtra(Constant.NEWS_DATA_KEY, testItem)
        launchActivity<DetailNewsActivity>(intent)
    }

    @Test
    fun test_isDetailVisible() {
        onView(withId(R.id.tv_description))
            .check(matches(withText(testItem.description)))
    }

    @Test
    fun test_favClick() {
        onView(withId(R.id.fav)).perform(click())
        onView(withId(R.id.fav)).check(matches(withContentDescription(R.string.fav_remove)))
    }

    @Test
    fun test_favDoubleClick() {
        onView(withId(R.id.fav)).perform(click())
        onView(withId(R.id.fav)).perform(click())
        onView(withId(R.id.fav)).check(matches(withContentDescription(R.string.fav_add)))
    }

    @Test
    fun test_shareClick() {
        Intents.init()
        val intent = allOf(
            hasAction(Intent.ACTION_SEND),
            hasType("text/plain"),
            hasExtra(Intent.EXTRA_TEXT, String.format(targetContext.getString(R.string.share_text), testItem.title, testItem.url)))

        val expectedIntent = allOf(
            hasAction(Intent.ACTION_CHOOSER),  // Intent.createChooser put your intent with the key EXTRA_INTENT
            hasExtra(`is`(Intent.EXTRA_INTENT), intent))

        onView(withId(R.id.share)).perform(click())
        intended(expectedIntent)
        Intents.release()
    }

}