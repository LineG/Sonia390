package com.fsck.k9.ui.settings.general

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceFragmentCompat.OnPreferenceStartScreenCallback
import android.support.v7.preference.PreferenceScreen
import android.view.Menu
import android.view.MenuItem
import com.bytehamster.lib.preferencesearch.SearchPreferenceActionView
import com.bytehamster.lib.preferencesearch.SearchPreferenceResult
import com.bytehamster.lib.preferencesearch.SearchPreferenceResultListener
import com.fsck.k9.activity.K9Activity
import com.fsck.k9.activity.setup.FontSizeSettings
import com.fsck.k9.ui.R
import com.fsck.k9.ui.fragmentTransaction
import com.fsck.k9.ui.fragmentTransactionWithBackStack
import com.fsck.k9.ui.resolveAttribute

class GeneralSettingsActivity : K9Activity(), OnPreferenceStartScreenCallback, SearchPreferenceResultListener {
    private lateinit var searchPreferenceActionView: SearchPreferenceActionView
    private lateinit var searchPreferenceMenuItem: MenuItem
    private lateinit var searchQuery: String
    private var searchEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayout(R.layout.general_settings)

        initializeActionBar()

        if (savedInstanceState == null) {
            fragmentTransaction {
                add(R.id.generalSettingsContainer, GeneralSettingsFragment.create())
            }
        } else {
            searchQuery = savedInstanceState.getString(KEY_SEARCH_QUERY)
            searchEnabled = savedInstanceState.getBoolean(KEY_SEARCH_ENABLED)
        }
    }

    private fun initializeActionBar() {
        val actionBar = supportActionBar ?: throw RuntimeException("getSupportActionBar() == null")
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_SEARCH_QUERY, searchPreferenceActionView.query.toString())
        outState.putBoolean(KEY_SEARCH_ENABLED, !searchPreferenceActionView.isIconified)
        searchPreferenceActionView.onBackPressed()
        super.onSaveInstanceState(outState)
    }

    override fun onSearchResultClicked(result: SearchPreferenceResult) {
        searchPreferenceActionView.close()
        searchPreferenceMenuItem.collapseActionView()

        if (result.resourceFile == R.xml.font_preferences) {
            startActivity(Intent(this, FontSizeSettings::class.java))
        } else {
            val fragment = GeneralSettingsFragment.create(result.screen)
            fragmentTransaction {
                addToBackStack("Search result")
                replace(R.id.generalSettingsContainer, fragment)
            }

            val accentColor = theme.resolveAttribute(R.attr.colorAccent)
            result.highlight(fragment as PreferenceFragmentCompat, accentColor)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.general_settings_option, menu)

        searchPreferenceMenuItem = menu.findItem(R.id.search)
        searchPreferenceActionView = searchPreferenceMenuItem.actionView as SearchPreferenceActionView
        searchPreferenceActionView.setActivity(this)

        with(searchPreferenceActionView.searchConfiguration) {
            setFragmentContainerViewId(R.id.generalSettingsContainer)
            setBreadcrumbsEnabled(true)
            setFuzzySearchEnabled(true)

            with(index()) {
                addFile(R.xml.general_settings)
                addBreadcrumb(R.string.general_settings_title)
                addBreadcrumb(R.string.display_preferences)
                addBreadcrumb(R.string.global_preferences)
                addBreadcrumb(R.string.font_size_settings_title)
                addFile(R.xml.font_preferences)
            }
        }

        searchPreferenceMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                searchPreferenceActionView.onBackPressed()
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }
        })

        if (searchEnabled) {
            Handler().post {
                // If we do not use a handler here, it will not be possible
                // to use the menuItem after dismissing the searchView
                searchPreferenceMenuItem.expandActionView()
                searchPreferenceActionView.setQuery(searchQuery, false)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!searchPreferenceActionView.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onPreferenceStartScreen(
            caller: PreferenceFragmentCompat, preferenceScreen: PreferenceScreen
    ): Boolean {
        fragmentTransactionWithBackStack {
            replace(R.id.generalSettingsContainer, GeneralSettingsFragment.create(preferenceScreen.key))
        }

        return true
    }


    companion object {
        private const val KEY_SEARCH_QUERY = "search_query"
        private const val KEY_SEARCH_ENABLED = "search_enabled"
        fun start(context: Context) {
            val intent = Intent(context, GeneralSettingsActivity::class.java)
            context.startActivity(intent)
        }
    }
}
