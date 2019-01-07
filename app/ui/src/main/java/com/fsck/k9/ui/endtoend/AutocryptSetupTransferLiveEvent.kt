package com.fsck.k9.ui.endtoend

import android.app.PendingIntent
import com.fsck.k9.Account
import com.fsck.k9.controller.MessagingController
import com.fsck.k9.helper.SingleLiveEvent
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.coroutines.experimental.bg

class AutocryptSetupTransferLiveEvent(
        private val messagingController: MessagingController
) : SingleLiveEvent<AutocryptSetupTransferResult>() {

    fun sendMessageAsync(account: Account, setupMsg: AutocryptSetupMessage) {
        launch(UI) {
            val setupMessage = bg {
                messagingController.sendMessageBlocking(account, setupMsg.setupMessage)
            }

            delay(2000)

            try {
                setupMessage.await()
                value = AutocryptSetupTransferResult.Success(setupMsg.showTransferCodePi)
            } catch (e: Exception) {
                value = AutocryptSetupTransferResult.Failure(e)
            }
        }
    }
}

sealed class AutocryptSetupTransferResult {
    data class Success(val showTransferCodePi: PendingIntent) : AutocryptSetupTransferResult()
    data class Failure(val exception: Exception) : AutocryptSetupTransferResult()
}
