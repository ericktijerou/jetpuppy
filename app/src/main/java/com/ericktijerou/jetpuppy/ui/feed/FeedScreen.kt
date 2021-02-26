package com.ericktijerou.jetpuppy.ui.feed

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun FeedScreen(viewModel: FeedViewModel, onBoarding: () -> Unit = {}) {
    if (viewModel.hasOnboarding()) {
        onBoarding()
        return
    }
    Text("FeedScreen")
}