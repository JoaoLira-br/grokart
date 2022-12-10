package com.example.grokart;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.StringEndsWith.endsWith;

@RunWith(AndroidJUnit4.class)
public class EditProfileTests {
    // delay time for volley requests
    private static final int SIMULATED_DELAY_MS = 500 ;
    // user information
    private String username = "mrm", store = "HyVee", displayName = "Mattie", email = "mrm4@iastate.edu";
    private int basePrivilege = 0, storeAdminPrivilege = 1, appAdminPrivilege = 2;
    // report information
    private String title = "EspressoTest", description = "this report was created with espresso testing",
            successResponse = "success";

    @Rule
    public ActivityTestRule<EditProfileActivity> mActivityRule =
            new ActivityTestRule<EditProfileActivity>(EditProfileActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("userName", username);
                    intent.putExtra("displayName", displayName);
                    intent.putExtra("preferredStore", store);
                    return intent;
                }
            };

    @Test
    public void testPageTitle() {
        // verify that the page title is correct
        onView(withId(R.id.tv_editProfileTitle)).check(matches(withText("Edit Profile")));
    }

    @Test
    public void testEditDisplayName() {
        // type in the new display name
        onView(withId(R.id.et_name)).perform(typeText("changeName"), closeSoftKeyboard());
        // submit the changes
        onView(withId(R.id.btn_editProfile)).perform(click());
        // wait for the response from the backend
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        // verify that the request was successful
        onView(withId(R.id.msgResponse)).check(matches(withText(containsString("Name updated."))));
    }

    @Test
    public void testEditEmail() {
        // type in the new email
        onView(withId(R.id.et_email)).perform(typeText("email@email.com"), closeSoftKeyboard());
        // submit the changes
        onView(withId(R.id.btn_editProfile)).perform(click());
        // wait for the response from the backend
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        // verify that the request was successful
        onView(withId(R.id.msgResponse)).check(matches(withText(containsString("Email updated."))));
    }

    @Test
    public void testClickPreferredStore() {
        // wait for the stores from the backend to populate the stores menu
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        // select the new store
        onView(withId(R.id.spinner)).perform(click());
        onView(withText("Walmart")).perform(click());
        // test to see if text displayed in spinner changed
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("Walmart"))));
    }

    @Test
    public void testEditPreferredStore() {
        // wait for the stores from the backend to populate the stores menu
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        // select the new store
        onView(withId(R.id.spinner)).perform(click());
        onView(withText("Walmart")).perform(click());
        // submit the changes
        onView(withId(R.id.btn_editProfile)).perform(click());
        // wait for the response from the backend
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        // verify that the request was successful
        onView(withId(R.id.msgResponse)).check(matches(withText(containsString("Preferred store updated."))));
    }

    @Test
    public void testAllEditable() {
        onView(withId(R.id.et_name)).perform(typeText(displayName), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(typeText(email), closeSoftKeyboard());
        // wait for the stores from the backend to populate the stores menu
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        // select the new store
        onView(withId(R.id.spinner)).perform(click());
        onView(withText(store)).perform(click());
        // submit the changes
        onView(withId(R.id.btn_editProfile)).perform(click());
        // wait for the response from the backend
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        // verify that the request was successful
        onView(withId(R.id.msgResponse)).check(matches(not(withText(containsString("failed")))));
    }

}