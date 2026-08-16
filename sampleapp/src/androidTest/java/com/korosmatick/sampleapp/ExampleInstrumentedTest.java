package com.korosmatick.sampleapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented smoke test for the sample application.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    /**
     * Verifies that instrumentation can resolve the sample app package context.
     */
    @Test
    public void useAppContext() {
        // The target context should belong to the installed sample app.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.korosmatick.sampleapp", appContext.getPackageName());
    }
}
