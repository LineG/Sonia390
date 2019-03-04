package com.fsck.k9.activity;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.fsck.k9.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BotEspressoTest {

    @Rule
    public ActivityTestRule<Accounts> mActivityTestRule = new ActivityTestRule<>(Accounts.class);

    @Test
    public void botEspressoTest() {
        ViewInteraction button = onView(
                allOf(withId(R.id.next), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.foldableControl),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.foldable_advanced_options),
                                        0),
                                0)));
        linearLayout.perform(scrollTo(), click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.account_email),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        editText.perform(scrollTo(), replaceText("390soen@gmail.com"), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.account_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        editText2.perform(scrollTo(), replaceText("minicapste"), closeSoftKeyboard());

        ViewInteraction checkBox = onView(
                allOf(withId(R.id.show_password), withText("Show password"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        checkBox.perform(scrollTo(), click());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.account_password), withText("minicapste"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        editText3.perform(scrollTo(), replaceText("minicapstone390"));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.account_password), withText("minicapstone390"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1),
                        isDisplayed()));
        editText4.perform(closeSoftKeyboard());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.next), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.account_description), withContentDescription("Give this account a name (optional):"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        editText5.perform(scrollTo(), replaceText("soen390"), closeSoftKeyboard());

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.account_name), withContentDescription("Type your name (displays on outgoing messages):"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        editText6.perform(scrollTo(), replaceText("miniCap"), closeSoftKeyboard());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.done), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        button3.perform(click());

        ViewInteraction button4 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        button4.perform(click());

        DataInteraction linearLayout2 = onData(anything())
                .inAdapterView(allOf(withId(android.R.id.list),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)))
                .atPosition(1);
        linearLayout2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.bot_redirect), withContentDescription("Bot"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.bot_redirect), withContentDescription("Bot"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.widget.ActionBarContainer")),
                                        0),
                                2),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textMessage), withText("Here is a list of built in commands \n '!cm mc': redirects to message compose \n '!cm ml': redirects to message list \n '!cm faq': BOT gives us faqs \n\n Enter a command or just say hi :D"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.conversation),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Here is a list of built in commands   '!cm mc': redirects to message compose   '!cm ml': redirects to message list   '!cm faq': BOT gives us faqs    Enter a command or just say hi :D")));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.userInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("hello"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.userInput), withText("hello"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textMessage), withText("hello"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.conversation),
                                        1),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("hello")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textMessage), withText("hello"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.conversation),
                                        1),
                                0),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textMessage), withText("Hello I am K9BOT :) \n\n Please enter one of my built-in commands\n Here is a list of built in commands \n\n '!cm mc': redirects to message compose \n '!cm ml': redirects to message list \n '!cm faq': Frequently asked questions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.conversation),
                                        2),
                                0),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.textMessage), withText("Hello I am K9BOT :) \n\n Please enter one of my built-in commands\n Here is a list of built in commands \n\n '!cm mc': redirects to message compose \n '!cm ml': redirects to message list \n '!cm faq': Frequently asked questions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.conversation),
                                        2),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("Hello I am K9BOT :)    Please enter one of my built-in commands  Here is a list of built in commands    '!cm mc': redirects to message compose   '!cm ml': redirects to message list   '!cm faq': Frequently asked questions")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.textMessage), withText("Hello I am K9BOT :) \n\n Please enter one of my built-in commands\n Here is a list of built in commands \n\n '!cm mc': redirects to message compose \n '!cm ml': redirects to message list \n '!cm faq': Frequently asked questions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.conversation),
                                        2),
                                0),
                        isDisplayed()));
        textView7.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.userInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("!cm mc"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.userInput), withText("!cm mc"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());

        ViewInteraction viewGroup = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                        childAtPosition(
                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                0),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction viewGroup2 = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                        childAtPosition(
                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                0),
                        isDisplayed()));
        viewGroup2.check(matches(isDisplayed()));

        pressBack();

        pressBack();

        DataInteraction linearLayout3 = onData(anything())
                .inAdapterView(allOf(withId(android.R.id.list),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)))
                .atPosition(1);
        linearLayout3.perform(click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.bot_redirect), withContentDescription("Bot"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.widget.ActionBarContainer")),
                                        0),
                                2),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction editText7 = onView(
                allOf(withId(R.id.userInput), withText("Ask Christopher"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText7.check(matches(isDisplayed()));

        ViewInteraction editText8 = onView(
                allOf(withId(R.id.userInput), withText("Ask Christopher"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText8.check(matches(withText("Ask Christopher")));

        ViewInteraction editText9 = onView(
                allOf(withId(R.id.userInput), withText("Ask Christopher"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText9.check(matches(withText("Ask Christopher")));

        ViewInteraction editText10 = onView(
                allOf(withId(R.id.userInput), withText("Ask Christopher"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText10.check(matches(withText("Ask Christopher")));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.userInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("random text"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.userInput), withText("random text"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(pressImeActionButton());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.userInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("random"), closeSoftKeyboard());

        ViewInteraction editText11 = onView(
                allOf(withId(R.id.userInput), withText("random"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText11.check(matches(withText("random")));

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.userInput), withText("random"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText8.perform(pressImeActionButton());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.textMessage), withText("random"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.conversation),
                                        3),
                                0),
                        isDisplayed()));
        textView8.check(matches(isDisplayed()));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.textMessage), withText("random"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.conversation),
                                        3),
                                0),
                        isDisplayed()));
        textView9.check(matches(withText("random")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.textMessage), withText("random is not a command"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.conversation),
                                        4),
                                0),
                        isDisplayed()));
        textView10.check(matches(isDisplayed()));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.textMessage), withText("random is not a command"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.conversation),
                                        4),
                                0),
                        isDisplayed()));
        textView11.check(matches(withText("random is not a command")));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.textMessage), withText("random is not a command"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.conversation),
                                        4),
                                0),
                        isDisplayed()));
        textView12.check(matches(withText("random is not a command")));
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
