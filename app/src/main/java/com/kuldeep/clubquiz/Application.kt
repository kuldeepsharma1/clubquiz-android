package com.kuldeep.clubquiz

import android.app.Application
import com.kuldeep.clubquiz.util.PreferenceHelper
import com.google.android.material.color.DynamicColors

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        // save context to the preference helper
        PreferenceHelper.setContext(this)

        // apply material you colors
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
