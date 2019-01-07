package com.fsck.k9.backend.pop3


import com.fsck.k9.backend.api.BackendStorage
import com.fsck.k9.backend.api.FolderInfo
import com.fsck.k9.mail.Folder.FolderType
import com.fsck.k9.mail.store.pop3.Pop3Folder


internal class CommandRefreshFolderList(private val backendStorage: BackendStorage) {
    fun refreshFolderList() {
        val folderServerIds = backendStorage.getFolderServerIds()
        if (Pop3Folder.INBOX !in folderServerIds) {
            val inbox = FolderInfo(Pop3Folder.INBOX, Pop3Folder.INBOX, FolderType.INBOX)
            backendStorage.createFolders(listOf(inbox))
        }
    }
}
