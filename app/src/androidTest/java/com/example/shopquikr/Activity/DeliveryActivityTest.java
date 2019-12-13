package com.example.shopquikr.Activity;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.example.shopquikr.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class DeliveryActivityTest {

    @Rule
    public ActivityTestRule<DeliveryActivity> deliveryActivityTestRule = new ActivityTestRule<DeliveryActivity>(DeliveryActivity.class);
    public DeliveryActivity deliveryActivity = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MyAddressesActivity.class.getName(), null, false);


    @Before
    public void setUp() throws Exception {
        deliveryActivity = deliveryActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        deliveryActivity = null;
    }

    @Test
    public void testLaunchOfSecondActivity() {

        assertNotNull(deliveryActivity.findViewById(R.id.change_or_add_address_btn));
        onView(withId(R.id.change_or_add_address_btn)).perform(click());
        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(secondActivity);

    }

}