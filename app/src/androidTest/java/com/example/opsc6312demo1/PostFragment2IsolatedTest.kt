package com.example.opsc6312demo1

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostFragment2IsolatedTest {

    private lateinit var scenario: FragmentScenario<PostFragment2>

    @Before
    fun setup() {
        // Launch the fragment in isolation in a container
        scenario = launchFragmentInContainer<PostFragment2>()
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }


    @Test
    fun testFragmentIsLaunched() {
        // If no crash, fragment is launched successfully.
        scenario.onFragment { fragment ->
            assert(fragment.isAdded)
        }
    }

    @Test
    fun testButtonClickStartsHandleJSONActivity() {
        // Click the button
        onView(withId(R.id.btnJsonHandler)).perform(click())

        // Check if correct Intent is fired
        Intents.intended(hasComponent(HandleJSONActivity::class.java.name))
    }

}