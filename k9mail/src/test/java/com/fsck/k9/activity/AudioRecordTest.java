package com.fsck.k9.activity;


import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import com.fsck.k9.BuildConfig;
import org.junit.Before;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AudioRecordTest {

    protected Activity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(MessageCompose.class)
                .create()
                .resume()
                .get();
        ((MessageCompose) activity).mediaRecorder = mock(MediaRecorder.class);
        ((MessageCompose) activity).stopAudio();
        ((MessageCompose) activity).recordAudio();
        ((MessageCompose) activity).mp = mock(MediaPlayer.class);
        ((MessageCompose) activity).playAudio();

    }

    @Test
    public void acitivityThrown() {
        assertNotNull(activity);
    }

    @Test
    public void commandChoiceStop() {

        verify(((MessageCompose) activity).mediaRecorder).stop();
    }

    @Test
    public void commandChoiceRecord() {

        verify(((MessageCompose) activity).mediaRecorder).start();
    }

    @Test
    public void commandChoicePlay() {

        verify(((MessageCompose) activity).mp).start();
    }
}