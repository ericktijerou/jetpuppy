package com.ericktijerou.jetpuppy.ui.onboarding

import androidx.lifecycle.ViewModel
import com.ericktijerou.jetpuppy.ui.util.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject
constructor(private val preferenceManager: PreferenceManager) :
    ViewModel() {
    fun setOnboarding() {
        preferenceManager.hasOnboarding = false
    }
}