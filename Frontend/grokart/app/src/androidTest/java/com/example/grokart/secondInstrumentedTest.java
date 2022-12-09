package com.example.grokart;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;

import android.support.annotation.NonNull;
import androidx.test.runner.AndroidJUnit4;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;
import java.util.logging.Filter;

@RunWith(AndroidJUnit4.class)
public class secondInstrumentedTest {
    @Rule
    public ActivityScenarioRule<RegisterActivity> mRegisterActivityTestRule =
            new ActivityScenarioRule<RegisterActivity>(RegisterActivity.class);

    @Test
    public void loginExistingUser_NavigateToMain() throws Exception{
        Random rand = new Random(100);
        String username = "newUser";
        onView(withId(R.id.et_username)).perform(typeText(username));
        onView(withId(R.id.et_password)).perform(typeText("Victor123"));
        onView(withId(R.id.btn_register)).perform(click());
        onView(withId(R.id.layout_main)).check(matches(isDisplayed()));

//        onView(withId(R.id.tv_main_welcome)).check(matches(withText(String.format("Welcome %1$s!",username ))));

    }
}
