package com.fsck.k9.activity;



import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;




import com.fsck.k9.R;




import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class ReminderTest {

    @Rule
    public ActivityTestRule<Accounts> mActivityTestRule = new ActivityTestRule<>(Accounts.class);

    @Test
    public void reminderTest() {
        ViewInteraction button = onView(
                allOf(withId(R.id.next), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.account_email),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        editText.perform(scrollTo(), click());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.account_email),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        editText2.perform(scrollTo(), replaceText("390soen@gmail.com"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.account_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        editText3.perform(scrollTo(), replaceText("minicapstone390"), closeSoftKeyboard());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.next), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.account_description), withContentDescription("Give this account a name (optional):"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        editText4.perform(scrollTo(), click());

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.account_description), withContentDescription("Give this account a name (optional):"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        editText5.perform(scrollTo(), click());
        assertEquals("", "");
    }
}
