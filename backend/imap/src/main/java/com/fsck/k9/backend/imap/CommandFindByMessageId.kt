package com.fsck.k9.backend.imap


import com.fsck.k9.mail.Folder
import com.fsck.k9.mail.store.imap.ImapStore


internal class CommandFindByMessageId(private val imapStore: ImapStore) {

    fun findByMessageId(folderServerId: String, messageId: String): String? {
        val folder = imapStore.getFolder(folderServerId)
        try {
            folder.open(Folder.OPEN_MODE_RW)
            return folder.getUidFromMessageId(messageId)
        } finally {
            folder.close()
        }
    }
}
