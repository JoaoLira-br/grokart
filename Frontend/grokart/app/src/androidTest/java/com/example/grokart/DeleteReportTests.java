package com.example.grokart;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.contrib.RecyclerViewActions;


import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.StringContains.containsString;
import static java.util.EnumSet.allOf;
import static kotlin.jvm.internal.Intrinsics.checkNotNull;

@RunWith(AndroidJUnit4.class)
public class DeleteReportTests {
    // delay time for volley requests
    private static final int SIMULATED_DELAY_MS = 500 ;
    // user information
    private String username = "mrm", store = "HyVee";
    private int privilege = 0;
    // report information
    private String title = "EspressoTesting";


    @Rule
    public ActivityTestRule<ReportsActivity> mActivityRule =
            new ActivityTestRule<ReportsActivity>(ReportsActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("userName", username);
                    return intent;
                }
            };

    @Test(expected = PerformException.class)
    public void testDeleteReport() {
        // wait for the response from the backend
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        //click on report
        // Attempt to scroll to an item that contains the special text.
        onView(ViewMatchers.withId(R.id.rv_reports))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(title)), click()));

        // wait for the response from the backend
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.btn_action)).perform(click());
        onView(withText("Confirm")).inRoot(isDialog()).perform(click());
        // wait for the response from the backend. this will also bring us back to the reports page
        try {
            Thread.sleep(SIMULATED_DELAY_MS*2);
        } catch (InterruptedException e) {
        }
        // Attempt to scroll to an item that contains the special text.
        onView(ViewMatchers.withId(R.id.rv_reports))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(title))
                ));
    }

}