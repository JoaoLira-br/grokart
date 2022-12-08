package com.example.grokart;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.*;
import static java.util.EnumSet.allOf;

@RunWith(AndroidJUnit4.class)
public class CreateReportTest {
    private static final int SIMULATED_DELAY_MS = 500 ;
    private String username = "mrm";

    @Rule
    public ActivityTestRule<NewReportActivity> mActivityRule =
            new ActivityTestRule<NewReportActivity>(NewReportActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("userName", username);
                    return intent;
                }
            };

    @Test
    public void testCreateReport() {
        String title = "EspressoTest";
        String description = "this report was created with espresso testing";
        // TODO figure out success message
        String response = "";
        // type in the title of the report
        onView(withId(R.id.et_title)).perform(typeText(title), closeSoftKeyboard());
        // picks the store out of the spinner
        onData(anything()).inAdapterView(withId(R.id.spinner)).atPosition(1).perform(click());
        // types in the description
        onView(withId(R.id.et_description)).perform(typeText(description), closeSoftKeyboard());
        // clicks the create report button to send request
        onView(withId(R.id.btn_createReport)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        // checks to see if volley returned the expected value
        onView(withId(R.id.msgResponse)).check(matches(withText(endsWith(resultString))));

    }
}