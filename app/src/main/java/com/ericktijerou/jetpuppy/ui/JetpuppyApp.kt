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
package com.ericktijerou.jetpuppy.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import androidx.navigation.compose.rememberNavController
import com.ericktijerou.jetpuppy.ui.dog.DogScreen
import com.ericktijerou.jetpuppy.ui.main.MainScreen
import com.ericktijerou.jetpuppy.ui.onboarding.OnboardingPage
import com.ericktijerou.jetpuppy.ui.onboarding.OnboardingScreen
import com.ericktijerou.jetpuppy.ui.theme.JetpuppyTheme
import com.ericktijerou.jetpuppy.util.Screen
import com.ericktijerou.jetpuppy.util.hiltNavGraphViewModel
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@Composable
fun JetpuppyApp() {
    ProvideWindowInsets {
        JetpuppyTheme {
            val navController = rememberNavController()
            NavHost(navController, startDestination = Screen.Main.route) {
                composable(Screen.Onboarding.route) {
                    OnboardingScreen(
                        items = listOf(
                            OnboardingPage.Page1,
                            OnboardingPage.Page2,
                            OnboardingPage.Page3
                        ),
                        viewModel = it.hiltNavGraphViewModel()
                    ) {
                        navController.navigate(route = Screen.Main.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                }
                composable(Screen.Main.route) {
                    MainScreen(viewModel = it.hiltNavGraphViewModel()) { route, clearBackStack ->
                        navController.navigate(route = route) {
                            if (clearBackStack) popUpTo(Screen.Main.route) { inclusive = true }
                        }
                    }
                }
                composable(Screen.Dog.route) {
                    val dogId = it.arguments?.getString(Screen.Dog.ARG_DOG_ID).orEmpty()
                    DogScreen(
                        viewModel = it.hiltNavGraphViewModel(),
                        dogId = dogId
                    )
                }
            }
        }
    }
}
