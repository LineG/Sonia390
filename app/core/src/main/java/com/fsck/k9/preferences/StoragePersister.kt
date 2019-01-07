package com.fsck.k9.preferences

import android.support.annotation.CheckResult


interface StoragePersister {
    @CheckResult
    fun loadValues(): Map<String, String>

    fun createStorageEditor(storage: Storage): StorageEditor
}
