package lynxz.org.kotlinapplication.activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by lynxz on 02/05/2017.
 */
@RunWith(AndroidJUnit4.class)
public class EspressoActivityTest {
    @Rule
    public ActivityTestRule<EspressoActivity> mActivityRule
            = new ActivityTestRule<>(EspressoActivity.class, true, false);


    @Test
    public void testTextview() {
        Intent intent = new Intent();
        intent.putExtra(EspressoActivity.KEY_TITLE, "hello espresso");
        mActivityRule.launchActivity(intent);

        onView(withText("hello espresso")).check(matches(isDisplayed()));
    }
}