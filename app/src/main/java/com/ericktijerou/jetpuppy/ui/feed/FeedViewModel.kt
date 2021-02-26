package com.ericktijerou.jetpuppy.ui.feed

import androidx.lifecycle.ViewModel
import com.ericktijerou.jetpuppy.ui.util.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject
constructor(private val preferenceManager: PreferenceManager) :
    ViewModel() {
    fun hasOnboarding() = preferenceManager.hasOnboarding
}