package com.fsck.k9.mail.transport;


import java.util.Collections;

import com.fsck.k9.mail.K9MailLib;
import com.fsck.k9.mail.Message;
import com.fsck.k9.mail.MessagingException;
import com.fsck.k9.mail.Transport;
import com.fsck.k9.mail.ssl.TrustManagerFactory;
import com.fsck.k9.mail.store.StoreConfig;
import com.fsck.k9.mail.store.webdav.WebDavStore;
import com.fsck.k9.mail.store.webdav.WebDavStoreSettings;
import timber.log.Timber;

public class WebDavTransport extends Transport {
    private WebDavStore store;

    public WebDavTransport(TrustManagerFactory trustManagerFactory, WebDavStoreSettings serverSettings, StoreConfig storeConfig) {
        store = new WebDavStore(trustManagerFactory, serverSettings, storeConfig);

        if (K9MailLib.isDebug())
            Timber.d(">>> New WebDavTransport creation complete");
    }

    @Override
    public void open() throws MessagingException {
        if (K9MailLib.isDebug())
            Timber.d( ">>> open called on WebDavTransport ");

        store.getHttpClient();
    }

    @Override
    public void close() {
    }

    @Override
    public void sendMessage(Message message) throws MessagingException {
        store.sendMessages(Collections.singletonList(message));
    }

    public void checkSettings() throws MessagingException {
        store.checkSettings();
    }
}
