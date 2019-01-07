package com.fsck.k9.backends

import com.fsck.k9.Account
import com.fsck.k9.backend.BackendFactory
import com.fsck.k9.backend.api.Backend
import com.fsck.k9.backend.pop3.Pop3Backend
import com.fsck.k9.backend.pop3.Pop3StoreUriCreator
import com.fsck.k9.backend.pop3.Pop3StoreUriDecoder
import com.fsck.k9.mail.ServerSettings
import com.fsck.k9.mail.oauth.OAuth2TokenProvider
import com.fsck.k9.mail.ssl.TrustedSocketFactory
import com.fsck.k9.mail.store.pop3.Pop3Store
import com.fsck.k9.mail.transport.smtp.SmtpTransport
import com.fsck.k9.mail.transport.smtp.SmtpTransportUriCreator
import com.fsck.k9.mail.transport.smtp.SmtpTransportUriDecoder
import com.fsck.k9.mailstore.K9BackendStorageFactory

class Pop3BackendFactory(
        private val backendStorageFactory: K9BackendStorageFactory,
        private val trustedSocketFactory: TrustedSocketFactory
) : BackendFactory {
    override val transportUriPrefix = "smtp"

    override fun createBackend(account: Account): Backend {
        val accountName = account.displayName
        val backendStorage = backendStorageFactory.createBackendStorage(account)
        val pop3Store = createPop3Store(account)
        val smtpTransport = createSmtpTransport(account)
        return Pop3Backend(accountName, backendStorage, pop3Store, smtpTransport)
    }

    private fun createPop3Store(account: Account): Pop3Store {
        val serverSettings = decodeStoreUri(account.storeUri)
        return Pop3Store(serverSettings, account, trustedSocketFactory)
    }

    private fun createSmtpTransport(account: Account): SmtpTransport {
        val serverSettings = decodeTransportUri(account.transportUri)
        val oauth2TokenProvider: OAuth2TokenProvider? = null
        return SmtpTransport(serverSettings, trustedSocketFactory, oauth2TokenProvider)
    }

    override fun decodeStoreUri(storeUri: String): ServerSettings {
        return Pop3StoreUriDecoder.decode(storeUri)
    }

    override fun createStoreUri(serverSettings: ServerSettings): String {
        return Pop3StoreUriCreator.create(serverSettings)
    }

    override fun decodeTransportUri(transportUri: String): ServerSettings {
        return SmtpTransportUriDecoder.decodeSmtpUri(transportUri)
    }

    override fun createTransportUri(serverSettings: ServerSettings): String {
        return SmtpTransportUriCreator.createSmtpUri(serverSettings)
    }
}
