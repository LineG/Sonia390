package com.fsck.k9.activity;

import android.app.Activity;

import com.fsck.k9.BuildConfig;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ThesaurusTest {

    protected Activity activity;
    private String result;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(MessageCompose.class)
                .create()
                .resume()
                .get();

    }

    @Test
    public void testThesaurus() {

        result = ((MessageCompose) activity).getTestResult();

        // Synonyms for null should be the same
        Assert.assertEquals(null, result);

    }

}


