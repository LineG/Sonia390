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

    private MessageViewFragment messageViewFragment1;
    private LocalMessage lmMock1;
    private LocalMessage lmMock2;
    private LocalMessage lmMock3;


    @Before
    public void setUp() {
        MessageReference mrMock1;
        mrMock1 = mock(MessageReference.class);
        messageViewFragment1 = MessageViewFragment.newInstance(mrMock1);
        lmMock1 = mock(LocalMessage.class);
        when(lmMock1.getPreview()).thenReturn("TTS test");
        messageViewFragment1.setLocalMessage(lmMock1);

        lmMock2 = mock(LocalMessage.class);
        when(lmMock2.getPreview()).thenReturn("");

        lmMock3 = mock(LocalMessage.class);
        when(lmMock3.getPreview()).thenReturn("@#$%^&*");

    }

    @Test
    public void testPreview() {
        String preview1 = messageViewFragment1.getTextMessage();
        Assert.assertEquals("TTS test", preview1);
        verify(lmMock1).getPreview();

    }

    @Test
    public void testPreviewEmpty() {
        messageViewFragment1.setLocalMessage(lmMock2);
        String preview2 = messageViewFragment1.getTextMessage();
        Assert.assertEquals("", preview2);
        verify(lmMock2).getPreview();

    }

    @Test
    public void testPreviewSpecialChar() {
        messageViewFragment1.setLocalMessage(lmMock3);
        String preview3 = messageViewFragment1.getTextMessage();
        Assert.assertEquals("@#$%^&*", preview3);
        verify(lmMock3).getPreview();

    }
}
