package com.fsck.k9.activity;


import android.app.Activity;

import com.fsck.k9.BuildConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import static junit.framework.Assert.assertNotNull;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TranslationTest {

    protected Activity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MessageCompose.class)
                .create()
                .resume()
                .get();



        //ResourceBundle schemaBundle = Mockito.mock(ResourceBundle.class);

    }

    @Test
    public void testTranslation(){

        assertNotNull(activity);
    }

}

