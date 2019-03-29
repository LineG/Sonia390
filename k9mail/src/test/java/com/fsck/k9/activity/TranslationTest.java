package com.fsck.k9.activity;


import android.app.Activity;

import com.fsck.k9.BuildConfig;
import com.fsck.k9.ui.EolConvertingEditText;
import com.ibm.watson.developer_cloud.language_translator.v3.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslateOptions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TranslationTest {

    protected Activity activity;
    private LanguageTranslator ltMock;
    private EolConvertingEditText ecetMock;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MessageCompose.class)
                .create()
                .resume()
                .get();


        //ResourceBundle schemaBundle = Mockito.mock(ResourceBundle.class);


        ltMock = mock(LanguageTranslator.class);
        ecetMock = mock(EolConvertingEditText.class);
    }

    @Test
    public void testTranslation(){

        ((MessageCompose) activity).showtTranslation(ecetMock, "Hello");

        verify(ecetMock).setText("Hello");

    }

}

