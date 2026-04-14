package com.flybuys.loyaltyapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
        new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void bottomNavigation_isDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.bottom_navigation))
            .check(matches(isDisplayed()));
    }

    @Test
    public void homeScreen_greetingIsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.tv_greeting))
            .check(matches(isDisplayed()));
    }

    @Test
    public void navigateToCollect_showsOffersSection() {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_collect))
            .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.rv_offers))
            .check(matches(isDisplayed()));
    }

    @Test
    public void navigateToActivity_showsTransactions() {
        Espresso.onView(ViewMatchers.withId(R.id.navigation_activity))
            .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.rv_transactions))
            .check(matches(isDisplayed()));
    }
}
