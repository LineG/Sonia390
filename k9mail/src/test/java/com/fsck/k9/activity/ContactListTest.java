package com.fsck.k9.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.fsck.k9.BuildConfig;
import com.fsck.k9.R;
import com.fsck.k9.firebasedb.Contact;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.robolectric.Shadows.shadowOf;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)

public class ContactListTest {

    private ContactList activity;
    private Contact contact;

    @Before
    public void setUp() {

        Bundle b = mock(Bundle.class);

        activity = Robolectric.buildActivity(ContactList.class).get();

        try {
            activity.onCreate(b);
        }
        catch (Exception e) {
            e.getMessage();
        }



    }

    @Test
    public void testSendContact() {

        //just to show that a field that is not instantiated in this activity is null
        EditText name = (EditText) activity.findViewById(R.id.add_contact_name);
        assertNull(name);

        activity.sendContact("line");
        Intent expectedIntent = new Intent(activity, MessageCompose.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());


    }

    @Test
    public void testOpenAddContactActivity() {

        activity.openAddContactActivity();
        Intent expectedIntent = new Intent(activity, AddContact.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());

    }

}