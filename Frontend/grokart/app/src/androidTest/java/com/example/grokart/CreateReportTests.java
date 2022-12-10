package com.example.grokart;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringEndsWith.endsWith;

@RunWith(AndroidJUnit4.class)
public class CreateReportTests {
    // delay time for volley requests
    private static final int SIMULATED_DELAY_MS = 500 ;
    // user information
    private String username = "mrm", store = "HyVee";
    private int privilege = 0;
    // report information
    private String title = "EspressoTesting", description = "this report was created with espresso testing",
            successResponse = "success";


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
        // type in the title of the report
        onView(withId(R.id.et_title)).perform(typeText(title), closeSoftKeyboard());
        // wait for the page to get the stores from the backend
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        // select the new store
        onView(withId(R.id.spinner)).perform(click());
        onView(withText(store)).perform(click());
        // types the description
        onView(withId(R.id.et_description)).perform(typeText(description), closeSoftKeyboard());
        // clicks the create report button to send request
        onView(withId(R.id.btn_createReport)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS*2);
        } catch (InterruptedException e) {
        }
        // checks to see if volley returned the expected value
        onView(withId(R.id.msgResponse)).check(matches(withText(containsString(successResponse))));
    }
}