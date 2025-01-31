package com.fsck.k9.activity;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fsck.k9.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.anything;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TemplateTests {

    @Rule
    public ActivityTestRule<MessageCompose> mActivityTestRule = new ActivityTestRule<>(MessageCompose.class);

    @Test
    public void templateTests() {
        // Check that apply template has the right text
        onView(withId(R.id.apply_template)).check(matches(withText("Apply Template")));

        // Check that spinner at position(1) matches the right string
        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.templates)).check(matches(withSpinnerText(containsString("Professor"))));

        // Check that spinner at position(2) matches the right string
        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.templates)).check(matches(withSpinnerText(containsString("Nice to meet you"))));

        // Check that spinner at position(1) matches the right string
        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.templates)).check(matches(withSpinnerText(containsString("Job"))));

        // Check that spinner at position(1) matches the right string
        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(4).perform(click());
        onView(withId(R.id.templates)).check(matches(withSpinnerText(containsString("Apartment Inquiry"))));

        // Check that spinner at position(1) matches the right string
        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(5).perform(click());
        onView(withId(R.id.templates)).check(matches(withSpinnerText(containsString("Formal non-specific"))));

        // Check that spinner at position(1) matches the right string
        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(6).perform(click());
        onView(withId(R.id.templates)).check(matches(withSpinnerText(containsString("Event"))));

        // Check that spinner at position(1) matches the right string
        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(7).perform(click());
        onView(withId(R.id.templates)).check(matches(withSpinnerText(containsString("Team Meeting"))));
    }

    @Test
    public void checkMessage() {

        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.apply_template)).perform(click());
        onView(withId(R.id.message_content)).check(matches(withText(R.string.template1)));

        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.apply_template)).perform(click());
        onView(withId(R.id.message_content)).check(matches(withText(R.string.template2)));

        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.apply_template)).perform(click());
        onView(withId(R.id.message_content)).check(matches(withText(R.string.template3)));

        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(4).perform(click());
        onView(withId(R.id.apply_template)).perform(click());
        onView(withId(R.id.message_content)).check(matches(withText(R.string.template4)));

        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(5).perform(click());
        onView(withId(R.id.apply_template)).perform(click());
        onView(withId(R.id.message_content)).check(matches(withText(R.string.template5)));

        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(6).perform(click());
        onView(withId(R.id.apply_template)).perform(click());
        onView(withId(R.id.message_content)).check(matches(withText(R.string.template6)));

        onView(withId(R.id.templates)).perform(click());
        onData(anything()).atPosition(7).perform(click());
        onView(withId(R.id.apply_template)).perform(click());
        onView(withId(R.id.message_content)).check(matches(withText(R.string.template7)));

    }
    }


