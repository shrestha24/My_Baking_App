package com.example.my_baking_app;

import androidx.test.espresso.IdlingResource;

import com.example.my_baking_app.tester.SimpleIdlingresource;

public class IdlingRes {
    private static SimpleIdlingresource sIdlingResource;
    public static IdlingResource getIdlingResource() {
        if (sIdlingResource == null) {
            sIdlingResource = new SimpleIdlingresource();
        }
        return sIdlingResource;
    }

    public static void setIdleResourceTo(boolean isIdleNow){
        if (sIdlingResource == null) {
            sIdlingResource = new SimpleIdlingresource();
        }
        sIdlingResource.setIdleState(isIdleNow);
    }
}
