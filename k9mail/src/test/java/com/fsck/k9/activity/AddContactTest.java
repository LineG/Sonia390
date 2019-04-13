package com.fsck.k9.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.fsck.k9.BuildConfig;
import com.fsck.k9.R;
import com.fsck.k9.firebasedb.Contact;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.robolectric.Shadows.shadowOf;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AddContactTest {

    private AddContact activity;
    private Contact contact;
    private FirebaseDatabase db;

    @Before
    public void setUp() {

        Bundle b = mock(Bundle.class);

        activity = Robolectric.buildActivity(AddContact.class).get();
        activity.getIntent().putExtra("email","email");

        try {
            activity.onCreate(b);
        }
        catch (Exception e) {
            e.getMessage();
        }


    }

    @Test
    public void testTextField() {

        EditText name = (EditText) activity.findViewById(R.id.add_contact_name);
        name.setText("line");

        assertEquals("line", name.getText().toString());

        Button button = (Button) activity.findViewById(R.id.save_contact);



    }



}
