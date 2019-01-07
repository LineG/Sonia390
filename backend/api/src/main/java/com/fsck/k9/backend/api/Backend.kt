package com.fsck.k9.backend.api


import com.fsck.k9.mail.BodyFactory
import com.fsck.k9.mail.FetchProfile
import com.fsck.k9.mail.Flag
import com.fsck.k9.mail.Folder
import com.fsck.k9.mail.Message
import com.fsck.k9.mail.MessagingException
import com.fsck.k9.mail.Part
import com.fsck.k9.mail.PushReceiver
import com.fsck.k9.mail.Pusher


interface Backend {
    val supportsSeenFlag: Boolean
    val supportsExpunge: Boolean
    val supportsMove: Boolean
    val supportsCopy: Boolean
    val supportsUpload: Boolean
    val supportsTrashFolder: Boolean
    val supportsSearchByDate: Boolean
    val isPushCapable: Boolean

    @Throws(MessagingException::class)
    fun refreshFolderList()

    // TODO: Add a way to cancel the sync process
    fun sync(folder: String, syncConfig: SyncConfig, listener: SyncListener, providedRemoteFolder: Folder<*>?)

    @Throws(MessagingException::class)
    fun downloadMessage(syncConfig: SyncConfig, folderServerId: String, messageServerId: String)

    @Throws(MessagingException::class)
    fun setFlag(folderServerId: String, messageServerIds: List<String>, flag: Flag, newState: Boolean)

    @Throws(MessagingException::class)
    fun markAllAsRead(folderServerId: String)

    @Throws(MessagingException::class)
    fun expunge(folderServerId: String)

    @Throws(MessagingException::class)
    fun expungeMessages(folderServerId: String, messageServerIds: List<String>)

    @Throws(MessagingException::class)
    fun deleteAllMessages(folderServerId: String)

    @Throws(MessagingException::class)
    fun moveMessages(
            sourceFolderServerId: String,
            targetFolderServerId: String,
            messageServerIds: List<String>
    ): Map<String, String>?

    @Throws(MessagingException::class)
    fun copyMessages(
            sourceFolderServerId: String,
            targetFolderServerId: String,
            messageServerIds: List<String>
    ): Map<String, String>?

    @Throws(MessagingException::class)
    fun search(
            folderServerId: String,
            query: String?,
            requiredFlags: Set<Flag>?,
            forbiddenFlags: Set<Flag>?
    ): List<String>

    @Throws(MessagingException::class)
    fun fetchMessage(folderServerId: String, messageServerId: String, fetchProfile: FetchProfile): Message

    @Throws(MessagingException::class)
    fun fetchPart(folderServerId: String, messageServerId: String, part: Part, bodyFactory: BodyFactory)

    @Throws(MessagingException::class)
    fun findByMessageId(folderServerId: String, messageId: String): String?

    @Throws(MessagingException::class)
    fun uploadMessage(folderServerId: String, message: Message): String?

    fun createPusher(receiver: PushReceiver): Pusher

    @Throws(MessagingException::class)
    fun checkIncomingServerSettings()

    @Throws(MessagingException::class)
    fun sendMessage(message: Message)

    @Throws(MessagingException::class)
    fun checkOutgoingServerSettings()
}
