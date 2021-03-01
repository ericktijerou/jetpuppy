/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.jetpuppy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.ericktijerou.jetpuppy.util.LocalSysUiController

private val DarkColorPalette = darkColors(
    primary = BlackLight,
    primaryVariant = BlackLight,
    secondary = Teal200,
    background = BackgroundDark,
    surface = Color.Black,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = Color.White,
    secondary = Teal200,
    background = BackgroundLight,
    surface = Color.White,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val LightPuppyColorPalette = JetpuppyColors(
    textPrimaryColor = Color.Black,
    textSecondaryColor = TextSecondaryLight,
    searchBoxColor = GraySearchBoxLight,
    isDark = false
)

private val DarkPuppyColorPalette = JetpuppyColors(
    textPrimaryColor = Color.White,
    textSecondaryColor = TextSecondaryDark,
    searchBoxColor = GraySearchBoxDark,
    isDark = true
)

@Composable
fun JetpuppyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val (colors, customColors) = if (darkTheme) DarkColorPalette to DarkPuppyColorPalette else LightColorPalette to LightPuppyColorPalette
    val sysUiController = LocalSysUiController.current
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = colors.primary
        )
    }
    ProvideJetpuppyColors(customColors) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = Shapes,
            content = content
        )
    }
}

object JetpuppyTheme {
    val colors: JetpuppyColors
        @Composable
        get() = LocalJetpuppyColors.current
}

@Composable
fun ProvideJetpuppyColors(
    colors: JetpuppyColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalJetpuppyColors provides colorPalette, content = content)
}

@Stable
class JetpuppyColors(
    textPrimaryColor: Color,
    textSecondaryColor: Color,
    searchBoxColor: Color,
    isDark: Boolean
) {
    var textPrimaryColor by mutableStateOf(textPrimaryColor)
        private set
    var textSecondaryColor by mutableStateOf(textSecondaryColor)
        private set
    var searchBoxColor by mutableStateOf(searchBoxColor)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: JetpuppyColors) {
        textPrimaryColor = other.textPrimaryColor
        textSecondaryColor = other.textSecondaryColor
        searchBoxColor = other.searchBoxColor
        isDark = other.isDark
    }
}

private val LocalJetpuppyColors = staticCompositionLocalOf<JetpuppyColors> {
    error("No JetsnackColorPalette provided")
}
