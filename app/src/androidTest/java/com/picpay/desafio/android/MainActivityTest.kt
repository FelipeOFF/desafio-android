package com.picpay.desafio.android

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.RecyclerViewMatchers.checkRecyclerViewItem
import com.picpay.desafio.android.gateway.mock.PicPayMockWebServer
import com.picpay.desafio.android.gateway.mock.PicPayMockWebServer.MOCK_SERVER_PORT
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private val mockServer: MockWebServer by lazy {
        MockWebServer().apply {
            dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/users" -> PicPayMockWebServer.successUserResponse
                        else -> PicPayMockWebServer.errorUserResponse
                    }
                }
            }
        }
    }

    @Before
    fun setup() {
        mockServer.start(MOCK_SERVER_PORT)
    }

    @After
    fun tearDown() {
        mockServer.close()
    }

    @Test
    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {
            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayListItem() {
        launchActivity<MainActivity>().apply {
            checkRecyclerViewItem(R.id.recyclerView, 0, withText("@eduardo.santos"))
            checkRecyclerViewItem(R.id.recyclerView, 0, withText("Eduardo Santos"))
        }
    }
}