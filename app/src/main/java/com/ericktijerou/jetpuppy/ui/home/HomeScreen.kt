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
package com.ericktijerou.jetpuppy.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ericktijerou.jetpuppy.R
import com.ericktijerou.jetpuppy.ui.theme.JetpuppyTheme
import com.ericktijerou.jetpuppy.ui.util.JetpuppyHelper

@Composable
fun HomeScreen(viewModel: HomeViewModel, onBoarding: () -> Unit = {}) {
    if (viewModel.hasOnboarding()) {
        onBoarding()
        return
    }
    Scaffold(
        topBar = { HomeAppBar() }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        LazyColumn(modifier = modifier) {
            item {
                DogList(
                    title = R.string.label_recommended,
                    caption = R.string.caption_recommended,
                    dogs = JetpuppyHelper.generatePuppyDataSet(),
                    modifier = Modifier.padding(top = 16.dp),
                    navigateTo = { /*TODO*/ }
                )
            }
            item {
                ShelterList(
                    title = R.string.label_shelter,
                    caption = R.string.caption_shelter,
                    shelters = JetpuppyHelper.generateShelterDataSet()
                )
            }
            item {
                DogList(
                    title = R.string.label_near_you,
                    caption = R.string.caption_near_you,
                    dogs = JetpuppyHelper.generatePuppyDataSet(),
                    modifier = Modifier.padding(top = 16.dp),
                    navigateTo = { /*TODO*/ }
                )
            }
            item {
                DogList(
                    title = R.string.label_senior,
                    caption = R.string.caption_senior,
                    dogs = JetpuppyHelper.generateSeniorDataSet(),
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                    navigateTo = { /*TODO*/ }
                )
            }
        }
    }
}

@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary
) {
    TopAppBar(
        title = {
            var textState by remember { mutableStateOf(TextFieldValue()) }
            Row {
                IconButton(
                    onClick = { /* TODO: Open search */ }
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
                val (icon, textField, text) = createRefs()
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
                            linkTo(top = parent.top, bottom = parent.bottom)
                        },
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
                }
            }
        }
    }
}

val KeyboardShownKey = SemanticsPropertyKey<Boolean>("KeyboardShownKey")
var SemanticsPropertyReceiver.keyboardShownProperty by KeyboardShownKey
