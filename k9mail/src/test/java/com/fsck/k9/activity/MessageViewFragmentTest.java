package com.fsck.k9.activity;


import android.app.Activity;
import android.view.MenuItem;
import com.fsck.k9.BuildConfig;
import com.fsck.k9.R;
import com.fsck.k9.ui.messageview.MessageViewFragment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MessageViewFragmentTest {


    protected Activity activity;
    protected MenuItem itemMock;
    protected MessageViewFragment fragmentMock;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(MessageList.class)
                .create()
                .resume()
                .get();
        fragmentMock = mock(MessageViewFragment.class);
        ((MessageList) activity).setMessageViewFragment(fragmentMock);
        itemMock = mock(MenuItem.class);
        when(itemMock.getItemId()).thenReturn(R.id.reminder_button);
    }

    @Test
    public void reminder() {

        assertEquals(activity.onOptionsItemSelected(itemMock), true);
        verify(fragmentMock).reminder();
    }
}

