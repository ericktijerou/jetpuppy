package com.ericktijerou.jetpuppy.util

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.ericktijerou.jetpuppy.ui.theme.JetpuppyTheme

@Composable
internal fun ThemedPreview(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    JetpuppyTheme(darkTheme = darkTheme) {
        Surface {
            content()
        }
    }
}
