package com.b07group2.taamapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.endsWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AdminLoginFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testSuccessfulLogin() {
        // Navigate to AdminLoginFragment
        onView(withId(R.id.buttonReport)).perform(ViewActions.click());

        // Verify AdminLoginFragment is displayed
        onView(withId(R.id.usernameInput)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordInput)).check(matches(isDisplayed()));
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));

        // add delay so the window could get loaded
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // check viewID
        onView(withId(R.id.usernameInput)).check(matches(withId(R.id.usernameInput)));

        // Enter username and password
        onView(allOf(
                isDescendantOfA(withId(R.id.usernameInput)),
                withClassName(endsWith("EditText"))
        )).perform(ViewActions.typeText("admin"));

        onView(allOf(
                isDescendantOfA(withId(R.id.passwordInput)),
                withClassName(endsWith("EditText"))
        )).perform(ViewActions.typeText("pass"));

        // Click login button
        onView(withId(R.id.loginButton)).perform(ViewActions.click());

        // Verify activity_admin_home_fragment layout is displayed
        onView(withId(R.id.buttonView)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonSearch)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonNext)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonAdd)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonRemove)).check(matches(isDisplayed()));
    }

    @Test
    public void testWrongUsername() {
        // Navigate to AdminLoginFragment
        onView(withId(R.id.buttonReport)).perform(ViewActions.click());

        // Verify AdminLoginFragment is displayed
        onView(withId(R.id.usernameInput)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordInput)).check(matches(isDisplayed()));
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));

        // add delay so the window could get loaded
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // check viewID
        onView(withId(R.id.usernameInput)).check(matches(withId(R.id.usernameInput)));

        // Enter username and password
        onView(allOf(
                isDescendantOfA(withId(R.id.usernameInput)),
                withClassName(endsWith("EditText"))
        )).perform(ViewActions.typeText("Nope"));

        onView(allOf(
                isDescendantOfA(withId(R.id.passwordInput)),
                withClassName(endsWith("EditText"))
        )).perform(ViewActions.typeText("pass"));

        // Click login button
        onView(withId(R.id.loginButton)).perform(ViewActions.click());

        // Verify error message is displayed
        onView(withText("Username or Password is Incorrect*")).check(matches(isDisplayed()));
    }

    @Test
    public void testWrongPassword() {
        // Navigate to AdminLoginFragment
        onView(withId(R.id.buttonReport)).perform(ViewActions.click());

        // Verify AdminLoginFragment is displayed
        onView(withId(R.id.usernameInput)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordInput)).check(matches(isDisplayed()));
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));

        // add delay so the window could get loaded
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // check viewID
        onView(withId(R.id.usernameInput)).check(matches(withId(R.id.usernameInput)));

        // Enter username and password
        onView(allOf(
                isDescendantOfA(withId(R.id.usernameInput)),
                withClassName(endsWith("EditText"))
        )).perform(ViewActions.typeText("admin"));

        onView(allOf(
                isDescendantOfA(withId(R.id.passwordInput)),
                withClassName(endsWith("EditText"))
        )).perform(ViewActions.typeText("Nope"));

        // Click login button
        onView(withId(R.id.loginButton)).perform(ViewActions.click());

        // Verify error message is displayed
        onView(withText("Username or Password is Incorrect*")).check(matches(isDisplayed()));
    }
}
