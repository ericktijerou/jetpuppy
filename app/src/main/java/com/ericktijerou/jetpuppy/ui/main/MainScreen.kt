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
package com.ericktijerou.jetpuppy.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import androidx.navigation.compose.rememberNavController
import com.ericktijerou.jetpuppy.R
import com.ericktijerou.jetpuppy.ui.chat.ChatScreen
import com.ericktijerou.jetpuppy.ui.favorite.FavoriteScreen
import com.ericktijerou.jetpuppy.ui.home.HomeScreen
import com.ericktijerou.jetpuppy.ui.profile.ProfileScreen
import com.ericktijerou.jetpuppy.ui.theme.JetpuppyTheme
import com.ericktijerou.jetpuppy.util.EMPTY
import com.ericktijerou.jetpuppy.util.Screen
import com.ericktijerou.jetpuppy.util.hiltNavGraphViewModel

@Composable
fun MainScreen(viewModel: MainViewModel, navigateTo: (String, Boolean) -> Unit) {
    if (viewModel.hasOnboarding()) {
        navigateTo(Screen.Onboarding.route, true)
        return
    }
    val navController = rememberNavController()
    val sections =
        listOf(MainSection.Home, MainSection.Favorite, MainSection.Chat, MainSection.Profile)
    Scaffold(
        topBar = { MainAppBar() },
        bottomBar = { MainBottomNavigation(navController = navController, sections = sections) }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        NavHost(navController = navController, startDestination = MainSection.Home.route) {
            composable(MainSection.Home.route) {
                HomeScreen(
                    it.hiltNavGraphViewModel(),
                    modifier
                ) { route -> navigateTo(route, false) }
            }
            composable(MainSection.Favorite.route) { FavoriteScreen(modifier) }
            composable(MainSection.Chat.route) { ChatScreen(modifier) }
            composable(MainSection.Profile.route) { ProfileScreen(modifier) }
        }
    }
}

@Composable
fun MainAppBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary
) {
    TopAppBar(
        title = {
            var textState by remember { mutableStateOf(TextFieldValue()) }
            Row {
                IconButton(
                    onClick = { /* Empty */ }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(R.string.label_menu)
                    )
                }
                UserInputText(
                    textFieldValue = textState,
                    onTextChanged = { textState = it }
                )
            }
        },
        backgroundColor = backgroundColor,
        modifier = modifier
    )
}

@Composable
fun MainBottomNavigation(
    sections: List<MainSection>,
    navController: NavHostController
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        sections.forEach { section ->
            BottomNavigationItem(
                icon = { Icon(imageVector = section.icon, contentDescription = "") },
                label = { Text(stringResource(section.label)) },
                alwaysShowLabel = false,
                selected = currentRoute == section.route,
                onClick = {
                    navController.navigate(route = section.route) {
                        popUpTo(currentRoute.orEmpty()) { inclusive = true }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun UserInputText(
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextChanged: (TextFieldValue) -> Unit,
    textFieldValue: TextFieldValue,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
            .height(48.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .background(JetpuppyTheme.colors.searchBoxColor)
                    .padding(start = 8.dp, end = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                val (icon, textField, text, close) = createRefs()
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .constrainAs(icon) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = { onTextChanged(it) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = ImeAction.Send
                    ),
                    maxLines = 1,
                    cursorBrush = SolidColor(LocalContentColor.current),
                    textStyle = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(textField) {
                            start.linkTo(icon.end, margin = 8.dp)
                            end.linkTo(close.start)
                            linkTo(top = parent.top, bottom = parent.bottom)
                            width = Dimension.fillToConstraints
                        }
                )

                val disableContentColor =
                    MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                if (textFieldValue.text.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.hint_search),
                        style = MaterialTheme.typography.body1.copy(color = disableContentColor),
                        modifier = Modifier.constrainAs(text) {
                            start.linkTo(textField.start)
                            linkTo(top = parent.top, bottom = parent.bottom)
                        },
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = EMPTY,
                        Modifier
                            .size(20.dp)
                            .clickable { onTextChanged(TextFieldValue(EMPTY))}
                            .constrainAs(close) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                }
            }
        }
    }
}

sealed class MainSection(val route: String, @StringRes val label: Int, val icon: ImageVector) {
    object Home : MainSection("home", R.string.label_home, Icons.Outlined.Home)
    object Profile : MainSection("profile", R.string.label_profile, Icons.Outlined.AccountCircle)
    object Favorite :
        MainSection("favorite", R.string.label_favorite, Icons.Outlined.FavoriteBorder)

    object Chat : MainSection("chat", R.string.label_chat, Icons.Outlined.Chat)
}
