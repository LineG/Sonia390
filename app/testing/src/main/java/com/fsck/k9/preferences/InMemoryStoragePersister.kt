package com.fsck.k9.preferences

import android.content.SharedPreferences

class InMemoryStoragePersister : StoragePersister {
    private val values = mutableMapOf<String, Any?>()


    override fun loadValues(): Map<String, String> {
        return values.mapValues { (_, value) -> value?.toString() ?: "" }
    }

    override fun createStorageEditor(storage: Storage): StorageEditor = InMemoryStorageEditor(storage)


    private inner class InMemoryStorageEditor(private val storage: Storage) : StorageEditor {
        private val snapshot = storage.all.toMutableMap()
        private val removals = mutableSetOf<String>()
        private val changes = mutableMapOf<String, String>()
        private var alreadyCommitted = false


        override fun copy(input: SharedPreferences) = Unit

        override fun putBoolean(key: String, value: Boolean) = apply {
            changes[key] = value.toString()
            removals.remove(key)
        }

        override fun putInt(key: String, value: Int) = apply {
            changes[key] = value.toString()
            removals.remove(key)
        }

        override fun putLong(key: String, value: Long) = apply {
            changes[key] = value.toString()
            removals.remove(key)
        }

        override fun putString(key: String, value: String?) = apply {
            if (value == null) {
                remove(key)
            } else {
                changes[key] = value
                removals.remove(key)
            }
        }

        override fun remove(key: String) = apply {
            removals.add(key)
            changes.remove(key)
        }

        override fun commit(): Boolean {
            if (alreadyCommitted) throw AssertionError("StorageEditor.commit() called more than once")
            alreadyCommitted = true

            removals.forEach { snapshot.remove(it) }
            snapshot.putAll(changes)

            storage.replaceAll(snapshot)

            return true
        }
    }
}
