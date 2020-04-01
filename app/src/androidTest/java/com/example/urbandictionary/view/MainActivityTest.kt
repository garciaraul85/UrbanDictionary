package com.example.urbandictionary.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.urbandictionary.R
import com.example.urbandictionary.view.CustomAssertions.Companion.hasItemCount
import com.example.urbandictionary.view.CustomMatchers.Companion.typeSearchViewText
import com.example.urbandictionary.view.CustomMatchers.Companion.withRecyclerView
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun onLaunchCheckAmountInputIsDisplayed() {
        // launch activity
        ActivityScenario.launch(MainActivity::class.java)

        // check searchview is visible
        onView(withId(R.id.word_search)).check(matches(isDisplayed()))

        // enter text in searchview
        onView(withId(R.id.word_search)).perform(typeSearchViewText("words"))

        // check there are 10 items
        onView(withId(R.id.rvNews))
            .check(hasItemCount(3))

        // Check item at position 0 has "The greatest [video game]/[card game]/[tv show] of all time"
        onView(withRecyclerView(R.id.rvNews)?.atPosition(0))
            .check(matches(hasDescendant(withText("word1"))));
    }

}