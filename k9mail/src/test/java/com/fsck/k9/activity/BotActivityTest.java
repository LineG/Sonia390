package com.fsck.k9.activity;



import android.app.Activity;
import android.content.res.Resources;

import com.fsck.k9.BuildConfig;
import com.fsck.k9.R;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;






@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
    public class BotActivityTest {

    Activity activity;
    String faqString;
    String initialString;
    String helloString;
    String aboutString;
    String accountOverviewString;
    String configureString;
    String additionalMailString;
    String saveSettingsString;
    String signatureString;
    @Before
    public void Setup() {
        activity = Robolectric.buildActivity(BotActivity.class)
                .create()
                .resume()
                .get();
        initialString =((BotActivity) activity).responseMessageList.get(0).getText();
        faqString = ((BotActivity) activity).commandChoice("!cm faq");
        helloString = ((BotActivity) activity).commandChoice("hello");
        accountOverviewString = ((BotActivity) activity).commandChoice("account overview");
        configureString = ((BotActivity) activity).commandChoice("configure folders");
        additionalMailString = ((BotActivity) activity).commandChoice("additional mails");
        saveSettingsString = ((BotActivity) activity).commandChoice("save settings");
        signatureString = ((BotActivity) activity).commandChoice("signature");
        aboutString = ((BotActivity) activity).commandChoice("about");

    }

    @Test
    public void acitivityThrown(){
        assertNotNull(activity);
    }

    @Test
    public void commandChoice() {

       String initialMessage = RuntimeEnvironment.application.getString(R.string.bot_initial_message);
       String faqMessage = RuntimeEnvironment.application.getString(R.string.frequently_asked_questions);
       String helloMessage = RuntimeEnvironment.application.getString(R.string.bot_hello_message);
       String accountOverviewMessage =  RuntimeEnvironment.application.getString(R.string.account_overview);
       String configureSettingsMessage =  RuntimeEnvironment.application.getString(R.string.configure_folders);
       String additionalMailMessage =  RuntimeEnvironment.application.getString(R.string.additional_mail);
       String saveSettingsMessage =  RuntimeEnvironment.application.getString(R.string.save_settings);
       String signatureMessage =  RuntimeEnvironment.application.getString(R.string.signature);
       String aboutMessage =  RuntimeEnvironment.application.getString(R.string.about);


        assertEquals(initialString, initialMessage);
        assertEquals(faqString, faqMessage );
        assertEquals(helloString, helloMessage);
        assertEquals(accountOverviewString, accountOverviewMessage );
        assertEquals(configureString, configureSettingsMessage);
        assertEquals(additionalMailString, additionalMailMessage);
        assertEquals(saveSettingsString, saveSettingsMessage);
        assertEquals(signatureString, signatureMessage );
        assertEquals(aboutString, aboutMessage);
    }
}