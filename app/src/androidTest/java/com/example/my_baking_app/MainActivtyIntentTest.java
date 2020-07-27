package com.example.my_baking_app;


import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivtyIntentTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule=new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource () {
        mIdlingResource = IdlingRes.getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);


    }
    @Test
    public void clickRecipe_OpenRecipeInfoActivity(){
        // when the data is downloaded, click on the first element of the recycler view
        onView(withId(R.id.recyclerMain))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
    @After
    public void unregisterIdlingResource(){
        if (mIdlingResource != null) {


            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

}
