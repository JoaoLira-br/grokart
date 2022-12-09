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
import static androidx.test.espresso.matcher.ViewMatchers.withAlpha;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Root;
import androidx.test.espresso.action.ViewActions;
import androidx.test.runner.AndroidJUnit4;

import android.os.SystemClock;
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

public class MainActivityInstTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mMainctivityTestRule1 =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);


    @Test
    public void clickViewStoreButton_displaysRV() throws Exception {

       onView(withId(R.id.btn_main_createNewList)).perform(click());
//       onView(Root).wait(1500);
       onView(withId(R.id.layout_main)).check(matches(isDisplayed()));

    }
    @Test
    public void clickViewKartButton_navigateToViewListDetails() throws Exception {
        onView(withHint("Name your Kart")).perform(typeText("kart1"));
        onView(withContentDescription("shopping_kart")).perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.layout_viewList)).check(matches(isDisplayed()));




    }

}
