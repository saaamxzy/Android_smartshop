package com.group3.smartshop;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class testTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.email), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.email), isDisplayed()));
        appCompatEditText2.perform(replaceText("samxzy0207@hotmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password), isDisplayed()));
        appCompatEditText3.perform(replaceText("123456"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_login), withText("LOGIN"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.app_bar)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Home"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.login), withText("Profile Test"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.change_name_button), withText("Chang User Name"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.new_name), isDisplayed()));
        appCompatEditText4.perform(replaceText("betterstudent"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.changeUserName), withText("Change"), isDisplayed()));
        appCompatButton4.perform(click());

        pressBack();

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.change_email_button), withText("Change Email"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.change_password_button), withText("Change Password"), isDisplayed()));
        appCompatButton6.perform(click());

        pressBack();

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.app_bar)))),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Home"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.button_nearby), withText("NEARBY"), isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.button_online), withText("ONLINE"), isDisplayed()));
        appCompatButton8.perform(click());

    }

}
