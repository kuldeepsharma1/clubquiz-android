package com.kuldeep.clubquiz.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.Preference
import com.kuldeep.clubquiz.R
import com.kuldeep.clubquiz.extensions.PrefFragment
import com.kuldeep.clubquiz.util.ApiInstance
import com.kuldeep.clubquiz.util.BackupHelper
import com.kuldeep.clubquiz.util.PreferenceHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingsFragment : PrefFragment() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val api = findPreference<ListPreference>(context?.getString(R.string.api_key)!!)
        api?.setOnPreferenceChangeListener { _, newValue ->
            ApiInstance.updateApiHelper(
                PreferenceHelper.getApi()
            )
            true
        }

        val resetStats = findPreference<Preference>(context?.getString(R.string.reset_stats_key)!!)
        resetStats?.setOnPreferenceClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.reset_stats)
                .setMessage(R.string.irreversible)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    PreferenceHelper.resetTotalStats()
                }
                .show()
            true
        }

        val clearLibrary =
            findPreference<Preference>(context?.getString(R.string.clear_library_key)!!)
        clearLibrary?.setOnPreferenceClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.clear_library)
                .setMessage(R.string.irreversible)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    PreferenceHelper.deleteAllQuizzes()
                }
                .show()
            true
        }

        val backupHelper = BackupHelper(activity as AppCompatActivity)

        val backup = findPreference<Preference>(context?.getString(R.string.backup_key)!!)
        backup?.setOnPreferenceClickListener {
            backupHelper.backupQuizzes()
            true
        }

        val restore = findPreference<Preference>(context?.getString(R.string.restore_key)!!)
        restore?.setOnPreferenceClickListener {
            backupHelper.restoreQuizzes()
            true
        }
    }
}
