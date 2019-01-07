package com.fsck.k9.ui.settings

import android.support.v14.preference.MultiSelectListPreference
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference


inline fun Preference.onClick(crossinline action: () -> Unit) = setOnPreferenceClickListener {
    action()
    true
}

fun Preference.remove() = parent?.removePreference(this)

fun ListPreference.removeEntry(entryValue: String) {
    val deleteIndex = entryValues.indexOf(entryValue)
    entries = entries.filterIndexed { index, _ -> index != deleteIndex }.toTypedArray()
    entryValues = entryValues.filterIndexed { index, _ -> index != deleteIndex }.toTypedArray()
}

fun MultiSelectListPreference.removeEntry(entryValue: String) {
    val deleteIndex = entryValues.indexOf(entryValue)
    entries = entries.filterIndexed { index, _ -> index != deleteIndex }.toTypedArray()
    entryValues = entryValues.filterIndexed { index, _ -> index != deleteIndex }.toTypedArray()
}

inline fun Preference.oneTimeClickListener(clickHandled: Boolean = true, crossinline block: () -> Unit) {
    onPreferenceClickListener = Preference.OnPreferenceClickListener { preference ->
        preference.onPreferenceClickListener = null
        block()
        clickHandled
    }
}
