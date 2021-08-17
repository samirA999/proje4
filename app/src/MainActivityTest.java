package com.example.p4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import static com.example.p4.utils.RecyclerViewAtPosition.atPosition;
import static com.example.p4.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsNull.notNullValue;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.p4.view.ui.MainActivity;

import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
	private MainActivity mActivity;

	@Rule
	public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

	@Before
	public void setUp() {
		mActivity = mActivityTestRule.getActivity();
		assertThat(mActivity, notNullValue());
	}

	@Test
	public void mainActivityListShouldNotBeEmptyTest() {
		onView(allOf(isDisplayed(), withId(R.id.meeting_list_recyclerview)))
				.check(matches(hasMinimumChildCount(1)));
	}

	@Test
	public void addingAValueToTheFilterShouldChangeList() {
		onView(allOf(isDisplayed(), withId(R.id.action_filter)))
				.perform(click());

		onView(allOf(isDisplayed(), withId(R.id.filterMeetingChipsTextView)))
				.perform(typeText("Salle 2\n"));

		Espresso.closeSoftKeyboard();

		onView(allOf(isDisplayed(), withText("Appliquer")))
				.perform(click());

		onView(allOf(isDisplayed(), withId(R.id.meeting_list_recyclerview)))
				.check(matches(hasChildCount(1)));
	}

	@Test
	public void deleteActionShouldRemoveItem() {
		onView(allOf(isDisplayed(), withId(R.id.meeting_list_recyclerview))).check(withItemCount(3));

		final ViewInteraction perform = onView(AllOf.allOf(isDisplayed() , withId(R.id.meeting_list_recyclerview)))
				.perform(RecyclerViewActions.actionOnItemAtPosition(1 , new com.example.p4.utils.DeleteViewAction()));

		onView(allOf(isDisplayed(), withId(R.id.meeting_list_recyclerview))).check(withItemCount(2));
	}

	@Test
	public void addingAMeetingShouldChangeList() {
		onView(allOf(isDisplayed(), withId(R.id.meeting_list_addMeetingBtn)))
				.perform(click());

		onView(allOf(isDisplayed(), withText("Créer")))
				.perform(click());

		onView(allOf(isDisplayed(), withId(R.id.meeting_list_recyclerview)))
				.check(matches(hasChildCount(4)));
	}

	@Test
	public void newMeetingShouldHaveCorrectInfos() {
		String topic = "Sujet";
		String room = "Salle";
		String participant = "test@example.com";

		onView(allOf(isDisplayed(), withId(R.id.meeting_list_addMeetingBtn)))
				.perform(click());

		onView(allOf(isDisplayed(), withId(R.id.newMeetingTopicEditText)))
				.perform(typeText(topic));

		onView(allOf(isDisplayed(), withId(R.id.newMeetingPlaceEditText)))
				.perform(typeText(room));

		Espresso.closeSoftKeyboard();

		onView(allOf(isDisplayed(), withId(R.id.newMeetingChipsTextView)))
				.perform(typeText(participant + "\n"));

		Espresso.closeSoftKeyboard();

		onView(allOf(isDisplayed(), withText("Créer")))
				.perform(click());

		onView(allOf(isDisplayed(), withId(R.id.meeting_list_recyclerview)))
				.check(matches(atPosition(3, hasDescendant(withText(containsString(topic))))))
				.check(matches(atPosition(3, hasDescendant(withText(containsString(room))))))
				.check(matches(atPosition(3, hasDescendant(withText(containsString(participant))))));
	}
}
