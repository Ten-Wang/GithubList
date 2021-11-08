package example.kotlin.teng.githublist

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import example.kotlin.teng.githublist.ui.list.UserItemPagingAdapter
import example.kotlin.teng.githublist.ui.list.UserListActivity
import org.hamcrest.Matcher
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("example.kotlin.teng.githublist", appContext.packageName)
    }

    @get:Rule
    val activityTestRule = ActivityScenarioRule(UserListActivity::class.java)

    // check recycler view in user list activity
    @Test
    fun checkUserListActivity() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    // check recycler view click in user list activity
    @Test
    fun clickUserListActivity() {
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<UserItemPagingAdapter.UserViewHolder>
                (0, MyViewAction.clickChildViewWithId(R.id.item_user_content)))
        onView(withId(R.id.tv_name)).check(matches(isDisplayed()))
    }

    // check close in user detail activity
    @Test
    fun checkCloseUserDetailActivity() {
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<UserItemPagingAdapter.UserViewHolder>
                (0, MyViewAction.clickChildViewWithId(R.id.item_user_content)))
        onView(withId(R.id.btn_close)).perform()
    }

    object MyViewAction {
        fun clickChildViewWithId(id: Int): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun getDescription(): String {
                    return "Click on a child view with specified id."
                }

                override fun perform(uiController: UiController?, view: View) {
                    val v: View = view.findViewById(id)
                    v.performClick()
                }
            }
        }
    }

}
