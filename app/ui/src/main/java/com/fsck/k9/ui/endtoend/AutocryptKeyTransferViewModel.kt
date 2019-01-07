package com.fsck.k9.ui.endtoend

import android.arch.lifecycle.ViewModel

internal class AutocryptKeyTransferViewModel(
        val autocryptSetupMessageLiveEvent: AutocryptSetupMessageLiveEvent,
        val autocryptSetupTransferLiveEvent: AutocryptSetupTransferLiveEvent) : ViewModel()
