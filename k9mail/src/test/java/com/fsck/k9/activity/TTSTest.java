package com.fsck.k9.activity;

import com.fsck.k9.K9RobolectricTestRunner;
import com.fsck.k9.mailstore.LocalMessage;
import com.fsck.k9.ui.messageview.MessageViewFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(K9RobolectricTestRunner.class)
public class TTSTest {

    MessageViewFragment messageViewFragment1;
    LocalMessage lmMock1;
    LocalMessage lmMock2;
    MessageReference mrMock1;
    MessageReference mrMock2;

    @Before
    public void Setup() {
        mrMock1 = mock(MessageReference.class);
        messageViewFragment1 = MessageViewFragment.newInstance(mrMock1);
        lmMock1 = mock(LocalMessage.class);
        when(lmMock1.getPreview()).thenReturn("TTS test");
        messageViewFragment1.setLocalMessage(lmMock1);

        mrMock2 = mock(MessageReference.class);
        lmMock2 = mock(LocalMessage.class);
        when(lmMock2.getPreview()).thenReturn("");

    }

    @Test
    public void TestPreview() {
        String preview1 = messageViewFragment1.getTextMessage();
        Assert.assertEquals("TTS test", preview1);
        verify(lmMock1).getPreview();

    }

    @Test
    public void TestPreviewEmpty() {
        messageViewFragment1.setLocalMessage(lmMock2);
        String preview2 = messageViewFragment1.getTextMessage();
        Assert.assertEquals("", preview2);
        verify(lmMock2).getPreview();

    }
}
