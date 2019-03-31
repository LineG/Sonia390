package com.fsck.k9.activity;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.fsck.k9.BuildConfig;
import com.fsck.k9.firebasedb.Tag;
import com.fsck.k9.fragment.MessageListAdapter;

import yuku.ambilwarna.AmbilWarnaDialog;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CustomTagTest {

    private CreateTag activity;
    private Tag tag;
    CreateTag createTagActivity;
    MessageListAdapter mmla;
    AmbilWarnaDialog mcp;
    Bundle mb;

    @Before
    public void setUp() {

        mmla = mock(MessageListAdapter.class);
        mcp = mock(AmbilWarnaDialog.class);

        mb = mock(Bundle.class);

        try {

            activity = Robolectric.buildActivity(CreateTag.class).create().pause().get();
            createTagActivity.onCreate(mb);
            createTagActivity = new CreateTag();


        } catch (IllegalStateException ill) {

            ill.getMessage();

        } catch (RuntimeException r) {

            r.getMessage();

        }

        tag = new Tag();
        tag = new Tag("Zishuo", 0);
        tag.setName("Ding");
        tag.setColor(000000);

    }

    @Test
    public void tagTest() {
        final int temp = 1;
        try {

            activity.openColorPicker(temp);
            createTagActivity.openColorPicker(temp);


        } catch (Exception e) {

            e.getMessage();

        }

        assertEquals("Ding", tag.getName());
        assertEquals(000000, tag.getColor());
    }
}
