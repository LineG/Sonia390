package com.fsck.k9.ui.messageview;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;

import com.fsck.k9.activity.MessageList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.Robolectric;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MessageViewFragmentTest {

    MessageViewFragment messageViewFragment;
    NotificationManager notification;
    Notification notify;

    @Before
    public void setUp() throws Exception {
        messageViewFragment = new MessageViewFragment();
        notify = mock(Notification.class);
        notification = mock(NotificationManager.class);


    }

    @Test
    public void reminder() {
        messageViewFragment.reminder();
        verify(messageViewFragment.getApplicationContext());
    }
}