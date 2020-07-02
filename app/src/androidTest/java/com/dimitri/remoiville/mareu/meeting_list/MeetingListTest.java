package com.dimitri.remoiville.mareu.meeting_list;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.dimitri.remoiville.mareu.R;
import com.dimitri.remoiville.mareu.service.MeetingApiService;
import com.dimitri.remoiville.mareu.di.DI;
import com.dimitri.remoiville.mareu.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.dimitri.remoiville.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Instrumented test
 */
@RunWith(AndroidJUnit4.class)
public class MeetingListTest {

    private final int ITEM_COUNT = 14;

    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        MainActivity activity = mActivityRule.getActivity();
        assertThat(activity, notNullValue());
        MeetingApiService apiService = DI.getMeetingApiService();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingsList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_meeting))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingList_deleteAction_shouldRemoveItem() {
        // Given : We have 6 meetings in the recycler view
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEM_COUNT));
        // When perform a click on a delete icon - We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_meeting))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 5 now
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEM_COUNT - 1));
    }

    @Test
    public void myMeetingList_filterByRoom() {
        //Given : Filter meeting on room called Yoshi
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEM_COUNT));
        ViewInteraction overflowMenuButton = onView(allOf(withContentDescription("More options"),
                childAtPosition(childAtPosition(withId(R.id.toolbar), 1), 0), isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction materialTextView = onView(allOf(withId(R.id.title), withText("Filtrer par salle"),
                childAtPosition(childAtPosition(withId(R.id.content), 0), 0), isDisplayed()));
        materialTextView.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything()).inAdapterView(allOf(withId(R.id.select_dialog_listview),
                childAtPosition(withId(R.id.contentPanel), 0))).atPosition(5);
        appCompatCheckedTextView.perform(click());

        ViewInteraction materialButton = onView(allOf(withId(android.R.id.button1), withText("OK"),
                childAtPosition(childAtPosition(withId(R.id.buttonPanel),0),3)));
        materialButton.perform(scrollTo(), click());

        // Then : the number of element is 4 now
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(4));
    }

    @Test
    public void myMeetingList_filterByDate() {
        //Given : Filter meeting on date 01/08/2020
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEM_COUNT));
        ViewInteraction overflowMenuButton = onView(allOf(withContentDescription("More options"),
                childAtPosition(childAtPosition(withId(R.id.toolbar), 1), 0), isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction materialTextView = onView(allOf(withId(R.id.title), withText("Filtrer par date"),
                        childAtPosition(childAtPosition(withId(R.id.content),0),0), isDisplayed()));
        materialTextView.perform(click());


        ViewInteraction materialButton = onView(allOf(withId(android.R.id.button1), withText("OK"),
                childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")),0),3)));
        materialButton.perform(scrollTo(), click());

        // Then : the number of element is 5 now
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(2));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}