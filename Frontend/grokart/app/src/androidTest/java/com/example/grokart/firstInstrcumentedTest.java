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

import androidx.test.espresso.action.ViewActions;
import androidx.test.runner.AndroidJUnit4;

import android.support.annotation.NonNull;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Filter;

@RunWith(AndroidJUnit4.class)
public class firstInstrcumentedTest {
    @Rule
    public ActivityScenarioRule<CreateNewListActivity> mCreateNewlistActivityTestRule1 =
            new ActivityScenarioRule<CreateNewListActivity>(CreateNewListActivity.class);


    @Test
    public void clickViewStoreButton_displaysRV() throws Exception {

        onView(withId(R.id.btn_viewStoreItems)).perform(click());
        onView(withId(R.id.rv_storeItems)).check(matches(isDisplayed()));

    }
    @Test
    public void clickViewKartButton_navigateToViewListDetails() throws Exception {
        onView(withHint("Name your Kart")).perform(typeText("kart1"));
        onView(withContentDescription("shopping_kart")).perform(click());
        onView(withId(R.id.layout_viewList)).check(matches(isDisplayed()));




    }


}
