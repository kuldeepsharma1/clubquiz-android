package com.kuldeep.clubquiz.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kuldeep.clubquiz.R
import com.kuldeep.clubquiz.databinding.ActivitySettingsBinding
import com.kuldeep.clubquiz.fragments.SettingsFragment
import com.kuldeep.clubquiz.util.ThemeHelper

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeHelper.setThemeMode(this)
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)

        binding.backImageButton.setOnClickListener {
            onBackPressed()
        }

        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
    }
}
