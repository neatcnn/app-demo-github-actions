package com.example.opsc6312demo1


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PostFragment2Test {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        // Make RetrofitClient use the test server
        RetrofitClient.changeBaseUrl(mockWebServer.url("/").toString())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testFragmentDisplaysFetchedPost() {
        val mockResponse = """
            {
                "userId": 1,
                "id": 2,
                "title": "Test Post Title",
                "body": "This is the body of the test post"
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(mockResponse).setResponseCode(200))

        val scenario = ActivityScenario.launch(MainActivity2::class.java)
        scenario.onActivity {
            it.supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, PostFragment2())
                .commitNow()
        }

        Thread.sleep(2000) // Wait for coroutine to complete (replace with IdlingResource in real tests)

        onView(allOf(withId(R.id.textTitle), withText("Test Post Title")))
            .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.textBody), withText(containsString("This is the body"))))
            .check(matches(isDisplayed()))
    }

}
