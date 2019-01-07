
package com.fsck.k9.activity.setup;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.fsck.k9.Account;
import com.fsck.k9.Core;
import com.fsck.k9.DI;
import com.fsck.k9.Preferences;
import com.fsck.k9.ui.R;
import com.fsck.k9.activity.K9Activity;
import com.fsck.k9.controller.MessagingController;


public class AccountSetupOptions extends K9Activity implements OnClickListener {
    private static final String EXTRA_ACCOUNT = "account";

    private static final String EXTRA_MAKE_DEFAULT = "makeDefault";

    private final MessagingController messagingController = DI.get(MessagingController.class);

    private Spinner mCheckFrequencyView;

    private Spinner mDisplayCountView;


    private CheckBox mNotifyView;
    private CheckBox mNotifySyncView;
    private CheckBox mPushEnable;

    private Account mAccount;

    public static void actionOptions(Context context, Account account, boolean makeDefault) {
        Intent i = new Intent(context, AccountSetupOptions.class);
        i.putExtra(EXTRA_ACCOUNT, account.getUuid());
        i.putExtra(EXTRA_MAKE_DEFAULT, makeDefault);
        context.startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.account_setup_options);

        mCheckFrequencyView = findViewById(R.id.account_check_frequency);
        mDisplayCountView = findViewById(R.id.account_display_count);
        mNotifyView = findViewById(R.id.account_notify);
        mNotifySyncView = findViewById(R.id.account_notify_sync);
        mPushEnable = findViewById(R.id.account_enable_push);

        findViewById(R.id.next).setOnClickListener(this);

        SpinnerOption checkFrequencies[] = {
            new SpinnerOption(-1,
            getString(R.string.account_setup_options_mail_check_frequency_never)),
            new SpinnerOption(15,
            getString(R.string.account_setup_options_mail_check_frequency_15min)),
            new SpinnerOption(30,
            getString(R.string.account_setup_options_mail_check_frequency_30min)),
            new SpinnerOption(60,
            getString(R.string.account_setup_options_mail_check_frequency_1hour)),
            new SpinnerOption(120,
            getString(R.string.account_setup_options_mail_check_frequency_2hour)),
            new SpinnerOption(180,
            getString(R.string.account_setup_options_mail_check_frequency_3hour)),
            new SpinnerOption(360,
            getString(R.string.account_setup_options_mail_check_frequency_6hour)),
            new SpinnerOption(720,
            getString(R.string.account_setup_options_mail_check_frequency_12hour)),
            new SpinnerOption(1440,
            getString(R.string.account_setup_options_mail_check_frequency_24hour)),

        };

        ArrayAdapter<SpinnerOption> checkFrequenciesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, checkFrequencies);
        checkFrequenciesAdapter
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCheckFrequencyView.setAdapter(checkFrequenciesAdapter);

        SpinnerOption displayCounts[] = {
            new SpinnerOption(10, getString(R.string.account_setup_options_mail_display_count_10)),
            new SpinnerOption(25, getString(R.string.account_setup_options_mail_display_count_25)),
            new SpinnerOption(50, getString(R.string.account_setup_options_mail_display_count_50)),
            new SpinnerOption(100, getString(R.string.account_setup_options_mail_display_count_100)),
            new SpinnerOption(250, getString(R.string.account_setup_options_mail_display_count_250)),
            new SpinnerOption(500, getString(R.string.account_setup_options_mail_display_count_500)),
            new SpinnerOption(1000, getString(R.string.account_setup_options_mail_display_count_1000)),
        };

        ArrayAdapter<SpinnerOption> displayCountsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, displayCounts);
        displayCountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDisplayCountView.setAdapter(displayCountsAdapter);

        String accountUuid = getIntent().getStringExtra(EXTRA_ACCOUNT);
        mAccount = Preferences.getPreferences(this).getAccount(accountUuid);

        mNotifyView.setChecked(mAccount.isNotifyNewMail());
        mNotifySyncView.setChecked(mAccount.isNotifySync());
        SpinnerOption.setSpinnerOptionValue(mCheckFrequencyView, mAccount
                                            .getAutomaticCheckIntervalMinutes());
        SpinnerOption.setSpinnerOptionValue(mDisplayCountView, mAccount
                                            .getDisplayCount());


        boolean isPushCapable = messagingController.isPushCapable(mAccount);
        if (!isPushCapable) {
            mPushEnable.setVisibility(View.GONE);
        } else {
            mPushEnable.setChecked(true);
        }


    }

    private void onDone() {
        mAccount.setDescription(mAccount.getEmail());
        mAccount.setNotifyNewMail(mNotifyView.isChecked());
        mAccount.setNotifySync(mNotifySyncView.isChecked());
        mAccount.setAutomaticCheckIntervalMinutes((Integer)((SpinnerOption)mCheckFrequencyView
                .getSelectedItem()).value);
        mAccount.setDisplayCount((Integer)((SpinnerOption)mDisplayCountView
                                           .getSelectedItem()).value);

        if (mPushEnable.isChecked()) {
            mAccount.setFolderPushMode(Account.FolderMode.FIRST_CLASS);
        } else {
            mAccount.setFolderPushMode(Account.FolderMode.NONE);
        }

        Preferences.getPreferences(getApplicationContext()).saveAccount(mAccount);
        if (mAccount.equals(Preferences.getPreferences(this).getDefaultAccount()) ||
                getIntent().getBooleanExtra(EXTRA_MAKE_DEFAULT, false)) {
            Preferences.getPreferences(this).setDefaultAccount(mAccount);
        }
        Core.setServicesEnabled(this);
        AccountSetupNames.actionSetNames(this, mAccount);
        finish();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.next) {
            onDone();
        }
    }
}
