package com.dimitri.remoiville.mareu.meeting_list;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.dimitri.remoiville.mareu.R;
import com.dimitri.remoiville.mareu.di.DI;
import com.dimitri.remoiville.mareu.model.Meeting;
import com.dimitri.remoiville.mareu.service.MeetingApiService;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.dimitri.remoiville.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddMeetingActivityTest {

    private MeetingApiService mApiService;

    @Rule
    public final ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        MainActivity activity = mActivityTestRule.getActivity();
        assertThat(activity, notNullValue());
        mApiService = DI.getMeetingApiService();
    }

    @Test
    public void myMeetingList_displayActivityAdd() {
        //Given: Click on the floating action button (addButton)
        ViewInteraction floatingActionButton = onView(allOf(withId(R.id.addButton),
                childAtPosition(allOf(withId(R.id.main_content),
                        childAtPosition(withId(android.R.id.content), 0)), 2), isDisplayed()));
        floatingActionButton.perform(click());
        //Then: Check that the activity add meeting is displayed
        onView(ViewMatchers.withId(R.id.activity_add_meeting)).check(matches(isDisplayed()));
    }

    @Test
    public void AddMeetingActivity_shouldAddNewReunion() {
        //Given: Adding a new reunion
        int ITEM_COUNT = 14;
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEM_COUNT));
        //Click on the AddButton
        ViewInteraction floatingActionButton = onView(allOf(withId(R.id.addButton),
                childAtPosition(allOf(withId(R.id.main_content),
                        childAtPosition(withId(android.R.id.content), 0)), 2), isDisplayed()));
        floatingActionButton.perform(click());

        //Entering the name of the meeting
        ViewInteraction textInputEditText = onView(allOf(withId(R.id.name_meeting_in),
                childAtPosition(childAtPosition(withId(R.id.name_meeting_layout),0),0),isDisplayed()));
        textInputEditText.perform(replaceText("Test"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(allOf(withId(R.id.name_meeting_in), withText("Test"),
                childAtPosition(childAtPosition(withId(R.id.name_meeting_layout),0),0), isDisplayed()));
        textInputEditText2.perform(pressImeActionButton());

        //Picking the room
        ViewInteraction materialButton = onView(allOf(withId(R.id.pick_room_btn),
                childAtPosition(allOf(withId(R.id.room_layout), childAtPosition(withClassName(
                        is("android.widget.LinearLayout")),1)),1), isDisplayed()));
        materialButton.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(withId(R.id.contentPanel),0))).atPosition(6);
        appCompatCheckedTextView.perform(click());

        ViewInteraction materialButton2 = onView(allOf(withId(android.R.id.button1), withText("OK"),
                childAtPosition(childAtPosition(withId(R.id.buttonPanel),0),3)));
        materialButton2.perform(scrollTo(), click());

        //Picking the participants
        ViewInteraction materialButton3 = onView(allOf(withId(R.id.pick_participant_btn),
                childAtPosition(allOf(withId(R.id.participant_layout),
                        childAtPosition(withClassName(is("android.widget.LinearLayout")),2)),2), isDisplayed()));
        materialButton3.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(withId(R.id.contentPanel),0))).atPosition(0);
        appCompatCheckedTextView2.perform(click());

        DataInteraction appCompatCheckedTextView3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(withId(R.id.contentPanel),0))).atPosition(2);
        appCompatCheckedTextView3.perform(click());

        DataInteraction appCompatCheckedTextView4 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(withId(R.id.contentPanel),0))).atPosition(4);
        appCompatCheckedTextView4.perform(click());

        DataInteraction appCompatCheckedTextView5 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(withId(R.id.contentPanel),0))).atPosition(7);
        appCompatCheckedTextView5.perform(click());

        DataInteraction appCompatCheckedTextView6 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(withId(R.id.contentPanel),0))).atPosition(10);
        appCompatCheckedTextView6.perform(click());

        DataInteraction appCompatCheckedTextView7 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(withId(R.id.contentPanel),0))).atPosition(3);
        appCompatCheckedTextView7.perform(click());

        DataInteraction appCompatCheckedTextView8 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(withId(R.id.contentPanel),0))).atPosition(5);
        appCompatCheckedTextView8.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(childAtPosition(withId(R.id.buttonPanel),0),3)));
        materialButton4.perform(scrollTo(), click());

        //Picking the date
        ViewInteraction materialButton5 = onView(allOf(withId(R.id.pick_date_btn),
                childAtPosition(allOf(withId(R.id.date_layout),
                        childAtPosition(withClassName(is("android.widget.LinearLayout")),1)),1), isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction materialButton6 = onView(allOf(withId(android.R.id.button1), withText("OK"),
                childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")),0),3)));
        materialButton6.perform(scrollTo(), click());

        //Picking the time
        ViewInteraction materialButton7 = onView(allOf(withId(R.id.pick_time_btn),
                childAtPosition(allOf(withId(R.id.time_layout),
                        childAtPosition(withClassName(is("android.widget.LinearLayout")),2)),1), isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction materialButton8 = onView(allOf(withId(android.R.id.button1), withText("OK"),
                childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")),0),3)));
        materialButton8.perform(scrollTo(), click());

        //Creating the meeting
        ViewInteraction materialButton9 = onView(allOf(withId(R.id.create_btn), withText("Valider"),
                childAtPosition(childAtPosition(withId(R.id.activity_add_meeting),0),2)));
        materialButton9.perform(scrollTo(), click());

        //Then:
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEM_COUNT + 1));
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
