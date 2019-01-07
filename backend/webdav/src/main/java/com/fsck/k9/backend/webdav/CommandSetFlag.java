package com.fsck.k9.backend.webdav;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fsck.k9.backend.api.BackendFolder;
import com.fsck.k9.mail.Flag;
import com.fsck.k9.mail.Folder;
import com.fsck.k9.mail.Message;
import com.fsck.k9.mail.MessagingException;
import com.fsck.k9.mail.store.webdav.WebDavFolder;
import com.fsck.k9.mail.store.webdav.WebDavStore;
import org.jetbrains.annotations.NotNull;


class CommandSetFlag {
    private final WebDavStore webDavStore;


    CommandSetFlag(WebDavStore webDavStore) {
        this.webDavStore = webDavStore;
    }

    void setFlag(@NotNull String folderServerId, @NotNull List<String> messageServerIds, @NotNull Flag flag,
            boolean newState) throws MessagingException {

        WebDavFolder remoteFolder = webDavStore.getFolder(folderServerId);
        if (!remoteFolder.exists() || !remoteFolder.isFlagSupported(flag)) {
            return;
        }

        try {
            remoteFolder.open(Folder.OPEN_MODE_RW);
            if (remoteFolder.getMode() != Folder.OPEN_MODE_RW) {
                return;
            }
            List<Message> messages = new ArrayList<>();
            for (String uid : messageServerIds) {
                if (!uid.startsWith(BackendFolder.LOCAL_UID_PREFIX)) {
                    messages.add(remoteFolder.getMessage(uid));
                }
            }

            if (messages.isEmpty()) {
                return;
            }
            remoteFolder.setFlags(messages, Collections.singleton(flag), newState);
        } finally {
            remoteFolder.close();
        }
    }
}
