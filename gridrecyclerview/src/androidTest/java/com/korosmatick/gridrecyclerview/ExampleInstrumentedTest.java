package com.korosmatick.gridrecyclerview;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented smoke test for the library test APK.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    /**
     * Verifies that instrumentation can resolve the library test package context.
     */
    @Test
    public void useAppContext() {
        // The target context comes from the generated Android test package.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.korosmatick.gridrecyclerview.test", appContext.getPackageName());
    }
}
