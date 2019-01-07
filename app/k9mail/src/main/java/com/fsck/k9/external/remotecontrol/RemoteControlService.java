package com.fsck.k9.external.remotecontrol;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.fsck.k9.Account;
import com.fsck.k9.Account.FolderMode;
import com.fsck.k9.DI;
import com.fsck.k9.K9;
import com.fsck.k9.K9.BACKGROUND_OPS;
import com.fsck.k9.Preferences;
import com.fsck.k9.R;
import com.fsck.k9.job.K9JobManager;
import com.fsck.k9.preferences.Storage;
import com.fsck.k9.preferences.StorageEditor;
import com.fsck.k9.service.BootReceiver;
import com.fsck.k9.service.CoreService;

import java.util.List;

import timber.log.Timber;

import static com.fsck.k9.external.remotecontrol.K9RemoteControl.K9_ACCOUNT_UUID;
import static com.fsck.k9.external.remotecontrol.K9RemoteControl.K9_ALL_ACCOUNTS;
import static com.fsck.k9.external.remotecontrol.K9RemoteControl.K9_BACKGROUND_OPERATIONS;
import static com.fsck.k9.external.remotecontrol.K9RemoteControl.K9_NOTIFICATION_ENABLED;
import static com.fsck.k9.external.remotecontrol.K9RemoteControl.K9_POLL_CLASSES;
import static com.fsck.k9.external.remotecontrol.K9RemoteControl.K9_POLL_FREQUENCY;
import static com.fsck.k9.external.remotecontrol.K9RemoteControl.K9_PUSH_CLASSES;
import static com.fsck.k9.external.remotecontrol.K9RemoteControl.K9_RING_ENABLED;
import static com.fsck.k9.external.remotecontrol.K9RemoteControl.K9_THEME;
import static com.fsck.k9.external.remotecontrol.K9RemoteControl.K9_VIBRATE_ENABLED;

public class RemoteControlService extends CoreService {
    private final static String RESCHEDULE_ACTION = "com.fsck.k9.service.RemoteControlService.RESCHEDULE_ACTION";
    private final static String PUSH_RESTART_ACTION = "com.fsck.k9.service.RemoteControlService.PUSH_RESTART_ACTION";

    private final static String SET_ACTION = "com.fsck.k9.service.RemoteControlService.SET_ACTION";

    private final Preferences preferences = DI.get(Preferences.class);
    private final K9JobManager jobManager = DI.get(K9JobManager.class);

    public static void set(Context context, Intent i, Integer wakeLockId) {
        //  Intent i = new Intent();
        i.setClass(context, RemoteControlService.class);
        i.setAction(RemoteControlService.SET_ACTION);
        addWakeLockId(context, i, wakeLockId, true);
        context.startService(i);
    }

    public static final int REMOTE_CONTROL_SERVICE_WAKE_LOCK_TIMEOUT = 20000;

    @Override
    public int startService(final Intent intent, final int startId) {
        Timber.i("RemoteControlService started with startId = %d", startId);

        if (RESCHEDULE_ACTION.equals(intent.getAction())) {
            Timber.i("RemoteControlService requesting jobmanager mail poll reschedule");
            jobManager.scheduleMailSync();
        }
        if (PUSH_RESTART_ACTION.equals(intent.getAction())) {
            Timber.i("RemoteControlService requesting jobmanager push restart");
            jobManager.schedulePusherRefresh();
        } else if (RemoteControlService.SET_ACTION.equals(intent.getAction())) {
            Timber.i("RemoteControlService got request to change settings");
            execute(getApplication(), new Runnable() {
                public void run() {
                    try {
                        boolean needsReschedule = false;
                        boolean needsPushRestart = false;
                        String uuid = intent.getStringExtra(K9_ACCOUNT_UUID);
                        boolean allAccounts = intent.getBooleanExtra(K9_ALL_ACCOUNTS, false);

                        if (allAccounts) {
                            Timber.i("RemoteControlService changing settings for all accounts");
                        } else {
                            Timber.i("RemoteControlService changing settings for account with UUID %s", uuid);
                        }

                        List<Account> accounts = preferences.getAccounts();
                        for (Account account : accounts) {
                            //warning: account may not be isAvailable()
                            if (allAccounts || account.getUuid().equals(uuid)) {

                                Timber.i("RemoteControlService changing settings for account %s",
                                        account.getDescription());

                                String notificationEnabled = intent.getStringExtra(K9_NOTIFICATION_ENABLED);
                                String ringEnabled = intent.getStringExtra(K9_RING_ENABLED);
                                String vibrateEnabled = intent.getStringExtra(K9_VIBRATE_ENABLED);
                                String pushClasses = intent.getStringExtra(K9_PUSH_CLASSES);
                                String pollClasses = intent.getStringExtra(K9_POLL_CLASSES);
                                String pollFrequency = intent.getStringExtra(K9_POLL_FREQUENCY);

                                if (notificationEnabled != null) {
                                    account.setNotifyNewMail(Boolean.parseBoolean(notificationEnabled));
                                }
                                if (ringEnabled != null) {
                                    account.getNotificationSetting().setRingEnabled(Boolean.parseBoolean(ringEnabled));
                                }
                                if (vibrateEnabled != null) {
                                    account.getNotificationSetting().setVibrateEnabled(Boolean.parseBoolean(vibrateEnabled));
                                }
                                if (pushClasses != null) {
                                    needsPushRestart |= account.setFolderPushMode(FolderMode.valueOf(pushClasses));
                                }
                                if (pollClasses != null) {
                                    needsReschedule |= account.setFolderSyncMode(FolderMode.valueOf(pollClasses));
                                }
                                if (pollFrequency != null) {
                                    String[] allowedFrequencies = getResources().getStringArray(R.array.check_frequency_values);
                                    for (String allowedFrequency : allowedFrequencies) {
                                        if (allowedFrequency.equals(pollFrequency)) {
                                            Integer newInterval = Integer.parseInt(allowedFrequency);
                                            needsReschedule |= account.setAutomaticCheckIntervalMinutes(newInterval);
                                        }
                                    }
                                }
                                Preferences.getPreferences(getApplicationContext()).saveAccount(account);
                            }
                        }

                        Timber.i("RemoteControlService changing global settings");

                        String backgroundOps = intent.getStringExtra(K9_BACKGROUND_OPERATIONS);
                        if (K9RemoteControl.K9_BACKGROUND_OPERATIONS_ALWAYS.equals(backgroundOps)
                                || K9RemoteControl.K9_BACKGROUND_OPERATIONS_NEVER.equals(backgroundOps)
                                || K9RemoteControl.K9_BACKGROUND_OPERATIONS_WHEN_CHECKED_AUTO_SYNC.equals(backgroundOps)) {
                            BACKGROUND_OPS newBackgroundOps = BACKGROUND_OPS.valueOf(backgroundOps);
                            boolean needsReset = K9.setBackgroundOps(newBackgroundOps);
                            needsPushRestart |= needsReset;
                            needsReschedule |= needsReset;
                        }

                        String theme = intent.getStringExtra(K9_THEME);
                        if (theme != null) {
                            K9.setK9Theme(K9RemoteControl.K9_THEME_DARK.equals(theme) ? K9.Theme.DARK : K9.Theme.LIGHT);
                        }

                        StorageEditor editor = preferences.createStorageEditor();
                        K9.save(editor);
                        editor.commit();

                        if (needsReschedule) {
                            Intent i = new Intent(RemoteControlService.this, RemoteControlService.class);
                            i.setAction(RESCHEDULE_ACTION);
                            long nextTime = System.currentTimeMillis() + 10000;
                            BootReceiver.scheduleIntent(RemoteControlService.this, nextTime, i);
                        }
                        if (needsPushRestart) {
                            Intent i = new Intent(RemoteControlService.this, RemoteControlService.class);
                            i.setAction(PUSH_RESTART_ACTION);
                            long nextTime = System.currentTimeMillis() + 10000;
                            BootReceiver.scheduleIntent(RemoteControlService.this, nextTime, i);
                        }
                    } catch (Exception e) {
                        Timber.e(e, "Could not handle K9_SET");
                        Toast toast = Toast.makeText(RemoteControlService.this, e.getMessage(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
            , RemoteControlService.REMOTE_CONTROL_SERVICE_WAKE_LOCK_TIMEOUT, startId);
        }

        return START_NOT_STICKY;
    }

}
