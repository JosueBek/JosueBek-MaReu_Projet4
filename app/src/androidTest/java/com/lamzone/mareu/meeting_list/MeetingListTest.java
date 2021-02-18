package com.lamzone.mareu.meeting_list;

import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.lamzone.mareu.R;
import com.lamzone.mareu.ui.meeting_list.MeetingActivity;
import com.lamzone.mareu.utils.DeleteViewAction;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.lamzone.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 */
@RunWith(AndroidJUnit4.class)
public class MeetingListTest {

    private MeetingActivity activity;

    @Rule
    public ActivityTestRule<MeetingActivity> mActivityRule = new ActivityTestRule(MeetingActivity.class);

    @Before
    public void setUp() {
        activity = mActivityRule.getActivity();
        assertThat(activity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingList_isNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.list_meeting_recyclerview)).check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingList_deleteAction() {
        // Given : We get the element at position
        onView(withId(R.id.list_meeting_recyclerview)).check(withItemCount(3));
        // When perform a click on a delete icon at position 1
        onView(withId(R.id.list_meeting_recyclerview))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 2
        onView(withId(R.id.list_meeting_recyclerview)).check(withItemCount(3 - 1));
    }

    /**
     * When we click on a list item, displays the Dialogue details
     */
    @Test
    public void myMeetingList_clickOnItemList_shouldViewDetails() {
        // Given : We lunch elements these list.
        onView(withId(R.id.list_meeting_recyclerview)).check(withItemCount(3));
        // When perform a click on element number 0.
        onView(withId(R.id.list_meeting_recyclerview)).perform(actionOnItemAtPosition(1, click()));
        // Then : the number of element 0 is displaying on the dialogues.
        onView(withClassName(Matchers.equalTo(Builder.class.getName())));
        // Confirm exit
        onView(withText("Yes")).perform(click());

    }

    /**
     * at starting the new screen, these EditText indicating The details of the meeting is not well filled
     */
    @Test
    public void createMeetingActivity_shouldBeFill() {
        // Given : We click the button to addMeeting in the MeetingActivity
        onView(withId(R.id.activity_add_meeting)).perform(click());
        // Then : Adding the Subject of the meeting
        onView(withId(R.id.meeting_subject)).perform(typeText("Reunion D"), closeSoftKeyboard());
        // Adding time the meeting in the timePicker
        onView(withId(R.id.meetingLytTime)).perform(click(), click());
        onView(withClassName(Matchers.equalTo(TimePickerDialog.class.getName()))).perform();;
        // Validate time
        onView(withText("OK")).perform(click());
        // Adding date in the meeting in the DatePicker
        onView(withId(R.id.date_meeting)).perform(click(), click());
        onView(withClassName(Matchers.equalTo(DatePickerDialog.class.getName()))).perform();
        //Validate date
        onView(withText("OK")).perform(click());
        // Adding mails of the participants
        onView(withId(R.id.meeting_email_participants)).perform(typeText("Aline@lamezone.com"), closeSoftKeyboard());

        // Selected Rooms or Autocomplete this in the generator
        onView(withId(R.id.rooms_meeting_selected)).perform(scrollTo(), typeText("Peach"));
        onView(withText("Peach")).perform(click());
    }


    /**
     * at starting the new dialogue, the filter is filter date
     */

    @Test
    public void dateFilter_show_only_matching_date() {
        // Given : We lunch elements these list.
        onView(withId(R.id.list_meeting_recyclerview)).check(withItemCount(3));
        // Verification searchView
        onView(withId(R.id.menu_search)).perform(click());
        // When : click the menu filter
        onView(withId(R.id.menu_filter)).perform(click());
        // Choice filter date in the dialogue
        onView(withText("Date")).perform(click());
        // validate
        onView(withText("Proceed")).perform(click());
        // selected date in the days of the calendar
        onView(withClassName(Matchers.comparesEqualTo(Builder.class.getName()))).perform();
        // validate this
        onView(withText("OK")).perform(click());
        // Then : verification in the list
        onView(withId(R.id.list_meeting_recyclerview)).check(matches(hasMinimumChildCount(0)));
    }

    /**
     * at starting the new Dialogue, the filter is filter rooms
     */
    @Test
    public void roomsFilter_show_only_matching_room() {
        // Given : We lunch elements these list.
        onView(withId(R.id.list_meeting_recyclerview)).check(withItemCount(3));
        // When : click the menu filter
        onView(withId(R.id.menu_filter)).perform(click());
        //choice filter rooms
        onView(withText("Rooms")).perform(click());
        // Validate
        onView(withText("Proceed")).perform(click());
        // selected rooms
        onView(withText("Mario")).perform(click());
        // When : click the menu filter
        onView(withId(R.id.menu_filter)).perform(click());
        //choice filter rooms
        onView(withText("Rooms")).perform(click());
        // Validate
        onView(withText("Proceed")).perform(click());
        // selected rooms
        onView(withText("Peach")).perform(click());
        // Verification in the list
        onView(withId(R.id.list_meeting_recyclerview)).check(matches(hasMinimumChildCount(1)));
    }
}