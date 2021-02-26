package com.ericktijerou.jetpuppy.ui.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceManager @Inject constructor(context: Context) {

    companion object {
        private const val PREF_PACKAGE_NAME = "com.ericktijerou.jetpuppy.preferences"
        private const val PREF_KEY_ONBOARDING = "onboarding"
    }

    private val pref: SharedPreferences =
        context.getSharedPreferences(PREF_PACKAGE_NAME, Context.MODE_PRIVATE)

    var hasOnboarding: Boolean
        get() = pref.getBoolean(PREF_KEY_ONBOARDING, true)
        set(hasOnboarding) = pref.edit().putBoolean(PREF_KEY_ONBOARDING, hasOnboarding).apply()
}