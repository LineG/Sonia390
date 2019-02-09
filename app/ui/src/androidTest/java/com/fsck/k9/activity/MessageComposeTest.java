package com.fsck.k9.activity;

import com.fsck.k9.activity.MessageCompose;
import com.fsck.k9.ui.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;



@RunWith(AndroidJUnit4.class)

public class MessageComposeTest {

    @Rule
    public ActivityTestRule<MessageCompose> mActivityRule =
            new ActivityTestRule<>(MessageCompose.class);

    @Test

    public void templatesTest() {
        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.templates)).check(matches(withSpinnerText(containsString("Dear Dr.{last name},\n\nMy name is {name} and my student ID is {student id}."
                + " I am sending this message in regards to {inquiry}"
                + "\n\n" + "I hope to hear from you soon. Thank you  very much for your time.\n\n Best regard,\n\n{name}"))));
    }

}