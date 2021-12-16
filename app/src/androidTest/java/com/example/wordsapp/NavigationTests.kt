package com.example.wordsapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTests {
    @Test
    fun navigate_to_word_nav_component() {
        // Create a test instance of the navigation controller
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        // Specify the fragment we want to launch and pass it
        // the app's theme so that the UI components know which
        // theme to use or the test may crash
        val letterListScenario = launchFragmentInContainer<LetterListFragment>(
            themeResId = R.style.Theme_Words
        )

        // Explicitly declare which navigation graph
        // we want the nav controller to use for the fragment launched.
        letterListScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // Trigger the event that prompts the navigation
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
        )

        assertEquals(navController.currentDestination?.id, R.id.wordListFragment)
    }
}