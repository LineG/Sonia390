package com.fsck.k9.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import com.fsck.k9.BuildConfig;

import org.junit.Assert;
import org.junit.Before;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AudioRecordTest {

    protected Activity activity;
    private MediaRecorder mrMock;
    private MediaPlayer mpMock;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(MessageCompose.class)
                .create()
                .resume()
                .get();
        mrMock = mock(MediaRecorder.class);
        mpMock = mock(MediaPlayer.class);
        ((MessageCompose) activity).setMediaRecorder(mrMock);
        ((MessageCompose) activity).setMediaPlayer(mpMock);

    }

    @Test
    public void activityThrown() {
        assertNotNull(activity);
    }

    @Test
    public void recordAudioUnitTest() {
        ((MessageCompose) activity).recordAudio();
        ((MessageCompose) activity).stopAudio();
        ((MessageCompose) activity).playAudio();

        verify(mrMock).setAudioSource(MediaRecorder.AudioSource.MIC);
        verify(mrMock).setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        verify(mrMock).setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        try {

            verify(mrMock).prepare();
            verify(mrMock).start();

        } catch (IllegalStateException ise) {

            ise.getMessage();

        } catch (IOException io) {

            io.getMessage();

        }

        verify(mrMock).stop();
        try {

            verify(mpMock).prepare();

        } catch (IOException io) {

            io.getMessage();

        }

        verify(mpMock).start();
        Assert.assertTrue(true);
    }

}
